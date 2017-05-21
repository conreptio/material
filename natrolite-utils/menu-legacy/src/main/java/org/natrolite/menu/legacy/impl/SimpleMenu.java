/*
 * Natrolite - Minecraft Minigame Manager
 *
 * Copyright (c) 2017 XNITY <info@xnity.net>
 * Copyright (c) 2017 Natrolite <info@natrolite.org>
 * Copyright (c) 2017 Lukas Nehrke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.menu.legacy.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.natrolite.menu.legacy.Menu;
import org.natrolite.menu.legacy.MenuManager;
import org.natrolite.menu.legacy.item.Icon;

@NotThreadSafe
public class SimpleMenu implements Menu {

  private final InventoryType type;
  private final int size;
  private final Icon[] icons;
  private final Object iconLock = new Object();
  private final Map<UUID, Inventory> viewers = new ConcurrentHashMap<>();

  @Nullable
  private String title;

  /**
   * Creates a NON-CHEST menu.
   *
   * @param type the inventory type
   */
  public SimpleMenu(InventoryType type) {
    this.type = type;
    size = type.getDefaultSize();
    icons = new Icon[size];
  }

  /**
   * Creates a CHEST menu.
   *
   * @param type  the inventory type
   * @param size  the inventory size
   * @param title the inventory title
   */
  public SimpleMenu(InventoryType type, int size, String title) {
    this.type = type;
    this.size = size;
    this.title = title;
    icons = new Icon[size];
  }

  @Override
  public InventoryType getType() {
    return type;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  @Nullable
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(@Nullable String title) {
    this.title = title;
  }

  @Override
  public Icon[] getIcons() {
    synchronized (iconLock) {
      return icons;
    }
  }

  @Override
  public Icon getIcon(int slot) throws IllegalArgumentException {
    if (slot < 0 || slot > size) {
      throw new IllegalArgumentException("Invalid slot in menu: " + slot);
    }
    synchronized (iconLock) {
      return icons[slot];
    }
  }

  @Override
  public void setIcon(int slot, @Nullable Icon icon) throws IllegalArgumentException {
    if (slot < 0 || slot > size) {
      throw new IllegalArgumentException("Invalid slot in menu: " + slot);
    }
    synchronized (iconLock) {
      icons[slot] = icon;
    }
  }

  @Override
  public void removeIcon(int slot) {
    setIcon(slot, null);
  }

  @Override
  public void handleOpen(Player player) {
    final Inventory inventory = toInventory();
    viewers.put(player.getUniqueId(), inventory);
    player.openInventory(inventory);
  }

  @Override
  public void handleClose(Player player) {
    viewers.remove(player.getUniqueId());
  }

  @Override
  public void update() {
    for (UUID uuid : viewers.keySet()) {
      final Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        update(player);
      }
    }
  }

  @Override
  public void update(Player player) {
    final Inventory inventory = viewers.get(player.getUniqueId());
    if (inventory != null) {
      synchronized (iconLock) {
        final int size = Math.min(icons.length, inventory.getSize());
        for (int i = 0; i < size; i++) {
          final Icon item = icons[i];
          if (item != null) {
            inventory.setItem(i, item.toItemStack());
          }
        }
      }
    }
  }

  @Override
  public Inventory toInventory() {
    final Inventory inventory = type == InventoryType.CHEST
        ? Bukkit.createInventory(null, size, title) : Bukkit.createInventory(null, type, title);
    synchronized (iconLock) {
      final int size = Math.min(icons.length, inventory.getSize());
      for (int i = 0; i < size; i++) {
        final Icon item = icons[i];
        if (item != null) {
          inventory.setItem(i, item.toItemStack());
        }
      }
      return inventory;
    }
  }

  @Override
  public void onClick(int slot, MenuManager menuManager, Player player, InventoryClickEvent event) {
    if (slot < 0 || slot > size) {
      return;
    }
    synchronized (iconLock) {
      final Icon item = getIcon(slot);
      if (item != null) {
        item.onClick(menuManager, player, event);
      }
    }
  }
}

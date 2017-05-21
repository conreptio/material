/*
 * Natrolite Minigames
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

import com.google.common.base.Preconditions;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.concurrent.NotThreadSafe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.natrolite.menu.legacy.Menu;
import org.natrolite.menu.legacy.MenuManager;
import org.natrolite.menu.legacy.animation.AnimatedMenu;
import org.natrolite.menu.legacy.item.Icon;

@NotThreadSafe
public class SimpleMenuManager implements MenuManager, Listener, Runnable {

  private final JavaPlugin plugin;
  private final ConcurrentMap<UUID, Menu> menus = new ConcurrentHashMap<>();

  public SimpleMenuManager(JavaPlugin plugin) {
    this.plugin = plugin;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public void run() {
    for (Menu menu : menus.values()) {
      if (menu instanceof AnimatedMenu) {
        ((AnimatedMenu) menu).tick();
      }
    }
  }

  /**
   * Closes all menus.
   */
  public void handleShutdown() {
    for (UUID uuid : menus.keySet()) {
      final Player player = plugin.getServer().getPlayer(uuid);
      if (player != null) {
        player.closeInventory();
      }
    }
  }

  @Override
  public void openMenu(final Player player, final Menu menu) {
    Preconditions.checkNotNull(player);
    Preconditions.checkNotNull(menu);
    new BukkitRunnable() {
      @Override
      public void run() {
        player.closeInventory();
        menus.put(player.getUniqueId(), menu);
        menu.handleOpen(player);
      }
    }.runTask(plugin);
  }

  @Override
  public Menu getMenu(Player player) {
    return menus.get(player.getUniqueId());
  }

  @Override
  public boolean hasMenu(Player player) {
    return menus.containsKey(player.getUniqueId());
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (event.getPlayer() instanceof Player) {
      final Menu menu = menus.remove(event.getPlayer().getUniqueId());
      if (menu != null) {
        menu.handleClose((Player) event.getPlayer());
      }
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onInventoryClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player)) {
      event.setCancelled(true);
      return;
    }
    final Menu menu = getMenu((Player) event.getWhoClicked());
    if (menu != null) {
      event.setCancelled(true);
      new BukkitRunnable() {
        @Override
        public void run() {
          if (event.getRawSlot() >= 0 && event.getRawSlot() <= menu.getSize()) {
            Icon icon = menu.getIcon(event.getRawSlot());
            if (icon != null) {
              icon.onClick(SimpleMenuManager.this, (Player) event.getWhoClicked(), event);
            }
          }
        }
      }.runTaskAsynchronously(plugin);
    }
  }
}

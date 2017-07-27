/*
 * This file is part of Natrolite.
 *
 * Copyright (C) 2016-2017 Lukas Nehrke
 *
 * Natrolite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Natrolite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Natrolite. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.impl.menu;

import com.google.common.collect.MapMaker;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.natrolite.annotations.Experimental;
import org.natrolite.menu.Menu;
import org.natrolite.menu.MenuContext;
import org.natrolite.menu.MenuManager;

@Experimental
public final class NatroMenuManager implements MenuManager, Listener {

  private final Map<UUID, NatroMenuContext> map = new MapMaker().concurrencyLevel(4).makeMap();

  @Override
  public void openMenu(Player player, Menu menu, int page) {
    final Optional<MenuContext> context = getContext(player);
    final UUID uuid = player.getUniqueId();

    if (context.isPresent()) {
      final NatroMenuContext con = (NatroMenuContext) context.get();
      if (con.getMenu().equals(menu) && con.getCurrentPage() == page) {
        // The player is already viewing this inventory
        con.refresh();
        map.put(player.getUniqueId(), con);
        return;
      }
    }

    player.closeInventory(); // This should remove the player from the map

    final NatroMenuContext con = new NatroMenuContext(uuid, menu, page);
    map.put(player.getUniqueId(), con);

    con.open(player);
  }

  @Override
  public Optional<MenuContext> getContext(Player player) {
    return Optional.ofNullable(map.get(player.getUniqueId()));
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onClick(InventoryClickEvent event) {
    if (event.getWhoClicked() instanceof Player) {
      final Player player = (Player) event.getWhoClicked();
      if (event.isAsynchronous()) {
        event.setCancelled(true);
        return;
      }
      Optional<MenuContext> context = getContext(player);
      if (context.isPresent()) {
        event.setCancelled(true);
        context.get().getPage().ifPresent(page -> page.getIcon(event.getRawSlot())
            .ifPresent(icon -> icon.click().accept(new NatroMenuArguments(this, event))));
      }
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onDrop(PlayerDropItemEvent event) {
    if (event.isAsynchronous() || hasMenu(event.getPlayer())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onClose(InventoryCloseEvent event) {
    map.remove(event.getPlayer().getUniqueId());
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onQuit(PlayerQuitEvent event) {
    map.remove(event.getPlayer().getUniqueId());
  }
}

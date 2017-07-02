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

package org.natrolite.menu.legacy;

import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.natrolite.menu.legacy.item.Icon;

@Deprecated
public interface Menu {

  InventoryType getType();

  int getSize();

  @Nullable
  String getTitle();

  void setTitle(@Nullable String title);

  Icon[] getIcons();

  @Nullable
  Icon getIcon(int slot) throws IllegalArgumentException;

  void setIcon(int slot, @Nullable Icon icon) throws IllegalArgumentException;

  void removeIcon(int slot);

  void handleOpen(Player player);

  void handleClose(Player player);

  void update();

  void update(Player player);

  Inventory toInventory();

  void onClick(int slot, MenuManager menuManager, Player player, InventoryClickEvent event);
}

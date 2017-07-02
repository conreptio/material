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

package org.natrolite.menu.legacy.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.natrolite.menu.legacy.MenuManager;

@Deprecated
public class DisplayIcon extends Icon {

  public DisplayIcon(Material material) {
    super(material);
  }

  public DisplayIcon(Material material, int amount) {
    super(material, amount);
  }

  public DisplayIcon(Material material, int amount, byte durability) {
    super(material, amount, durability);
  }

  public DisplayIcon(Material material, String name, String... lore) {
    super(material, name, lore);
  }

  public DisplayIcon(Material material, int amount, byte durability, String title, String... lore) {
    super(material, amount, durability, title, lore);
  }

  @Override
  public void onClick(MenuManager menuManager, Player player, InventoryClickEvent event) {}
}

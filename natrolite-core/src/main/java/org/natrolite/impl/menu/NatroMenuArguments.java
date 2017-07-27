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

import org.bukkit.event.inventory.InventoryClickEvent;
import org.natrolite.menu.MenuArguments;
import org.natrolite.menu.MenuManager;

class NatroMenuArguments implements MenuArguments {

  private final MenuManager menuManager;
  private final InventoryClickEvent event;

  NatroMenuArguments(MenuManager menuManager, InventoryClickEvent event) {
    this.menuManager = menuManager;
    this.event = event;
  }

  @Override
  public InventoryClickEvent event() {
    return event;
  }

  @Override
  public MenuManager manager() {
    return menuManager;
  }
}

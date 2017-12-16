/*
 * This file is part of Material.
 *
 * Copyright (c) 2016-2017 Neolumia
 *
 * Material is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Material is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Material. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.event.menu;

import static com.google.common.base.Preconditions.checkNotNull;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.natrolite.menu.Menu;

public class MenuEvent extends Event {

  private static final HandlerList handlers = new HandlerList();
  private final Menu menu;

  public MenuEvent(Menu menu) {
    this.menu = checkNotNull(menu, "Menu cannot be null");
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  public Menu getMenu() {
    return menu;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}

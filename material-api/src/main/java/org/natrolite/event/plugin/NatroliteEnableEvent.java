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

package org.natrolite.event.plugin;

import org.bukkit.event.HandlerList;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.natrolite.cause.Cause;
import org.natrolite.event.NatroliteEvent;

public class NatroliteEnableEvent extends PluginEnableEvent implements NatroliteEvent {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Cause cause;

  public NatroliteEnableEvent(Cause cause, Plugin plugin) {
    super(plugin);
    this.cause = cause;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public Cause getCause() {
    return cause;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}

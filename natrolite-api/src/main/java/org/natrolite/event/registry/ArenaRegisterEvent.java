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

package org.natrolite.event.registry;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.cause.Cause;
import org.natrolite.event.NatroliteEvent;

public class ArenaRegisterEvent extends Event implements NatroliteEvent, Cancellable {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Cause cause;
  private final String id;
  private final Class<? extends Arena> arenaClass;
  private final ArenaFactory<? extends Arena> factory;
  private boolean cancelled;

  public ArenaRegisterEvent(Cause cause, String id, Class<? extends Arena> clazz,
      ArenaFactory<? extends Arena> factory) {
    this.cause = cause;
    this.id = id;
    this.arenaClass = clazz;
    this.factory = factory;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  public String getId() {
    return id;
  }

  public Class<? extends Arena> getArenaClass() {
    return arenaClass;
  }

  public ArenaFactory<? extends Arena> getArenaFactory() {
    return factory;
  }

  @Override
  public Cause getCause() {
    return cause;
  }

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}

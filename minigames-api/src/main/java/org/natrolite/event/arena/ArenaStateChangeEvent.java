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

package org.natrolite.event.arena;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.natrolite.arena.Arena;
import org.natrolite.cause.Cause;
import org.natrolite.event.NatroliteEvent;
import org.natrolite.game.state.GameState;

public class ArenaStateChangeEvent extends Event implements NatroliteEvent, Cancellable {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Cause cause;
  private Arena arena;
  private GameState from;
  private GameState to;
  private boolean cancelled;

  public ArenaStateChangeEvent(Cause cause, Arena arena, GameState from, GameState to) {
    this.cause = checkNotNull(cause);
    this.arena = checkNotNull(arena);
    this.from = checkNotNull(from);
    this.to = checkNotNull(to);
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  public Arena getArena() {
    return arena;
  }

  public GameState getFrom() {
    return from;
  }

  public GameState getTo() {
    return to;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
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
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    ArenaStateChangeEvent that = (ArenaStateChangeEvent) other;
    return cancelled == that.cancelled
        && Objects.equals(cause, that.cause)
        && Objects.equals(arena, that.arena)
        && Objects.equals(from, that.from)
        && Objects.equals(to, that.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cause, arena, from, to, cancelled);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("cause", cause)
        .add("arena", arena)
        .add("from", from)
        .add("to", to)
        .add("cancelled", cancelled)
        .toString();
  }
}

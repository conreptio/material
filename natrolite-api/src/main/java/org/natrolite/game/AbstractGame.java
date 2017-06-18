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

package org.natrolite.game;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.natrolite.Natrolite.callEvent;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.natrolite.arena.Arena;
import org.natrolite.cause.Cause;
import org.natrolite.event.game.GameStateChangeEvent;
import org.natrolite.event.game.GameStateChangedEvent;
import org.natrolite.game.state.GameState;
import org.natrolite.game.state.GameStates;
import org.natrolite.plugin.GamePlugin;

public abstract class AbstractGame implements Game, Listener {

  private final UUID gameId = UUID.randomUUID();
  private final GamePlugin plugin;
  private final Arena arena;

  private GameState state = GameStates.LOADING;
  private long stateTime = System.currentTimeMillis();

  public AbstractGame(GamePlugin plugin, Arena arena) {
    this.plugin = checkNotNull(plugin);
    this.arena = checkNotNull(arena);
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public Arena getArena() {
    return arena;
  }

  @Override
  public UUID getUniqueId() {
    return gameId;
  }

  @Override
  public GameState getState() {
    return state;
  }

  @Override
  public void setState(GameState state, Cause cause) {
    checkNotNull(state, "State cannot be null");
    checkNotNull(cause, "Cause cannot be null");
    if (!callEvent(new GameStateChangeEvent(cause, this, this.state, state)).isCancelled()) {
      callEvent(new GameStateChangedEvent(cause, this, this.state, state));
      this.state = state;
    }
  }

  @Override
  public long getStateTime() {
    return stateTime;
  }

  @Override
  public final GamePlugin getPlugin() {
    return plugin;
  }

  public final String getName() {
    return plugin.getName();
  }

  protected void filter(Player player, Runnable runnable) {
    if (getArena().isPlaying(player)) {
      runnable.run();
    }
  }
}

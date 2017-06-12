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

import java.util.UUID;
import org.bukkit.event.Listener;
import org.natrolite.plugin.GamePlugin;

public abstract class AbstractGame implements Game, Listener {

  private final UUID gameId = UUID.randomUUID();
  private final GamePlugin plugin;

  private GameState state = GameStates.LOADING;
  private long stateTime = System.currentTimeMillis();

  public AbstractGame(GamePlugin plugin) {
    this.plugin = checkNotNull(plugin);
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
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
}

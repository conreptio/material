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

import org.natrolite.Identifiable;
import org.natrolite.cause.Cause;
import org.natrolite.plugin.GamePlugin;

public interface Game extends Identifiable {

  /**
   * Gets the current {@link GameState}.
   *
   * @return the current state
   */
  GameState getState();

  /**
   * Sets the game's {@link GameState}.
   *
   * @param state the new state
   * @param cause the cause
   */
  void setState(GameState state, Cause cause);

  /**
   * Gets the time when the latest state change happened.
   *
   * @return the time
   */
  long getStateTime();

  /**
   * Gets the {@link GamePlugin} this game is belonging to.
   *
   * @return the game plugin
   */
  GamePlugin getPlugin();
}
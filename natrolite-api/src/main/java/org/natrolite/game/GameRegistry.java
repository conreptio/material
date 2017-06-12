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

import org.natrolite.plugin.GamePlugin;

public interface GameRegistry {

  /**
   * Registers a {@link GamePlugin} with its name as id.
   *
   * @param plugin the plugin
   * @throws IllegalStateException if the id or the plugin is already registered
   */
  default void register(GamePlugin plugin) {
    register(plugin.getName().toLowerCase(), plugin);
  }

  /**
   * Registers a {@link GamePlugin}.
   *
   * @param plugin the plugin
   * @throws IllegalStateException if the id or the plugin is already registered
   */
  void register(String id, GamePlugin plugin);
}

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

package org.natrolite.registry;

import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.plugin.GamePlugin;
import org.natrolite.sign.GameSign;
import org.natrolite.sign.SignFactory;

public interface Registry {

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

  /**
   * Registers an {@link Arena}.
   *
   * @param id      the arena id
   * @param factory the arena factory
   */
  <T extends Arena> void register(String id, Class<T> clazz, ArenaFactory<T> factory);

  /**
   * Registers an {@link GameSign}.
   *
   * @param id      the sign id
   * @param clazz   the sign class
   * @param factory the sign factory
   * @param <T>     the sign
   */
  <T extends GameSign> void register(String id, Class<T> clazz, SignFactory<T> factory);
}

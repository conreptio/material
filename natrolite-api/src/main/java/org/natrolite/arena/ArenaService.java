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

package org.natrolite.arena;

import java.util.List;
import java.util.Optional;
import org.natrolite.util.DuplicationException;

public interface ArenaService {

  /**
   * Checks if a {@link String} is a valid arena id.
   *
   * @return true if valid
   */
  boolean isValid(String id);

  /**
   * Gets an arena by its id.
   *
   * @param id the arena id
   * @return arena or {@link Optional#empty()}
   */
  Optional<Arena> getArena(String id);

  /**
   * Registers an arena.
   *
   * @param arena the arena to register
   * @throws DuplicationException if the arena id is already used
   */
  void addArena(Arena arena) throws DuplicationException;

  /**
   * Returns an immutable list of all arenas.
   *
   * @return list of all arenas
   */
  List<Arena> getArenas();

  /**
   * Loads arenas from config.
   *
   * @throws Exception if an error occurs
   */
  void loadArenas() throws Exception;

  /**
   * Gets the amount of loaded arenas.
   *
   * @return amount of loaded arenas
   */
  int getSize();
}

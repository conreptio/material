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

package org.natrolite.service.placeholder;

import org.natrolite.cause.Cause;

public interface PlaceholderService {

  int DEFAULT_PRIORITY = 1000;

  /**
   * Registers a {@link Replacer} with the default priority.
   *
   * @param replacer the replacer to register
   */
  default void register(Replacer replacer) {
    register(replacer, DEFAULT_PRIORITY);
  }

  /**
   * Registers a {@link Replacer}.
   *
   * @param replacer the replacer to register
   * @param priority the replacer's priority
   */
  void register(Replacer replacer, int priority);

  /**
   * Unregisters a {@link Replacer}.
   *
   * @param replacer the replacer to remove
   * @return true if the replacer was removed
   */
  boolean unregister(Replacer replacer);

  /**
   * Unregisters all {@link Replacer}s.
   */
  void unregisterAll();

  /**
   * Replaces a {@link String} with all registered placeholders.
   *
   * @param cause the cause
   * @param text  the text to replace
   * @return the replaced text
   */
  String replace(Cause cause, String text);
}

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

package org.natrolite.placeholder;

import javax.annotation.Nullable;
import org.bukkit.OfflinePlayer;

public interface PlaceholderService {

  /**
   * Registers a {@link Replacer} with the default priority.
   *
   * @param replacer the replacer to register
   */
  void register(Replacer replacer);

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
   * @param player the optional player
   * @param text   the text to replace
   * @return the replaced text
   */
  String replace(@Nullable OfflinePlayer player, String text);
}

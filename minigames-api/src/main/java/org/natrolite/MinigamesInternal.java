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

package org.natrolite;

import java.nio.file.Path;
import org.bukkit.plugin.Plugin;
import org.natrolite.registry.Registry;

public interface MinigamesInternal {

  String PLUGIN_NAME = "Minigames";
  String BUNDLE_NAME = "minigames";

  /**
   * Gets the working directory.
   *
   * @return path of the working directory
   */
  Path getRoot();

  /**
   * Gets the Minigames{@link Plugin}.
   *
   * @return the natrolite plugin
   */
  Plugin getPlugin();

  /**
   * Gets the {@link Registry}.
   *
   * @return the registry
   */
  Registry getRegistry();
}

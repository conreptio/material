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

package org.natrolite.plugin;

import java.nio.file.Path;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.natrolite.util.Asset;

/**
 * An extension to the normal {@link Plugin}.
 */
public interface NeoPlugin extends Plugin {

  /**
   * Gets an {@link Asset} from the plugin jar.
   *
   * @param path the path to the file
   */
  default Asset getAsset(String path) {
    return new Asset(this, path.replace("\\", "/"));
  }

  /**
   * Gets the root directory (data folder) of this {@link Plugin}.
   */
  default Path getRoot() {
    return getDataFolder().toPath();
  }

  /**
   * Gets the {@link ServicesManager} belonging to this plugin.
   *
   * <p>In most cases, this is just a new instance of {@link SimpleServicesManager}.
   *
   * @return The service manager belonging to this plugin
   */
  ServicesManager getServicesManager();
}

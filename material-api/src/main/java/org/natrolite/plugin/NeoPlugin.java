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

package org.natrolite.plugin;

import java.nio.file.Path;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
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
   *
   * @see Plugin#getDataFolder()
   */
  default Path getRoot() {
    return getDataFolder().toPath();
  }

  default <T extends Listener> T register(T listener) {
    getServer().getPluginManager().registerEvents(listener, this);
    return listener;
  }

  default <T extends Event> T call(T event) {
    getServer().getPluginManager().callEvent(event);
    return event;
  }

  ServicesManager getServicesManager();
}

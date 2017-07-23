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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.natrolite.updater.Updatable;
import org.natrolite.util.Asset;

public class BetterPlugin extends JavaPlugin implements Listener, Updatable {

  public Path getRoot() {
    return this.getDataFolder().toPath();
  }

  public Asset getAsset(String path) {
    return new Asset(this, path.replace("\\", "/"));
  }

  @Override
  public void saveResource(String resourcePath, boolean replace) {
    if (!Files.exists(getRoot().resolve(resourcePath)) || replace) {
      super.saveResource(resourcePath, replace);
    }
  }

  @Override
  public File getFile() {
    return super.getFile();
  }
}

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

package org.natrolite.configurate.types;

import java.io.File;
import java.nio.file.Path;
import javax.annotation.Nullable;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.natrolite.configurate.Config;

public class GsonConfig<T> extends Config<T> {

  public GsonConfig(File file, @Nullable String root, Class<T> clazz) {
    super(file, root, clazz);
  }

  public GsonConfig(File file, Class<T> clazz) {
    super(file, null, clazz);
  }

  public GsonConfig(Path path, @Nullable String root, Class<T> clazz) {
    super(path, root, clazz);
  }

  public GsonConfig(Path path, Class<T> clazz) {
    super(path, null, clazz);
  }

  @Override
  protected ConfigurationLoader getLoader() {
    return GsonConfigurationLoader.builder()
        .setPath(getPath())
        .build();
  }
}
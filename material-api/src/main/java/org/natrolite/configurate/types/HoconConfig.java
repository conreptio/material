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

package org.natrolite.configurate.types;

import java.io.File;
import java.nio.file.Path;
import javax.annotation.Nullable;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.natrolite.configurate.Config;

public class HoconConfig<T> extends Config<T> {

  public HoconConfig(File file, @Nullable String root, Class<T> clazz) {
    super(file, root, clazz);
  }

  public HoconConfig(File file, Class<T> clazz) {
    super(file, null, clazz);
  }

  public HoconConfig(Path path, @Nullable String root, Class<T> clazz) {
    super(path, root, clazz);
  }

  public HoconConfig(Path path, Class<T> clazz) {
    super(path, null, clazz);
  }

  @Override
  protected ConfigurationLoader getLoader() {
    return HoconConfigurationLoader.builder()
        .setPath(getPath())
        .build();
  }
}

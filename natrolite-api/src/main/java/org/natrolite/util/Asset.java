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

package org.natrolite.util;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.natrolite.BetterPlugin;

public class Asset {

  private final BetterPlugin plugin;
  private final String file;

  public Asset(BetterPlugin plugin, String file) {
    this.plugin = checkNotNull(plugin, "Plugin cannot be null");
    this.file = checkNotNull(file, "File cannot be null");
  }

  public void copy(String path, CopyOption... options) throws IOException {
    copy(plugin.getRoot().resolve(path.replace("\\", "/")), options);
  }

  public void copyIn(String path, CopyOption... options) throws IOException {
    copyIn(plugin.getRoot().resolve(path.replace("\\", "/")), options);
  }

  public void copyIn(Path path, CopyOption... options) throws IOException {
    Files.createDirectories(path);
    copy(path.resolve(file), options);
  }

  public String getFile() {
    return file;
  }

  public void copy(Path path, CopyOption... options) throws IOException {
    if (!Files.exists(path) || Stream.of(options).anyMatch(o -> o == REPLACE_EXISTING)) {
      try (InputStream in = plugin.getResource(file)) {
        Files.copy(in, path, options);
      }
    }
  }
}

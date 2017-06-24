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

package org.natrolite.impl.map;

import java.nio.file.Path;
import java.util.Map;
import org.natrolite.map.GameMap;
import org.natrolite.map.MapSettings;
import org.natrolite.plugin.GamePlugin;

public class NatroliteMap implements GameMap {

  private final Path file;
  private final Map<GamePlugin<?>, MapSettings> map;

  NatroliteMap(Path file, Map<GamePlugin<?>, MapSettings> map) {
    this.file = file;
    this.map = map;
  }

  @Override
  public Path getFile() {
    return file;
  }
}

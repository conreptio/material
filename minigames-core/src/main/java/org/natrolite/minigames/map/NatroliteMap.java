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

package org.natrolite.minigames.map;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
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

  @Override
  public Optional<MapSettings> getSettings(GamePlugin plugin) {
    return map.entrySet().stream().filter(e -> e.equals(plugin)).map(Map.Entry::getValue).findAny();
  }
}

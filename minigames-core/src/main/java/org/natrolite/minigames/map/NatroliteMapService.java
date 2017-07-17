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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import org.natrolite.configurate.types.YamlConfig;
import org.natrolite.map.GameMap;
import org.natrolite.map.MapConfig;
import org.natrolite.map.MapService;
import org.natrolite.map.MapSettings;
import org.natrolite.minigames.MinigamesBukkit;
import org.natrolite.plugin.GamePlugin;

public class NatroliteMapService implements MapService {

  private final MinigamesBukkit natrolite;
  private final Path folder;
  private List<GameMap> maps = ImmutableList.of();

  public NatroliteMapService(MinigamesBukkit natrolite) {
    this.natrolite = natrolite;
    this.folder = natrolite.getRoot().resolve("maps");
  }

  private static boolean isWorld(Path path) {
    return Files.isDirectory(path) && Files.exists(path.resolve("level.dat"));
  }

  @Override
  public int loadMaps() throws Exception {
    final ArrayList<NatroliteMap> list = new ArrayList<>();
    Files.createDirectories(folder);
    Files.list(folder).filter(NatroliteMapService::isWorld).forEach(file -> {
      final Map<GamePlugin<?>, MapSettings> settingsMap = new HashMap<>();
      final YamlConfig<MapConfig> config = new YamlConfig<>(
          file.resolve("config.yml"),
          MapConfig.class
      );
      for (String gameId : config.getConfig().getGames()) {
        Optional<GamePlugin<?>> plugin = natrolite.getRegistry().getGame(gameId);
        if (plugin.isPresent()) {
          Optional<Class<? extends MapSettings>> settings = plugin.get().getMapSettings();
          if (!settings.isPresent()) {
            natrolite.getLogger().log(
                Level.WARNING,
                "Skipping {0} as it has no map settings configured", gameId
            );
            continue;
          }
          YamlConfig<? extends MapSettings> gameConfig = new YamlConfig<>(
              file.resolve(gameId + ".yml"),
              settings.get()
          );
          settingsMap.put(plugin.get(), gameConfig.getConfig());
        }
      }
      list.add(new NatroliteMap(file, ImmutableMap.copyOf(settingsMap)));
    });
    maps = ImmutableList.copyOf(list);
    return getSize();
  }

  @Override
  public int getSize() {
    return maps.size();
  }
}
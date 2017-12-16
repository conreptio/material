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

package org.natrolite.data.legacy;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.MapMaker;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.natrolite.configurate.types.HoconConfig;

@Deprecated
public class SimpleDataManager<T extends DataContainer<T>> implements DataManager<T> {

  private final Class<T> dataClass;
  private final Path folder;
  private final Map<UUID, HoconConfig<T>> cache = new MapMaker().concurrencyLevel(4).makeMap();

  public SimpleDataManager(Class<T> dataClass, Path folder) {
    this.dataClass = checkNotNull(dataClass);
    this.folder = checkNotNull(folder);
  }

  @Override
  public Optional<T> get(Player player) {
    return Optional.ofNullable(cache.get(player.getUniqueId())).map(HoconConfig::getConfig);
  }

  @Override
  public Optional<T> getOrCreate(Player player) {
    if (!cache.containsKey(player.getUniqueId())) {
      cache.put(player.getUniqueId(), new HoconConfig<T>(folder, "natrolite", dataClass));
    }
    return Optional.ofNullable(cache.get(player.getUniqueId())).map(HoconConfig::getConfig);
  }

  @Override
  public void set(Player player, T other) {
    getOrCreate(player).ifPresent(data -> {
      data.copyFrom(other);
    });
  }

  @Override
  public void saveAll() throws Exception {
    for (HoconConfig<T> config : cache.values()) {
      config.save();
    }
  }
}

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

package org.natrolite.minigames.arena;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.concurrent.NotThreadSafe;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaService;
import org.natrolite.minigames.MinigamesBukkit;
import org.natrolite.util.DuplicationException;

@NotThreadSafe
public class NatroliteArenaService implements ArenaService {

  private static final String PATTERN = "^[a-zA-Z0-9]*$";
  private static final String FILE = "arenas.json";
  private final MinigamesBukkit natrolite;
  private final GsonConfigurationLoader loader;
  private Map<String, Arena> arenas = new MapMaker().concurrencyLevel(4).makeMap();

  public NatroliteArenaService(MinigamesBukkit natrolite) {
    this.natrolite = natrolite;
    this.loader = GsonConfigurationLoader.builder().setPath(natrolite.resolve(FILE)).build();
  }

  @Override
  public boolean isValid(String id) {
    return Pattern.matches(PATTERN, id);
  }

  @Override
  public Optional<Arena> getArena(String id) {
    return arenas.entrySet().stream()
        .filter(en -> en.getKey().equals(id))
        .map(Map.Entry::getValue)
        .findAny();
  }

  @Override
  public void addArena(Arena arena) throws DuplicationException {
    if (arenas.containsKey(arena.getId())) {
      throw new DuplicationException();
    }
    arenas.put(arena.getId(), arena);
    save();
  }

  @Override
  public List<Arena> getArenas() {
    return ImmutableList.copyOf(arenas.values());
  }

  @Override
  public void loadArenas() throws IOException, ObjectMappingException {
    final ConfigurationNode root = loader.load(natrolite.defaultOptions());
    this.arenas = root.getList(TypeToken.of(Arena.class)).stream()
        .collect(Collectors.toMap(Arena::getId, arena -> arena));
  }

  private void save() {
    natrolite.getServer().getScheduler().runTaskAsynchronously(natrolite, () -> {
      try {
        final ConfigurationNode root = loader.load(natrolite.defaultOptions());
        root.setValue(new TypeToken<List<Arena>>() {}, new ArrayList<>(arenas.values()));
        loader.save(root);
      } catch (IOException | ObjectMappingException ex) {
        ex.printStackTrace();
      }
    });
  }
}

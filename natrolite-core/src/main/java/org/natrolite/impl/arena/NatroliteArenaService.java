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

package org.natrolite.impl.arena;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaService;
import org.natrolite.impl.NatroliteBukkit;

public class NatroliteArenaService implements ArenaService {

  private static final String FILE = "arenas.json";
  private final NatroliteBukkit natrolite;
  private final GsonConfigurationLoader loader;
  private List<Arena> arenas = ImmutableList.of();

  public NatroliteArenaService(NatroliteBukkit natrolite) {
    this.natrolite = natrolite;
    this.loader = GsonConfigurationLoader.builder().setPath(natrolite.resolve(FILE)).build();
  }

  @Override
  public void loadArenas() throws IOException, ObjectMappingException {
    final ConfigurationNode root = loader.load(natrolite.defaultOptions());
    this.arenas = ImmutableList.copyOf(root.getList(TypeToken.of(Arena.class)));
  }

  @Override
  public int getSize() {
    return arenas.size();
  }
}

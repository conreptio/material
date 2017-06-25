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

package org.natrolite.impl.arena;

import com.google.common.collect.ImmutableList;
import java.nio.file.Path;
import java.util.List;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import org.natrolite.arena.ArenaService;
import org.natrolite.impl.NatroliteBukkit;

public class NatroliteArenaService implements ArenaService {

  private final Path path;
  private List<NatroliteArena> arenas = ImmutableList.of();

  public NatroliteArenaService(NatroliteBukkit natrolite) {
    this.path = natrolite.getRoot().resolve("arenas.json");
  }

  @Override
  public void loadArenas() throws Exception {
    GsonConfigurationLoader loader = new GsonConfigurationLoader.Builder()
        .setPath(path)
        .build();
    ConfigurationNode root = loader.load();
  }

  @Override
  public int getSize() {
    return arenas.size();
  }
}

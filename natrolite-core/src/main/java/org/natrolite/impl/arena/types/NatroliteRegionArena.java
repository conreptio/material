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

package org.natrolite.impl.arena.types;

import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.command.CommandSender;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.arena.types.RegionArena;
import org.natrolite.impl.arena.NatroliteArena;

public class NatroliteRegionArena extends NatroliteArena implements RegionArena {

  private NatroliteRegionArena(String id) {
    super(id);
  }

  public static NatroliteRegionArena.Factory factory() {
    return new NatroliteRegionArena.Factory();
  }

  public static class Factory implements ArenaFactory<NatroliteRegionArena> {

    @Override
    public NatroliteRegionArena build(String id, ConfigurationNode value) {
      return new NatroliteRegionArena(id);
    }

    @Override
    public Optional<NatroliteRegionArena> build(String id, CommandSender sender, String[] args) {
      return Optional.empty();
    }
  }
}

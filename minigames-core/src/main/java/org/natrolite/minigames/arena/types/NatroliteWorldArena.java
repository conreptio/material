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

package org.natrolite.minigames.arena.types;

import java.util.Optional;
import javax.annotation.Nullable;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.command.CommandSender;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.arena.types.WorldArena;
import org.natrolite.map.GameMap;
import org.natrolite.map.MapSettings;
import org.natrolite.minigames.arena.NatroliteArena;

public class NatroliteWorldArena extends NatroliteArena implements WorldArena {

  @Nullable private GameMap map;

  private NatroliteWorldArena(String id) {
    super(id);
  }

  public static NatroliteWorldArena.Factory factory() {
    return new NatroliteWorldArena.Factory();
  }

  @Override
  public Optional<MapSettings> getSettings() {
    return getGame().flatMap(game -> Optional.ofNullable(map)
        .map(map -> map.getSettings(game).orElse(null)));
  }

  @Override
  public Optional<GameMap> getMap() {
    return Optional.ofNullable(map);
  }

  public static class Factory implements ArenaFactory<NatroliteWorldArena> {

    @Override
    public NatroliteWorldArena build(String id, ConfigurationNode value) {
      return new NatroliteWorldArena(id);
    }

    @Override
    public Optional<NatroliteWorldArena> build(String id, CommandSender sender, String[] args) {
      return Optional.of(new NatroliteWorldArena(id));
    }
  }
}

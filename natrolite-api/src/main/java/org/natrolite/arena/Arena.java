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

package org.natrolite.arena;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.natrolite.game.Game;

public interface Arena {

  String getId();

  void serialize(ConfigurationNode value) throws ObjectMappingException;

  Optional<Game> getGame();

  Set<UUID> getPlayers();

  Optional<World> getWorld();

  World getWorldUnsafe();

  default boolean isPlaying(Player player) {
    return getPlayers().contains(player.getUniqueId());
  }

  default Set<Player> getPlayerList() {
    return getPlayers().stream()
        .filter(uuid -> Bukkit.getPlayer(uuid) != null)
        .map(Bukkit::getPlayer).collect(Collectors.toSet());
  }
}

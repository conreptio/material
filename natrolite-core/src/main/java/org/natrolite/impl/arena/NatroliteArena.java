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

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.World;
import org.natrolite.arena.Arena;
import org.natrolite.game.Game;

public class NatroliteArena implements Arena {

  @Nullable private World world;
  private Set<UUID> players = new HashSet<>();
  @Nullable private Game game;

  @Override
  public Optional<Game> getGame() {
    return Optional.ofNullable(game);
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public Set<UUID> getPlayers() {
    return ImmutableSet.copyOf(players);
  }

  @Override
  public Optional<World> getWorld() {
    return Optional.ofNullable(world);
  }

  public void setWorld(World world) {
    this.world = world;
  }

  @Override
  public World getWorldUnsafe() {
    return world;
  }
}

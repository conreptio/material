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

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.bukkit.World;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaState;
import org.natrolite.arena.ArenaStates;
import org.natrolite.game.Game;

public abstract class NatroliteArena implements Arena {

  private final String id;
  @Nullable private World world;
  private Set<UUID> players = new HashSet<>();
  @Nullable private Game game;
  private ArenaState state = ArenaStates.ONLINE;

  public NatroliteArena(String id) {
    this.id = id;
  }

  @Override
  public final String getId() {
    return id;
  }

  @Override
  public Optional<Game> getGame() {
    return Optional.ofNullable(game);
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public ArenaState getState() {
    return state;
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

  @Override
  @OverridingMethodsMustInvokeSuper
  public void serialize(ConfigurationNode value) throws ObjectMappingException {
    value.getNode("natrolite").setValue(true);
  }
}

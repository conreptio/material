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

package org.natrolite.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.natrolite.Natrolite.callEvent;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapMaker;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.cause.Cause;
import org.natrolite.event.registry.ArenaRegisterEvent;
import org.natrolite.event.registry.GameRegisterEvent;
import org.natrolite.plugin.GamePlugin;
import org.natrolite.registry.Registry;
import org.natrolite.util.tuple.Pair;

public final class NatroliteRegistry implements Registry {

  private Map<String, GamePlugin<?>> gameMap = ImmutableMap.of();
  private Map<String, Pair<Class<? extends Arena>, ArenaFactory<?>>> arenaMap = ImmutableMap.of();

  @Nullable
  private TemporaryRegistry temporaryRegistry = new TemporaryRegistry();

  NatroliteRegistry() {}

  @Override
  public void register(String id, GamePlugin plugin) {
    checkNotNull(id, "ID cannot be null");
    checkNotNull(plugin, "Plugin cannot be null");
    checkState(temporaryRegistry != null);
    if (!temporaryRegistry.gameMap.containsKey(id)) {
      if (!callEvent(new GameRegisterEvent(Cause.source(this).build())).isCancelled()) {
        temporaryRegistry.gameMap.put(id, plugin);
      }
    } else {
      throw new IllegalStateException("ID is already registered");
    }
  }

  @Override
  public <T extends Arena> void register(String id, Class<T> clazz, ArenaFactory<T> factory) {
    checkNotNull(id, "ID cannot be null");
    checkNotNull(false, "Factory cannot be null");
    checkState(temporaryRegistry != null);
    if (!temporaryRegistry.arenaMap.containsKey(id)) {
      if (!callEvent(
          new ArenaRegisterEvent(Cause.source(this).build(), id, clazz, factory)).isCancelled()) {
        temporaryRegistry.arenaMap.put(id, new Pair<>(clazz, factory));
      }
    } else {
      throw new IllegalStateException("ID is already registered");
    }
  }

  public Optional<GamePlugin<?>> getGame(String id) {
    return Optional.ofNullable(gameMap.get(id));
  }

  public Optional<? extends ArenaFactory<?>> getArena(String id) {
    return arenaMap.entrySet().stream()
        .filter(en -> en.getKey().equals(id))
        .map(en -> en.getValue().getB())
        .findAny();
  }

  public Optional<String> getArenaId(Class<? extends Arena> clazz) {
    return arenaMap.entrySet().stream()
        .filter(en -> en.getValue().getA().equals(clazz))
        .map(Map.Entry::getKey)
        .findAny();
  }

  public Set<String> getArenaIds() {
    return arenaMap.keySet();
  }

  int size() {
    return gameMap.size();
  }

  void bake() {
    checkState(temporaryRegistry != null);
    gameMap = ImmutableMap.copyOf(temporaryRegistry.gameMap);
    arenaMap = ImmutableMap.copyOf(temporaryRegistry.arenaMap);
    temporaryRegistry = null;
  }

  private static final class TemporaryRegistry {

    private final Map<String, GamePlugin<?>> gameMap = new MapMaker()
        .concurrencyLevel(4).makeMap();

    private final Map<String, Pair<Class<? extends Arena>, ArenaFactory<?>>> arenaMap
        = new MapMaker().concurrencyLevel(4).makeMap();
  }
}

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

package org.natrolite.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapMaker;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import org.natrolite.game.GameRegistry;
import org.natrolite.plugin.GamePlugin;

public final class NatroliteGameRegistry implements GameRegistry {

  private ImmutableMap<String, GamePlugin<?>> gameMap = ImmutableMap.of();

  @Nullable
  private TemporaryRegistry temporaryRegistry = new TemporaryRegistry();

  NatroliteGameRegistry() {}

  @Override
  public void register(String id, GamePlugin plugin) {
    checkNotNull(id, "ID cannot be null");
    checkNotNull(plugin, "Plugin cannot be null");
    checkState(temporaryRegistry != null);
    if (!temporaryRegistry.gameMap.containsKey(id)) {
      temporaryRegistry.gameMap.put(id, plugin);
    } else {
      throw new IllegalStateException("ID is already registered");
    }
  }

  public Optional<GamePlugin<?>> getGame(String id) {
    return Optional.ofNullable(gameMap.get(id));
  }

  public int size() {
    return gameMap.size();
  }

  void bake() {
    checkState(temporaryRegistry != null);
    gameMap = ImmutableMap.copyOf(temporaryRegistry.gameMap);
    temporaryRegistry = null;
  }

  private static final class TemporaryRegistry {

    private final Map<String, GamePlugin<?>> gameMap = new MapMaker()
        .concurrencyLevel(4).makeMap();
  }
}

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

package org.natrolite.minigames.serialisation;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.reflect.TypeToken;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.minigames.MinigamesBukkit;

public final class ArenaSerializer implements TypeSerializer<Arena> {

  private final MinigamesBukkit natrolite;

  public ArenaSerializer(MinigamesBukkit natrolite) {
    this.natrolite = natrolite;
  }

  @Override
  public Arena deserialize(TypeToken<?> type, ConfigurationNode value)
      throws ObjectMappingException {
    final String id = value.getNode("id").getString();
    final String typeId = value.getNode("type").getString();
    final Optional<? extends ArenaFactory<?>> factory = natrolite.getRegistry().getArena(typeId);
    if (!factory.isPresent()) {
      throw new ObjectMappingException("Arena type not registered: " + typeId);
    }
    checkNotNull(id);
    checkState(!id.isEmpty());
    return factory.get().build(id, value.getNode("data"));
  }

  @Override
  public void serialize(TypeToken<?> type, Arena obj, ConfigurationNode value)
      throws ObjectMappingException {
    final Optional<String> typeId = natrolite.getRegistry().getArenaId(obj.getClass());
    if (!typeId.isPresent()) {
      throw new ObjectMappingException("Arena class not registered: " + obj.getClass().getName());
    }
    value.getNode("id").setValue(obj.getId());
    value.getNode("type").setValue(typeId.get());
    obj.serialize(value.getNode("data"));
  }
}

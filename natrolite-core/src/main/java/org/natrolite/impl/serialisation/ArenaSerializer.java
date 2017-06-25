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

package org.natrolite.impl.serialisation;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.reflect.TypeToken;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.impl.NatroliteBukkit;

public final class ArenaSerializer implements TypeSerializer<Arena> {

  private final NatroliteBukkit natrolite;

  private ArenaSerializer(NatroliteBukkit natrolite) {
    this.natrolite = natrolite;
  }

  public static void register(TypeSerializerCollection collection, NatroliteBukkit natrolite) {
    collection.registerType(TypeToken.of(Arena.class), new ArenaSerializer(natrolite));
  }

  @Override
  public Arena deserialize(TypeToken<?> type, ConfigurationNode value)
      throws ObjectMappingException {
    final String id = value.getNode("id").getString();
    final String typeId = value.getNode("type").getString();
    final Optional<ArenaFactory<?>> factory = natrolite.getGameRegistry().getArena(typeId);
    if (!factory.isPresent()) {
      throw new ObjectMappingException("Arena type not registered: " + typeId);
    }
    checkNotNull(id);
    checkState(!id.isEmpty());
    return factory.get().build(id, value);
  }

  @Override
  public void serialize(TypeToken<?> type, Arena obj, ConfigurationNode value)
      throws ObjectMappingException {
    value.getNode("id").setValue(obj.getId());
    value.getNode("type").setValue("natroWorld");
    obj.serialize(value);
  }
}

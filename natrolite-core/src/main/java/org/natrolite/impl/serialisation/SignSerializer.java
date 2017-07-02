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

package org.natrolite.impl.serialisation;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.reflect.TypeToken;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.bukkit.util.BlockVector;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.sign.GameSign;
import org.natrolite.sign.SignFactory;

public final class SignSerializer implements TypeSerializer<GameSign> {

  private static final String ID = "id";
  private static final String POSITION = "position";
  private static final String SUB_NODE = "data";
  private final NatroliteBukkit natrolite;

  public SignSerializer(NatroliteBukkit natrolite) {
    this.natrolite = natrolite;
  }

  @Override
  public GameSign deserialize(TypeToken<?> type, ConfigurationNode value)
      throws ObjectMappingException {
    final String id = value.getNode(ID).getString();
    final BlockVector vector = value.getNode(POSITION).getValue(TypeToken.of(BlockVector.class));
    final Optional<? extends SignFactory<?>> factory = natrolite.getRegistry().getSign(id);
    if (!factory.isPresent()) {
      throw new ObjectMappingException("Sign type not registered: " + id);
    }
    return factory.get().create(checkNotNull(vector), value.getNode(SUB_NODE));
  }

  @Override
  public void serialize(TypeToken<?> type, GameSign obj, ConfigurationNode value)
      throws ObjectMappingException {
    final Optional<String> id = natrolite.getRegistry().getSignId(obj.getClass());
    if (!id.isPresent()) {
      throw new ObjectMappingException("Sign class not registered: " + obj.getClass().getName());
    }
    value.getNode(ID).setValue(TypeToken.of(String.class), id.get());
    value.getNode(POSITION).setValue(TypeToken.of(BlockVector.class), obj.getPosition());
    obj.serialize(value.getNode(SUB_NODE));
  }
}

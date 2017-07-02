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

package org.natrolite.configurate.serialization;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.bukkit.util.BlockVector;

public class BlockVectorSerializer implements TypeSerializer<BlockVector> {

  @Override
  public BlockVector deserialize(TypeToken<?> type, ConfigurationNode value) throws
      ObjectMappingException {
    return new BlockVector(
        value.getNode("x").getInt(),
        value.getNode("y").getInt(),
        value.getNode("z").getInt());
  }

  @Override
  public void serialize(TypeToken<?> type, BlockVector obj, ConfigurationNode value) throws
      ObjectMappingException {
    value.getNode("x").setValue(obj.getBlockX());
    value.getNode("y").setValue(obj.getBlockY());
    value.getNode("z").setValue(obj.getBlockZ());
  }
}

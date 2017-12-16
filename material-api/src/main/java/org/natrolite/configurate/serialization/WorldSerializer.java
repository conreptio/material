/*
 * This file is part of Material.
 *
 * Copyright (c) 2016-2017 Neolumia
 *
 * Material is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Material is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Material. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.configurate.serialization;

import com.google.common.reflect.TypeToken;
import java.util.UUID;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldSerializer implements TypeSerializer<World> {

  @Override
  public World deserialize(TypeToken<?> type, ConfigurationNode value) throws
      ObjectMappingException {
    return Bukkit.getWorld(value.getNode("world").getValue(TypeToken.of(UUID.class)));
  }

  @Override
  public void serialize(TypeToken<?> type, World obj, ConfigurationNode value) throws
      ObjectMappingException {
    value.getNode("world").setValue(TypeToken.of(UUID.class), obj.getUID());
  }
}

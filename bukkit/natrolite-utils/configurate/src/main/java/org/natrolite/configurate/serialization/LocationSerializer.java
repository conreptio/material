/*
 * Natrolite - Minecraft Minigame Manager
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

package org.natrolite.configurate.serialization;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer implements TypeSerializer<Location> {

  @Override
  public Location deserialize(TypeToken<?> type, ConfigurationNode value) throws
      ObjectMappingException {
    return new Location(
        value.getNode("world").getValue(TypeToken.of(World.class)),
        value.getNode("x").getDouble(),
        value.getNode("y").getDouble(),
        value.getNode("z").getDouble(),
        value.getNode("yaw").getFloat(),
        value.getNode("pitch").getFloat()
    );
  }

  @Override
  public void serialize(TypeToken<?> type, Location obj, ConfigurationNode value) throws
      ObjectMappingException {
    value.getNode("world").setValue(TypeToken.of(World.class), obj.getWorld());
    value.getNode("x").setValue(obj.getX());
    value.getNode("y").setValue(obj.getY());
    value.getNode("z").setValue(obj.getZ());
  }
}

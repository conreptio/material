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

package org.natrolite;

import java.nio.file.Path;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.bukkit.plugin.Plugin;
import org.natrolite.registry.Registry;

public interface NatroliteInternal {

  /**
   * Gets the working directory.
   *
   * @return path of the working directory
   */
  Path getRoot();

  /**
   * Gets the Natrolite {@link Plugin}.
   *
   * @return the natrolite plugin
   */
  Plugin getPlugin();

  /**
   * Gets the {@link Registry}.
   *
   * @return the game registry
   */
  Registry getGameRegistry();

  /**
   * Gets the {@link TypeSerializerCollection} the internal plugin is using.
   *
   * @return type serializer collection of the plugin
   */
  default TypeSerializerCollection getSerializers() {
    return TypeSerializers.getDefaultSerializers();
  }
}

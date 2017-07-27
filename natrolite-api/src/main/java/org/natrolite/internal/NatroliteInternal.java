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

package org.natrolite.internal;

import java.util.UUID;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.bukkit.plugin.Plugin;
import org.natrolite.internal.config.NatroliteConfig;
import org.natrolite.plugin.NeoJavaPlugin;
import org.natrolite.registry.Registry;

public interface NatroliteInternal {

  String PLUGIN_NAME = "Natrolite";
  String BUNDLE_NAME = "natrolite";
  String LICENSE = "LICENSE.txt";
  String THIRD_PARTY_LICENSES = "THIRD-PARTY-LICENSES.txt";

  /**
   * Gets the Natrolite {@link Plugin}.
   *
   * @return the natrolite plugin
   */
  NeoJavaPlugin getPlugin();

  /**
   * Gets the {@link Registry}.
   */
  Registry getRegistry();

  /**
   * Gets the unique server id.
   *
   * @return The unique server id
   */
  UUID getServerId();

  /**
   * Gets the {@link NatroliteConfig}.
   *
   * <p><b>Note:</b> Calls to the config are not safe and can change over time.
   *
   * @return The natrolite config
   */
  NatroliteConfig getSettings();

  /**
   * Gets the {@link TypeSerializerCollection} Natrolite is using.
   *
   * <p>Use this rather than {@link TypeSerializers#getDefaultSerializers()}.
   * If you want to add your own {@link TypeSerializers}, create a new child with {@link
   * TypeSerializerCollection#newChild()}
   *
   * @return The serializer collection
   */
  TypeSerializerCollection getSerializers();
}

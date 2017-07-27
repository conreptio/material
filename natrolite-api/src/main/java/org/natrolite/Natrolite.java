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

package org.natrolite;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.ServicesManager;
import org.natrolite.internal.NatroliteInternal;
import org.natrolite.plugin.NeoJavaPlugin;
import org.natrolite.registry.Registry;

public final class Natrolite {

  @Nullable
  private static NatroliteInternal natrolite = null;

  private Natrolite() {}

  public static NatroliteInternal getNatrolite() {
    return checkNotNull(natrolite, "Natrolite has not been initialized");
  }

  public static NeoJavaPlugin getPlugin() {
    return getNatrolite().getPlugin();
  }

  public static Registry getRegistry() {
    return getNatrolite().getRegistry();
  }

  public static Path getRoot() {
    return getPlugin().getRoot();
  }

  public static UUID getServerId() {
    return getNatrolite().getServerId();
  }

  public static TypeSerializerCollection getSerializers() {
    return getNatrolite().getSerializers();
  }

  public static Server getServer() {
    return getPlugin().getServer();
  }

  public static ServicesManager getServicesManager() {
    return getServer().getServicesManager();
  }

  public static <T extends Event> T callEvent(T event) {
    getServer().getPluginManager().callEvent(event);
    return event;
  }

  public static <T> Optional<T> provide(Class<T> service) {
    return Optional.ofNullable(getServicesManager().getRegistration(service).getProvider());
  }

  public static <T> T provideUnchecked(Class<T> service) {
    return provide(service).orElseThrow(IllegalStateException::new);
  }
}

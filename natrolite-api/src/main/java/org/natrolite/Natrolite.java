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
import javax.annotation.Nullable;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;

public final class Natrolite {

  @Nullable
  private static NatroliteInternal natrolite = null;

  private Natrolite() {}

  public static NatroliteInternal getNatrolite() {
    return checkNotNull(natrolite, "Natrolite has not been initialized");
  }

  public static Path getRoot() {
    return getNatrolite().getRoot();
  }

  public static Plugin getPlugin() {
    return getNatrolite().getPlugin();
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

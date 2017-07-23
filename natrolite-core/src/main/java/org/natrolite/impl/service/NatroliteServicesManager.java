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

package org.natrolite.impl.service;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServiceUnregisterEvent;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.service.NatroliteService;

public final class NatroliteServicesManager implements Listener {

  public NatroliteServicesManager(NatroliteBukkit plugin) {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onServiceUnregister(ServiceUnregisterEvent event) {
    final Object service = event.getProvider().getProvider();
    if (service instanceof NatroliteService) {
      ((NatroliteService) service).shutdown();
      if (service instanceof Listener) {
        HandlerList.unregisterAll((Listener) event.getProvider());
      }
    }
  }
}

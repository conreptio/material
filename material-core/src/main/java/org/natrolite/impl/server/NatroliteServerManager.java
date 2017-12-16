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

package org.natrolite.impl.server;

import java.util.logging.Level;
import javax.annotation.Nullable;
import org.bukkit.scheduler.BukkitTask;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.server.ServerManager;
import org.natrolite.server.ServerSnapshot;

public final class NatroliteServerManager implements ServerManager, Runnable {

  private final NatroliteBukkit plugin;
  private final ServerRepository repository;

  @Nullable
  private BukkitTask task = null;

  /**
   * Creates a new instance.
   *
   * @param plugin the plugin
   */
  public NatroliteServerManager(NatroliteBukkit plugin) {
    this.plugin = plugin;
    this.repository = new ServerRepository(plugin);
    task = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 0L, 20L);
  }

  @Override
  public ServerSnapshot generateSnapshot() {
    return ServerSnapshot.builder()
        .uuid(plugin.getServerId())
        .address(getAddress())
        .port(plugin.getServer().getPort())
        .motd(plugin.getServer().getMotd())
        .playerCount(plugin.getServer().getOnlinePlayers().size())
        .maxPlayers(plugin.getServer().getMaxPlayers())
        .build();
  }

  @Override
  public void run() {
    try {
      repository.update(generateSnapshot());
    } catch (Throwable throwable) {
      plugin.getLogger().log(Level.SEVERE, "Could not update server status", throwable);
      cancel();
    }
  }

  /**
   * Cancels the update task if it is running.
   */
  public void cancel() {
    if (task != null) {
      task.cancel();
      task = null;
    }
  }

  private String getAddress() {
    final String ip = plugin.getServer().getIp();
    return ip.isEmpty() ? "localhost" : ip;
  }
}

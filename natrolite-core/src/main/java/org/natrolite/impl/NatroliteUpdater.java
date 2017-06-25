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

package org.natrolite.impl;

import static org.natrolite.impl.StaticMessageProvider.in;
import static org.natrolite.impl.StaticMessageProvider.wn;
import static org.natrolite.spiget.Queries.BASE;
import static org.natrolite.spiget.Queries.RESOURCE_DOWNLOAD;
import static org.natrolite.util.VersionComparator.isOlderThan;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.natrolite.spiget.Spiget;
import org.natrolite.updater.Spigot;
import org.natrolite.updater.Updatable;

public final class NatroliteUpdater implements Listener {

  private NatroliteBukkit natrolite;

  NatroliteUpdater(NatroliteBukkit natrolite) {
    this.natrolite = natrolite;
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onEnable(PluginEnableEvent event) {
    final Plugin plugin = event.getPlugin();
    final Spigot spigot = event.getPlugin().getClass().getAnnotation(Spigot.class);
    if (spigot != null) {
      Spiget.getLatestVersion(spigot.value()).thenAccept(version -> {
        try {
          final String current = plugin.getDescription().getVersion();
          if (isOlderThan(current, version.getName())) {
            in(plugin.getLogger(), "updater.outdated", current, version.getName());

            final String url = String.format(BASE + RESOURCE_DOWNLOAD, spigot.value());
            final Path file = getFolder().resolve(getJar(plugin).getName());
            Files.createDirectories(file.getParent());

            in(plugin.getLogger(), "updater.download", plugin.getName(), version.getName());
            try (InputStream in = new URL(url).openStream()) {
              Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
            }

            in(plugin.getLogger(), "updater.success", plugin.getName(), version.getName());
          }
        } catch (Throwable throwable) {
          throw new RuntimeException(throwable);
        }
      }).exceptionally(ex -> {
        wn(plugin.getLogger(), "updater.error");
        return null;
      });
    }
  }

  private Path getFolder() {
    return natrolite.getServer().getUpdateFolderFile().toPath();
  }

  private File getJar(Plugin plugin) throws Exception {
    if (plugin instanceof Updatable) {
      return ((Updatable) plugin).getFile();
    }
    return new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
  }
}

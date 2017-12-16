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

package org.natrolite.impl.updater;

import static org.natrolite.text.Text.un;
import static org.natrolite.text.Text.unb;
import static org.natrolite.updater.lightning.Lightning.FILE;
import static org.natrolite.util.VersionComparator.isOlderThan;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.updater.Updatable;
import org.natrolite.updater.Updater;
import org.natrolite.updater.lightning.Lightning;
import org.natrolite.updater.lightning.LightningElement;

public final class NatroUpdater implements Updater, Listener {

  private static final String AGENT = "Natrolite/1.0";
  private static final String URL = "https://raw.githubusercontent.com/%s/%s/%s/" + FILE;
  private static final Type type = new TypeToken<Map<String, LightningElement>>() {}.getType();
  private static final Gson gson = new Gson();
  private final NatroliteBukkit plugin;

  public NatroUpdater(NatroliteBukkit plugin) {
    this.plugin = plugin;
  }

  private static File getJar(Plugin plugin) throws Exception {
    if (plugin instanceof Updatable) {
      return ((Updatable) plugin).getFile();
    }
    return new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
  }

  private static String read(String url) throws RuntimeException {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestProperty("User-Agent", AGENT);
      try (InputStream in = connection.getInputStream()) {
        Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private String getFile(Lightning lightning) {
    if (!lightning.value().isEmpty()) {
      return read(lightning.value());
    }
    return read(String.format(URL, lightning.org(), lightning.repo(), lightning.branch()));
  }

  @EventHandler
  public void onEnable(PluginEnableEvent event) {
    final Lightning lightning = event.getPlugin().getClass().getAnnotation(Lightning.class);
    if (lightning != null) {
      lightningUpdate(lightning, event.getPlugin());
    }
  }

  private void lightningUpdate(Lightning lightning, Plugin plugin) {
    final String name = plugin.getDescription().getFullName();
    final String current = plugin.getDescription().getVersion();
    final Map<String, LightningElement> map = gson.fromJson(getFile(lightning), type);

    map.forEach((regex, element) -> {
      try {
        regex = regex.replace("?", ".?").replace("*", ".*?");
        if (Bukkit.getBukkitVersion().matches(regex)) {
          if (isOlderThan(current, element.getLatest())) {

            unb(this.plugin, "system.updater.outdated")
              .args(current, element.getLatest())
              .build()
              .info(plugin.getLogger());

            if (element.isInstall()) {

              final Path file = getUpdateFolder().resolve(getJar(plugin).getName());
              Files.createDirectories(file.getParent());

              unb(this.plugin, "system.updater.download")
                .args(name, element.getLatest())
                .build()
                .info(plugin.getLogger());

              try (InputStream in = new URL(element.getUrl()).openStream()) {
                Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
              }

              unb(this.plugin, "system.updater.success")
                .args(name)
                .build()
                .info(plugin.getLogger());
              return;
            }

            if (element.isInform()) {
              unb(this.plugin, "system.updater.inform").args(name).build().info(plugin.getLogger());
            }
          } else {
            un(this.plugin, "system.updater.up").info(plugin.getLogger());
          }
        }
      } catch (Throwable throwable) {
        un(this.plugin, "system.updater.error").warn(plugin.getLogger());
      }
    });
  }
}

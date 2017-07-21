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

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static org.natrolite.impl.StaticMessageProvider.in;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import javax.annotation.Nullable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.natrolite.BetterPlugin;
import org.natrolite.Natrolite;
import org.natrolite.NatroliteInternal;
import org.natrolite.NatrolitePlugin;
import org.natrolite.configurate.types.HoconConfig;
import org.natrolite.impl.config.NatroliteConfig;
import org.natrolite.impl.server.ServerRepository;
import org.natrolite.impl.service.sql.SqlServiceImpl;
import org.natrolite.metrics.Metrics;
import org.natrolite.service.sql.SqlService;
import org.natrolite.updater.Spigot;
import org.natrolite.util.ReflectionUtil;

@Spigot("39140")
public final class NatroliteBukkit extends BetterPlugin implements NatroliteInternal {

  public static final String TABLE_PREFIX = "natro_";
  public static final String SERVER_INFO = "server.dat";

  @Nullable
  private static NatroliteBukkit plugin;

  private UUID serverId;
  private HoconConfig<NatroliteConfig> config;

  public static NatroliteBukkit getInstance() {
    return checkNotNull(plugin);
  }

  public static NatroliteConfig config() {
    return getInstance().getSettings();
  }

  @Override
  public void onLoad() {
    plugin = this;
    ReflectionUtil.setFinalStatic(Natrolite.class, "natrolite", this);
    ReflectionUtil.setFinalStatic(NatroliteBukkit.class, "plugin", this);

    try {
      getAsset(LICENSE).copyIn("license", REPLACE_EXISTING);
      getAsset(THIRD_PARTY_LICENSES).copyIn("license", REPLACE_EXISTING);
    } catch (IOException ex) {
      getLogger().log(Level.WARNING, "Could not save licenses", ex);
    }

    serverId = readUUID();
    getLogger().info("Server UUID is " + serverId);

    config = new HoconConfig<>(
        getRoot().resolve("config").resolve("natrolite.conf"),
        NatroliteConfig.class
    );

    register(SqlService.class, new SqlServiceImpl(), ServicePriority.Low);
  }

  @Override
  public void onEnable() {
    try {
      final long start = System.currentTimeMillis();

      Class.forName("org.h2.Driver");

      new ServerRepository(this).update(serverId, "Test Server");

      try {
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.AdvancedPie("plugins") {
          @Override
          public HashMap<String, Integer> getValues(HashMap<String, Integer> valueMap) {
            HashMap<String, Integer> map = new HashMap<>();
            for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
              if (plugin instanceof NatrolitePlugin) {
                map.put(plugin.getName(), 1);
              }
            }
            return map;
          }
        });
      } catch (Throwable throwable) {
        getLogger().log(Level.FINE, "Could not start metrics service", throwable);
      }

      in(getLogger(), "plugin.enabled", System.currentTimeMillis() - start);
    } catch (Throwable throwable) {
      getLogger().log(Level.SEVERE, "Plugin could not be enabled", throwable);
      setEnabled(false);
    }
  }

  @Override
  public void onDisable() {
    try {
      Optional<SqlService> service = Natrolite.provide(SqlService.class);
      if (service.isPresent() && service.get() instanceof Closeable) {
        ((Closeable) service.get()).close();
      }
    } catch (Throwable throwable) {
      getLogger().log(Level.WARNING, "Could not close sql service on shutdown", throwable);
    }
  }

  @Override
  public Path getRoot() {
    return getDataFolder().toPath();
  }

  @Override
  public NatroliteBukkit getPlugin() {
    return this;
  }

  @Override
  public UUID getServerId() {
    return serverId;
  }

  public NatroliteConfig getSettings() {
    return config.getConfig();
  }

  private UUID readUUID() {
    try {
      final Path file = getRoot().resolve(SERVER_INFO);
      if (Files.exists(getRoot().resolve(SERVER_INFO))) {
        try (InputStream in = Files.newInputStream(file)) {
          try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            return UUID.fromString(reader.readLine());
          }
        }
      }
      try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE))) {
        final UUID uuid = UUID.randomUUID();
        out.write(uuid.toString().getBytes(), 0, uuid.toString().length());
        return uuid;
      }
    } catch (IOException ex) {
      getLogger().severe("Could not read server uuid in server.dat, using random id");
      return UUID.randomUUID();
    }
  }

  private <T> void register(Class<T> clazz, T provider, ServicePriority priority) {
    getServer().getServicesManager().register(clazz, provider, this, priority);
  }
}

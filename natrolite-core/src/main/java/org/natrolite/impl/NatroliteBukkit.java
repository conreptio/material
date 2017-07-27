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
import static ninja.leaping.configurate.objectmapping.serialize.TypeSerializers.getDefaultSerializers;
import static org.natrolite.text.Text.unb;
import static org.natrolite.util.StringUtils.capitalizeFirst;

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
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.natrolite.Natrolite;
import org.natrolite.NatrolitePlugin;
import org.natrolite.annotations.GitHub;
import org.natrolite.configurate.types.HoconConfig;
import org.natrolite.dictionary.TranslationDictionary;
import org.natrolite.dictionary.bundle.MultiSourceResourceBundleTranslationDictionary;
import org.natrolite.dictionary.bundle.SimpleResourceBundleTranslationDictionary;
import org.natrolite.impl.command.ServicesCommand;
import org.natrolite.impl.server.NatroliteServerManager;
import org.natrolite.impl.service.NatroliteServicesManager;
import org.natrolite.impl.service.sql.SqlServiceImpl;
import org.natrolite.internal.NatroliteInternal;
import org.natrolite.internal.config.NatroliteConfig;
import org.natrolite.internal.config.ServerConfig;
import org.natrolite.metrics.Metrics;
import org.natrolite.plugin.BetterPlugin;
import org.natrolite.service.sql.SqlService;
import org.natrolite.updater.Spigot;
import org.natrolite.util.Dependency;
import org.natrolite.util.ReflectionUtil;

@Spigot("39140")
@GitHub(organisation = "natrolite", repository = "natrolite")
public final class NatroliteBukkit extends BetterPlugin implements NatroliteInternal {

  public static final String TABLE_PREFIX = "natro_";
  public static final String SERVER_INFO = "server.dat";
  public static final String URL = "https://github.com/kanoxx/odium/releases/download/1.0.1/odium.jar";

  @Nullable private static NatroliteBukkit plugin;

  private final TypeSerializerCollection serializers = getDefaultSerializers().newChild();

  @Nullable private Throwable throwable;

  @Nullable private NatroliteServicesManager servicesManager;
  @Nullable private NatroliteServerManager serverManager;

  private HoconConfig<NatroliteConfig> config;
  private HoconConfig<ServerConfig> serverConfig;

  private UUID serverId;
  private String serverName;

  public static NatroliteBukkit getInstance() {
    return checkNotNull(plugin);
  }

  public static NatroliteConfig config() {
    return getInstance().getSettings();
  }

  @Override
  public void onLoad() {
    try {
      ReflectionUtil.setFinalStatic(Natrolite.class, "natrolite", this);
      ReflectionUtil.setFinalStatic(NatroliteBukkit.class, "plugin", this);

      try {
        getAsset(LICENSE).copyIn("license", REPLACE_EXISTING);
        getAsset(THIRD_PARTY_LICENSES).copyIn("license", REPLACE_EXISTING);
      } catch (IOException ex) {
        getLogger().log(Level.WARNING, "Could not save licenses", ex);
      }

      config = new HoconConfig<>(
          getRoot().resolve("config").resolve("natrolite.conf"),
          NatroliteConfig.class
      );

      serverConfig = new HoconConfig<>(
          getRoot().resolve("config").resolve("server.conf"),
          "server",
          ServerConfig.class
      );

      Dependency.of("com.flowpowered", "flow-nbt", "1.0.0").install(this);
      Dependency.of(URL, "odium.jar").install(this);

      try {
        Class.forName("org.h2.Driver");
      } catch (Throwable throwable) {
        getLogger().log(Level.WARNING, "Could not find the h2 driver");
      }

      setupDictionary();
      setupMetrics();

      register(SqlService.class, new SqlServiceImpl(), ServicePriority.Low);

      this.serverId = readUUID();
      this.serverName = serverConfig.getConfig().name();

      unb(this, "system.server.uuid").args(serverId).build().info(getLogger());
    } catch (Throwable throwable) {
      this.throwable = throwable;
    }
  }

  @Override
  public void onEnable() {
    try {
      final long start = System.currentTimeMillis();

      if (throwable != null) {
        throw throwable;
      }

      getCommand("services").setExecutor(new ServicesCommand(this));

      this.servicesManager = new NatroliteServicesManager(this);
      this.serverManager = new NatroliteServerManager(this);

      unb(this, "system.enabled").args(System.currentTimeMillis() - start)
          .build()
          .info(getLogger());
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

    try {
      if (serverManager != null) {
        serverManager.cancel();
      }
    } catch (Throwable throwable) {
      getLogger().log(Level.WARNING, "Could not stop server manager on shutdown", throwable);
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

  public String getServerName() {
    return serverName;
  }

  public NatroliteConfig getSettings() {
    return config.getConfig();
  }

  @Override
  public TypeSerializerCollection getSerializers() {
    return serializers;
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

  private void setupMetrics() {
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
  }

  private void setupDictionary() {
    final Locale def = getSettings().general().lang().locale();
    @Nonnull TranslationDictionary dictionary;
    if (getSettings().general().lang().custom()) {
      try {
        getAsset("natrolite_en.properties").copyIn("message");
        getAsset("natrolite_de.properties").copyIn("message");
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      dictionary = new MultiSourceResourceBundleTranslationDictionary(this, def, BUNDLE_NAME);
      //TODO custom messages
    } else {
      dictionary = new SimpleResourceBundleTranslationDictionary(this, def, BUNDLE_NAME);
    }
    getServicesManager().register(
        TranslationDictionary.class, dictionary, this, ServicePriority.Low
    );
    unb(this, "system.language").args(capitalizeFirst(def.getDisplayName(def))).build()
        .info(getLogger());
  }
}

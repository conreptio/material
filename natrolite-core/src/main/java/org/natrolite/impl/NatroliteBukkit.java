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
import static org.natrolite.impl.StaticMessageProvider.in;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.natrolite.BetterPlugin;
import org.natrolite.Natrolite;
import org.natrolite.NatroliteInternal;
import org.natrolite.NatrolitePlugin;
import org.natrolite.configurate.types.HoconConfig;
import org.natrolite.impl.config.NatroliteConfig;
import org.natrolite.impl.service.sql.SqlServiceImpl;
import org.natrolite.metrics.Metrics;
import org.natrolite.service.sql.SqlService;
import org.natrolite.updater.Spigot;
import org.natrolite.util.ReflectionUtil;

@Spigot("39140")
public final class NatroliteBukkit extends BetterPlugin implements NatroliteInternal {

  public static final String LICENSE = "LICENSE.txt";
  public static final String THIRD_PARTY_LICENSES = "THIRD-PARTY-LICENSES.txt";
  private static NatroliteBukkit plugin;
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
    saveResource(LICENSE, true);
    saveResource(THIRD_PARTY_LICENSES, true);

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
  public Path getRoot() {
    return getDataFolder().toPath();
  }

  @Override
  public NatroliteBukkit getPlugin() {
    return this;
  }

  public NatroliteConfig getSettings() {
    return config.getConfig();
  }

  private <T> void register(Class<T> clazz, T provider, ServicePriority priority) {
    getServer().getServicesManager().register(clazz, provider, this, priority);
  }
}

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

import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.plugin.Plugin;
import org.natrolite.BetterPlugin;
import org.natrolite.Natrolite;
import org.natrolite.NatroliteInternal;
import org.natrolite.NatrolitePlugin;
import org.natrolite.metrics.Metrics;
import org.natrolite.updater.Spigot;
import org.natrolite.util.ReflectionUtil;

@Spigot("39140")
public final class NatroliteBukkit extends BetterPlugin implements NatroliteInternal {

  @Override
  public void onLoad() {
    ReflectionUtil.setFinalStatic(Natrolite.class, "natrolite", this);
    saveResource("THIRD-PARTY.txt", true);
    saveResource("LICENSE.txt", true);
    saveResource("database.properties", false);
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
}

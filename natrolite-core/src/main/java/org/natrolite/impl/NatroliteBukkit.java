/*
 * Natrolite Minigames
 *
 * Copyright (c) 2017 XNITY <info@xnity.net>
 * Copyright (c) 2017 Natrolite <info@natrolite.org>
 * Copyright (c) 2017 Lukas Nehrke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.impl;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import org.bstats.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.natrolite.NatroliteInternal;
import org.natrolite.NatrolitePlugin;

public final class NatroliteBukkit extends JavaPlugin implements NatroliteInternal {

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

      getLogger().log(Level.INFO, "Plugin enabled ({0}ms)", System.currentTimeMillis() - start);
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
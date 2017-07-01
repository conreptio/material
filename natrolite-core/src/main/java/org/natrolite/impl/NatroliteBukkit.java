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
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.bstats.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.natrolite.Natrolite;
import org.natrolite.NatroliteInternal;
import org.natrolite.NatrolitePlugin;
import org.natrolite.arena.ArenaService;
import org.natrolite.impl.arena.NatroliteArenaService;
import org.natrolite.impl.arena.types.NatroliteRegionArena;
import org.natrolite.impl.arena.types.NatroliteWorldArena;
import org.natrolite.impl.commands.ArenaCommand;
import org.natrolite.impl.map.NatroliteMapService;
import org.natrolite.impl.serialisation.ArenaSerializer;
import org.natrolite.map.MapService;
import org.natrolite.updater.Spigot;
import org.natrolite.util.ReflectionUtil;

@Spigot("39140")
public final class NatroliteBukkit extends JavaPlugin implements NatroliteInternal {

  private NatroliteRegistry registry;
  private TypeSerializerCollection serializers;

  @Override
  public void onLoad() {
    ReflectionUtil.setFinalStatic(Natrolite.class, "natrolite", this);

    registry = new NatroliteRegistry();

    register(MapService.class, new NatroliteMapService(this), ServicePriority.Low);
    register(ArenaService.class, new NatroliteArenaService(this), ServicePriority.Low);

    registry.register("world", NatroliteWorldArena.class, NatroliteWorldArena.factory());
    registry.register("region", NatroliteRegionArena.class, NatroliteRegionArena.factory());
  }

  @Override
  public void onEnable() {
    try {
      final long start = System.currentTimeMillis();

      getCommand("arena").setExecutor(new ArenaCommand(registry));

      serializers = TypeSerializers.getDefaultSerializers().newChild();
      ArenaSerializer.register(serializers, this);

      registry.bake();
      in(getLogger(), registry.size() == 1 ? "game.loaded" : "games.loaded", registry.size());

      final MapService map = Natrolite.getService(MapService.class);
      map.loadMaps();
      in(getLogger(), map.getSize() == 1 ? "map.loaded" : "maps.loaded", map.getSize());

      final ArenaService arena = Natrolite.getService(ArenaService.class);
      arena.loadArenas();
      in(getLogger(), arena.getSize() == 1 ? "arena.loaded" : "arenas.loaded", arena.getSize());

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

      getServer().getPluginManager().registerEvents(new NatroliteUpdater(this), this);

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

  @Override
  public NatroliteRegistry getRegistry() {
    return registry;
  }

  @Override
  public TypeSerializerCollection getSerializers() {
    return serializers;
  }

  public Path resolve(String file) {
    return getRoot().resolve(file);
  }

  public ConfigurationOptions defaultOptions() {
    return ConfigurationOptions.defaults().setSerializers(getSerializers());
  }

  private <T> void register(Class<T> clazz, T provider, ServicePriority priority) {
    getServer().getServicesManager().register(clazz, provider, this, priority);
  }
}

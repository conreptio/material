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

package org.natrolite.minigames;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.natrolite.NatroliteInternal.LICENSE;
import static org.natrolite.NatroliteInternal.THIRD_PARTY_LICENSES;
import static org.natrolite.minigames.StaticMessageProvider.in;

import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Level;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.natrolite.BetterPlugin;
import org.natrolite.MinigamesInternal;
import org.natrolite.Natrolite;
import org.natrolite.NatrolitePlugin;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaService;
import org.natrolite.configurate.types.HoconConfig;
import org.natrolite.map.MapService;
import org.natrolite.metrics.Metrics;
import org.natrolite.minigames.arena.ArenaTicker;
import org.natrolite.minigames.arena.NatroliteArenaService;
import org.natrolite.minigames.arena.types.NatroliteRegionArena;
import org.natrolite.minigames.arena.types.NatroliteWorldArena;
import org.natrolite.minigames.map.NatroliteMapService;
import org.natrolite.minigames.serialisation.ArenaSerializer;
import org.natrolite.minigames.serialisation.SignSerializer;
import org.natrolite.minigames.sign.NatroliteSignService;
import org.natrolite.minigames.sign.types.ArenaSign;
import org.natrolite.minigames.sign.types.CakeSign;
import org.natrolite.minigames.sign.types.PluginSign;
import org.natrolite.sign.GameSign;
import org.natrolite.sign.SignService;

public final class MinigamesBukkit extends BetterPlugin implements MinigamesInternal {

  private final NatroliteServicesManager servicesManager = new NatroliteServicesManager(this);
  private final NatroliteRegistry registry = new NatroliteRegistry();
  private TypeSerializerCollection serializers;
  private HoconConfig<NatroliteConfig> config;

  @Override
  public void onLoad() {

    try {
      getAsset(LICENSE).copyIn("license", REPLACE_EXISTING);
      getAsset(THIRD_PARTY_LICENSES).copyIn("license", REPLACE_EXISTING);
    } catch (IOException ex) {
      getLogger().log(Level.WARNING, "Could not save licenses", ex);
    }

    config = new HoconConfig<>(
        getRoot().resolve("natrolite.conf"),
        "natrolite",
        NatroliteConfig.class
    );

    register(MapService.class, new NatroliteMapService(this), ServicePriority.Low);
    register(ArenaService.class, new NatroliteArenaService(this), ServicePriority.Low);
    register(SignService.class, new NatroliteSignService(this), ServicePriority.Low);

    registry.register("world", NatroliteWorldArena.class, NatroliteWorldArena.factory());
    registry.register("region", NatroliteRegionArena.class, NatroliteRegionArena.factory());

    registry.register("arena", ArenaSign.class, ArenaSign.factory());
    registry.register("cake", CakeSign.class, CakeSign.factory());
    registry.register("plugin", PluginSign.class, PluginSign.factory());
  }

  @Override
  public void onEnable() {
    try {
      final long start = System.currentTimeMillis();

      //getCommand("arena").setExecutor(new ArenaCommand(registry));

      registry.bake();

      serializers = TypeSerializers.getDefaultSerializers().newChild();
      serializers.registerType(TypeToken.of(Arena.class), new ArenaSerializer(this));
      serializers.registerType(TypeToken.of(GameSign.class), new SignSerializer(this));

      in(getLogger(), registry.size() == 1 ? "game.loaded" : "games.loaded", registry.size());

      final MapService map = Natrolite.provideUnchecked(MapService.class);
      map.loadMaps();
      in(getLogger(), map.getSize() == 1 ? "map.loaded" : "maps.loaded", map.getSize());

      final ArenaService arena = Natrolite.provideUnchecked(ArenaService.class);
      arena.loadArenas();
      in(getLogger(), arena.getSize() == 1 ? "arena.loaded" : "arenas.loaded", arena.getSize());

      final SignService sign = Natrolite.provideUnchecked(SignService.class);
      sign.loadSigns();
      final int signAmount = sign.getSigns().size();
      in(getLogger(), signAmount == 1 ? "sign.load.one" : "sign.load", signAmount);

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

      getServer().getPluginManager().registerEvents(servicesManager, this);
      getServer().getPluginManager().registerEvents(new NatroliteUpdater(this), this);

      ArenaTicker.start(this);

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
  public MinigamesBukkit getPlugin() {
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

  @Override
  public void saveResource(String resourcePath, boolean replace) {
    if (!Files.exists(getRoot().resolve(resourcePath)) || replace) {
      super.saveResource(resourcePath, replace);
    }
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

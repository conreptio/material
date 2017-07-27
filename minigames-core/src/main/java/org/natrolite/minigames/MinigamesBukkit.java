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
import static org.natrolite.Natrolite.getSerializers;
import static org.natrolite.internal.NatroliteInternal.LICENSE;
import static org.natrolite.internal.NatroliteInternal.THIRD_PARTY_LICENSES;
import static org.natrolite.text.Text.unb;

import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.natrolite.MinigamesInternal;
import org.natrolite.MinigamesPlugin;
import org.natrolite.Natrolite;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaService;
import org.natrolite.dictionary.TranslationDictionary;
import org.natrolite.dictionary.bundle.MultiSourceResourceBundleTranslationDictionary;
import org.natrolite.dictionary.bundle.SimpleResourceBundleTranslationDictionary;
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
import org.natrolite.plugin.BetterPlugin;
import org.natrolite.sign.GameSign;
import org.natrolite.sign.SignService;

public final class MinigamesBukkit extends BetterPlugin implements MinigamesInternal {

  private final NatroliteRegistry registry = new NatroliteRegistry();

  @Nullable
  private Throwable throwable;

  @Override
  public void onLoad() {
    try {
      try {
        getAsset(LICENSE).copyIn("license", REPLACE_EXISTING);
        getAsset(THIRD_PARTY_LICENSES).copyIn("license", REPLACE_EXISTING);
      } catch (IOException ex) {
        getLogger().log(Level.WARNING, "Could not save licenses", ex);
      }

      register(MapService.class, new NatroliteMapService(this), ServicePriority.Low);
      register(ArenaService.class, new NatroliteArenaService(this), ServicePriority.Low);
      register(SignService.class, new NatroliteSignService(this), ServicePriority.Low);

      registry.register("world", NatroliteWorldArena.class, NatroliteWorldArena.factory());
      registry.register("region", NatroliteRegionArena.class, NatroliteRegionArena.factory());

      registry.register("arena", ArenaSign.class, ArenaSign.factory());
      registry.register("cake", CakeSign.class, CakeSign.factory());
      registry.register("plugin", PluginSign.class, PluginSign.factory());

      setupDictionary();
      setupMetrics();
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

      //getCommand("arena").setExecutor(new ArenaCommand(registry));

      registry.bake();

      getSerializers().registerType(TypeToken.of(Arena.class), new ArenaSerializer(this));
      getSerializers().registerType(TypeToken.of(GameSign.class), new SignSerializer(this));

      final MapService map = Natrolite.provideUnchecked(MapService.class);
      map.loadMaps();

      Optional<ArenaService> arena = Natrolite.provide(ArenaService.class);
      if (arena.isPresent() && arena.get() instanceof NatroliteArenaService) {
        arena.get().loadArenas();
      }

      final SignService sign = Natrolite.provideUnchecked(SignService.class);
      sign.loadSigns();

      final String st = sign.getSigns().size() == 1 ? "system.sign.load.one" : "system.sign.load";
      unb(this, st).args(sign.getSigns().size()).build().info(getLogger());

      ArenaTicker.start(this);

      final long end = System.currentTimeMillis() - start;
      unb(Natrolite.getPlugin(), "system.enabled").args(end).build().info(getLogger());
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

  private <T> void register(Class<T> clazz, T provider, ServicePriority priority) {
    getServer().getServicesManager().register(clazz, provider, this, priority);
  }

  private void setupMetrics() {
    try {
      Metrics metrics = new Metrics(this);
      metrics.addCustomChart(new Metrics.AdvancedPie("games") {
        @Override
        public HashMap<String, Integer> getValues(HashMap<String, Integer> valueMap) {
          HashMap<String, Integer> map = new HashMap<>();
          for (Plugin plugin : getServer().getPluginManager().getPlugins()) {
            if (plugin instanceof MinigamesPlugin) {
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
    final Locale def = Natrolite.getNatrolite().getSettings().general().lang().locale();
    @Nonnull TranslationDictionary dictionary;
    if (Natrolite.getNatrolite().getSettings().general().lang().custom()) {
      try {
        getAsset(BUNDLE_NAME + "_en.properties").copyIn("message");
        getAsset(BUNDLE_NAME + "_de.properties").copyIn("message");
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
  }
}

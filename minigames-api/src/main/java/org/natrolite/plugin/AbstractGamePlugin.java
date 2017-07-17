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

package org.natrolite.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.Optional;
import javax.annotation.Nullable;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.natrolite.game.Game;
import org.natrolite.map.MapSettings;

public abstract class AbstractGamePlugin<G extends Game>
    extends JavaPlugin
    implements GamePlugin<G>, Listener {

  private final Class<G> gameClass;
  @Nullable private final Class<? extends MapSettings> settings;

  public AbstractGamePlugin(Class<G> gameClass, Class<? extends MapSettings> settings) {
    this.gameClass = checkNotNull(gameClass);
    this.settings = settings;

    //TODO Natrolite.getGameRegistry().register(this);
    getServer().getPluginManager().registerEvents(this, this);
  }

  @Override
  public final File getFile() {
    return super.getFile();
  }

  @Override
  public final Class<G> getGameClass() {
    return gameClass;
  }

  @Override
  public final Optional<Class<? extends MapSettings>> getMapSettings() {
    return Optional.ofNullable(settings);
  }
}
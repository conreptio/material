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

package org.natrolite.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.natrolite.Natrolite;
import org.natrolite.game.Game;

public abstract class AbstractGamePlugin<G extends Game>
    extends JavaPlugin
    implements GamePlugin<G>, Listener {

  private final Class<G> gameClass;

  public AbstractGamePlugin(Class<G> gameClass) {
    this.gameClass = checkNotNull(gameClass);
    Natrolite.getGameRegistry().register(this);
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
}

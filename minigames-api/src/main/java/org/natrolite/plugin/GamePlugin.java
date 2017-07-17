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

import java.util.Optional;
import org.bukkit.plugin.Plugin;
import org.natrolite.Nameable;
import org.natrolite.NatrolitePlugin;
import org.natrolite.arena.Arena;
import org.natrolite.game.Game;
import org.natrolite.map.MapSettings;
import org.natrolite.updater.Updatable;

public interface GamePlugin<G extends Game> extends Plugin, Nameable, NatrolitePlugin, Updatable {

  G createGame(Arena arena);

  Class<G> getGameClass();

  default Optional<Class<? extends MapSettings>> getMapSettings() {
    return Optional.empty();
  }
}

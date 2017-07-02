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

package org.natrolite.impl.arena;

import org.natrolite.Natrolite;
import org.natrolite.arena.ArenaService;
import org.natrolite.impl.NatroliteBukkit;

public final class ArenaTicker implements Runnable {

  private static final ArenaTicker instance = new ArenaTicker();

  private ArenaTicker() {}

  public static void start(NatroliteBukkit plugin) {
    plugin.getServer().getScheduler().runTaskTimer(plugin, instance, 0, 1L);
  }

  @Override
  public void run() {
    Natrolite.provideUnchecked(ArenaService.class).getArenas().forEach(arena -> {
      arena.tick();
      arena.tickOther();
    });
  }
}

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

package org.natrolite.impl.arena.types;

import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.command.CommandSender;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.arena.types.WorldArena;
import org.natrolite.impl.arena.NatroliteArena;

public class NatroliteWorldArena extends NatroliteArena implements WorldArena {

  private NatroliteWorldArena(String id) {
    super(id);
  }

  public static NatroliteWorldArena.Factory factory() {
    return new NatroliteWorldArena.Factory();
  }

  public static class Factory implements ArenaFactory<NatroliteWorldArena> {

    @Override
    public NatroliteWorldArena build(String id, ConfigurationNode value) {
      return new NatroliteWorldArena(id);
    }

    @Override
    public Optional<NatroliteWorldArena> build(String id, CommandSender sender, String[] args) {
      return Optional.of(new NatroliteWorldArena(id));
    }
  }
}

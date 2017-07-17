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

package org.natrolite.minigames.commands;

import static java.util.stream.Collectors.joining;
import static org.natrolite.minigames.StaticMessageProvider.mg;

import java.util.List;
import java.util.Optional;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.natrolite.Natrolite;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.arena.ArenaService;
import org.natrolite.arena.ArenaStates;
import org.natrolite.minigames.NatroliteRegistry;
import org.natrolite.util.DuplicationException;

public final class ArenaCommand implements CommandExecutor {

  private final NatroliteRegistry registry;

  public ArenaCommand(NatroliteRegistry registry) {
    this.registry = registry;
  }

  private static String color(Arena arena) {
    return (arena.getState() == ArenaStates.OFFLINE ? ChatColor.RED : ChatColor.GREEN)
        + arena.getId() + ChatColor.RESET;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("arena")) {

      boolean list = false;

      if (args.length == 0) {
        if (label.equalsIgnoreCase("arenas")) {
          list = true;
        } else {
          mg(sender, "help.header", "/arena");
          mg(sender, "help.command", "/arena list");
          mg(sender, "help.command", "/arena create <id> <type>");
          return true;
        }
      }

      if (list || args[0].equalsIgnoreCase("list")) {
        final List<Arena> arenas = Natrolite.provideUnchecked(ArenaService.class).getArenas();
        mg(sender, "arena.list", arenas.size(),
            arenas.stream().map(ArenaCommand::color).collect(joining(", ")));
        return true;
      }

      if (args[0].equalsIgnoreCase("create")) {

        if (args.length != 3) {
          mg(sender, "invalid.syntax", "/arena create <id> <type>");
          return true;
        }

        final String id = args[1].trim();
        final String type = args[2];
        final Optional<? extends ArenaFactory<?>> factory = registry.getArena(type);

        if (!factory.isPresent()) {
          mg(sender, "arena.type.unknown", type);
          mg(sender, "arena.type.list", registry.getArenaIds().stream().collect(joining(", ")));
          return true;
        }

        final ArenaService service = Natrolite.provideUnchecked(ArenaService.class);

        if (!service.isValid(id)) {
          mg(sender, "arena.id.invalid");
          return true;
        }

        try {
          if (service.getArena(id).isPresent()) {
            throw new DuplicationException();
          }
          final Optional<? extends Arena> arena = factory.get().build(id, sender, args);
          if (arena.isPresent()) {
            service.addArena(arena.get());
            mg(sender, "arena.creation.success", id);
          }
        } catch (DuplicationException ex) {
          mg(sender, "arena.creation.duplication", id);
        }
        return true;
      }
      mg(sender, "command.unknown", "/arena");
      return true;
    }
    return false;
  }
}

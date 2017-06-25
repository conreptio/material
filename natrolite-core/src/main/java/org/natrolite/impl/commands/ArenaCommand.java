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

package org.natrolite.impl.commands;

import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.natrolite.arena.Arena;
import org.natrolite.arena.ArenaFactory;
import org.natrolite.impl.NatroliteRegistry;

public final class ArenaCommand implements CommandExecutor {

  private final NatroliteRegistry registry;

  public ArenaCommand(NatroliteRegistry registry) {
    this.registry = registry;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("arena")) {

      if (args.length == 0) {
        sender.sendMessage("/arena create <type> <id>");
        return true;
      }

      if (args[0].equalsIgnoreCase("create")) {

        if (args.length != 3) {
          sender.sendMessage("Syntax: /arena create <type> <id>");
          return true;
        }

        final String type = args[1];
        final String id = args[2].trim();
        final Optional<ArenaFactory<?>> factory = registry.getArena(type);

        if (!factory.isPresent()) {
          sender.sendMessage("Unknown type '" + type + "'.");
          sender.sendMessage("Available arena types: " +
              registry.getRegisteredArenaIds().stream().collect(Collectors.joining(", ")));
          return true;
        }

        if (id.isEmpty() || id.contains(" ")) {
          sender.sendMessage("The arena may only contain letters and numbers.");
          return true;
        }

        final Optional<? extends Arena> arena = factory.get().build(id, sender, args);
        if (arena.isPresent()) {
          sender.sendMessage("Success! Arena [" + id + "] has been created.");
        }
        return true;
      }
      sender.sendMessage("Unknown sub-command. Use '/arena' to get a list of available " +
          "commands or check our documentation for help.");
      return true;
    }
    return false;
  }
}

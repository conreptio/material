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

package org.natrolite.impl.command;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.natrolite.text.format.TextColor.YELLOW;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.text.Text;

public final class ServicesCommand implements CommandExecutor {

  private final NatroliteBukkit plugin;

  public ServicesCommand(NatroliteBukkit plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equalsIgnoreCase("services")) {
      final Collection<Class<?>> services = Bukkit.getServicesManager().getKnownServices();
      if (!services.isEmpty()) {
        Text.get(plugin, "services.loaded").ifPresent(t -> t.toBuilder().args(services.size())
            .color(YELLOW).build().to(sender));
        for (Class<?> service : services) {
          final StringBuilder builder = new StringBuilder(AQUA + service.getSimpleName() + " ");
          final RegisteredServiceProvider pr = Bukkit.getServicesManager().getRegistration(service);
          if (pr == null) {
            builder.append(RED).append("(No Provider)");
          } else {
            builder.append(GREEN).append('(').append(pr.getPlugin().getName()).append(')');
          }
          sender.sendMessage(builder.toString());
        }
      }
      return true;
    }
    return false;
  }
}

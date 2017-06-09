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

package org.natrolite.impl;

import java.util.Optional;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.natrolite.lang.legacy.SimpleMessageProvider;

public final class StaticMessageProvider extends SimpleMessageProvider {

  private static final StaticMessageProvider INSTANCE = new StaticMessageProvider();

  private StaticMessageProvider() {
    super("natrolite");
  }

  public static void in(Logger logger, String key, Object... args) {
    tr(key, args).ifPresent(logger::info);
  }

  public static void wn(Logger logger, String key, Object... args) {
    tr(key, args).ifPresent(logger::warning);
  }

  public static void sv(Logger logger, String key, Object... args) {
    tr(key, args).ifPresent(logger::severe);
  }

  public static void mg(Player player, String key, Object... args) {
    tr(key, args).map(s -> ChatColor.translateAlternateColorCodes('&', s))
        .ifPresent(player::sendMessage);
  }

  public static Optional<String> tr(String key, Object... args) {
    return INSTANCE.get(key, args);
  }
}

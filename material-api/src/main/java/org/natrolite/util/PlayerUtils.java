/*
 * This file is part of Material.
 *
 * Copyright (c) 2016-2017 Neolumia
 *
 * Material is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Material is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Material. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.util;

import java.util.Locale;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import org.bukkit.entity.Player;

@ThreadSafe
public final class PlayerUtils {

  private PlayerUtils() {}

  /**
   * Gets the players client language.
   *
   * @param player the player
   * @return The players client locale or {@link Optional#empty()} if it could not be retrieved
   */
  public static Optional<Locale> getLocale(@Nullable Player player) {
    try {
      if (player != null) {
        final String lang = player.spigot().getLocale();
        if (lang != null) {
          return formatMinecraftLocale(lang);
        }
      }
      return Optional.empty();
    } catch (Throwable throwable) {
      return Optional.empty();
    }
  }

  /**
   * Formats the Minecraft language to to a {@link Locale}.
   *
   * @param tag the language tag
   * @return The locale or {@link Optional#empty()} if it could not be parsed
   */
  public static Optional<Locale> formatMinecraftLocale(String tag) {
    try {
      final String[] parts = tag.split("[_.]");
      if (parts.length == 1) {
        return Optional.of(new Locale(parts[0]));
      }
      if (parts.length == 2) {
        return Optional.of(new Locale(parts[0], parts[1]));
      }
      if (parts.length == 3) {
        return Optional.of(new Locale(parts[0], parts[1], parts[2]));
      }
      return Optional.empty();
    } catch (Throwable throwable) {
      return Optional.empty();
    }
  }

  /**
   * Resets a player.
   *
   * @param player the player to reset
   */
  public static void reset(@Nullable Player player) {
    if (player != null) {
      player.setFlying(false);
      player.setAllowFlight(false);
      player.setExp(0);
      player.setLevel(0);
      player.setHealth(20.0);
      player.setFoodLevel(0);
      player.getInventory().clear();
      player.getInventory().setArmorContents(null);
      player.resetPlayerTime();
      player.resetPlayerWeather();
    }
  }
}

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

package org.natrolite.menu;

import java.util.Optional;
import org.bukkit.entity.Player;
import org.natrolite.annotations.Experimental;

@Experimental
public interface MenuManager {

  int DEFAULT_PAGE = 0;

  default void openMenu(Player player, Menu menu) {
    openMenu(player, menu, DEFAULT_PAGE);
  }

  void openMenu(Player player, Menu menu, int position);

  default Optional<Menu> getMenu(Player player) {
    return getContext(player).map(MenuContext::getMenu);
  }

  Optional<MenuContext> getContext(Player player);

  default boolean hasMenu(Player player) {
    return getMenu(player).isPresent();
  }
}

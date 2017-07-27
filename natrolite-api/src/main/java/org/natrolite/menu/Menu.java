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

package org.natrolite.menu;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import java.util.stream.Stream;
import org.bukkit.entity.Player;
import org.natrolite.Natrolite;
import org.natrolite.annotations.Experimental;
import org.natrolite.registry.ResettableBuilder;

@Experimental
public interface Menu {

  static Menu.Builder builder() {
    return Natrolite.getRegistry().createBuilder(Builder.class);
  }

  /**
   * Gets an {@link ImmutableList} of all pages.
   */
  ImmutableList<Page> getPages();

  /**
   * Gets a {@link Page}.
   *
   * @param pos the position of the page
   * @return The page or {@link Optional#empty()} if it could not be found
   */
  Optional<Page> getPage(int pos);

  /**
   * Creates a new {@link Builder} instance from this {@link Menu}.
   */
  default Builder toBuilder() {
    return builder().from(this);
  }

  default void open(Player... players) {
    Stream.of(players).forEach(p -> Natrolite.getMenuManager().openMenu(p, this));
  }

  interface Builder extends ResettableBuilder<Menu, Menu.Builder> {

    Menu.Builder append(Page... pages);

    Menu.Builder insert(int pos, Page... pages);

    Menu.Builder remove(Page... pages);

    @Override
    Menu.Builder from(Menu value);

    @Override
    Menu.Builder reset();

    /**
     * Builds the {@link Menu}.
     */
    Menu build();
  }
}

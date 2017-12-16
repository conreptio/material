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

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.natrolite.Natrolite;
import org.natrolite.annotations.Experimental;
import org.natrolite.registry.ResettableBuilder;
import org.natrolite.text.Text;

@Experimental
public interface Page {

  static Builder builder() {
    return Natrolite.getRegistry().createBuilder(Builder.class);
  }

  /**
   * Gets the title of this {@link Page}.
   *
   * @see Inventory#getTitle()
   */
  Text getTitle();

  /**
   * Gets the {@link InventoryType}.
   */
  InventoryType getType();

  /**
   * Gets the {@link Inventory} size.
   */
  int getSize();

  /**
   * Gets an {@link ImmutableMap} of all {@link Icon}s on this {@link Page}.
   */
  ImmutableMap<Integer, Icon> getIcons();

  default Optional<Icon> getIcon(int pos) {
    return Optional.ofNullable(getIcons().get(pos));
  }

  /**
   * Applies this page to an {@link Inventory}.
   */
  default Inventory fill(Inventory inventory) {
    inventory.clear();
    getIcons().forEach((i, ic) -> inventory.setItem(i, ic.getItem()));
    return inventory;
  }

  /**
   * Checks if the {@link InventoryType} is {@link InventoryType#CHEST}.
   */
  default boolean isChest() {
    return getType() == InventoryType.CHEST;
  }

  /**
   * Creates a new {@link Builder} instance from this {@link Page}.
   */
  default Builder toBuilder() {
    return builder().from(this);
  }

  /**
   * Builds a new {@link Inventory} by this {@link Page}.
   */
  default Inventory toInventory() {
    return fill(isChest()
        ? Bukkit.createInventory(null, getSize(), getTitle().toPlain())
        : Bukkit.createInventory(null, getType(), getTitle().toPlain()));
  }

  interface Builder extends ResettableBuilder<Page, Page.Builder> {

    /**
     * Sets the title.
     *
     * @see Builder#title(Text)
     */
    default Page.Builder title(String title) {
      return title(Text.of(title));
    }

    /**
     * Sets the title.
     *
     * @see Builder#title(String)
     */
    Page.Builder title(Text title);

    /**
     * Sets the {@link InventoryType}.
     */
    Page.Builder type(InventoryType type);

    /**
     * Sets the {@link Inventory} size.
     *
     * <p>The size must be greater than 0.
     *
     * @param size Size of the inventory
     */
    Page.Builder size(int size);

    /**
     * Adds a new {@link Icon}.
     *
     * @param pos  the position (slot) of the icon
     * @param icon the icon to add
     */
    Page.Builder icon(int pos, Icon icon);

    @Override
    Page.Builder from(Page value);

    @Override
    Page.Builder reset();

    /**
     * Builds the {@link Page}.
     */
    Page build();
  }
}

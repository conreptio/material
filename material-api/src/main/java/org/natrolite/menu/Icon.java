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

import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.natrolite.Natrolite;
import org.natrolite.annotations.Experimental;
import org.natrolite.item.Item;
import org.natrolite.registry.ResettableBuilder;

@Experimental
public interface Icon {

  static Builder builder() {
    return Natrolite.getRegistry().createBuilder(Builder.class);
  }

  static Icon of(Item item) {
    return Icon.builder().item(item).build();
  }

  static Icon of(ItemStack item) {
    return Icon.builder().item(item).build();
  }

  static Icon of(Material material) {
    return Icon.builder().item(Item.of(material)).build();
  }

  static Icon of(Material material, int amount) {
    return Icon.builder().item(Item.of(material, amount)).build();
  }

  static Icon of(Material material, Consumer<MenuArguments> consumer) {
    return Icon.builder().item(Item.of(material)).click(consumer).build();
  }

  static Icon of(Material material, int amount, Consumer<MenuArguments> consumer) {
    return Icon.builder().item(Item.of(material, amount)).click(consumer).build();
  }

  /**
   * Gets the {@link ItemStack}.
   */
  ItemStack getItem();

  /**
   * Creates a new {@link Builder} instance from this {@link Icon}.
   */
  default Builder toBuilder() {
    return builder().from(this);
  }

  Consumer<MenuArguments> click();

  interface Builder extends ResettableBuilder<Icon, Icon.Builder> {

    /**
     * Sets the {@link ItemStack} by an {@link Item}.
     */
    default Icon.Builder item(Item item) {
      return item(item.toItemStack());
    }

    /**
     * Sets the {@link ItemStack}.
     */
    Icon.Builder item(ItemStack item);

    Icon.Builder click(Consumer<MenuArguments> consumer);

    @Override
    Icon.Builder from(Icon value);

    @Override
    Icon.Builder reset();

    /**
     * Builds the {@link Icon}.
     */
    Icon build();
  }
}

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

package org.natrolite.item;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnegative;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.natrolite.Natrolite;
import org.natrolite.registry.ResettableBuilder;

public interface Item {

  static Item.Builder builder() {
    return Natrolite.getRegistry().createBuilder(Builder.class);
  }

  static Item of(Material material) {
    return builder().material(material).build();
  }

  static Item of(Material material, int amount) {
    return builder().material(material).amount(amount).build();
  }

  int getAmount();

  void setAmount(int amount);

  short getDurability();

  List<ItemFlag> getFlags();

  void setFlags(List<ItemFlag> flags);

  Optional<String> getLocalizedName();

  void setLocalizedName(String localizedName);

  List<String> getLore();

  void setLore(List<String> lore);

  Material getMaterial();

  void setMaterial(Material material);

  Optional<MaterialData> getMaterialData();

  void setMaterialData(@Nullable MaterialData data);

  default short getMaxDurability() {
    return getMaterial().getMaxDurability();
  }

  default int getMaxStackSize() {
    return getMaterial().getMaxStackSize();
  }

  Optional<String> getName();

  void setName(String name);

  boolean isUnbreakable();

  void setUnbreakable(boolean unbreakable);

  ItemStack toItemStack();

  interface Builder extends ResettableBuilder<Item, Item.Builder> {

    /**
     * Sets the {@link Material} of this {@link ItemStack}.
     *
     * <p>You can also use {@link Builder#material(int)} with the material id.
     */
    Item.Builder material(Material material);

    /**
     * Sets the {@link Material} using its unique id.
     *
     * @throws IllegalArgumentException if the id is negative or does not belong to a material
     * @see Material
     * @see ItemStack#setType(Material)
     */
    Item.Builder material(@Nonnegative int id);

    /**
     * Sets the {@link Material} using its name.
     *
     * <p>The name is equal with the {@link Enum#name()}, e.g AIR.
     *
     * @throws IllegalArgumentException if the id does not belong to any material
     * @see Material
     * @see Material#name()
     */
    Item.Builder material(String name);

    /**
     * Sets the quantity.
     *
     * <p>The value must be greater than zero or an exception will be thrown.
     *
     * <p></p><b>Note:</b>Setting the amount higher than the max stack size can cause issues.
     *
     * @throws IllegalArgumentException if the amount is not greater than zero
     * @see ItemStack#setAmount(int)
     * @see Material#getMaxStackSize()
     */
    Item.Builder amount(@Nonnegative int amount);

    /**
     * Sets the {@link MaterialData}.
     *
     * <p>The data is for example used to change the color of a {@link Wool} block.
     *
     * <p>You can also use {@link Builder#data(byte)} to set the data by its id.
     *
     * @see MaterialData
     * @see ItemStack#setData(MaterialData)
     */
    Item.Builder data(MaterialData data);

    /**
     * Sets the {@link MaterialData} by its id.
     *
     * <p><b>Note:</b> Make sure you already have set the material
     *
     * @see MaterialData#MaterialData(Material, byte)
     * @see ItemStack#setData(MaterialData)
     */
    Item.Builder data(byte data);

    /**
     * Sets the durability.
     *
     * <p>The durability can not be lower than zero.
     *
     * @throws IllegalArgumentException if {@code durability} is negative
     * @see ItemStack#setDurability(short)
     */
    Item.Builder durability(@Nonnegative short durability);

    /**
     * Sets the {@code display name}.
     *
     * @see ItemMeta#setDisplayName(String)
     */
    Item.Builder name(String name);

    /**
     * Sets the localized name.
     *
     * @see ItemMeta#setLocalizedName(String)
     */
    Item.Builder localizedName(String localizedName);

    /**
     * Makes the {@link ItemStack} unbreakable (it does not loose durability).
     *
     * <p>Use{@link ItemFlag#HIDE_UNBREAKABLE} to hide this attribute.
     *
     * @param unbreakable true to make the item unbreakable
     * @see ItemMeta#setUnbreakable(boolean)
     */
    Item.Builder unbreakable(boolean unbreakable);

    /**
     * Adds a list of {@link ItemFlag}s.
     *
     * @see ItemFlag
     * @see ItemMeta#addItemFlags(ItemFlag...)
     */
    Item.Builder flags(ItemFlag... flags);

    /**
     * Adds a list of {@link ItemFlag}s.
     *
     * @see ItemFlag
     * @see ItemMeta#addItemFlags(ItemFlag...)
     */
    Item.Builder flags(Collection<ItemFlag> flags);

    /**
     * Sets the lore.
     *
     * <p>This is the text that will be displayed below the displayname on hovering.
     *
     * @see ItemMeta#setLore(List)
     */
    Item.Builder lore(String... lore);

    /**
     * Sets the lore.
     *
     * <p>This is the text that will be displayed below the displayname on hovering.
     *
     * @see ItemMeta#setLore(List)
     */
    Item.Builder lore(Collection<String> lore);

    @Override
    Item.Builder from(Item value);

    @Override
    Item.Builder reset();

    /**
     * Builds the {@link Item}.
     *
     * @throws IllegalStateException if the item is not complete
     */
    Item build() throws IllegalStateException;

    /**
     * Builds the {@link ItemStack}.
     *
     * @throws IllegalStateException if the item is not complete
     */
    default ItemStack buildStack() throws IllegalStateException {
      return build().toItemStack();
    }
  }
}

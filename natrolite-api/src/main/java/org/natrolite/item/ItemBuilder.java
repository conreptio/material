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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

/**
 * Builder pattern for an {@link ItemStack}.
 *
 * @author Lukas Nehrke
 * @version 1.0.1
 */
public final class ItemBuilder {

  @Nullable private Material material;
  @Nullable private Integer amount;
  @Nullable private MaterialData data;
  @Nullable private Short durability;
  @Nullable private String name;
  @Nullable private String localizedName;
  @Nullable private Boolean unbreakable;
  @Nullable private ItemFlag[] flags;
  @Nullable private String[] lore;

  /**
   * Creates a new {@link ItemBuilder} instance.
   */
  public ItemBuilder() {}

  /**
   * Sets the {@link Material} of this {@link ItemStack}.
   * <p>
   * You can also use {@link ItemBuilder#material(int)} with the material id.
   */
  public ItemBuilder material(Material material) {
    this.material = checkNotNull(material, "Material cannot be null");
    return this;
  }

  /**
   * Sets the {@link Material} of this {@link ItemStack} using its unique id.
   *
   * @throws IllegalArgumentException if the id is negative or does not belong to a material
   * @see Material
   * @see ItemStack#setType(Material)
   */
  @SuppressWarnings("deprecation")
  public ItemBuilder material(int id) {
    checkArgument(id >= 0);
    final Material material = Material.getMaterial(id);
    checkArgument(material != null, "Material could not be found");
    this.material = material;
    return this;
  }

  /**
   * Sets the {@link Material} of this {@link ItemStack} using its name.
   * <p>
   * The name is equal with the {@link Enum#name()}, e.g AIR.
   *
   * @throws IllegalArgumentException if the id does not belong to any material
   * @see Material
   * @see Material#name()
   */
  public ItemBuilder material(String name) {
    final Material material = Material.getMaterial(checkNotNull(name, "Name cannot be null"));
    checkArgument(material != null, "Material could not be found");
    this.material = material;
    return this;
  }

  /**
   * Sets the {@link ItemStack}'s quantity.
   * <p>
   * The value must be greater than zero or an exception will be thrown. Setting the amount higher
   * than the max stack size can cause issues.
   *
   * @throws IllegalArgumentException if the amount is not greater than zero
   * @see ItemStack#setAmount(int)
   * @see Material#getMaxStackSize()
   */
  public ItemBuilder amount(int amount) {
    checkArgument(amount > 0);
    this.amount = amount;
    return this;
  }

  /**
   * Sets the {@link MaterialData} for this {@link ItemStack}.
   * <p>
   * The data is for example used to change the color of a {@link Wool} block.
   * You can also use {@link ItemBuilder#data(byte)} to set the data by its id.
   *
   * @see MaterialData
   * @see ItemStack#setData(MaterialData)
   */
  public ItemBuilder data(MaterialData data) {
    this.data = checkNotNull(data);
    return this;
  }

  /**
   * Sets the {@link MaterialData} for this {@link ItemStack} by its id.
   * <p>
   * <b>Note:</b> Make sure you already have set the material
   *
   * @see MaterialData#MaterialData(Material, byte)
   * @see ItemStack#setData(MaterialData)
   */
  @SuppressWarnings("deprecation")
  public ItemBuilder data(byte data) {
    checkState(material != null, "Material must be set first");
    this.data = new MaterialData(material, data);
    return this;
  }

  /**
   * Sets the durability of this {@link ItemStack}.
   * <p>
   * The durability can not be lower than zero.
   *
   * @throws IllegalArgumentException if {@code durability} is negative
   * @see ItemStack#setDurability(short)
   */
  public ItemBuilder durability(short durability) {
    checkArgument(durability >= 0);
    this.durability = durability;
    return this;
  }

  /**
   * Sets the {@code Displayname} for this {@link ItemStack}.
   * <p>
   * Color-Codes are supported.
   *
   * @see ItemMeta#setDisplayName(String)
   */
  public ItemBuilder name(String name) {
    this.name = checkNotNull(name);
    return this;
  }

  /**
   * Sets the localized name for this {@link ItemStack}.
   *
   * @see ItemMeta#setLocalizedName(String)
   */
  public ItemBuilder localizedName(String localizedName) {
    this.localizedName = checkNotNull(localizedName);
    return this;
  }

  /**
   * Makes the {@link ItemStack} unbreakable (it does not loose durability).
   * <p>
   * Use{@link ItemFlag#HIDE_UNBREAKABLE} to hide this attribute.
   *
   * @param unbreakable true to make the item unbreakable
   * @see ItemMeta#setUnbreakable(boolean)
   */
  public ItemBuilder unbreakable(boolean unbreakable) {
    this.unbreakable = unbreakable;
    return this;
  }

  /**
   * Adds a list of {@link ItemFlag}s to this {@link ItemStack}.
   *
   * @see ItemFlag
   * @see ItemMeta#addItemFlags(ItemFlag...)
   */
  public ItemBuilder flags(ItemFlag... flags) {
    this.flags = checkNotNull(flags);
    return this;
  }

  /**
   * Adds a list of {@link ItemFlag}s to this {@link ItemStack}.
   *
   * @see ItemFlag
   * @see ItemMeta#addItemFlags(ItemFlag...)
   */
  public ItemBuilder flags(Collection<ItemFlag> flags) {
    this.flags = flags.toArray(new ItemFlag[flags.size()]);
    return this;
  }

  /**
   * Sets the lore for this {@link ItemStack}.
   * <p>
   * This is the text that will be displayed below the displayname on hovering.
   * Color-Codes are supported.
   *
   * @see ItemMeta#setLore(List)
   */
  public ItemBuilder lore(String... lore) {
    this.lore = checkNotNull(lore);
    return this;
  }

  /**
   * Sets the lore for this {@link ItemStack}.
   * <p>
   * This is the text that will be displayed below the displayname on hovering.
   * Color-Codes are supported.
   *
   * @see ItemMeta#setLore(List)
   */
  public ItemBuilder lore(Collection<String> lore) {
    this.lore = lore.toArray(new String[lore.size()]);
    return this;
  }

  /**
   * Builds an {@link ItemStack} with the requested features.
   *
   * @return the requested itemstack
   * @throws IllegalStateException if no material has been set
   */
  public ItemStack build() {
    checkState(material != null, "Material has not been set");
    final ItemStack item = new ItemStack(material);
    final ItemMeta meta = item.getItemMeta();
    if (amount != null) {
      item.setAmount(amount);
    }
    if (data != null) {
      item.setData(data);
    }
    if (durability != null) {
      item.setDurability(durability);
    }
    if (name != null) {
      meta.setDisplayName(name);
    }
    if (localizedName != null) {
      meta.setLocalizedName(localizedName);
    }
    if (unbreakable != null) {
      meta.setUnbreakable(unbreakable);
    }
    if (flags != null) {
      meta.addItemFlags(flags);
    }
    if (lore != null) {
      meta.setLore(Arrays.asList(lore));
    }
    item.setItemMeta(meta);
    return item;
  }

  /**
   * Creates a {@link String} with all set attributes of the current {@link ItemBuilder} instance.
   *
   * @see Object#toString()
   */
  @Override
  @SuppressWarnings("deprecation")
  public String toString() {
    final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);
    if (material != null) {
      helper.add("material", material.name());
    }
    if (amount != null) {
      helper.add("amount", amount);
    }
    if (data != null) {
      helper.add("data", data.getData());
    }
    if (durability != null) {
      helper.add("durability", amount);
    }
    if (name != null) {
      helper.add("name", name);
    }
    if (localizedName != null) {
      helper.add("localizedName", localizedName);
    }
    if (unbreakable != null) {
      helper.add("unbreakable", unbreakable);
    }
    if (flags != null) {
      helper.add("flags", flags);
    }
    if (lore != null) {
      helper.add("lore", lore);
    }
    return helper.toString();
  }
}

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

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * Builder pattern for an {@link ItemStack}.
 *
 * @author Lukas Nehrke
 * @version 1.0
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

  public ItemBuilder() {}

  public ItemBuilder material(Material material) {
    this.material = checkNotNull(material);
    return this;
  }

  public ItemBuilder amount(int amount) {
    checkArgument(amount > 0);
    this.amount = amount;
    return this;
  }

  public ItemBuilder data(MaterialData data) {
    this.data = checkNotNull(data);
    return this;
  }

  @SuppressWarnings("deprecation")
  public ItemBuilder data(byte data) {
    this.data = new MaterialData(data);
    return this;
  }

  public ItemBuilder durability(short durability) {
    checkArgument(durability > 0);
    this.durability = durability;
    return this;
  }

  public ItemBuilder name(String name) {
    this.name = checkNotNull(name);
    return this;
  }

  public ItemBuilder localizedName(String localizedName) {
    this.localizedName = checkNotNull(localizedName);
    return this;
  }

  public ItemBuilder unbreakable(boolean unbreakable) {
    this.unbreakable = unbreakable;
    return this;
  }

  public ItemBuilder flags(ItemFlag... flags) {
    this.flags = checkNotNull(flags);
    return this;
  }

  public ItemBuilder flags(Collection<ItemFlag> flags) {
    this.flags = flags.toArray(new ItemFlag[flags.size()]);
    return this;
  }

  public ItemBuilder lore(String... lore) {
    this.lore = checkNotNull(lore);
    return this;
  }

  public ItemBuilder lore(Collection<String> lore) {
    this.lore = lore.toArray(new String[lore.size()]);
    return this;
  }

  public ItemStack build() {
    final ItemStack item = new ItemStack(checkNotNull(material, "Material cannot be null"));
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

  @Override
  @SuppressWarnings("deprecation")
  public String toString() {
    final MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(this);
    if (material != null) {
      helper.add("material", material);
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

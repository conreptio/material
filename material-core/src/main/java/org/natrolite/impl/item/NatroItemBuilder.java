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

package org.natrolite.impl.item;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.material.MaterialData;
import org.natrolite.item.Item;

public final class NatroItemBuilder implements Item.Builder {

  private int amount;
  private short durability;
  private boolean unbreakable;
  @Nullable private Material material;
  @Nullable private MaterialData data;
  @Nullable private String name;
  @Nullable private String localizedName;
  @Nullable private ItemFlag[] flags;
  @Nullable private String[] lore;

  public NatroItemBuilder() {
    reset();
  }

  @Override
  public Item.Builder material(Material material) {
    this.material = checkNotNull(material, "Material cannot be null");
    return this;
  }

  @Override
  @SuppressWarnings("deprecation")
  public Item.Builder material(int id) {
    final Material material = Material.getMaterial(id);
    checkArgument(material != null, "Material could not be found");
    this.material = material;
    return this;
  }

  @Override
  public Item.Builder material(String name) {
    final Material material = Material.getMaterial(checkNotNull(name, "Name cannot be null"));
    checkArgument(material != null, "Material could not be found");
    this.material = material;
    return this;
  }

  @Override
  public Item.Builder amount(int amount) {
    checkArgument(amount > 0, "Amount must be greater than zero");
    this.amount = amount;
    return this;
  }

  @Override
  public Item.Builder data(MaterialData data) {
    this.data = checkNotNull(data, "MaterialData cannot be null");
    return this;
  }

  @Override
  @SuppressWarnings("deprecation")
  public Item.Builder data(byte data) {
    checkState(material != null, "Material must be set first");
    this.data = new MaterialData(material, data);
    return this;
  }

  @Override
  public Item.Builder durability(short durability) {
    checkArgument(durability >= 0, "Durability must not be lesser than 0");
    this.durability = durability;
    return this;
  }

  @Override
  public Item.Builder name(String name) {
    this.name = checkNotNull(name, "Name cannot be null");
    return this;
  }

  @Override
  public Item.Builder localizedName(String localizedName) {
    this.localizedName = checkNotNull(localizedName, "LocalizedName cannot be null");
    return this;
  }

  @Override
  public Item.Builder unbreakable(boolean unbreakable) {
    this.unbreakable = unbreakable;
    return this;
  }

  @Override
  public Item.Builder flags(ItemFlag... flags) {
    this.flags = checkNotNull(flags, "Flags cannot be null");
    return this;
  }

  @Override
  public Item.Builder flags(Collection<ItemFlag> flags) {
    this.flags = flags.toArray(new ItemFlag[flags.size()]);
    return this;
  }

  @Override
  public Item.Builder lore(String... lore) {
    this.lore = checkNotNull(lore, "Lore cannot be null");
    return this;
  }

  @Override
  public Item.Builder lore(Collection<String> lore) {
    this.lore = lore.toArray(new String[lore.size()]);
    return this;
  }

  @Override
  public Item.Builder from(Item value) {
    checkNotNull(value, "Item cannot be null");
    this.material = value.getMaterial();
    this.amount = value.getAmount();
    this.data = value.getMaterialData().orElse(null);
    this.durability = value.getDurability();
    this.unbreakable = value.isUnbreakable();
    this.name = value.getName().orElse(null);
    this.localizedName = value.getLocalizedName().orElse(null);
    this.flags = value.getFlags().toArray(new ItemFlag[value.getFlags().size()]);
    this.lore = value.getLore().toArray(new String[value.getLore().size()]);
    return this;
  }

  @Override
  public Item.Builder reset() {
    this.amount = 1;
    this.unbreakable = false;
    this.durability = 0;
    this.material = null;
    this.data = null;
    this.durability = 0;
    this.name = null;
    this.localizedName = null;
    this.flags = null;
    this.lore = null;
    return this;
  }

  @Override
  public Item build() throws IllegalStateException {
    checkState(material != null, "Material has not been set");
    final NatroItem item = new NatroItem(material, amount, durability, unbreakable);
    if (data != null) {
      item.setMaterialData(data);
    }
    if (name != null) {
      item.setName(name);
    }
    if (localizedName != null) {
      item.setLocalizedName(localizedName);
    }
    if (flags != null) {
      item.setFlags(Arrays.asList(flags));
    }
    if (lore != null) {
      item.setLore(Arrays.asList(lore));
    }
    return item;
  }
}

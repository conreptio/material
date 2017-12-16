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

import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.natrolite.item.Item;

public final class NatroItem implements Item {

  private Material material;
  private int amount;
  private short durability;
  private boolean unbreakable;
  private List<ItemFlag> flags = new ArrayList<>();
  private List<String> lore = new ArrayList<>();
  @Nullable private MaterialData data;
  @Nullable private String name;
  @Nullable private String localizedName;

  NatroItem(Material material, int amount, short durability, boolean unbreakable) {
    this.material = material;
    this.amount = amount;
    this.durability = durability;
    this.unbreakable = unbreakable;
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @Override
  public void setAmount(int amount) {
    this.amount = amount;
  }

  @Override
  public short getDurability() {
    return durability;
  }

  @Override
  public List<ItemFlag> getFlags() {
    return flags;
  }

  @Override
  public void setFlags(List<ItemFlag> flags) {
    this.flags = flags;
  }

  @Override
  public Optional<String> getLocalizedName() {
    return Optional.ofNullable(localizedName);
  }

  @Override
  public void setLocalizedName(String localizedName) {
    this.localizedName = localizedName;
  }

  @Override
  public List<String> getLore() {
    return lore;
  }

  @Override
  public void setLore(List<String> lore) {
    this.lore = lore;
  }

  @Override
  public Material getMaterial() {
    return material;
  }

  @Override
  public void setMaterial(Material material) {
    this.material = material;
  }

  @Override
  public Optional<MaterialData> getMaterialData() {
    return Optional.ofNullable(data);
  }

  @Override
  public void setMaterialData(@Nullable MaterialData data) {
    this.data = data;
  }

  @Override
  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean isUnbreakable() {
    return unbreakable;
  }

  @Override
  public void setUnbreakable(boolean unbreakable) {
    this.unbreakable = unbreakable;
  }

  @Override
  public ItemStack toItemStack() {
    final ItemStack item = new ItemStack(material, amount, durability);
    final ItemMeta meta = item.getItemMeta();
    meta.setUnbreakable(unbreakable);
    if (data != null) {
      item.setData(data);
    }
    if (name != null) {
      meta.setDisplayName(name);
    }
    if (localizedName != null) {
      meta.setLocalizedName(localizedName);
    }
    if (!flags.isEmpty()) {
      meta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
    }
    if (!lore.isEmpty()) {
      meta.setLore(lore);
    }
    item.setItemMeta(meta);
    return item;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .omitNullValues()
        .add("material", material)
        .add("amount", amount)
        .add("durability", durability)
        .add("unbreakable", unbreakable)
        .add("flags", flags)
        .add("lore", lore)
        .add("data", data)
        .add("name", name)
        .add("localizedName", localizedName)
        .toString();
  }
}

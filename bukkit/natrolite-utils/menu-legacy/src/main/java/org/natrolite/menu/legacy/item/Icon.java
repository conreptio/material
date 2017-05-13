/*
 * Natrolite - Minecraft Minigame Manager
 *
 * Copyright (c) 2017 XNITY <info@xnity.net>
 * Copyright (c) 2017 Natrolite <info@natrolite.org>
 * Copyright (c) 2017 Lukas Nehrke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.menu.legacy.item;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.natrolite.menu.legacy.MenuManager;

public abstract class Icon implements MenuItem, Cloneable {

  private Material material;
  private int amount;
  private byte durability;
  @Nullable private String name;
  @Nullable private List<String> lore;

  public Icon(Material material) {
    this.material = material;
  }

  public Icon(Material material, int amount) {
    this.material = material;
    this.amount = amount;
  }

  /**
   * Creates a new instance.
   *
   * @param material   the material
   * @param amount     the amount
   * @param durability the durability
   */
  public Icon(Material material, int amount, byte durability) {
    this.material = material;
    this.amount = amount;
    this.durability = durability;
  }

  /**
   * Creates a new instance.
   *
   * @param material the material
   * @param name     the name
   * @param lore     the lore
   */
  public Icon(Material material, String name, String... lore) {
    this(material, 1, (byte) 0, name, lore);
  }

  /**
   * Creates a new instance.
   *
   * @param material   the material
   * @param amount     the amount
   * @param durability the durability
   * @param name       the name
   * @param lore       the lore
   */
  public Icon(Material material, int amount, byte durability, String name, @Nullable String... lore) {
    this.material = material;
    this.amount = amount;
    this.durability = durability;
    this.name = name;
    if (lore != null) {
      this.lore = Arrays.asList(lore);
    }
  }

  @Override
  public Material getMaterial() {
    return material;
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @Override
  public byte getDurability() {
    return durability;
  }

  @Override
  @Nullable
  public String getName() {
    return name;
  }

  @Override
  @Nullable
  public List<String> getLore() {
    return lore;
  }

  @Override
  public ItemStack toItemStack() {
    ItemStack item = new ItemStack(material, amount, durability);
    ItemMeta meta = item.getItemMeta();
    if (name != null) {
      meta.setDisplayName(name);
    }
    if (lore != null) {
      meta.setLore(lore);
    }
    item.setItemMeta(meta);
    return item;
  }

  public abstract void onClick(MenuManager menuManager, Player player, InventoryClickEvent event);
}
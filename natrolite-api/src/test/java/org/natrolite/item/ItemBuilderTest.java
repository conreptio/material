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

import org.bukkit.Material;
import org.junit.Test;

public class ItemBuilderTest {

  @Test(expected = NullPointerException.class)
  public void testDefault() {
    new ItemBuilder().build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAmount() {
    new ItemBuilder().material(Material.APPLE).amount(0).build();
  }

  /*

  @Test
  public void testBuilder() {
    ItemStack item1 = new ItemBuilder()
        .material(Material.APPLE)
        .amount(3)
        .name("My Apple")
        .unbreakable(true)
        .flags(ItemFlag.HIDE_ATTRIBUTES)
        .build();
    assertNotNull(item1);
    assertNotNull(item1.getItemMeta());
    assertSame(item1.getType(), Material.APPLE);
    assertSame(item1.getAmount(), 3);
    assertEquals(item1.getItemMeta().getDisplayName(), "My Apple");
    assertTrue(item1.getItemMeta().isUnbreakable());
    assertTrue(item1.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES));
    assertFalse(item1.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS));
  }

  */
}

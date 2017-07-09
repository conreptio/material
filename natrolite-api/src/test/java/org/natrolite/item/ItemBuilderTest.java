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

import java.util.Collections;
import org.bukkit.Material;
import org.junit.Test;

public final class ItemBuilderTest {

  @Test
  public void test() {
    new ItemBuilder().flags(Collections.emptyList()).lore(Collections.emptyList());
  }

  @Test(expected = IllegalStateException.class)
  public void testDefault() {
    new ItemBuilder().build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAmount() {
    new ItemBuilder().material(Material.APPLE).amount(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMaterial() {
    new ItemBuilder().material(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalDurability() {
    new ItemBuilder().durability((short) -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMaterialName() {
    new ItemBuilder().material("einhorn");
  }
}

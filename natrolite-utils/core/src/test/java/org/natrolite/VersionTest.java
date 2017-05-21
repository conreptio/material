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

package org.natrolite;

import org.junit.Assert;
import org.junit.Test;
import org.natrolite.util.VersionComparator;

public class VersionTest {

  private final VersionComparator comparator = new VersionComparator();

  @Test
  public void testVersion() {
    Assert.assertTrue(comparator.compare("1.0", "2.0") == -1);
    Assert.assertFalse(comparator.compare("1.0", "2.0") == 0);
    Assert.assertTrue(comparator.compare("1.0", "1.0") == 0);
    Assert.assertTrue(comparator.compare("1.0.0", "1.0") == 0);
    Assert.assertTrue(comparator.compare("2.0", "1.0") == 1);
    Assert.assertFalse(comparator.compare("1.0", "1.1") == 0);
  }
}

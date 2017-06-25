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

package spiget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.natrolite.spiget.Category;
import org.natrolite.spiget.Spiget;

public class CategoryTest {

  @Test
  public void testCategory() {
    try {
      List<Category> categories = Spiget.getCategories().get(5, TimeUnit.SECONDS);
      assertEquals(categories.get(0).getId(), (Integer) 2);
    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
      ex.printStackTrace();
      fail();
    }
  }
}

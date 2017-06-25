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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.natrolite.spiget.Resource;
import org.natrolite.spiget.Spiget;

public class ResourceTest {

  @Test
  public void testResource() {
    try {
      Resource resource = Spiget.getResource("34523").get(5, TimeUnit.SECONDS);
      assertEquals(resource.getId(), (Integer) 34523);
      assertEquals(resource.getName(), "AutomaticInventory");
    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
      ex.printStackTrace();
      fail();
    }
  }
}

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

package org.natrolite.spiget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.natrolite.spiget.client.Spiget;

public class AuthorTest {

  @Test
  public void testAuthor() throws Exception {
    try {
      Author author = Spiget.getAuthor("200439").get(5, TimeUnit.SECONDS);
      assertEquals(author.getId(), (Integer) 200439);
      assertEquals(author.getName(), "kanoxx");
    } catch (InterruptedException | ExecutionException | TimeoutException ex) {
      ex.printStackTrace();
      fail();
    }
  }
}

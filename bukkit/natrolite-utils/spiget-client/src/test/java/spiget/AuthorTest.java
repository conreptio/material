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

package spiget;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.Test;
import org.natrolite.spiget.Author;
import org.natrolite.spiget.Spiget;

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

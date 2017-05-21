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

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.Test;
import org.natrolite.util.logging.PrefixHandler;

public class PrefixHandlerTest {

  @Test
  public void testHandler() {
    PrefixHandler handler = new PrefixHandler("[test]");
    Logger logger = Logger.getLogger("test");
    assertTrue(Stream.of(logger.getHandlers()).noneMatch(PrefixHandler.class::isInstance));
    PrefixHandler.register(logger, handler);
    assertTrue(Stream.of(logger.getHandlers()).anyMatch(PrefixHandler.class::isInstance));
    PrefixHandler.register(logger, handler);
    assertTrue(
        Stream.of(logger.getHandlers()).filter(PrefixHandler.class::isInstance).count() == 1);
  }
}

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

package org.natrolite.util.logging;

import java.util.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class PrefixHandler extends Handler {

  @Nullable
  private volatile String prefix;

  public PrefixHandler(@Nullable String prefix) {
    this.prefix = prefix;
  }

  public static void register(Logger logger, String prefix) {
    register(logger, new PrefixHandler(prefix));
  }

  public static void register(Logger logger, PrefixHandler handler) {
    if (Stream.of(logger.getHandlers()).noneMatch(handler::equals)) {
      logger.addHandler(handler);
    }
  }

  @Override
  public void publish(LogRecord record) {
    if (prefix != null && !prefix.isEmpty()) {
      record.setMessage(prefix + ' ' + record.getMessage());
    }
  }

  @Override
  public void flush() {}

  @Override
  public void close() throws SecurityException {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PrefixHandler that = (PrefixHandler) o;
    return Objects.equals(prefix, that.prefix);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prefix);
  }

  @Override
  public String toString() {
    return "PrefixHandler{" + "prefix='" + prefix + '\'' + '}';
  }

  @Nullable
  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(@Nullable String prefix) {
    this.prefix = prefix;
  }
}

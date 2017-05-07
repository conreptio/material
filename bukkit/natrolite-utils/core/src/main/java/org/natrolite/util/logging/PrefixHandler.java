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

import com.google.common.base.Objects;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class PrefixHandler extends Handler {

  @Nullable
  private volatile String prefix;

  public PrefixHandler(@Nullable String prefix) {
    this.prefix = prefix;
  }

  /**
   * Creates a new {@link PrefixHandler} and adds it to a {@link Logger}.
   *
   * @param logger the logger
   * @param prefix the prefix
   */
  public static void register(Logger logger, String prefix) {
    register(logger, new PrefixHandler(prefix));
  }

  /**
   * Adds a {@link PrefixHandler} to add {@link Logger}.
   *
   * @param logger  the logger
   * @param handler the handler
   */
  public static void register(Logger logger, PrefixHandler handler) {
    if (Stream.of(logger.getHandlers()).noneMatch(handler::equals)) {
      logger.addHandler(handler);
    }
  }

  @Override
  public void publish(LogRecord record) {
    final String prefix = this.prefix;
    if (prefix != null && !prefix.isEmpty()) {
      record.setMessage(prefix + ' ' + record.getMessage());
    }
  }

  @Override
  public void flush() {}

  @Override
  public void close() throws SecurityException {}

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    PrefixHandler that = (PrefixHandler) other;
    return Objects.equal(prefix, that.prefix);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(prefix);
  }

  @Override
  @SuppressWarnings("deprecation")
  public String toString() {
    return Objects.toStringHelper(this)
        .add("prefix", prefix)
        .toString();
  }

  @Nullable
  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(@Nullable String prefix) {
    this.prefix = prefix;
  }
}

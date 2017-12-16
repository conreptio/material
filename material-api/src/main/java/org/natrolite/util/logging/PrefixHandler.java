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

package org.natrolite.util.logging;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.Optional;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import org.bukkit.plugin.PluginLogger;

/**
 * A custom {@link Handler} that adds a defined prefix to all log messages,
 * similar to a {@link PluginLogger}.
 *
 * <p>One {@link Logger} can only have one {@link PrefixHandler}.
 *
 * <p>The {@link PrefixHandler} is not immutable and can be set
 * with {@link PrefixHandler#setPrefix(String)}.
 *
 * <p><b>All operations are Thread-Safe</b>
 *
 * @author Lukas Nehrke
 */
@ThreadSafe
public class PrefixHandler extends Handler {

  @Nullable
  private volatile String prefix;

  /**
   * Creates a new instance.
   *
   * @param prefix the prefix
   */
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
   * Adds a {@link PrefixHandler} to a {@link Logger}.
   *
   * @param logger  the logger
   * @param handler the handler
   */
  public static void register(Logger logger, PrefixHandler handler) {
    if (Stream.of(logger.getHandlers()).noneMatch(handler::equals)) {
      logger.addHandler(handler);
    }
  }

  /**
   * Gets the prefix of this {@link PrefixHandler}.
   *
   * @return The prefix or {@link Optional#empty()} if there is no prefix set
   */
  public Optional<String> getPrefix() {
    return Optional.ofNullable(prefix);
  }

  /**
   * Sets the prefix of this {@link PrefixHandler}.
   *
   * <p>To reset the prefix you can use <code>null</code> as parameter.
   *
   * @param prefix the prefix (can be null)
   */
  public void setPrefix(@Nullable String prefix) {
    this.prefix = prefix;
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
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("prefix", prefix)
        .toString();
  }
}

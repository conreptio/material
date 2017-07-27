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

package org.natrolite.util;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.concurrent.ThreadSafe;

/**
 * An utility class that provides various methods for {@link String}s.
 */
@ThreadSafe
public final class StringUtils {

  public static final String EMPTY = "";

  public static final String SPACE = " ";

  private StringUtils() {}

  /**
   * Capitalizes the first latter.
   *
   * @param string the string that should be formatted
   * @throws IllegalArgumentException if the string is empty
   */
  public static String capitalizeFirst(String string) {
    checkArgument(!string.isEmpty(), "String cannot be empty");
    return string.substring(0, 1).toUpperCase() + string.substring(1);
  }
}

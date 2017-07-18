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

package org.natrolite.service.placeholder;

import org.apache.commons.lang.StringUtils;
import org.natrolite.cause.Cause;

@FunctionalInterface
public interface Replacer {

  static String replace(String text, String token, String replacement) {
    return StringUtils.replace(text, '{' + token + '}', replacement);
  }

  /**
   * Replaces all placeholders of a text.
   *
   * @param cause the cause
   * @param text  the text to replace
   * @return the replaced text
   */
  String replace(Cause cause, String text);
}
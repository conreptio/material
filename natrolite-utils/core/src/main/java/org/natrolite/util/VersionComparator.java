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

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

  public static boolean isOlderThan(String o1, String o2) {
    return new VersionComparator().compare(o1, o2) == -1;
  }

  @Override
  public int compare(String o1, String o2) {
    if (o1 == null) {
      return 1;
    }
    if (o2 == null) {
      return 1;
    }
    String[] thisParts = o1.split("\\.");
    String[] thatParts = o2.split("\\.");
    int length = Math.max(thisParts.length, thatParts.length);
    for (int i = 0; i < length; i++) {
      int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
      int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
      if (thisPart < thatPart) {
        return -1;
      }
      if (thisPart > thatPart) {
        return 1;
      }
    }
    return 0;
  }
}

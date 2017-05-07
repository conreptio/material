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

package org.natrolite.util.tuple;

import com.google.common.base.Objects;

public class Pair<A, B> {

  private final A valueA;
  private final B valueB;

  public Pair(A valueA, B valueB) {
    this.valueA = valueA;
    this.valueB = valueB;
  }

  public A getA() {
    return valueA;
  }

  public B getB() {
    return valueB;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Pair<?, ?> pair = (Pair<?, ?>) other;
    return Objects.equal(valueA, pair.valueA) && Objects.equal(valueB, pair.valueB);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(valueA, valueB);
  }

  @Override
  @SuppressWarnings("deprecation")
  public String toString() {
    return Objects.toStringHelper(this)
        .add("valueA", valueA)
        .add("valueB", valueB)
        .toString();
  }
}

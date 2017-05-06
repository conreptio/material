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

import java.util.Objects;

public class Triplet<A, B, C> {

  private final A valueA;
  private final B valueB;
  private final C valueC;

  public Triplet(A valueA, B valueB, C valueC) {
    this.valueA = valueA;
    this.valueB = valueB;
    this.valueC = valueC;
  }

  public A getA() {
    return valueA;
  }

  public B getB() {
    return valueB;
  }

  public C getC() {
    return valueC;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
    return Objects.equals(valueA, triplet.valueA)
        && Objects.equals(valueB, triplet.valueB)
        && Objects.equals(valueC, triplet.valueC);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valueA, valueB, valueC);
  }

  @Override
  public String toString() {
    return com.google.common.base.Objects.toStringHelper(this)
        .add("valueA", valueA)
        .add("valueB", valueB)
        .add("valueC", valueC)
        .toString();
  }
}

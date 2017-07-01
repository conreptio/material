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

package org.natrolite.impl.sign.types;

import ninja.leaping.configurate.ConfigurationNode;
import org.bukkit.util.BlockVector;
import org.natrolite.impl.sign.NatroliteSign;
import org.natrolite.sign.SignFactory;

public class CakeSign extends NatroliteSign {

  private CakeSign(BlockVector vector) {
    super(vector);
  }

  public static Factory factory() {
    return new Factory();
  }

  public static class Factory implements SignFactory<CakeSign> {

    private Factory() {}

    @Override
    public CakeSign create(BlockVector vector, ConfigurationNode node) {
      return new CakeSign(vector);
    }
  }
}

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

package org.natrolite.sign;

import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

public interface SignService {

  /**
   * Loads signs from config.
   *
   * @throws Exception if an error occurs
   */
  void loadSigns() throws Exception;

  default Optional<GameSign> getSign(Sign sign) {
    return getSign(sign.getBlock());
  }

  default Optional<GameSign> getSign(Block block) {
    return getSign(block.getLocation());
  }

  default Optional<GameSign> getSign(Location location) {
    return getSign(location.toVector());
  }

  default Optional<GameSign> getSign(Vector vector) {
    return getSign(vector.toBlockVector());
  }

  Optional<GameSign> getSign(BlockVector vector);

  List<GameSign> getSigns();
}

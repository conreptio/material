/*
 * This file is part of Material.
 *
 * Copyright (c) 2016-2017 Neolumia
 *
 * Material is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Material is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Material. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.internal.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.natrolite.internal.config.category.GeneralCategory;
import org.natrolite.internal.config.category.SqlCategory;

@ConfigSerializable
public class NatroliteConfig {

  @Setting("general")
  private GeneralCategory general = new GeneralCategory();

  @Setting("sql")
  private SqlCategory sql = new SqlCategory();

  public GeneralCategory general() {
    return general;
  }

  public SqlCategory sql() {
    return sql;
  }
}

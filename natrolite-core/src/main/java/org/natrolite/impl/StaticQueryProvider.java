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

package org.natrolite.impl;

import java.io.IOException;
import java.util.Properties;

public final class StaticQueryProvider {

  private static final Properties properties = new Properties();

  static {
    try {
      properties.load(StaticQueryProvider.class.getResourceAsStream("/sql_natrolite.properties"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private StaticQueryProvider() {}

  public static String sql(String key) {
    return properties.getProperty(key).replace("{prefix}", NatroliteBukkit.TABLE_PREFIX);
  }
}

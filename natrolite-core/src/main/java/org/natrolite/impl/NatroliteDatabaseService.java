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

import static com.google.common.base.Preconditions.checkNotNull;

import com.zaxxer.hikari.HikariConfig;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import org.natrolite.sql.DatabaseService;

public final class NatroliteDatabaseService implements DatabaseService {

  @Nullable
  private HikariConfig config;

  NatroliteDatabaseService(NatroliteBukkit plugin) {
    try {
      try (InputStream in = Files.newInputStream(plugin.getRoot().resolve("database.properties"))) {
        final Properties properties = new Properties();
        properties.load(in);
        System.out.println(properties.stringPropertyNames());
        config = new HikariConfig(properties);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public DataSource getDataSource() {
    return checkNotNull(config).getDataSource();
  }
}
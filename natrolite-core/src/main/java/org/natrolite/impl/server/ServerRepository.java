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

package org.natrolite.impl.server;

import static org.natrolite.impl.NatroliteBukkit.TABLE_PREFIX;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import org.natrolite.Natrolite;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.service.sql.SqlService;

public final class ServerRepository {

  private static final String TABLE = "CREATE TABLE IF NOT EXISTS `" + TABLE_PREFIX + "server` (uuid VARCHAR(32) NOT NULL, name VARCHAR(256) NOT NULL, PRIMARY KEY(uuid));";
  private static final String UPDATE = "INSERT INTO `" + TABLE_PREFIX + "server` (uuid, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = ?;";
  private final NatroliteBukkit plugin;

  public ServerRepository(NatroliteBukkit plugin) {
    this.plugin = plugin;
  }

  public void update(UUID uuid, String name) throws SQLException {
    final SqlService service = Natrolite.provideUnchecked(SqlService.class);
    final Optional<String> url = service.getConnectionUrlFromAlias("default");
    if (url.isPresent()) {
      try (Connection connection = service.getDataSource(plugin, url.get()).getConnection()) {
        try (PreparedStatement table = connection.prepareStatement(TABLE)) {
          table.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
          statement.setString(1, uuid.toString().replace("-", ""));
          statement.setString(2, name);
          statement.setString(3, name);
          statement.executeUpdate();
        }
      }
    }
  }
}

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

import static org.natrolite.impl.StaticQueryProvider.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import org.natrolite.Natrolite;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.server.ServerSnapshot;
import org.natrolite.service.sql.SqlService;

final class ServerRepository {

  private static final String TABLE = sql("server.table");
  private static final String UPDATE = sql("server.update");
  private final NatroliteBukkit plugin;
  private boolean init = false;

  ServerRepository(NatroliteBukkit plugin) {
    this.plugin = plugin;
  }

  /**
   * Creates all needed tables.
   *
   * @throws SQLException if an sql error occurs
   */
  void init() throws SQLException {
    final SqlService service = Natrolite.provideUnchecked(SqlService.class);
    final Optional<String> url = service.getConnectionUrlFromAlias("default");
    if (url.isPresent()) {
      try (Connection connection = service.getDataSource(plugin, url.get()).getConnection()) {
        try (PreparedStatement table = connection.prepareStatement(TABLE)) {
          table.executeUpdate();
        }
      }
    }
  }

  void update(ServerSnapshot server) throws SQLException {
    final SqlService service = Natrolite.provideUnchecked(SqlService.class);
    final Optional<String> url = service.getConnectionUrlFromAlias("default");
    if (url.isPresent()) {

      if (!init) {
        init = true;
        init();
      }

      try (Connection connection = service.getDataSource(plugin, url.get()).getConnection()) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {

          statement.setString(1, plugin.getServerId().toString().replace("-", ""));
          statement.setString(2, plugin.getServerName());
          statement.setString(3, server.getAddress());
          statement.setInt(4, server.getPort());
          statement.setString(5, server.getMotd());
          statement.setInt(6, server.getPlayerCount());
          statement.setInt(7, server.getMaxPlayers());

          statement.setString(8, plugin.getServerName());
          statement.setString(9, server.getAddress());
          statement.setInt(10, server.getPort());
          statement.setString(11, server.getMotd());
          statement.setInt(12, server.getPlayerCount());
          statement.setInt(13, server.getMaxPlayers());

          statement.executeUpdate();
        }
      }
    }
  }
}

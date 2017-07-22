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
import org.natrolite.Natrolite;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.server.Server;
import org.natrolite.service.sql.SqlService;

public final class ServerRepository {

  private static final String TABLE = "CREATE TABLE IF NOT EXISTS `{prefix}server` (uuid VARCHAR(32) NOT NULL, name VARCHAR(256) NOT NULL, PRIMARY KEY(uuid));";
  private static final String INSERT = "INSERT INTO `{prefix}server` (uuid, name) VALUES (?, ?) ON DUPLICATE KEY UPDATE name = ?;";
  private static final String TABLE_SERVERS = "CREATE TABLE IF NOT EXISTS `{prefix}servers` (server VARCHAR(32) NOT NULL, address VARCHAR(256) NOT NULL, port INT NOT NULL, motd VARCHAR(256) NOT NULL, players INT NOT NULL, lastUpdate DATETIME NOT NULL, FOREIGN KEY (server) REFERENCES {prefix}server(uuid), PRIMARY KEY(server));";
  private static final String UPDATE = "INSERT INTO `{prefix}servers` (server, address, port, motd, players, lastUpdate) VALUES (?, ?, ?, ?, ?, now()) ON DUPLICATE KEY UPDATE address = ?, port = ?, motd = ?, players = ?, lastUpdate = NOW();";

  private final NatroliteBukkit plugin;

  ServerRepository(NatroliteBukkit plugin) {
    this.plugin = plugin;
  }

  private static String sql(String sql) {
    return sql.replace("{prefix}", TABLE_PREFIX);
  }

  /**
   * Creates all needed tables.
   *
   * @throws SQLException if an sql error occurs
   */
  public void init() throws SQLException {
    final SqlService service = Natrolite.provideUnchecked(SqlService.class);
    final Optional<String> url = service.getConnectionUrlFromAlias("default");
    if (url.isPresent()) {
      try (Connection connection = service.getDataSource(plugin, url.get()).getConnection()) {
        try (
            PreparedStatement table = connection.prepareStatement(sql(TABLE))) {
          table.executeUpdate();
        }
        try (
            PreparedStatement table = connection.prepareStatement(sql(TABLE_SERVERS))) {
          table.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(sql(INSERT))) {
          statement.setString(1, plugin.getServerId().toString().replace("-", ""));
          statement.setString(2, plugin.getServerName());
          statement.setString(3, plugin.getServerName());
          statement.executeUpdate();
        }
      }
    }
  }

  void update(Server server) throws SQLException {
    final SqlService service = Natrolite.provideUnchecked(SqlService.class);
    final Optional<String> url = service.getConnectionUrlFromAlias("default");
    if (url.isPresent()) {
      try (Connection connection = service.getDataSource(plugin, url.get()).getConnection()) {
        try (PreparedStatement statement = connection.prepareStatement(sql(UPDATE))) {

          statement.setString(1, server.getServerId().toString().replace("-", ""));
          statement.setString(2, server.getAddress());
          statement.setInt(3, server.getPort());
          statement.setString(4, server.getMotd());
          statement.setInt(5, server.getPlayerCount());

          statement.setString(6, server.getAddress());
          statement.setInt(7, server.getPort());
          statement.setString(8, server.getMotd());
          statement.setInt(9, server.getPlayerCount());

          statement.executeUpdate();
        }
      }
    }
  }
}

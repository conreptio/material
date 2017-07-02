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

package org.natrolite.impl.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.natrolite.Natrolite;
import org.natrolite.impl.NatroliteBukkit;
import org.natrolite.sql.DatabaseService;

public final class NatroliteStatusService {

  private static final String TABLE =
      "CREATE TABLE IF NOT EXISTS `natrolite_server` (" +
          "id INT(11) NOT NULL AUTO_INCREMENT, " +
          "address VARCHAR(256) NOT NULL, " +
          "port INT(11) NOT NULL, " +
          "motd VARCHAR(256) NOT NULL, " +
          "players INT(11) NOT NULL, " +
          "updated LONG NOT NULL, " +
          "PRIMARY KEY(id));";
  private static final String UPDATE = "";

  private final NatroliteBukkit natrolite;
  private boolean init;

  public NatroliteStatusService(NatroliteBukkit natrolite) {
    this.natrolite = natrolite;
    natrolite.getServer().getScheduler().runTaskTimerAsynchronously(natrolite, this::update, 0L, 20L);
  }

  private void update() {
    Natrolite.provide(DatabaseService.class).ifPresent(database -> {

      if (init) {
        init = false;
        try {
          try (Connection connection = database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(TABLE)) {
              statement.executeUpdate();
            }
          }
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    });
  }
}

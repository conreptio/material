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

package org.natrolite.config.category;

import java.util.HashMap;
import java.util.Map;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SqlCategory {

  @Setting(comment = "Aliases for SQL connections, in the format "
      + "jdbc:protocol://[username[:password]@]host/database")
  private Map<String, String> aliases = new HashMap<>();

  public SqlCategory() {
    aliases.putIfAbsent("default", "jdbc:h2:natrolite;MODE=MYSQL");
  }

  public Map<String, String> getAliases() {
    return this.aliases;
  }
}

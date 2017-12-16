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

package org.natrolite.server;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;
import javax.annotation.Nullable;

public final class ServerSnapshot {

  private UUID uuid;
  private String address;
  private int port;
  private String motd;
  private int playerCount;
  private int maxPlayers;

  private ServerSnapshot(ServerSnapshot.Builder builder) {
    this.uuid = checkNotNull(builder.uuid);
    this.address = checkNotNull(builder.address);
    this.port = builder.port;
    this.motd = checkNotNull(builder.motd);
    this.playerCount = builder.playerCount;
    this.maxPlayers = builder.maxPlayers;
  }

  public static ServerSnapshot.Builder builder() {
    return new ServerSnapshot.Builder();
  }

  public UUID getServerId() {
    return uuid;
  }

  public String getAddress() {
    return address;
  }

  public int getPort() {
    return port;
  }

  public String getMotd() {
    return motd;
  }

  public int getPlayerCount() {
    return playerCount;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public static final class Builder {

    @Nullable private UUID uuid;
    @Nullable private String address;
    private int port;
    @Nullable private String motd;
    private int playerCount;
    private int maxPlayers;

    public Builder uuid(UUID uuid) {
      this.uuid = uuid;
      return this;
    }

    public Builder address(String address) {
      this.address = address;
      return this;
    }

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder motd(String motd) {
      this.motd = motd;
      return this;
    }

    public Builder playerCount(int playerCount) {
      this.playerCount = playerCount;
      return this;
    }

    public Builder maxPlayers(int maxPlayers) {
      this.maxPlayers = maxPlayers;
      return this;
    }

    public ServerSnapshot build() {
      return new ServerSnapshot(this);
    }
  }
}

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

package org.natrolite.minigames.sign;

import static ninja.leaping.configurate.gson.GsonConfigurationLoader.builder;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.bukkit.util.BlockVector;
import org.natrolite.Natrolite;
import org.natrolite.minigames.MinigamesBukkit;
import org.natrolite.sign.GameSign;
import org.natrolite.sign.SignService;

public final class NatroliteSignService implements SignService {

  private static final String FILE = "signs.json";
  private final MinigamesBukkit natrolite;
  private final ConfigurationOptions options;
  private final GsonConfigurationLoader loader;
  private List<GameSign> signs = new ArrayList<>();

  /**
   * Creates a new instance.
   *
   * @param natrolite the plugin
   */
  public NatroliteSignService(MinigamesBukkit natrolite) {
    this.natrolite = natrolite;
    this.options = ConfigurationOptions.defaults().setSerializers(Natrolite.getSerializers());
    this.loader = builder().setPath(natrolite.getRoot().resolve(FILE)).build();
  }

  @Override
  public void loadSigns() throws Exception {
    final ConfigurationNode root = loader.load(options);
    this.signs = root.getList(TypeToken.of(GameSign.class));
  }

  @Override
  public Optional<GameSign> getSign(BlockVector vector) {
    return signs.stream().filter(s -> s.getPosition().equals(vector)).findAny();
  }

  @Override
  public List<GameSign> getSigns() {
    return ImmutableList.copyOf(signs);
  }

  private void saveAsync() {
    natrolite.getServer().getScheduler().runTaskAsynchronously(natrolite, this::save);
  }

  private void save() {
    try {
      final ConfigurationNode root = loader.load(options);
      root.setValue(new TypeToken<List<GameSign>>() {}, signs);
      loader.save(root);
    } catch (IOException | ObjectMappingException ex) {
      ex.printStackTrace();
    }
  }
}

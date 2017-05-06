/*
 * Natrolite - Minecraft Minigame Manager
 *
 * Copyright (c) 2017 XNITY <info@xnity.net>
 * Copyright (c) 2017 Natrolite <info@natrolite.org>
 * Copyright (c) 2017 Lukas Nehrke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.configurate;

import static ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode.root;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BlockVector;
import org.natrolite.configurate.serialization.BlockVectorSerializer;
import org.natrolite.configurate.serialization.LocationSerializer;
import org.natrolite.configurate.serialization.WorldSerializer;

public abstract class Config<T> {

  static {
    register(TypeToken.of(BlockVector.class), new BlockVectorSerializer());
    register(TypeToken.of(Location.class), new LocationSerializer());
    register(TypeToken.of(World.class), new WorldSerializer());
  }

  private final Path path;
  private final String rootSection;
  protected ConfigurationNode node = root(ConfigurationOptions.defaults());
  protected ConfigurationLoader loader;
  protected ObjectMapper<T>.BoundInstance mapper;
  protected T config;

  public Config(File file, Class<T> clazz) {
    this(file, null, clazz);
  }

  public Config(Path path, Class<T> clazz) {
    this(path, null, clazz);
  }

  public Config(File file, @Nullable String root, Class<T> clazz) {
    this(file.toPath(), root, clazz);
  }

  @SuppressWarnings("unchecked")
  public Config(Path path, @Nullable String root, Class<T> clazz) {
    this.path = path;
    this.rootSection = root;
    try {
      Files.createDirectories(path.getParent());
      if (Files.notExists(path)) {
        Files.createFile(path);
      }
      loader = getLoader();
      mapper = ObjectMapper.forClass(clazz).bindToNew();
      load();
      save();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static <T> void register(TypeToken<T> type, TypeSerializer<? super T> serializer) {
    TypeSerializers.getDefaultSerializers().registerType(type, serializer);
  }

  public void save() throws IOException, ObjectMappingException {
    mapper.serialize(rootSection != null ? node.getNode(rootSection) : node);
    loader.save(node);
  }

  public void load() throws IOException, ObjectMappingException {
    node = loader.load(ConfigurationOptions.defaults());
    config = mapper.populate(rootSection != null ? node.getNode(rootSection) : node);
  }

  public T getConfig() {
    return config;
  }

  public Path getPath() {
    return path;
  }

  protected abstract ConfigurationLoader getLoader();
}
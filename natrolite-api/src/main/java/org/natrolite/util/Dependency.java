/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package org.natrolite.util;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import org.bukkit.plugin.Plugin;
import org.natrolite.annotations.Experimental;

@Experimental
public class Dependency {

  public static final String NAME = "%s-%s-%s.jar";
  public static final String DIRECTORY_NAME = "lib";
  public static final String DOWNLOAD = "https://repo1.maven.org/maven2/%s/%s/%s/%s-%s.jar";

  @Nullable
  private static Method addURL = null;

  static {
    try {
      addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      addURL.setAccessible(true);
    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

  private final String url;
  private final String name;

  public Dependency(String groupId, String artifactId, String version) {
    this(
        format(DOWNLOAD, groupId.replace(".", "/"), artifactId, version, artifactId, version),
        format(NAME, groupId, artifactId, version)
    );
  }

  public Dependency(String url, String name) {
    this.url = checkNotNull(url);
    this.name = checkNotNull(name);
  }

  public static Dependency of(String artifactId, String groupId, String version) {
    return new Dependency(artifactId, groupId, version);
  }

  public static Dependency of(String url, String name) {
    return new Dependency(url, name);
  }

  public static void load(Plugin plugin, File jar) throws Exception {
    final URLClassLoader loader = (URLClassLoader) plugin.getClass().getClassLoader();
    addURL.invoke(loader, jar.toURI().toURL());
  }

  public void install(Plugin plugin) throws Exception {
    final Path dir = plugin.getDataFolder().toPath().resolve(DIRECTORY_NAME);
    Files.createDirectories(dir);

    final Path file = dir.resolve(getName());
    if (!Files.exists(file)) {
      try (InputStream in = new URL(getURL()).openStream()) {
        Files.copy(in, file);
      }
    }

    load(plugin, file.toFile());
  }

  public final String getName() {
    return name;
  }

  public String getURL() {
    return url;
  }
}

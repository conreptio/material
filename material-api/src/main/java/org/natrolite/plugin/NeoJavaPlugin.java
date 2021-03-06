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

package org.natrolite.plugin;

import java.io.File;
import java.nio.file.Files;
import javax.annotation.Nullable;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.natrolite.util.Asset;

/**
 * An extension to the normal {@link JavaPlugin}.
 */
public abstract class NeoJavaPlugin extends JavaPlugin implements NeoPlugin, Listener {

  @Nullable
  private ServicesManager servicesManager;

  @Override
  public void onEnable() {
    enable();
  }

  @Override
  public void onDisable() {
    disable();
  }

  protected void enable() {}

  protected void disable() {}

  @Override
  public Asset getAsset(String path) {
    return new Asset(this, path.replace("\\", "/"));
  }

  @Override
  public File getFile() {
    return super.getFile();
  }

  @Override
  public final ServicesManager getServicesManager() {
    if (servicesManager == null) {
      servicesManager = new SimpleServicesManager();
    }
    return servicesManager;
  }

  @Override
  public void saveResource(String resourcePath, boolean replace) {
    if (!Files.exists(getRoot().resolve(resourcePath)) || replace) {
      super.saveResource(resourcePath, replace);
    }
  }
}

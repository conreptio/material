package org.natrolite.plugin;

import java.nio.file.Path;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
import org.natrolite.util.Asset;

/**
 * An extension to the normal {@link Plugin}.
 */
public interface NeoPlugin extends Plugin {

  default Asset getAsset(String path) {
    return new Asset(this, path.replace("\\", "/"));
  }

  /**
   * Gets the root directory (data folder) of this {@link Plugin}.
   */
  default Path getRoot() {
    return getDataFolder().toPath();
  }

  ServicesManager getServicesManager();
}

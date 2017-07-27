package org.natrolite.plugin;

import java.nio.file.Path;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.natrolite.util.Asset;

/**
 * An extension to the normal {@link Plugin}.
 */
public interface NeoPlugin extends Plugin {

  /**
   * Gets an {@link Asset} from the plugin jar.
   *
   * @param path the path to the file
   */
  default Asset getAsset(String path) {
    return new Asset(this, path.replace("\\", "/"));
  }

  /**
   * Gets the root directory (data folder) of this {@link Plugin}.
   */
  default Path getRoot() {
    return getDataFolder().toPath();
  }

  /**
   * Gets the {@link ServicesManager} belonging to this plugin.
   *
   * <p>In most cases, this is just a new instance of {@link SimpleServicesManager}.
   *
   * @return The service manager belonging to this plugin
   */
  ServicesManager getServicesManager();
}

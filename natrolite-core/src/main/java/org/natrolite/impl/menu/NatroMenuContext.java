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

package org.natrolite.impl.menu;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.natrolite.menu.Menu;
import org.natrolite.menu.MenuContext;
import org.natrolite.menu.Page;

public final class NatroMenuContext implements MenuContext {

  private final UUID uuid;
  private final Menu menu;
  private final int page;
  @Nullable private Inventory inventory;

  NatroMenuContext(UUID uuid, Menu menu, int page) {
    this.uuid = uuid;
    this.menu = menu;
    this.page = page;
  }

  @Override
  public UUID getUniqueId() {
    return uuid;
  }

  @Override
  public Menu getMenu() {
    return menu;
  }

  @Override
  public int getCurrentPage() {
    return page;
  }

  @Override
  public Optional<Inventory> getInventory() {
    return Optional.ofNullable(inventory);
  }

  void refresh() {
    if (inventory == null) {
      System.out.println("Natrolite: refresh()");
      // This can never happen
      inventory = getPageUnchecked().toInventory();
      return;
    }
    getPageUnchecked().fill(inventory);
  }

  void open(Player player) {
    this.inventory = getPageUnchecked().toInventory();
    player.openInventory(inventory);
  }

  private Page getPageUnchecked() {
    return menu.getPage(getCurrentPage()).orElseThrow(IllegalStateException::new);
  }
}

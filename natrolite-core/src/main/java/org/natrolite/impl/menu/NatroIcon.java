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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.bukkit.inventory.ItemStack;
import org.natrolite.menu.Icon;
import org.natrolite.menu.MenuArguments;

public final class NatroIcon implements Icon {

  private final ItemStack item;
  private final Consumer<MenuArguments> click;

  private NatroIcon(Builder builder) {
    this.item = checkNotNull(builder.item);
    this.click = checkNotNull(builder.click);
  }

  @Override
  public ItemStack getItem() {
    return item;
  }

  @Override
  public Consumer<MenuArguments> click() {
    return click;
  }

  public static class Builder implements Icon.Builder {

    @Nullable private ItemStack item;
    private Consumer<MenuArguments> click = args -> { };

    @Override
    public Icon.Builder item(ItemStack item) {
      this.item = item;
      return this;
    }

    @Override
    public Icon.Builder click(Consumer<MenuArguments> consumer) {
      this.click = checkNotNull(consumer, "Click consumer cannot be null");
      return this;
    }

    @Override
    public Icon.Builder from(Icon value) {
      this.item = value.getItem();
      return this;
    }

    @Override
    public Icon.Builder reset() {
      this.item = null;
      this.click = args -> { };
      return this;
    }

    @Override
    public Icon build() {
      return new NatroIcon(this);
    }
  }
}

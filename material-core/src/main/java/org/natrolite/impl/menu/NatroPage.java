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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.inventory.InventoryType;
import org.natrolite.menu.Icon;
import org.natrolite.menu.Page;
import org.natrolite.text.Text;

public final class NatroPage implements Page {

  private final Text title;
  private final InventoryType type;
  private final int size;
  private final Map<Integer, Icon> icons;

  private NatroPage(Builder builder) {
    checkState(!isChest() || builder.size > 0, "Size has not been set");
    this.title = builder.title;
    this.type = builder.type;
    this.size = builder.size;
    this.icons = builder.icons;
  }

  @Override
  public Text getTitle() {
    return title;
  }

  @Override
  public InventoryType getType() {
    return type;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public ImmutableMap<Integer, Icon> getIcons() {
    return ImmutableMap.copyOf(icons);
  }

  public static class Builder implements Page.Builder {

    private Text title = Text.of("Unknown Menu");
    private InventoryType type = InventoryType.CHEST;
    private int size = -1;
    private Map<Integer, Icon> icons = new HashMap<>();

    @Override
    public Page.Builder title(Text title) {
      this.title = checkNotNull(title, "Title cannot be null");
      return this;
    }

    @Override
    public Page.Builder type(InventoryType type) {
      this.type = checkNotNull(type, "Type cannot be null");
      return this;
    }

    @Override
    public Page.Builder size(int size) {
      checkArgument(size > 0, "Size must be greater than 0");
      this.size = size;
      return this;
    }

    @Override
    public Page.Builder icon(int pos, Icon icon) {
      checkArgument(pos >= 0, "Position cannot be negative");
      icons.put(pos, checkNotNull(icon, "Icon cannot be null"));
      return this;
    }

    @Override
    public Page.Builder from(Page value) {
      this.title = value.getTitle();
      this.type = value.getType();
      this.size = value.getSize();
      this.icons = new HashMap<>(value.getIcons());
      return this;
    }

    @Override
    public Page.Builder reset() {
      this.title = Text.of("Unknown Menu");
      this.type = InventoryType.CHEST;
      this.size = -1;
      this.icons.clear();
      return this;
    }

    @Override
    public NatroPage build() {
      return new NatroPage(this);
    }
  }
}

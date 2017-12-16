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

package org.natrolite.impl.menu;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.natrolite.menu.Menu;
import org.natrolite.menu.Page;

public final class NatroMenu implements Menu {

  private final List<Page> pages;

  private NatroMenu(Builder builder) {
    this.pages = builder.pages;
  }

  @Override
  public ImmutableList<Page> getPages() {
    return ImmutableList.copyOf(pages);
  }

  @Override
  public Optional<Page> getPage(int pos) {
    return Optional.ofNullable(pages.get(pos));
  }

  public static final class Builder implements Menu.Builder {

    private List<Page> pages = new ArrayList<>();

    @Override
    public Menu.Builder append(Page... pages) {
      Collections.addAll(this.pages, pages);
      return this;
    }

    @Override
    public Menu.Builder insert(int pos, Page... pages) {
      this.pages.addAll(pos, Arrays.asList(pages));
      return this;
    }

    @Override
    public Menu.Builder remove(Page... pages) {
      this.pages.removeAll(Arrays.asList(pages));
      return this;
    }

    @Override
    public Menu.Builder from(Menu value) {
      this.pages = value.getPages();
      return this;
    }

    @Override
    public Menu.Builder reset() {
      pages.clear();
      return this;
    }

    @Override
    public Menu build() {
      return new NatroMenu(this);
    }
  }
}

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

package org.natrolite.example;

import org.natrolite.arena.Arena;
import org.natrolite.plugin.GameJavaPlugin;

public class ExamplePlugin extends GameJavaPlugin<Example> {

  public ExamplePlugin() {
    super(Example.class, ExampleSettings.class);
  }

  @Override
  public void onEnable() {
    getLogger().info("Hello World!");
  }

  @Override
  public Example createGame(Arena arena) {
    return new Example(this, arena);
  }
}

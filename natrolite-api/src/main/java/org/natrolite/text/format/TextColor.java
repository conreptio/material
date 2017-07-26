/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package org.natrolite.text.format;

import org.bukkit.ChatColor;

public final class TextColor {

  public static final TextColor AQUA = new TextColor(ChatColor.AQUA, "aqua");

  public static final TextColor BLACK = new TextColor(ChatColor.BLACK, "black");

  public static final TextColor BLUE = new TextColor(ChatColor.BLUE, "blue");

  public static final TextColor DARK_AQUA = new TextColor(ChatColor.DARK_AQUA, "dark_aqua");

  public static final TextColor DARK_BLUE = new TextColor(ChatColor.DARK_BLUE, "dark_blue");

  public static final TextColor DARK_GRAY = new TextColor(ChatColor.DARK_GRAY, "dark_gray");

  public static final TextColor DARK_GREEN = new TextColor(ChatColor.DARK_GREEN, "dark_green");

  public static final TextColor DARK_PURPLE = new TextColor(ChatColor.DARK_PURPLE, "dark_purple");

  public static final TextColor DARK_RED = new TextColor(ChatColor.DARK_RED, "dark_red");

  public static final TextColor GOLD = new TextColor(ChatColor.GOLD, "gold");

  public static final TextColor GRAY = new TextColor(ChatColor.GRAY, "gray");

  public static final TextColor GREEN = new TextColor(ChatColor.GREEN, "green");

  public static final TextColor LIGHT_PURPLE = new TextColor(ChatColor.LIGHT_PURPLE, "light_purple");

  public static final TextColor NONE = new TextColor(ChatColor.BLACK, "black");

  public static final TextColor RED = new TextColor(ChatColor.RED, "red");

  public static final TextColor RESET = new TextColor(ChatColor.RESET, "reset");

  public static final TextColor WHITE = new TextColor(ChatColor.WHITE, "white");

  public static final TextColor YELLOW = new TextColor(ChatColor.YELLOW, "yellow");

  private ChatColor color;
  private String name;

  private TextColor(ChatColor color, String name) {
    this.color = color;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public ChatColor getColor() {
    return color;
  }
}

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

  public static final TextColor AQUA = new TextColor(ChatColor.AQUA);

  public static final TextColor BLACK = new TextColor(ChatColor.BLACK);

  public static final TextColor BLUE = new TextColor(ChatColor.BLUE);

  public static final TextColor DARK_AQUA = new TextColor(ChatColor.DARK_AQUA);

  public static final TextColor DARK_BLUE = new TextColor(ChatColor.DARK_BLUE);

  public static final TextColor DARK_GRAY = new TextColor(ChatColor.DARK_GRAY);

  public static final TextColor DARK_GREEN = new TextColor(ChatColor.DARK_GREEN);

  public static final TextColor DARK_PURPLE = new TextColor(ChatColor.DARK_PURPLE);

  public static final TextColor DARK_RED = new TextColor(ChatColor.DARK_RED);

  public static final TextColor GOLD = new TextColor(ChatColor.GOLD);

  public static final TextColor GRAY = new TextColor(ChatColor.GRAY);

  public static final TextColor GREEN = new TextColor(ChatColor.GREEN);

  public static final TextColor LIGHT_PURPLE = new TextColor(ChatColor.LIGHT_PURPLE);

  public static final TextColor NONE = new TextColor(ChatColor.BLACK);

  public static final TextColor RED = new TextColor(ChatColor.RED);

  public static final TextColor RESET = new TextColor(ChatColor.RESET);

  public static final TextColor WHITE = new TextColor(ChatColor.WHITE);

  public static final TextColor YELLOW = new TextColor(ChatColor.YELLOW);

  private ChatColor color;

  private TextColor(ChatColor color) {
    this.color = color;
  }

  public ChatColor getColor() {
    return color;
  }
}

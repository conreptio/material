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

public final class TextStyle {

  public static final TextStyle RESET = new TextStyle(ChatColor.RESET);

  public static final TextStyle OBFUSCATED = new TextStyle(ChatColor.MAGIC);

  public static final TextStyle BOLD = new TextStyle(ChatColor.BOLD);

  public static final TextStyle STRIKETHROUGH = new TextStyle(ChatColor.STRIKETHROUGH);

  public static final TextStyle UNDERLINE = new TextStyle(ChatColor.UNDERLINE);

  public static final TextStyle ITALIC = new TextStyle(ChatColor.ITALIC);

  private final ChatColor color;

  private TextStyle(ChatColor color) {
    this.color = color;
  }

  public ChatColor getColor() {
    return color;
  }
}

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

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.Nullable;

public final class TextFormat {

  /**
   * An empty {@link TextFormat} with no {@link TextColor} and no {@link TextStyle}.
   */
  public static final TextFormat NONE = new TextFormat(null, null);

  /**
   * The text color.
   */
  @Nullable
  private final TextColor color;

  /**
   * The text style.
   */
  @Nullable
  private final TextStyle style;

  /**
   * Constructs a new {@link TextFormat}.
   *
   * @param color The color
   * @param style The style
   */
  private TextFormat(@Nullable TextColor color, @Nullable TextStyle style) {
    this.color = color;
    this.style = style;
  }

  /**
   * Gets the {@link TextFormat} with the default style and color.
   *
   * @return The empty text format
   */
  public static TextFormat of() {
    return NONE;
  }

  /**
   * Constructs a new {@link TextFormat} with the specific style.
   *
   * @param style The style
   * @return The new text format
   */
  public static TextFormat of(TextStyle style) {
    return new TextFormat(null, style);
  }

  /**
   * Constructs a new {@link TextFormat} with the specific color.
   *
   * @param color The color
   * @return The new text format
   */
  public static TextFormat of(TextColor color) {
    return new TextFormat(color, null);
  }

  /**
   * Constructs a new {@link TextFormat} with the specific color and style.
   *
   * @param color The color
   * @param style The style
   * @return The new text format
   */
  public static TextFormat of(TextColor color, TextStyle style) {
    return new TextFormat(color, style);
  }

  /**
   * Returns the {@link TextColor} in this format.
   *
   * @return The color
   */
  @Nullable
  public TextColor getColor() {
    return this.color;
  }

  /**
   * Returns the {@link TextStyle} in this format.
   *
   * @return The style
   */
  @Nullable
  public TextStyle getStyle() {
    return this.style;
  }

  /**
   * Returns a new {@link TextFormat} with the given color.
   *
   * @param color The color
   * @return The new text format
   */
  public TextFormat color(TextColor color) {
    return new TextFormat(color, this.style);
  }

  /**
   * Returns a new {@link TextFormat} with the given style.
   *
   * @param style The style
   * @return The new text format
   */
  public TextFormat style(TextStyle style) {
    return new TextFormat(this.color, style);
  }

  /**
   * Returns whether this {@link TextFormat} has no color and format
   * specified.
   *
   * @return If the format does not contain a color or any styles
   */
  public boolean isEmpty() {
    return this.color == null && this.style == null;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.color, this.style);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("color", this.color)
        .add("style", this.style)
        .toString();
  }
}

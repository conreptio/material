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

package org.natrolite.text;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nullable;
import org.natrolite.text.format.TextColor;
import org.natrolite.text.format.TextFormat;
import org.natrolite.text.format.TextStyle;

public final class LiteralText extends Text {

  static final LiteralText EMPTY = new LiteralText("");

  final String content;

  LiteralText(String content) {
    this.content = checkNotNull(content, "content");
  }

  /**
   * Constructs a new immutable {@link LiteralText} for the given plain text
   * content with the specified formatting and text actions applied.
   *
   * @param format   The format of the text
   * @param children The immutable list of children of the text
   * @param content  The plain text content of the text
   */
  LiteralText(TextFormat format, ImmutableList<Text> children, Object[] objects, String content) {
    super(format, children, objects);
    this.content = checkNotNull(content, "content");
  }

  /**
   * Returns the plain text content of this {@link Text}.
   *
   * @return The content of this text
   */
  public String getContent() {
    return this.content;
  }

  @Override
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public String toPlain() {
    return (getFormat().getColor() != null ? getFormat().getColor().getColor().toString() : "")
        + (getFormat().getStyle() != null ? getFormat().getStyle().getColor().toString() : "")
        + MessageFormat.format(content, objects);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LiteralText) || !super.equals(o)) {
      return false;
    }
    LiteralText that = (LiteralText) o;
    return this.content.equals(that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.content);
  }

  @Override
  MoreObjects.ToStringHelper toStringHelper() {
    return super.toStringHelper().addValue(this.content);
  }

  /**
   * Represents a {@link Text.Builder} creating immutable {@link LiteralText}
   * instances.
   *
   * @see LiteralText
   */
  public static class Builder extends Text.Builder {

    private String content;

    /**
     * Constructs a new empty {@link Builder}.
     */
    Builder() {
      this("");
    }

    /**
     * Constructs a new unformatted {@link Builder} with the given content.
     *
     * @param content The content for the text builder
     */
    Builder(String content) {
      content(content);
    }

    /**
     * Constructs a new {@link Builder} with the formatting and actions of
     * the specified {@link Text} and the given content.
     *
     * @param text    The text to apply the properties from
     * @param content The content for the text builder
     */
    Builder(Text text, String content) {
      super(text);
      content(content);
    }

    /**
     * Constructs a new {@link Builder} with the formatting, actions and
     * content of the specified {@link LiteralText}.
     *
     * @param text The text to apply the properties from
     */
    Builder(LiteralText text) {
      super(text);
      this.content = text.content;
    }

    /**
     * Returns the current content of this builder.
     *
     * @return The current content
     * @see LiteralText#getContent()
     */
    public final String getContent() {
      return this.content;
    }

    /**
     * Sets the plain text content of this text.
     *
     * @param content The content of this text
     * @return This text builder
     * @see LiteralText#getContent()
     */
    public Builder content(String content) {
      this.content = checkNotNull(content, "content");
      return this;
    }

    @Override
    public LiteralText build() {
      // Special case for empty builder
      if (this.format.isEmpty() && this.children.isEmpty()) {
        if (this.content.isEmpty()) {
          return EMPTY;
        } else if (this.content.equals(NEW_LINE_STRING)) {
          return NEW_LINE;
        }
      }

      return new LiteralText(
          this.format,
          ImmutableList.copyOf(this.children),
          this.objects,
          this.content);
    }

    @Override
    public boolean equals(@Nullable Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Builder) || !super.equals(o)) {
        return false;
      }
      Builder that = (Builder) o;
      return Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
      return Objects.hash(super.hashCode(), this.content);
    }

    @Override
    MoreObjects.ToStringHelper toStringHelper() {
      return super.toStringHelper()
          .addValue(this.content);
    }

    @Override
    public Builder format(TextFormat format) {
      return (Builder) super.format(format);
    }

    @Override
    public Builder color(TextColor color) {
      return (Builder) super.color(color);
    }

    @Override
    public Builder style(TextStyle style) {
      return (Builder) super.style(style);
    }

    @Override
    public Builder append(Text... children) {
      return (Builder) super.append(children);
    }

    @Override
    public Builder append(Collection<? extends Text> children) {
      return (Builder) super.append(children);
    }

    @Override
    public Builder append(Iterable<? extends Text> children) {
      return (Builder) super.append(children);
    }

    @Override
    public Builder append(Iterator<? extends Text> children) {
      return (Builder) super.append(children);
    }

    @Override
    public Builder insert(int pos, Text... children) {
      return (Builder) super.insert(pos, children);
    }

    @Override
    public Builder insert(int pos, Collection<? extends Text> children) {
      return (Builder) super.insert(pos, children);
    }

    @Override
    public Builder insert(int pos, Iterable<? extends Text> children) {
      return (Builder) super.insert(pos, children);
    }

    @Override
    public Builder insert(int pos, Iterator<? extends Text> children) {
      return (Builder) super.insert(pos, children);
    }

    @Override
    public Builder remove(Text... children) {
      return (Builder) super.remove(children);
    }

    @Override
    public Builder remove(Collection<? extends Text> children) {
      return (Builder) super.remove(children);
    }

    @Override
    public Builder remove(Iterable<? extends Text> children) {
      return (Builder) super.remove(children);
    }

    @Override
    public Builder remove(Iterator<? extends Text> children) {
      return (Builder) super.remove(children);
    }

    @Override
    public Builder removeAll() {
      return (Builder) super.removeAll();
    }
  }
}

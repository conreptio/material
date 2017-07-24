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
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.natrolite.BetterPlugin;
import org.natrolite.dictionary.TranslationDictionaries;
import org.natrolite.dictionary.TranslationDictionary;
import org.natrolite.text.format.TextColor;
import org.natrolite.text.format.TextFormat;
import org.natrolite.text.format.TextStyle;

public abstract class Text implements Comparable<Text> {

  /**
   * The empty, unformatted {@link Text} instance.
   */
  public static final Text EMPTY = LiteralText.EMPTY;
  /**
   * A {@link Comparator} for texts that compares the plain text of two text
   * instances.
   */
  public static final Comparator<Text> PLAIN_COMPARATOR = (text1, text2) -> text1.toPlain().compareTo(text2.toPlain());
  static final char NEW_LINE_CHAR = '\n';
  static final String NEW_LINE_STRING = "\n";
  /**
   * An unformatted {@link Text} that will start a new line (if supported).
   */
  public static final LiteralText NEW_LINE = new LiteralText(NEW_LINE_STRING);
  final TextFormat format;
  final ImmutableList<Text> children;
  final Object[] objects;

  /**
   * An {@link Iterable} providing an {@link Iterator} over this {@link Text}
   * as well as all children text and their children.
   */
  final Iterable<Text> childrenIterable;

  Text() {
    this.format = TextFormat.NONE;
    this.children = ImmutableList.of();
    this.objects = new Object[0];
    this.childrenIterable = () -> Iterators.singletonIterator(this);
  }

  /**
   * Constructs a new immutable {@link Text} with the specified formatting and
   * text actions applied.
   *
   * @param format   The format of the text
   * @param children The immutable list of children of the text
   */
  Text(TextFormat format, ImmutableList<Text> children, Object[] objects) {
    this.format = checkNotNull(format, "format");
    this.children = checkNotNull(children, "children");
    this.objects = checkNotNull(objects, "objects");
    this.childrenIterable = () -> new TextIterator(this);
  }

  public static Text of() {
    return EMPTY;
  }

  /**
   * Creates a {@link Text} with the specified plain text. The created text
   * won't have any formatting or events configured.
   *
   * @param content The content of the text
   * @return The created text
   * @see LiteralText
   */
  public static LiteralText of(String content) {
    if (checkNotNull(content, "content").isEmpty()) {
      return LiteralText.EMPTY;
    } else if (content.equals(NEW_LINE_STRING)) {
      return NEW_LINE;
    } else {
      return new LiteralText(content);
    }
  }

  /**
   * Creates a {@link Text} with the specified char as plain text. The created
   * text won't have any formatting or events configured.
   *
   * @param content The contant of the text as char
   * @return The created text
   * @see LiteralText
   */
  public static LiteralText of(char content) {
    if (content == NEW_LINE_CHAR) {
      return NEW_LINE;
    }
    return new LiteralText(String.valueOf(content));
  }

  /**
   * Gets an optional {@link Text} from the provided
   * {@link TranslationDictionary} by key.
   *
   * @param dictionary The translation dictionary to retrieve from
   * @param key        The translation key
   * @return The text, if present, {@link Optional#empty()} otherwise
   */
  public static Optional<Text> get(TranslationDictionary dictionary, String key) {
    return dictionary.get(key).map(Text::of);
  }

  /**
   * Gets an optional {@link Text} from the provided
   * {@link TranslationDictionary} by key and locale.
   *
   * @param dictionary The translation dictionary to retrieve from
   * @param key        The translation key
   * @param locale     The locale under which the value should be obtained in
   * @return The text, if present, {@link Optional#empty()} otherwise
   */
  public static Optional<Text> get(TranslationDictionary dictionary, String key, Locale locale) {
    return dictionary.get(key, locale).map(Text::of);
  }

  /**
   * Gets an optional {@link Text} from the provided
   * plugin's {@link TranslationDictionary} by key.
   *
   * @param plugin The plugin to retrieve the translation dictionary from
   * @param key    The translation key
   * @return The text, if present, {@link Optional#empty()} otherwise
   */
  public static Optional<Text> get(BetterPlugin plugin, String key) {
    return TranslationDictionaries.plugin(plugin)
        .map(dictionary -> dictionary.get(key).map(Text::of).orElse(null));
  }

  /**
   * Gets an optional {@link Text} from the provided
   * plugin's {@link TranslationDictionary} by key and locale.
   *
   * @param plugin The plugin to retrieve the translation dictionary from
   * @param key    The translation key
   * @param locale The locale under which the value should be obtained in
   * @return The text, if present, {@link Optional#empty()} otherwise
   */
  public static Optional<Text> get(BetterPlugin plugin, String key, Locale locale) {
    return TranslationDictionaries.plugin(plugin)
        .map(dictionary -> dictionary.get(key, locale).map(Text::of).orElse(null));
  }

  /**
   * Creates a {@link Text.Builder} with empty text.
   *
   * @return A new text builder with empty text
   */
  public static Text.Builder builder() {
    return new LiteralText.Builder();
  }

  /**
   * Creates a new unformatted {@link LiteralText.Builder} with the specified
   * content.
   *
   * @param content The content of the text
   * @return The created text builder
   * @see LiteralText
   * @see LiteralText.Builder
   */
  public static LiteralText.Builder builder(String content) {
    return new LiteralText.Builder(content);
  }

  /**
   * Creates a new unformatted {@link LiteralText.Builder} with the specified
   * content.
   *
   * @param content The content of the text as char
   * @return The created text builder
   * @see LiteralText
   * @see LiteralText.Builder
   */
  public static LiteralText.Builder builder(char content) {
    return builder(String.valueOf(content));
  }

  /**
   * Creates a new {@link LiteralText.Builder} with the formatting and actions
   * of the specified {@link Text} and the given content.
   *
   * @param text    The text to apply the properties from
   * @param content The content for the text builder
   * @return The created text builder
   * @see LiteralText
   * @see LiteralText.Builder
   */
  public static LiteralText.Builder builder(Text text, String content) {
    return new LiteralText.Builder(text, content);
  }

  /**
   * Returns the format of this {@link Text}.
   *
   * @return The format of this text
   */
  public final TextFormat getFormat() {
    return this.format;
  }

  /**
   * Returns the color of this {@link Text}.
   *
   * @return The color of this text
   */
  public final TextColor getColor() {
    return this.format.getColor();
  }

  /**
   * Returns the style of this {@link Text}. This will return a compound
   * {@link TextStyle} if multiple different styles have been set.
   *
   * @return The style of this text
   */
  public final TextStyle getStyle() {
    return this.format.getStyle();
  }

  /**
   * Returns the immutable list of children appended after the content of this
   * {@link Text}.
   *
   * @return The immutable list of children
   */
  public final ImmutableList<Text> getChildren() {
    return this.children;
  }

  /**
   * Returns an immutable {@link Iterable} over this text and all of its
   * children. This is recursive, the children of the children will be also
   * included.
   *
   * @return An iterable over this text and the children texts
   */
  public final Iterable<Text> withChildren() {
    return this.childrenIterable;
  }

  public final Object[] getObjects() {
    return objects;
  }

  /**
   * Returns whether this {@link Text} is empty.
   *
   * @return {@code true} if this text is empty
   */
  public final boolean isEmpty() {
    return this == EMPTY;
  }

  /**
   * Returns a new {@link Builder} with the content, formatting and actions of
   * this text. This can be used to edit an otherwise immutable {@link Text}
   * instance.
   *
   * @return A new message builder with the content of this text
   */
  public abstract Builder toBuilder();

  /**
   * Returns a plain text representation of this {@link Text} without any
   * formatting.
   *
   * @return This text converted to plain text
   */
  public abstract String toPlain();

  public final void send(CommandSender... senders) {
    Stream.of(senders).forEach(sender -> {
      sender.sendMessage(toPlain());
    });
  }

  public final void log(Logger logger, Level level, Throwable throwable) {
    logger.log(level, toPlain(), throwable);
  }

  public final void info(Logger logger) {
    logger.log(Level.INFO, toPlain());
  }

  public final void info(Logger logger, Throwable throwable) {
    logger.log(Level.INFO, toPlain(), throwable);
  }

  public final void warn(Logger logger) {
    logger.log(Level.WARNING, toPlain());
  }

  public final void warn(Logger logger, Throwable throwable) {
    logger.log(Level.WARNING, toPlain(), throwable);
  }

  public final void severe(Logger logger) {
    logger.log(Level.SEVERE, toPlain());
  }

  public final void severe(Logger logger, Throwable throwable) {
    logger.log(Level.SEVERE, toPlain(), throwable);
  }

  /**
   * Concatenates the specified {@link Text} to this Text and returns the
   * result.
   *
   * @param other To concatenate
   * @return Concatenated text
   */
  public final Text concat(Text other) {
    return toBuilder().append(other).build();
  }

  /**
   * Removes all empty texts from the beginning and end of this
   * text.
   *
   * @return Text result
   */
  public final Text trim() {
    return toBuilder().trim().build();
  }

  public final Text args(Object... objects) {
    return toBuilder().args(objects).build();
  }

  @Override
  public int compareTo(Text o) {
    return PLAIN_COMPARATOR.compare(this, o);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Text)) {
      return false;
    }
    Text that = (Text) o;
    return this.format.equals(that.format) && this.children.equals(that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.format, this.children);
  }

  MoreObjects.ToStringHelper toStringHelper() {
    return MoreObjects.toStringHelper(Text.class)
        .omitNullValues()
        .add("format", this.format.isEmpty() ? null : this.format)
        .add("children", this.children.isEmpty() ? null : this.children);
  }

  @Override
  public final String toString() {
    return toStringHelper().toString();
  }

  /**
   * Represents a builder class to create immutable {@link Text} instances.
   *
   * @see Text
   */
  public abstract static class Builder {

    TextFormat format = TextFormat.NONE;
    List<Text> children = new ArrayList<>();
    Object[] objects = new Object[0];

    /**
     * Constructs a new empty {@link Builder}.
     */
    Builder() {}

    /**
     * Constructs a new {@link Builder} with the properties of the given
     * {@link Text} as initial values.
     *
     * @param text The text to copy the values from
     */
    Builder(Text text) {
      this.format = text.format;
      this.children = new ArrayList<>(text.children);
      this.objects = text.objects;
    }

    public final Object getObjects() {
      return objects;
    }

    public Builder args(Object... objects) {
      this.objects = checkNotNull(objects, "objects");
      return this;
    }

    /**
     * Returns the current format of the {@link Text} in this builder.
     *
     * @return The current format
     * @see Text#getFormat()
     */
    public final TextFormat getFormat() {
      return this.format;
    }

    /**
     * Sets the {@link TextFormat} of this text.
     *
     * @param format The new text format for this text
     * @return The text builder
     * @see Text#getFormat()
     */
    public Builder format(TextFormat format) {
      this.format = checkNotNull(format, "format");
      return this;
    }

    /**
     * Returns the current color of the {@link Text} in this builder.
     *
     * @return The current color
     * @see Text#getColor()
     */
    public final TextColor getColor() {
      return this.format.getColor();
    }

    /**
     * Sets the {@link TextColor} of this text.
     *
     * @param color The new text color for this text
     * @return This text builder
     * @see Text#getColor()
     */
    public Builder color(TextColor color) {
      this.format = this.format.color(color);
      return this;
    }

    /**
     * Returns the current style of the {@link Text} in this builder.
     *
     * @return The current style
     * @see Text#getStyle()
     */
    public final TextStyle getStyle() {
      return this.format.getStyle();
    }

    /**
     * Sets the text styles of this text. This will construct a composite
     * {@link TextStyle} of the current style and the specified styles first
     * and set it to the text.
     *
     * @param style The text style to apply
     * @return This text builder
     * @see Text#getStyle()
     */
    public Builder style(TextStyle style) {
      this.format = this.format.style(style);
      return this;
    }

    /**
     * Returns a view of the current children of this builder.
     * <p>
     * <p>The returned list is unmodifiable, but not immutable. It will
     * change if new children get added through this builder.</p>
     *
     * @return An unmodifiable list of the current children
     * @see Text#getChildren()
     */
    public final List<Text> getChildren() {
      return Collections.unmodifiableList(this.children);
    }

    /**
     * Appends the specified {@link Text} to the end of this text.
     *
     * @param children The texts to append
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder append(Text... children) {
      Collections.addAll(this.children, children);
      return this;
    }

    /**
     * Appends the specified {@link Text} to the end of this text.
     *
     * @param children The texts to append
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder append(Collection<? extends Text> children) {
      this.children.addAll(children);
      return this;
    }

    /**
     * Appends the specified {@link Text} to the end of this text.
     *
     * @param children The texts to append
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder append(Iterable<? extends Text> children) {
      for (Text child : children) {
        this.children.add(child);
      }
      return this;
    }

    /**
     * Appends the specified {@link Text} to the end of this text.
     *
     * @param children The texts to append
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder append(Iterator<? extends Text> children) {
      while (children.hasNext()) {
        this.children.add(children.next());
      }
      return this;
    }

    /**
     * Inserts the specified {@link Text} at the given position of this
     * builder.
     *
     * @param pos      The position to insert the texts to
     * @param children The texts to insert
     * @return This text builder
     * @throws IndexOutOfBoundsException If the position is out of bounds
     * @see Text#getChildren()
     */
    public Builder insert(int pos, Text... children) {
      this.children.addAll(pos, Arrays.asList(children));
      return this;
    }

    /**
     * Inserts the specified {@link Text} at the given position of this
     * builder.
     *
     * @param pos      The position to insert the texts to
     * @param children The texts to insert
     * @return This text builder
     * @throws IndexOutOfBoundsException If the position is out of range
     * @see Text#getChildren()
     */
    public Builder insert(int pos, Collection<? extends Text> children) {
      this.children.addAll(pos, children);
      return this;
    }

    /**
     * Inserts the specified {@link Text} at the given position of this
     * builder.
     *
     * @param pos      The position to insert the texts to
     * @param children The texts to insert
     * @return This text builder
     * @throws IndexOutOfBoundsException If the position is out of range
     * @see Text#getChildren()
     */
    public Builder insert(int pos, Iterable<? extends Text> children) {
      for (Text child : children) {
        this.children.add(pos++, child);
      }
      return this;
    }

    /**
     * Inserts the specified {@link Text} at the given position of this
     * builder.
     *
     * @param pos      The position to insert the texts to
     * @param children The texts to insert
     * @return This text builder
     * @throws IndexOutOfBoundsException If the position is out of range
     * @see Text#getChildren()
     */
    public Builder insert(int pos, Iterator<? extends Text> children) {
      while (children.hasNext()) {
        this.children.add(pos++, children.next());
      }
      return this;
    }

    /**
     * Removes the specified {@link Text} from this builder.
     *
     * @param children The texts to remove
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder remove(Text... children) {
      this.children.removeAll(Arrays.asList(children));
      return this;
    }

    /**
     * Removes the specified {@link Text} from this builder.
     *
     * @param children The texts to remove
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder remove(Collection<? extends Text> children) {
      this.children.removeAll(children);
      return this;
    }


    /**
     * Removes the specified {@link Text} from this builder.
     *
     * @param children The texts to remove
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder remove(Iterable<? extends Text> children) {
      for (Text child : children) {
        this.children.remove(child);
      }
      return this;
    }

    /**
     * Removes the specified {@link Text} from this builder.
     *
     * @param children The texts to remove
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder remove(Iterator<? extends Text> children) {
      while (children.hasNext()) {
        this.children.remove(children.next());
      }
      return this;
    }

    /**
     * Removes all children from this builder.
     *
     * @return This text builder
     * @see Text#getChildren()
     */
    public Builder removeAll() {
      this.children.clear();
      return this;
    }

    /**
     * Removes all empty texts from the beginning and end of this
     * builder.
     *
     * @return This builder
     */
    public Builder trim() {
      Iterator<Text> front = this.children.iterator();
      while (front.hasNext()) {
        if (front.next().isEmpty()) {
          front.remove();
        } else {
          break;
        }
      }
      ListIterator<Text> back = this.children.listIterator(this.children.size());
      while (back.hasPrevious()) {
        if (back.previous().isEmpty()) {
          back.remove();
        } else {
          break;
        }
      }
      return this;
    }

    /**
     * Builds an immutable instance of the current state of this text
     * builder.
     *
     * @return An immutable {@link Text} with the current properties of this
     * builder
     */
    public abstract Text build();

    @Override
    public boolean equals(@Nullable Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Builder)) {
        return false;
      }

      Builder that = (Builder) o;
      return Objects.equals(this.format, that.format)
          && Objects.equals(this.children, that.children);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.format, this.children);
    }

    MoreObjects.ToStringHelper toStringHelper() {
      return MoreObjects.toStringHelper(Builder.class)
          .omitNullValues()
          .add("format", this.format.isEmpty() ? null : this.format)
          .add("children", this.children.isEmpty() ? null : this.children);
    }

    @Override
    public final String toString() {
      return toStringHelper().toString();
    }

    public final Text toText() {
      return build();
    }
  }
}

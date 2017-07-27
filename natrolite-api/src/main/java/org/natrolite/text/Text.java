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
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import net.xnity.odium.ChatMessageType;
import net.xnity.odium.Odium;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.natrolite.dictionary.TranslationDictionaries;
import org.natrolite.dictionary.TranslationDictionary;
import org.natrolite.plugin.NeoJavaPlugin;
import org.natrolite.text.action.ClickAction;
import org.natrolite.text.action.HoverAction;
import org.natrolite.text.action.ShiftClickAction;
import org.natrolite.text.format.TextColor;
import org.natrolite.text.format.TextFormat;
import org.natrolite.text.format.TextStyle;
import org.natrolite.text.serialisation.TextSerializers;

public abstract class Text {

  /**
   * The empty, unformatted {@link Text} instance.
   */
  public static final Text EMPTY = LiteralText.EMPTY;

  static final char NEW_LINE_CHAR = '\n';
  static final String NEW_LINE_STRING = "\n";

  /**
   * An unformatted {@link Text} that will start a new line (if supported).
   */
  public static final LiteralText NEW_LINE = new LiteralText(NEW_LINE_STRING);

  final TextFormat format;
  final ImmutableList<Text> children;
  final Optional<ClickAction<?>> clickAction;
  final Optional<HoverAction<?>> hoverAction;
  final Optional<ShiftClickAction<?>> shiftClickAction;
  final Object[] args;

  /**
   * An {@link Iterable} providing an {@link Iterator} over this {@link Text}
   * as well as all children text and their children.
   */
  final Iterable<Text> childrenIterable;

  Text() {
    this.format = TextFormat.NONE;
    this.clickAction = Optional.empty();
    this.hoverAction = Optional.empty();
    this.shiftClickAction = Optional.empty();
    this.args = new Object[0];
    this.children = ImmutableList.of();
    this.childrenIterable = () -> Iterators.singletonIterator(this);
  }

  /**
   * Constructs a new immutable {@link Text} with the specified formatting and
   * text actions applied.
   *
   * @param format           The format of the text
   * @param children         The immutable list of children of the text
   * @param clickAction      The click action of the text, or {@code null} for none
   * @param hoverAction      The hover action of the text, or {@code null} for none
   * @param shiftClickAction The shift click action of the text, or
   *                         {@code null} for none
   */
  Text(TextFormat format, ImmutableList<Text> children, @Nullable ClickAction<?> clickAction,
      @Nullable HoverAction<?> hoverAction, @Nullable ShiftClickAction<?> shiftClickAction,
      Object[] args) {
    this.format = checkNotNull(format, "format");
    this.children = checkNotNull(children, "children");
    this.clickAction = Optional.ofNullable(clickAction);
    this.hoverAction = Optional.ofNullable(hoverAction);
    this.shiftClickAction = Optional.ofNullable(shiftClickAction);
    this.args = checkNotNull(args, "args");
    this.childrenIterable = () -> new TextIterator(this);
  }

  /**
   * Returns an empty, unformatted {@link Text} instance.
   *
   * @return An empty text
   */
  public static Text of() {
    return EMPTY;
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
   * Returns the immutable list of children appended after the content of this
   * {@link Text}.
   *
   * @return The immutable list of children
   */
  public final ImmutableList<Text> getChildren() {
    return this.children;
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
  public static Optional<Text> get(NeoJavaPlugin plugin, String key) {
    return TranslationDictionaries.plugin(plugin)
        .map(dictionary -> dictionary.get(key).map(Text::of).orElse(null));
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
   * Gets an optional {@link Text} from the provided
   * plugin's {@link TranslationDictionary} by key and locale.
   *
   * @param plugin The plugin to retrieve the translation dictionary from
   * @param key    The translation key
   * @param locale The locale under which the value should be obtained in
   * @return The text, if present, {@link Optional#empty()} otherwise
   */
  public static Optional<Text> get(NeoJavaPlugin plugin, String key, Locale locale) {
    return TranslationDictionaries.plugin(plugin)
        .map(dictionary -> dictionary.get(key, locale).map(Text::of).orElse(null));
  }

  public static Text un(NeoJavaPlugin plugin, String key) {
    return get(plugin, key).orElseThrow(IllegalStateException::new);
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

  public static Text un(NeoJavaPlugin plugin, String key, Locale locale) {
    return get(plugin, key, locale).orElseThrow(IllegalStateException::new);
  }

  public static Text.Builder unb(NeoJavaPlugin plugin, String key) {
    return get(plugin, key).orElseThrow(IllegalStateException::new).toBuilder();
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

  public static Text.Builder unb(NeoJavaPlugin plugin, String key, Locale locale) {
    return get(plugin, key, locale).orElseThrow(IllegalStateException::new).toBuilder();
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

  @Override
  public final String toString() {
    return toStringHelper().toString();
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
   * Joins a sequence of text objects together.
   *
   * @param texts The texts to join
   * @return A text object that joins the given text objects
   */
  public static Text join(Text... texts) {
    return builder().append(texts).build();
  }

  /**
   * Joins a sequence of text objects together.
   *
   * @param texts The texts to join
   * @return A text object that joins the given text objects
   */
  public static Text join(Iterable<? extends Text> texts) {
    return builder().append(texts).build();
  }

  /**
   * Joins a sequence of text objects together.
   *
   * @param texts The texts to join
   * @return A text object that joins the given text objects
   */
  public static Text join(Iterator<? extends Text> texts) {
    return builder().append(texts).build();
  }

  /**
   * Joins a sequence of text objects together along with a separator.
   *
   * @param separator The separator
   * @param texts     The texts to join
   * @return A text object that joins the given text objects
   */
  public static Text joinWith(Text separator, Text... texts) {
    switch (texts.length) {
      case 0:
        return EMPTY;
      case 1:
        return texts[0];
      default:
        Text.Builder builder = builder();
        boolean appendSeparator = false;
        for (Text text : texts) {
          if (appendSeparator) {
            builder.append(separator);
          } else {
            appendSeparator = true;
          }
          builder.append(text);
        }
        return builder.build();
    }
  }

  /**
   * Joins a sequence of text objects together along with a separator.
   *
   * @param separator The separator
   * @param texts     The texts to join
   * @return A text object that joins the given text objects
   */
  public static Text joinWith(Text separator, Iterable<? extends Text> texts) {
    return joinWith(separator, texts.iterator());
  }

  /**
   * Joins a sequence of text objects together along with a separator.
   *
   * @param separator The separator
   * @param texts     An iterator for the texts to join
   * @return A text object that joins the given text objects
   */
  public static Text joinWith(Text separator, Iterator<? extends Text> texts) {
    if (!texts.hasNext()) {
      return EMPTY;
    }

    Text first = texts.next();
    if (!texts.hasNext()) {
      return first;
    }

    Text.Builder builder = builder().append(first);
    do {
      builder.append(separator);
      builder.append(texts.next());
    }
    while (texts.hasNext());

    return builder.build();
  }

  /**
   * Returns the color of this {@link Text}.
   *
   * @return The color of this text
   */
  public final Optional<TextColor> getColor() {
    return this.format.getColor();
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
   * Returns the {@link ClickAction} executed on the client when this
   * {@link Text} gets clicked.
   *
   * @return The click action of this text, or {@link Optional#empty()} if not set
   */
  public final Optional<ClickAction<?>> getClickAction() {
    return this.clickAction;
  }

  /**
   * Returns the {@link HoverAction} executed on the client when this
   * {@link Text} gets hovered.
   *
   * @return The hover action of this text, or {@link Optional#empty()} if not set
   */
  public final Optional<HoverAction<?>> getHoverAction() {
    return this.hoverAction;
  }

  /**
   * Returns the {@link ShiftClickAction} executed on the client when this
   * {@link Text} gets shift-clicked.
   *
   * @return The shift-click action of this text, or {@link Optional#empty()} if not set
   */
  public final Optional<ShiftClickAction<?>> getShiftClickAction() {
    return this.shiftClickAction;
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

  public final Object[] getArgs() {
    return args;
  }

  public final void to(CommandSender... senders) {
    to(ChatMessageType.CHAT, senders);
  }

  /**
   * Sends this {@link Text} to an {@link CommandSender}.
   *
   * @param senders the senders that should receive the message
   */
  public final void to(ChatMessageType type, CommandSender... senders) {
    for (CommandSender sender : senders) {
      if (sender instanceof Player) {
        Odium.sendJsonMessage((Player) sender, toJson(), type);
        continue;
      }
      sender.sendMessage(toPlain());
    }
  }

  public final String toPlain() {
    return TextSerializers.PLAIN.serialize(this);
  }

  public final String toJson() {
    return TextSerializers.JSON.serialize(this);
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
    return this.format.equals(that.format)
        && this.children.equals(that.children)
        && this.clickAction.equals(that.clickAction)
        && this.hoverAction.equals(that.hoverAction)
        && this.shiftClickAction.equals(that.shiftClickAction);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.format, this.children, this.clickAction, this.hoverAction, this.shiftClickAction);
  }

  MoreObjects.ToStringHelper toStringHelper() {
    return MoreObjects.toStringHelper(Text.class)
        .omitNullValues()
        .add("format", this.format.isEmpty() ? null : this.format)
        .add("children", this.children.isEmpty() ? null : this.children)
        .add("clickAction", this.clickAction.orElse(null))
        .add("hoverAction", this.hoverAction.orElse(null))
        .add("shiftClickAction", this.shiftClickAction.orElse(null));
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
   * Represents a builder class to create immutable {@link Text} instances.
   *
   * @see Text
   */
  public abstract static class Builder {

    TextFormat format = TextFormat.NONE;
    List<Text> children = new ArrayList<>();
    Object[] args = new Object[0];
    @Nullable ClickAction<?> clickAction;
    @Nullable HoverAction<?> hoverAction;
    @Nullable ShiftClickAction<?> shiftClickAction;

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
      this.clickAction = text.clickAction.orElse(null);
      this.hoverAction = text.hoverAction.orElse(null);
      this.shiftClickAction = text.shiftClickAction.orElse(null);
      this.args = text.args;
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
    public final Optional<TextColor> getColor() {
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

    public Builder args(Object... args) {
      this.args = args;
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
     * @param styles The text styles to apply
     * @return This text builder
     * @see Text#getStyle()
     */
    // TODO: Make sure this is the correct behaviour
    public Builder style(TextStyle... styles) {
      this.format = this.format.style(this.format.getStyle().and(styles));
      return this;
    }

    /**
     * Returns the current {@link ClickAction} of this builder.
     *
     * @return The current click action or {@link Optional#empty()} if none
     * @see Text#getClickAction()
     */
    public final Optional<ClickAction<?>> getClickAction() {
      return Optional.ofNullable(this.clickAction);
    }

    /**
     * Sets the {@link ClickAction} that will be executed if the text is
     * clicked in the chat.
     *
     * @param clickAction The new click action for the text
     * @return This text builder
     * @see Text#getClickAction()
     */
    public Builder onClick(@Nullable ClickAction<?> clickAction) {
      this.clickAction = clickAction;
      return this;
    }

    /**
     * Returns the current {@link HoverAction} of this builder.
     *
     * @return The current hover action or {@link Optional#empty()} if none
     * @see Text#getHoverAction()
     */
    public final Optional<HoverAction<?>> getHoverAction() {
      return Optional.ofNullable(this.hoverAction);
    }

    /**
     * Sets the {@link HoverAction} that will be executed if the text is
     * hovered in the chat.
     *
     * @param hoverAction The new hover action for the text
     * @return This text builder
     * @see Text#getHoverAction()
     */
    public Builder onHover(@Nullable HoverAction<?> hoverAction) {
      this.hoverAction = hoverAction;
      return this;
    }

    /**
     * Returns the current {@link ShiftClickAction} of this builder.
     *
     * @return The current shift click action or {@link Optional#empty()} if none
     * @see Text#getShiftClickAction()
     */
    public final Optional<ShiftClickAction<?>> getShiftClickAction() {
      return Optional.ofNullable(this.shiftClickAction);
    }

    /**
     * Sets the {@link ShiftClickAction} that will be executed if the text
     * is shift-clicked in the chat.
     *
     * @param shiftClickAction The new shift click action for the text
     * @return This text builder
     * @see Text#getShiftClickAction()
     */
    public Builder onShiftClick(@Nullable ShiftClickAction<?> shiftClickAction) {
      this.shiftClickAction = shiftClickAction;
      return this;
    }

    /**
     * Returns a view of the current children of this builder.
     *
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
     * @return An immutable {@link Text} with the current properties of this builder
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
      return Objects.equal(this.format, that.format)
          && Objects.equal(this.clickAction, that.clickAction)
          && Objects.equal(this.hoverAction, that.hoverAction)
          && Objects.equal(this.shiftClickAction, that.shiftClickAction)
          && Objects.equal(this.children, that.children);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(this.format, this.clickAction, this.hoverAction,
          this.shiftClickAction, this.children);
    }

    MoreObjects.ToStringHelper toStringHelper() {
      return MoreObjects.toStringHelper(Builder.class)
          .omitNullValues()
          .add("format", this.format.isEmpty() ? null : this.format)
          .add("children", this.children.isEmpty() ? null : this.children)
          .add("clickAction", this.clickAction)
          .add("hoverAction", this.hoverAction)
          .add("shiftClickAction", this.shiftClickAction);
    }

    @Override
    public final String toString() {
      return toStringHelper().toString();
    }
  }
}

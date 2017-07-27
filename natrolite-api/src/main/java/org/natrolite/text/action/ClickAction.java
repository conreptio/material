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

package org.natrolite.text.action;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.net.URL;
import org.natrolite.text.Text;

public abstract class ClickAction<R> extends TextAction<R> {

  /**
   * Constructs a new {@link ClickAction} with the given result.
   *
   * @param result The result of the click action
   */
  ClickAction(R result) {
    super(result);
  }

  @Override
  public void applyTo(Text.Builder builder) {
    builder.onClick(this);
  }

  /**
   * Opens a url.
   */
  public static final class OpenUrl extends ClickAction<URL> {

    /**
     * Constructs a new {@link OpenUrl} instance that will ask the player to
     * open an URL when it is clicked.
     *
     * @param url The url to open
     */
    OpenUrl(URL url) {
      super(url);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      object.addProperty("action", "open_url");
      object.addProperty("value", result.toString());
    }
  }

  /**
   * Runs a command.
   */
  public static final class RunCommand extends ClickAction<String> {

    /**
     * Constructs a new {@link RunCommand} instance that will run a command
     * on the client when it is clicked.
     *
     * @param command The command to execute
     */
    RunCommand(String command) {
      super(command);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      object.addProperty("action", "run_command");
      object.addProperty("value", result);
    }
  }

  /**
   * For books, changes pages.
   */
  public static final class ChangePage extends ClickAction<Integer> {

    /**
     * Constructs a new {@link ChangePage} instance that will change the
     * page in a book when it is clicked.
     *
     * @param page The book page to switch to
     */
    ChangePage(int page) {
      super(page);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      object.addProperty("action", "change_page");
      object.addProperty("value", result);
    }
  }

  /**
   * Suggests a command in the prompt.
   */
  public static final class SuggestCommand extends ClickAction<String> {

    /**
     * Constructs a new {@link SuggestCommand} instance that will suggest
     * the player a command when it is clicked.
     *
     * @param command The command to suggest
     */
    SuggestCommand(String command) {
      super(command);
    }

    @Override
    public void apply(JsonObject object, JsonSerializationContext context) {
      object.addProperty("action", "suggest_command");
      object.addProperty("value", result);
    }
  }
}
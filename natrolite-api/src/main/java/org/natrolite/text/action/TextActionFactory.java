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

import java.net.URL;
import org.bukkit.inventory.ItemStack;
import org.natrolite.text.Text;

/**
 * A factory to create {@link TextAction}s.
 */
public interface TextActionFactory {

  /**
   * Creates a new {@link ClickAction} that will ask the player to open an URL
   * when it is clicked.
   *
   * @param url The URL to open
   * @return The created click action instance
   */
  ClickAction.OpenUrl openUrl(URL url);

  /**
   * Creates a new {@link ClickAction} that will type a command on the client
   * when it is clicked.
   *
   * @param command The command to execute
   * @return The created click action instance
   */
  ClickAction.RunCommand runCommand(String command);

  /**
   * Creates a new {@link ClickAction} that will change the page in a book
   * when it is clicked.
   *
   * @param page The book page to switch to
   * @return The created click action instance
   */
  ClickAction.ChangePage changePage(int page);

  /**
   * Creates a new {@link ClickAction} that will suggest the player a command
   * when it is clicked.
   *
   * @param command The command to suggest
   * @return The created click action instance
   */
  ClickAction.SuggestCommand suggestCommand(String command);

  /**
   * Creates a new {@link HoverAction} that will show a text on the client
   * when it is hovered.
   *
   * @param text The text to display
   * @return The created hover action instance
   */
  HoverAction.ShowText showText(Text text);

  /**
   * Creates a new {@link HoverAction} that will show information about an
   * item when it is hovered.
   *
   * @param stack The item to display
   * @return The created hover action instance
   */
  HoverAction.ShowItem showItem(ItemStack stack);

  /**
   * Creates a new {@link HoverAction} that will show information about an
   * entity when it is hovered.
   *
   * @param entity The entity to display
   * @return The created hover action instance
   */
  HoverAction.ShowEntity showEntity(HoverAction.ShowEntity.Ref entity);

  /**
   * Creates a new {@link ShiftClickAction} that will insert text at the
   * current cursor position in the chat when it is shift-clicked.
   *
   * @param text The text to insert
   * @return The created shift click action instance
   */
  ShiftClickAction.InsertText insertText(String text);
}

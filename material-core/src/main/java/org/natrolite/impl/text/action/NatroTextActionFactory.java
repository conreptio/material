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

package org.natrolite.impl.text.action;

import java.net.URL;
import org.bukkit.inventory.ItemStack;
import org.natrolite.text.Text;
import org.natrolite.text.action.ClickAction;
import org.natrolite.text.action.HoverAction;
import org.natrolite.text.action.ShiftClickAction;
import org.natrolite.text.action.TextActionFactory;

public enum NatroTextActionFactory implements TextActionFactory {

  INSTANCE;

  @Override
  public ClickAction.OpenUrl openUrl(URL url) {
    return new NatroClickTextAction.NatroOpenUrl(url);
  }

  @Override
  public ClickAction.RunCommand runCommand(String command) {
    return new NatroClickTextAction.NatroRunCommand(command);
  }

  @Override
  public ClickAction.ChangePage changePage(int page) {
    return new NatroClickTextAction.NatroChangePage(page);
  }

  @Override
  public ClickAction.SuggestCommand suggestCommand(String command) {
    return new NatroClickTextAction.NatroSuggestCommand(command);
  }

  @Override
  public HoverAction.ShowText showText(Text text) {
    return new NatroHoverTextAction.NatroShowText(text);
  }

  @Override
  public HoverAction.ShowItem showItem(ItemStack item) {
    return new NatroHoverTextAction.NatroShowItem(item);
  }

  @Override
  public HoverAction.ShowEntity showEntity(HoverAction.ShowEntity.Ref entity) {
    return new NatroHoverTextAction.NatroShowEntity(entity);
  }

  @Override
  public ShiftClickAction.InsertText insertText(String text) {
    return new NatroShiftClickTextAction.NatroInsertText(text);
  }
}

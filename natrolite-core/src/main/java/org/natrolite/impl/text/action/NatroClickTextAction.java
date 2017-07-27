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

import com.google.gson.JsonObject;
import java.net.URL;
import org.natrolite.text.action.ClickAction;
import org.natrolite.text.serialisation.TextSerializer;

abstract class NatroClickTextAction<R> extends NatroTextAction<R> implements ClickAction<R> {

  NatroClickTextAction(R result) {
    super(result);
  }

  static final class NatroOpenUrl extends NatroClickTextAction<URL> implements OpenUrl {

    NatroOpenUrl(URL result) {
      super(result);
    }

    @Override
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      JsonObject obj = new JsonObject();
      obj.addProperty(ACTION, "open_url");
      obj.addProperty(VALUE, result.toString());
      object.add(CLICK, obj);
    }
  }

  static final class NatroRunCommand extends NatroClickTextAction<String> implements RunCommand {

    NatroRunCommand(String result) {
      super(result);
    }

    @Override
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      JsonObject obj = new JsonObject();
      obj.addProperty(ACTION, "run_command");
      obj.addProperty(VALUE, result);
      object.add(CLICK, obj);
    }
  }

  static final class NatroChangePage extends NatroClickTextAction<Integer> implements ChangePage {

    NatroChangePage(Integer result) {
      super(result);
    }

    @Override
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      JsonObject obj = new JsonObject();
      obj.addProperty(ACTION, "change_page");
      obj.addProperty(VALUE, result);
      object.add(CLICK, obj);
    }
  }

  static final class NatroSuggestCommand extends NatroClickTextAction<String>
      implements SuggestCommand {

    NatroSuggestCommand(String result) {
      super(result);
    }

    @Override
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      JsonObject obj = new JsonObject();
      obj.addProperty(ACTION, "suggest_command");
      obj.addProperty(VALUE, result);
      object.add(CLICK, obj);
    }
  }
}

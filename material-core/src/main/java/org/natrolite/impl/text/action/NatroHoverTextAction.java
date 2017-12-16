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
import org.bukkit.inventory.ItemStack;
import org.natrolite.text.Text;
import org.natrolite.text.action.HoverAction;
import org.natrolite.text.serialisation.TextSerializer;

abstract class NatroHoverTextAction<R> extends NatroTextAction<R> implements HoverAction<R> {

  NatroHoverTextAction(R result) {
    super(result);
  }

  static final class NatroShowText extends NatroHoverTextAction<Text> implements ShowText {

    NatroShowText(Text result) {
      super(result);
    }

    @Override
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      final JsonObject obj = new JsonObject();
      obj.addProperty(ACTION, "show_text");
      obj.add(VALUE, serializer.serializeJson(result));
      object.add(HOVER, obj);
    }
  }

  static final class NatroShowItem extends NatroHoverTextAction<ItemStack> implements ShowItem {

    NatroShowItem(ItemStack result) {
      super(result);
    }

    @Override
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      final JsonObject obj = new JsonObject();
      obj.addProperty(ACTION, "show_item");
      //TODO obj.addProperty(VALUE, Odium.itemToJson(getResult()).orElse("{}"));
      object.add(HOVER, obj);
    }
  }

  static final class NatroShowEntity extends NatroHoverTextAction<ShowEntity.Ref>
    implements ShowEntity {

    NatroShowEntity(Ref result) {
      super(result);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void serialize(JsonObject object, TextSerializer.Json serializer) {
      final JsonObject obj = new JsonObject();
      final JsonObject ent = new JsonObject();
      ent.addProperty("id", result.getUniqueId().toString());
      ent.addProperty("name", result.getName());
      result.getType().ifPresent(type -> {
        if (type.getName() != null) {
          ent.addProperty("type", "minecraft:" + type.getName());
        }
      });
      obj.addProperty(ACTION, "show_entity");
      obj.addProperty(VALUE, ent.toString());
      object.add(HOVER, obj);
    }
  }
}

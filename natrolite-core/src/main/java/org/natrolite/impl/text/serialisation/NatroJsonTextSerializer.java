/*
 * This file is part of Natrolite.
 *
 * Copyright (C) 2016-2017 Lukas Nehrke
 *
 * Natrolite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Natrolite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Natrolite. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.impl.text.serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.natrolite.text.LiteralText;
import org.natrolite.text.Text;
import org.natrolite.text.serialisation.TextSerializer;

public enum NatroJsonTextSerializer implements TextSerializer.Json, JsonSerializer<Text> {

  INSTANCE;

  private static final Gson gson = new GsonBuilder()
      .registerTypeHierarchyAdapter(Text.class, NatroJsonTextSerializer.INSTANCE)
      .create();

  @Override
  public JsonElement serializeJson(Text text) {
    return gson.toJsonTree(text, Text.class);
  }

  @Override
  public String serialize(Text text) {
    return gson.toJson(text, Text.class);
  }

  @Override
  public JsonElement serialize(Text text, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject object = new JsonObject();

    text.getColor().ifPresent(c -> object.addProperty("color", c.getName()));
    text.getStyle().isBold().ifPresent(b -> object.addProperty("bold", b));
    text.getStyle().isItalic().ifPresent(b -> object.addProperty("italic", b));
    text.getStyle().hasUnderline().ifPresent(b -> object.addProperty("underlined", b));
    text.getStyle().hasStrikethrough().ifPresent(b -> object.addProperty("strikethrough", b));
    text.getStyle().isObfuscated().ifPresent(b -> object.addProperty("obfuscated", b));
    text.getClickAction().ifPresent(a -> a.serialize(object, this));
    text.getHoverAction().ifPresent(a -> a.serialize(object, this));
    text.getShiftClickAction().ifPresent(a -> a.serialize(object, this));

    if (text instanceof LiteralText) {
      object.addProperty("text", ((LiteralText) text).getContentFormatted());
    }

    if (!text.getChildren().isEmpty()) {
      object.add("extra", context.serialize(text.getChildren()));
    }

    return object;
  }
}

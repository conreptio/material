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

package org.natrolite.text.serialisation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.natrolite.text.Text;

public abstract class TextSerializer {

  protected JsonElement serialize(JsonObject object, Text text, JsonSerializationContext context) {
    text.getColor().ifPresent(c -> object.addProperty("color", c.getName()));
    text.getStyle().isBold().ifPresent(b -> object.addProperty("bold", b));
    text.getStyle().isItalic().ifPresent(b -> object.addProperty("italic", b));
    text.getStyle().hasUnderline().ifPresent(b -> object.addProperty("underlined", b));
    text.getStyle().hasStrikethrough().ifPresent(b -> object.addProperty("strikethrough", b));
    text.getStyle().isObfuscated().ifPresent(b -> object.addProperty("obfuscated", b));

    text.getClickAction().ifPresent(a -> {
      JsonObject clickAction = new JsonObject();
      a.apply(clickAction, context);
      object.add("clickEvent", clickAction);
    });

    text.getHoverAction().ifPresent(a -> {
      JsonObject hoverAction = new JsonObject();
      a.apply(hoverAction, context);
      object.add("hoverEvent", hoverAction);
    });

    text.getShiftClickAction().ifPresent(a -> a.apply(object, context));

    if (!text.getChildren().isEmpty()) {
      object.add("extra", context.serialize(text.getChildren()));
    }
    return object;
  }
}

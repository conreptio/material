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

import org.natrolite.text.LiteralText;
import org.natrolite.text.Text;
import org.natrolite.text.serialisation.TextSerializer;

public enum NatroPlainTextSerializer implements TextSerializer.Plain {

  INSTANCE;

  @Override
  public String serialize(Text text) {
    final StringBuilder builder = new StringBuilder();
    text.withChildren().forEach(t -> {
      if (t instanceof LiteralText) {
        builder.append(((LiteralText) t).getContentFormatted());
      }
    });
    return builder.toString();
  }
}

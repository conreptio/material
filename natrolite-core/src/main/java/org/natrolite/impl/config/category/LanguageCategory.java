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

package org.natrolite.impl.config.category;

import static com.google.common.base.MoreObjects.firstNonNull;
import static org.natrolite.util.Locales.DEFAULT;

import java.util.Locale;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class LanguageCategory {

  @Setting("locale")
  private String locale = DEFAULT.toLanguageTag();

  @Setting("custom")
  private boolean custom = false;

  public Locale locale() {
    return Locale.forLanguageTag(firstNonNull(locale, DEFAULT.toLanguageTag()).replace("_", "-"));
  }

  public boolean custom() {
    return custom;
  }
}

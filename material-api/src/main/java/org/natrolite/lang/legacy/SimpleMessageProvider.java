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

package org.natrolite.lang.legacy;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Deprecated
public class SimpleMessageProvider implements MessageProvider {

  private final String bundle;

  public SimpleMessageProvider(String bundle) {
    this.bundle = checkNotNull(bundle);
  }

  public Optional<String> get(String key, Object... args) {
    return get(key, getDefault(), args);
  }

  public Optional<String> get(String key, Locale locale, Object... args) {
    return get(key, locale).map(s -> MessageFormat.format(s, args));
  }

  @Override
  public ResourceBundle getBundle(Locale locale) {
    return ResourceBundle.getBundle(bundle, locale);
  }
}

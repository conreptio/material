/*
 * This file is part of Material.
 *
 * Copyright (c) 2016-2017 Neolumia
 *
 * Material is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Material is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Material. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.lang.legacy;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;

@Deprecated
public interface MessageProvider {

  default Locale getDefault() {
    return Locale.ENGLISH;
  }

  default Optional<String> get(String key) {
    return get(key, getDefault());
  }

  default Optional<String> get(String key, Locale locale) {
    try {
      return Optional.of(getBundle(locale).getString(key));
    } catch (MissingResourceException ex1) {
      try {
        return Optional.of(getBundle(getDefault()).getString(key));
      } catch (MissingResourceException ex2) {
        return Optional.empty();
      }
    }
  }

  ResourceBundle getBundle(Locale locale);
}

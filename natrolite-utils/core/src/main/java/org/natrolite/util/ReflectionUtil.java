/*
 * Natrolite - Minecraft Minigame Manager
 *
 * Copyright (c) 2017 XNITY <info@xnity.net>
 * Copyright (c) 2017 Natrolite <info@natrolite.org>
 * Copyright (c) 2017 Lukas Nehrke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.natrolite.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

public final class ReflectionUtil {

  private ReflectionUtil() {}

  /**
   * Sets a static final field.
   *
   * @param clazz the class
   * @param field the field name
   * @param value the new value
   */
  public static void setFinalStatic(Class<?> clazz, String field, Object value) {
    try {
      setFinalStatic(clazz.getDeclaredField(field), value);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Sets a static final field.
   *
   * @param field    the field
   * @param newValue the new value
   */
  public static void setFinalStatic(Field field, Object newValue) throws Exception {
    field.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    field.set(null, newValue);
  }

  /**
   * Gets a {@link Field} from a {@link Class}.
   *
   * @param clazz the class
   * @param name  the filed name
   * @return the field
   * @throws NoSuchFieldException if the field does not exist
   */
  public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
    Field field = clazz.getDeclaredField(name);
    field.setAccessible(true);
    return field;
  }

  /**
   * Gets the value of a {@link Field}.
   *
   * @param object the object with the field
   * @param name   the field name
   * @return the field value
   * @throws NoSuchFieldException   if the field does not exist
   * @throws IllegalAccessException if the value could not be retrieved
   */
  public static Object getFieldInstance(Object object, String name) throws NoSuchFieldException,
      IllegalAccessException {
    return getField(object.getClass(), name).get(object);
  }

  /**
   * Gets the annotation of a method if it is present.
   *
   * @param method     the method
   * @param annotation the annotation class
   * @param <T>        the annotation
   * @return the annotation instance or {@link Optional#empty()}
   */
  public static <T extends Annotation> Optional<T> getIfPresent(Method method,
      Class<T> annotation) {
    if (method.isAnnotationPresent(annotation)) {
      return Optional.of(method.getAnnotation(annotation));
    }
    return Optional.empty();
  }
}

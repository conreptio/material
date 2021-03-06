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

package org.natrolite.impl.registry;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import org.natrolite.registry.Registry;
import org.natrolite.registry.ResettableBuilder;
import org.natrolite.util.PlayerUtils;

public final class NatroliteRegistry implements Registry {

  private final Map<Class<?>, Supplier<?>> builderMap = new IdentityHashMap<>();
  private final Map<Class<?>, Supplier<?>> implMap = new IdentityHashMap<>();

  @Override
  public <T> Registry registerBuilder(Class<T> builderClass, Supplier<? extends T> supplier) {
    checkArgument(!builderMap.containsKey(builderClass), "Supplier already registered");
    builderMap.put(builderClass, supplier);
    return this;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends ResettableBuilder<?, ? super T>> T createBuilder(Class<T> builderClass) {
    checkNotNull(builderClass, "Builder class was null!");
    final Supplier<?> supplier = builderMap.get(builderClass);
    if (supplier == null) {
      throw new IllegalArgumentException("Could not find a Supplier for the builder class: "
          + builderClass.getCanonicalName());
    }
    return (T) supplier.get();
  }

  @Override
  public <T> Registry registerImplementation(Class<T> clazz, Supplier<? extends T> supplier) {
    checkArgument(!implMap.containsKey(clazz), "Supplier already registered");
    implMap.put(clazz, supplier);
    return this;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getImplementation(Class<T> clazz) {
    checkNotNull(clazz, "Interface class was null!");
    final Supplier<?> supplier = implMap.get(clazz);
    if (supplier == null) {
      throw new IllegalArgumentException("Could not find a Supplier for class: "
          + clazz.getCanonicalName());
    }
    return (T) supplier.get();
  }

  @Override
  public Locale getLocale(String locale) {
    //TODO
    return PlayerUtils.formatMinecraftLocale(locale).orElseThrow(IllegalArgumentException::new);
  }
}

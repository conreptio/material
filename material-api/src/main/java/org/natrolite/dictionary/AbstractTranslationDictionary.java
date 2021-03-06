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

package org.natrolite.dictionary;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;
import org.natrolite.util.Locales;

/**
 * An abstract implementation of a {@link TranslationDictionary}.
 */
public abstract class AbstractTranslationDictionary implements TranslationDictionary {

  protected final Object subject;
  protected final Locale defaultLocale;

  /**
   * Constructs an abstract dictionary with
   * the {@link Locales#DEFAULT default} locale.
   *
   * @param subject The subject of this dictionary
   */
  protected AbstractTranslationDictionary(Object subject) {
    this(subject, Locales.DEFAULT);
  }

  /**
   * Constructs an abstract dictionary with a default locale.
   *
   * @param subject       The subject of this dictionary
   * @param defaultLocale The default locale for this dictionary
   */
  protected AbstractTranslationDictionary(Object subject, Locale defaultLocale) {
    this.subject = checkNotNull(subject, "subject");
    this.defaultLocale = checkNotNull(defaultLocale, "default locale");
  }

  @Override
  public Object getSubject() {
    return this.subject;
  }

  @Override
  public Locale getDefaultLocale() {
    return this.defaultLocale;
  }
}
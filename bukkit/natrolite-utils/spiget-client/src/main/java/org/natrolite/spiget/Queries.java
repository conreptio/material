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

package org.natrolite.spiget;

public final class Queries {

  public static final String BASE = "http://api.spiget.org/v2";
  public static final String AUTHOR_LIST = "/authors";
  public static final String AUTHOR_DETAILS = "/authors/%s";
  public static final String AUTHOR_RESOURCES = "/authors/%s/resources";
  public static final String AUTHOR_REVIEWS = "/authors/%s/reviews";
  public static final String AUTHOR_SEARCH = "/search/authors/%s";
  public static final String CATEGORY_LIST = "/categories";
  public static final String CATEGORY_DETAILS = "/categories/%s";
  public static final String CATEGORY_RESOURCES = "/categories/%s/resources";
  public static final String RESOURCE_DETAILS = "/resources/%s";
  public static final String RESOURCE_UPDATES = "/resources/%s/updates";
  public static final String RESOURCE_VERSIONS = "/resources/%s/versions";
  public static final String RESOURCE_VERSION = "/resources/%s/versions/%s";
  public static final String RESOURCE_VERSION_DOWNLOAD = "/resources/%s/version/%s/download";

  private Queries() {}
}

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

package org.natrolite.spiget.client;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.natrolite.spiget.Author;
import org.natrolite.spiget.Category;
import org.natrolite.spiget.Resource;
import org.natrolite.spiget.Version;

public class Spiget {

  public static final String AGENT = "Natrolite/1.0";
  protected static final Gson gson = new Gson();
  protected String url;
  @Nullable protected String agent;

  public Spiget(String url) {
    this.url = checkNotNull(url);
  }

  public Spiget(String url, @Nullable String agent) {
    this.url = checkNotNull(url);
    this.agent = agent;
  }

  public static Spiget of(String url) {
    return new Spiget(url);
  }

  public static Spiget of(String url, String agent) {
    return new Spiget(url, agent);
  }

  public static CompletableFuture<List<Category>> getCategories() {
    return of(Queries.CATEGORY_LIST).execute(Tokens.CATEGORIES);
  }

  public static CompletableFuture<Resource> getResource(String id) {
    return of(String.format(Queries.RESOURCE_DETAILS, id)).execute(Resource.class);
  }

  public static CompletableFuture<Author> getAuthor(String id) {
    return of(String.format(Queries.AUTHOR_DETAILS, id)).execute(Author.class);
  }

  public static CompletableFuture<Version> getVersion(String resource, String version) {
    return of(String.format(Queries.RESOURCE_VERSION, resource, version)).execute(Version.class);
  }

  public static CompletableFuture<Version> getLatestVersion(String resource) {
    return of(String.format(Queries.RESOURCE_VERSION, resource, "latest")).execute(Version.class);
  }

  public CompletableFuture<String> execute() {
    return supplyAsync(this::read);
  }

  public <T> CompletableFuture<T> execute(Class<T> object) {
    return supplyAsync(this::read).thenApplyAsync(s -> gson.fromJson(s, object));
  }

  public <T> CompletableFuture<T> execute(TypeToken<T> type) {
    return supplyAsync(this::read).thenApplyAsync(s -> gson.fromJson(s, type.getType()));
  }

  private String read() throws RuntimeException {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(Queries.BASE + url).openConnection();
      connection.setRequestProperty("User-Agent", agent != null ? agent : AGENT);
      try (InputStream in = connection.getInputStream()) {
        Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

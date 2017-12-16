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

package org.natrolite.updater.lightning;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("all")
public class LightningElement {

  @SerializedName("latest")
  @Expose
  private String latest;

  @SerializedName("inform")
  @Expose
  private boolean inform;

  @SerializedName("install")
  @Expose
  private boolean install;

  @SerializedName("url")
  @Expose
  private String url;

  public String getLatest() {
    return latest;
  }

  public void setLatest(String latest) {
    this.latest = latest;
  }

  public boolean isInform() {
    return inform;
  }

  public void setInform(boolean inform) {
    this.inform = inform;
  }

  public boolean isInstall() {
    return install;
  }

  public void setInstall(boolean install) {
    this.install = install;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

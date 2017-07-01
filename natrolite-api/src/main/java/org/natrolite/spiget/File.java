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

package org.natrolite.spiget;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

  @SerializedName("type")
  @Expose
  private String type;

  @SerializedName("size")
  @Expose
  private Integer size;

  @SerializedName("sizeUnit")
  @Expose
  private String sizeUnit;

  @SerializedName("url")
  @Expose
  private String url;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getSizeUnit() {
    return sizeUnit;
  }

  public void setSizeUnit(String sizeUnit) {
    this.sizeUnit = sizeUnit;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

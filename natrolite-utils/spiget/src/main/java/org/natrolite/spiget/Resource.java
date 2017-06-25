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
import java.util.List;

public class Resource {

  @SerializedName("id")
  @Expose
  private Integer id;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("tag")
  @Expose
  private String tag;

  @SerializedName("contributors")
  @Expose
  private String contributors;

  @SerializedName("likes")
  @Expose
  private Integer likes;

  @SerializedName("file")
  @Expose
  private File file;

  @SerializedName("testedVersions")
  @Expose
  private List<String> testedVersions = null;

  @SerializedName("links")
  @Expose
  private Links links;

  @SerializedName("rating")
  @Expose
  private Rating rating;

  @SerializedName("releaseDate")
  @Expose
  private Long releaseDate;

  @SerializedName("updateDate")
  @Expose
  private Long updateDate;

  @SerializedName("downloads")
  @Expose
  private Integer downloads;

  @SerializedName("external")
  @Expose
  private Boolean external;

  @SerializedName("icon")
  @Expose
  private Icon icon;

  @SerializedName("reviews")
  @Expose
  private List<Review> reviews = null;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getContributors() {
    return contributors;
  }

  public void setContributors(String contributors) {
    this.contributors = contributors;
  }

  public Integer getLikes() {
    return likes;
  }

  public void setLikes(Integer likes) {
    this.likes = likes;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public List<String> getTestedVersions() {
    return testedVersions;
  }

  public void setTestedVersions(List<String> testedVersions) {
    this.testedVersions = testedVersions;
  }

  public Links getLinks() {
    return links;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public Rating getRating() {
    return rating;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public Long getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Long releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Long getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Long updateDate) {
    this.updateDate = updateDate;
  }

  public Integer getDownloads() {
    return downloads;
  }

  public void setDownloads(Integer downloads) {
    this.downloads = downloads;
  }

  public Boolean getExternal() {
    return external;
  }

  public void setExternal(Boolean external) {
    this.external = external;
  }

  public Icon getIcon() {
    return icon;
  }

  public void setIcon(Icon icon) {
    this.icon = icon;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
}

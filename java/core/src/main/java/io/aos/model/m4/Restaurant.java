/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package io.aos.model.m4;

import io.aos.validation.CreateGroup;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Restaurant implements Comparable<Restaurant> {

    private Integer id;

    @NotNull(
            message = "country.name.length.null",
            groups = {
                CreateGroup.class
            })
    @Size(
            message = "country.name.length.violation",
            min = 1,
            groups = {
                CreateGroup.class
            })
    private String name;

    @NotNull(
            message = "country.category.length.null",
            groups = {
                CreateGroup.class
            })
    @Size(
            message = "country.category.length.violation",
            min = 1,
            groups = {
                CreateGroup.class
            })
    private String category;

    private List<Review> reviews = new ArrayList<Review>();

    public Restaurant() {
        this.id = 0;
    }

    public Restaurant(Integer id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public double getAverageRating() {
        double average = 0D;
        if (reviews.size() == 0) {
            return 0;
        }
        for (Review review : reviews) {
            average += review.getRating();
        }
        return average / reviews.size();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews
     *            the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<Review>();
        }
        reviews.add(review);
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Restaurant other = (Restaurant) obj;
        if ((this.id != other.id) && ((this.id == null) || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.category == null) ? (other.category != null) : !this.category.equals(other.category)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = (71 * hash) + (this.id != null ? this.id.hashCode() : 0);
        hash = (71 * hash) + (this.name != null ? this.name.hashCode() : 0);
        hash = (71 * hash) + (this.category != null ? this.category.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Restaurant o) {
        return new Double(o.getAverageRating()).compareTo(new Double(this.getAverageRating()));
    }

}

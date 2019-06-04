/*
 *
 *  * Copyright (c) Crio.Do 2019. All rights reserved
 *
 */

package com.crio.qeats.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// COMPLETED: CRIO_TASK_MODULE_SERIALIZATION - Implement Restaurant class.
// Complete the class such that it produces the following JSON during serialization.
// {
//  "restaurantId": "10",
//  "name": "A2B",
//  "city": "Hsr Layout",
//  "imageUrl": "www.google.com",
//  "latitude": 20.027,
//  "longitude": 30.0,
//  "opensAt": "18:00",
//  "closesAt": "23:00",
//  "attributes": [
//    "Tamil",
//    "South Indian"
//  ]
// }
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Restaurant {

  @JsonProperty(value = "restaurantId")
  String restaurantId;

  @JsonProperty(value = "name")
  String name;

  @JsonProperty(value = "city")
  String city;

  @JsonProperty(value = "imageUrl")
  String imageUrl;

  @JsonProperty(value = "latitude")
  double latitude;

  @JsonProperty(value = "longitude")
  double longitude;

  @JsonProperty(value = "opensAt")
  String opensAt;

  @JsonProperty(value = "closesAt")
  String closesAt;

  @JsonProperty(value = "attributes")
  ArrayList<String> attributes;

  public boolean isOpen(LocalTime now) {
    if (this.opensAt == null || this.closesAt == null) {
      return true;
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime opens = LocalTime.parse(opensAt, formatter);
    LocalTime closes = LocalTime.parse(closesAt, formatter);
    return now.isAfter(opens) && now.isBefore(closes);
  }
}


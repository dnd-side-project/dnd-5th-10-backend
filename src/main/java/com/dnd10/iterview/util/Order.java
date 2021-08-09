package com.dnd10.iterview.util;

import lombok.Getter;

@Getter
public enum Order {

  ASC("asc"),
  DESC("DESC"),
  LIKED("liked"),
  DEFAULT("default");

  private String order;

  Order(String order) {
    this.order = order;
  }
}

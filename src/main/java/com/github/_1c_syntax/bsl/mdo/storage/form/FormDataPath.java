/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2024
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * MDClasses is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * MDClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MDClasses.
 */
package com.github._1c_syntax.bsl.mdo.storage.form;

import lombok.Getter;
import lombok.Value;

/**
 * Путь к реквизиту атрибута форма
 */
@Value
public class FormDataPath {

  /**
   * ссылка на пустой элемент
   */
  public static final FormDataPath EMPTY = new FormDataPath();

  /**
   * Путь к реквизиту
   */
  String segments;

  /**
   * Признак отсутствия пути
   */
  @Getter
  boolean empty;

  private FormDataPath() {
    segments = "";
    empty = true;
  }

  public FormDataPath(String segments) {
    this.segments = segments;
    this.empty = false;
  }
}

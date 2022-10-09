/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.bsl.mdo.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * POJO представление свойств типа «Обработчики событий» для таких типов метаданных как
 * «Регламентные задания» или «Подписки на события»
 */
@Getter
@Setter
@NoArgsConstructor
public class Handler {

  /**
   * Ссылка на пустой обработчик
   */
  public static final Handler EMPTY = new Handler();

  private static final String METHOD_HANDLER_SPLIT_REGEX = "\\.";
  private static final Pattern METHOD_HANDLER_SPLIT_PATTERN = Pattern.compile(METHOD_HANDLER_SPLIT_REGEX);
  private static final int METHOD_NAME_POSITION = 2;
  private static final int MODULE_NAME_POSITION = 1;

  private String methodPath = "";
  private String moduleName = "";
  private String methodName = "";

  public Handler(String path) {
    path = path == null ? "" : path;

    methodPath = path;
    String[] data = METHOD_HANDLER_SPLIT_PATTERN.split(path);
    if (data.length > MODULE_NAME_POSITION) {
      moduleName = data[MODULE_NAME_POSITION];
    }
    if (data.length > METHOD_NAME_POSITION) {
      methodName = data[METHOD_NAME_POSITION];
    }
  }

  /**
   * Возвращает признак пустого обработчика
   *
   * @return Признак пустоты
   */
  public boolean isEmpty() {
    return methodPath.isBlank();
  }
}

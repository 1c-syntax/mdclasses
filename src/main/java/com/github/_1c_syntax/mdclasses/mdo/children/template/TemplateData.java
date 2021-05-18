/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.mdclasses.mdo.children.template;

import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.Optional;

@RequiredArgsConstructor
public class TemplateData<T> {
  private static final TemplateData<String> EMPTY = new TemplateData<>();
  private final T data;

  public TemplateData() {
    data = null;
  }

  public Optional<T> getData() {
    return Optional.ofNullable(data);
  }

  public static TemplateData<String> empty() {
    return EMPTY;
  }

  /**
   * Фабричный метод
   *
   * @param type Тип шаблона
   * @param path Путь к данным шаблона
   * @return Данные
   */
  public static TemplateData<?> create(TemplateType type, Path path) {
    if (type == TemplateType.DATA_COMPOSITION_SCHEME) {
      var value = MDOFactory.readDataCompositionSchema(path);
      if (value.isPresent()) {
        return new TemplateData<>(value.get());
      }
    }
    return TemplateData.empty();
  }
}

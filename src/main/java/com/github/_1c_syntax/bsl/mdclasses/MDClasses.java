/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2023
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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.reader.MDOReader;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class MDClasses {

  /**
   * Создает пустую конфигурацию
   *
   * @return Пустая конфигурация
   */
  public MDClass createConfiguration() {
    return Configuration.EMPTY;
  }

  /**
   * Создает пустой внешний отчет
   *
   * @return Пустой внешний отчет
   */
  public MDClass createExternalReport() {
    return ExternalReport.EMPTY;
  }

  /**
   * Создает конфигурацию или расширение по указанному пути
   *
   * @param path Путь к корню проекта
   * @return Конфигурация или расширение
   */
  public MDClass createConfiguration(Path path) {
    return createConfiguration(path, false);
  }

  /**
   * Создает конфигурацию или расширение по указанному пути
   *
   * @param path        Путь к корню проекта
   * @param skipSupport Флаг управления чтением информации о поддержке
   * @return Конфигурация или расширение
   */
  public MDClass createConfiguration(Path path, boolean skipSupport) {
    return MDOReader.readConfiguration(path, skipSupport);
  }

  /**
   * Создает внешнюю обработку или внешний отчет по указанному пути
   *
   * @param mdoPath Путь к файлу описания обработки или отчета
   * @return Конфигурация или расширение
   */
  public MDClass createExternalSource(Path mdoPath) {
    return MDOReader.readExternalSource(mdoPath);
  }
}

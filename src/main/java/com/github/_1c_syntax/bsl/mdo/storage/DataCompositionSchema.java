/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.support.DataSetType;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Реализация хранения содержимого макета-схемы компоновки данных
 */
@Value
public class DataCompositionSchema implements TemplateData {

  /**
   * Дерево наборов данных
   */
  List<DataSet> dataSets;

  /**
   * Плоский список наборов данных
   */
  @Getter(lazy = true)
  List<DataSet> plainDataSets = computePlainDataSets();

  /**
   * Путь к файлу с данными макета
   */
  @Getter
  Path dataPath;

  public DataCompositionSchema(List<DataSet> dataSetsTree, Path path) {
    dataSets = dataSetsTree;
    dataPath = path;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  private List<DataSet> computePlainDataSets() {
    List<DataSet> result = new ArrayList<>();
    fillPlainDataSetByList(result, Objects.requireNonNull(dataSets));

    return result;
  }

  private static void fillPlainDataSetByList(List<DataSet> result, List<DataSet> items) {
    items.forEach((DataSet dataSet) -> {
      result.add(dataSet);
      fillPlainDataSetByList(result, dataSet.items());
    });
  }

  /**
   * @param name        Имя набора данных
   * @param type        Тип набора данных
   * @param dataSource  Имя источника данных
   * @param items       Подчиненные наборы данных
   * @param querySource Текста запроса (опционально)
   * @param fields      Поля набора данных
   */
  @Builder
  public record DataSet(String name,
                        DataSetType type,
                        String dataSource,
                        @Singular List<DataSet> items,
                        QuerySource querySource,
                        @Singular List<DataSetField> fields) {
  }

  /**
   * @param dataPath Путь к данным поля
   * @param name     Имя поля
   */
  public record DataSetField(String dataPath, String name) {
  }
}

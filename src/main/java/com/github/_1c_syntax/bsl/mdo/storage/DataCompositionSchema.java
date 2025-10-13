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
import com.github._1c_syntax.utils.Lazy;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
  Lazy<List<DataSet>> plainDataSets = new Lazy<>(this::computePlainDataSets);

  /**
   * Путь к файлу с данными макета
   */
  @Getter
  Path dataPath;

  public DataCompositionSchema(@NonNull List<DataSet> dataSetsTree, @NonNull Path path) {
    dataSets = dataSetsTree;
    dataPath = path;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  public List<DataSet> getPlainDataSets() {
    return plainDataSets.getOrCompute();
  }

  private List<DataSet> computePlainDataSets() {
    List<DataSet> result = new ArrayList<>();
    fillPlainDataSetByList(result, dataSets);

    return result;
  }

  private static void fillPlainDataSetByList(List<DataSet> result, List<DataSet> items) {
    items.forEach((DataSet dataSet) -> {
      result.add(dataSet);
      fillPlainDataSetByList(result, dataSet.getItems());
    });
  }

  @Value
  @ToString(of = {"name"})
  @EqualsAndHashCode(of = {"name"})
  @Builder
  public static class DataSet {
    /**
     * Имя набора данных
     */
    @Default
    String name = "";

    /**
     * Тип набора данных
     */
    @Default
    DataSetType type = DataSetType.DATA_SET_QUERY;

    /**
     * Имя источника данных
     */
    @Default
    String dataSource = "";

    /**
     * Подчиненные наборы данных
     */
    @Singular
    List<DataSet> items;

    /**
     * Текста запроса (опционально)
     */
    @Default
    QuerySource querySource = QuerySource.empty();

    /**
     * Поля набора данных
     */
    @Singular
    List<DataSetField> fields;
  }

  /**
   * @param dataPath Путь к данным поля
   * @param name     Имя поля
   */
  public record DataSetField(String dataPath, String name) {
  }
}

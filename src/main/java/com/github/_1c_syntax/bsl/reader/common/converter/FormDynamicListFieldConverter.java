/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
package com.github._1c_syntax.bsl.reader.common.converter;

import com.github._1c_syntax.bsl.mdo.storage.form.FormDynamicListField;
import com.github._1c_syntax.bsl.mdo.support.DynamicListFieldKind;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import org.jspecify.annotations.Nullable;

/**
 * Конвертор поля состава динамического списка, применяемый в формах.
 * Имена узлов у форматов совпадают, поэтому конвертор общий
 */
@CommonConverter
public class FormDynamicListFieldConverter implements ReadConverter {

  private static final String DATA_PATH_NODE_NAME = "dataPath";
  private static final String FIELD_NODE_NAME = "field";
  private static final String VALUE_TYPE_NODE_NAME = "valueType";
  private static final String TYPE_ATTRIBUTE_NAME = "type";

  /** Окончания имени типа записи, по которым различаются ее виды. */
  private static final String NESTED_DATA_SET_SUFFIX = "NestedDataSet";
  private static final String FOLDER_SUFFIX = "Folder";

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var field = FormDynamicListField.builder()
      .kind(kindOf(reader.getAttribute(TYPE_ATTRIBUTE_NAME)));
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      switch (reader.getNodeName()) {
        case DATA_PATH_NODE_NAME -> field.dataPath(reader.getValue());
        case FIELD_NODE_NAME -> field.name(reader.getValue());
        case VALUE_TYPE_NODE_NAME -> field.type(ExtendXStream.readValue(context, ValueTypeDescription.class));
        default -> {
          // остальные настройки поля (заголовок, ограничения использования) не нужны
        }
      }
      reader.moveUp();
    }
    return field.build();
  }

  /**
   * Вид записи по имени ее типа. Имя пишется с префиксом пространства имен, а сам
   * префикс у форматов и файлов разный ({@code dcssch:DataSetFieldNestedDataSet},
   * {@code schema:DataCompositionSchemaNestedDataSet}, в макете СКД - вовсе без
   * префикса), поэтому вид определяется по окончанию имени.
   *
   * @param typeName значение атрибута {@code xsi:type}; может отсутствовать.
   * @return вид записи; {@link DynamicListFieldKind#FIELD}, если имя не названо
   *   либо не опознано.
   */
  private static DynamicListFieldKind kindOf(@Nullable String typeName) {
    if (typeName == null) {
      return DynamicListFieldKind.FIELD;
    }
    if (typeName.endsWith(NESTED_DATA_SET_SUFFIX)) {
      return DynamicListFieldKind.NESTED_DATA_SET;
    }
    if (typeName.endsWith(FOLDER_SUFFIX)) {
      return DynamicListFieldKind.FOLDER;
    }
    return DynamicListFieldKind.FIELD;
  }

  @Override
  public boolean canConvert(Class type) {
    return type == FormDynamicListField.class;
  }
}

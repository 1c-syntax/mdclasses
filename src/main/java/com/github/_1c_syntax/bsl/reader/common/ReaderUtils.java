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
package com.github._1c_syntax.bsl.reader.common;

import com.github._1c_syntax.bsl.reader.common.context.MDReaderContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.experimental.UtilityClass;

/**
 * Набор вспомогательных методов, используемых при чтении данных
 */
@UtilityClass
public class ReaderUtils {
  public void unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context, MDReaderContext readerContext) {
    if (readerContext.isDesignerFormat()) {
      com.github._1c_syntax.bsl.reader.designer.converter.Unmarshaller.unmarshal(reader, context, readerContext);
    } else {
      com.github._1c_syntax.bsl.reader.edt.converter.Unmarshaller.unmarshal(reader, context, readerContext);
    }
  }

  /**
   * Читает значение из файла
   *
   * @param context Контекст чтения файла
   * @param clazz   Класс для преобразования
   * @return Прочитанное значение
   */
  public <T> T readValue(UnmarshallingContext context, Class<T> clazz) {
    return (T) context.convertAnother(clazz, clazz);
  }
}

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
package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.MDLanguage;
import com.github._1c_syntax.mdclasses.mdo.support.MDOReference;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import io.vavr.control.Either;

/**
 * Конвертирует строки с mdoRef в Either для последующей обработки
 */
public class PairConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if ("languages".equals(reader.getNodeName())) {
      var uuid = reader.getAttribute("uuid");
      var language = (MDLanguage) context.convertAnother(new MDLanguage(), MDLanguage.class);
      language.setUuid(uuid);
      language.setMdoReference(new MDOReference(language));
      return Either.right(language);
    } else if (reader.getValue().contains(".")) { // уже лежит имя
      return Either.left(reader.getValue());
    } else {
      var type = MDOType.fromValue(reader.getNodeName());
      return type
        .map(mdoType -> Either.left(mdoType.getName() + "." + reader.getValue()))
        .orElseGet(() -> Either.left(reader.getNodeName() + "." + reader.getValue()));
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return Either.class.isAssignableFrom(type);
  }
}

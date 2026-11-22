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

import com.github._1c_syntax.bsl.mdclasses.StandaloneConfigurationContent;
import com.github._1c_syntax.bsl.mdclasses.StandaloneServerPriority;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Общая часть чтения состава автономной конфигурации. Разбор элементов выполняют наследники.
 */
@Slf4j
public abstract class AbstractStandaloneContentConverter implements ReadConverter {

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var builder = StandaloneConfigurationContent.builder();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      readItem(reader, context, builder);
      reader.moveUp();
    }
    return builder.build();
  }

  @Override
  public boolean canConvert(Class type) {
    return StandaloneConfigurationContent.class.isAssignableFrom(type);
  }

  /**
   * Читает один дочерний элемент состава.
   *
   * @param reader  Читатель, установленный на дочерний элемент
   * @param context Контекст чтения XStream
   * @param builder Собиратель состава
   */
  protected abstract void readItem(HierarchicalStreamReader reader,
                                   UnmarshallingContext context,
                                   StandaloneConfigurationContent.StandaloneConfigurationContentBuilder builder);

  /**
   * Добавляет включённый объект.
   *
   * @param builder  Собиратель состава
   * @param metadata Ссылка на объект метаданных
   */
  protected void addUsed(StandaloneConfigurationContent.StandaloneConfigurationContentBuilder builder,
                         String metadata) {
    createReference(metadata).ifPresent(builder::addUsedItem);
  }

  /**
   * Добавляет исключённый объект.
   *
   * @param builder  Собиратель состава
   * @param metadata Ссылка на объект метаданных
   */
  protected void addUnused(StandaloneConfigurationContent.StandaloneConfigurationContentBuilder builder,
                           String metadata) {
    createReference(metadata).ifPresent(builder::addUnusedItem);
  }

  /**
   * Добавляет приоритет сервера для объекта.
   *
   * @param builder  Собиратель состава
   * @param metadata Ссылка на объект метаданных
   * @param priority Приоритет сервера
   */
  protected void addPriority(StandaloneConfigurationContent.StandaloneConfigurationContentBuilder builder,
                             String metadata,
                             String priority) {
    var value = StandaloneServerPriority.valueByName(priority);
    createReference(metadata).ifPresent(reference -> builder.addPriorityItem(reference, value));
  }

  private static Optional<MdoReference> createReference(String value) {
    try {
      return Optional.of(MdoReference.create(value));
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Can't resolve standalone content metadata reference '{}' (skipped)", value);
      return Optional.empty();
    }
  }
}

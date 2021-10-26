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
package com.github._1c_syntax.bsl.mdo.support;

import com.github._1c_syntax.bsl.types.MDOType;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ссылка на объект в формате ВидОбъектаМетаданных.ИмяОбъекта
 */
@Value
@EqualsAndHashCode(of = {"mdoRef"})
@ToString(of = {"mdoRef"})
public class MdoReference {
  public static final MdoReference EMPTY = new MdoReference(MDOType.UNKNOWN, "", "");

  /**
   * Кэш всех ссылок
   */
  private static final Map<String, MdoReference> REFERENCES = new CaseInsensitiveMap<>();

  private static final ReentrantLock referenceLock = new ReentrantLock();

  /**
   * Тип объекта метаданных
   */
  MDOType type;

  /**
   * Строковое представление ссылки
   */
  String mdoRef;

  /**
   * Строковое представление ссылки на русском языке
   */
  String mdoRefRu;

  private MdoReference(MDOType type, String mdoRef, String mdoRefRu) {
    this.type = type;
    this.mdoRef = mdoRef;
    this.mdoRefRu = mdoRefRu;
  }

  /**
   * Создает ссылку, сохраняя ее в кэш
   *
   * @param type     Тип метаданных
   * @param mdoRef   Строковая ссылка
   * @param mdoRefRu Строковая ссылка на русском языке
   * @return Ссылка на объект
   */
  public static MdoReference create(MDOType type, String mdoRef, String mdoRefRu) {
    return getOrCompute(type, mdoRef, mdoRefRu);
  }

  /**
   * Создает ссылку, сохраняя ее в кэш
   *
   * @param type Тип метаданных
   * @param name Имя объекта метаданных
   * @return Ссылка на объект
   */
  public static MdoReference create(MDOType type, String name) {
    var mdoRef = type.getName() + "." + name;
    var mdoRefRu = type.getNameRu() + "." + name;

    return getOrCompute(type, mdoRef, mdoRefRu);
  }

  /**
   * Выполняет поиск ссылки по имени, используя кэш прочитанных ранее
   *
   * @param mdoRef Строковое представление ссылки
   * @return Optional-контейнер для ссылки
   */
  public static Optional<MdoReference> find(@NonNull String mdoRef) {
    Optional<MdoReference> result = Optional.empty();
    referenceLock.lock();
    if (REFERENCES.containsKey(mdoRef)) {
      result = Optional.of(REFERENCES.get(mdoRef));
    }
    referenceLock.unlock();
    return result;
  }

  private static MdoReference getOrCompute(MDOType type, String mdoRef, String mdoRefRu) {
    referenceLock.lock();
    if (REFERENCES.containsKey(mdoRef)) {
      referenceLock.unlock();
      return REFERENCES.get(mdoRef);
    }
    var newMdoReference = new MdoReference(type, mdoRef, mdoRefRu);
    REFERENCES.put(mdoRef, newMdoReference);
    referenceLock.unlock();
    return newMdoReference;
  }
}

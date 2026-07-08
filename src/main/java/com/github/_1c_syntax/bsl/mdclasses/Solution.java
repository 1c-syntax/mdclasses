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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.support.RoleRight;
import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Delegate;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Представляет результат разрешения конфигурации — результат слияния основной конфигурации
 * с нулем или более расширениями.
 * <p>
 * Создаётся через {@link MDClasses#createSolution(java.nio.file.Path)} и является
 * основным входным классом для потребителей, которым требуется конфигурация с поддержкой расширений.
 *
 */
@Value
@Builder
public class Solution implements CF {

  /**
   * Пустое решение (нет объектов метаданных, нет расширений)
   */
  public static final Solution EMPTY = new Solution(
    Configuration.EMPTY, Configuration.EMPTY, Collections.emptyList(), Map.of());

  /**
   * Объединенная конфигурация — результат слияния основной конфигурации со всеми расширениями
   */
  @Delegate
  Configuration mergedConfiguration;

  /**
   * Оригинальная конфигурация без расширений
   */
  @Builder.Default
  Configuration baseConfiguration = Configuration.EMPTY;

  /**
   * Список расширений, примененных к базовой конфигурации
   */
  @Builder.Default
  List<ConfigurationExtension> extensions = Collections.emptyList();

  /**
   * Хранит, какой конфигурации или расширению принадлежит объект и какими расширениями
   * был изменен.
   */
  Map<MdoReference, ObjectProvenance> provenance;

  /**
   * Возвращает принадлежность для заданной ссылки на объект метаданных.
   *
   * @param mdoRef ссылка на объект метаданных в объединенной конфигурации
   * @return запись о происхождении, или {@code null}, если объект не отслеживается
   */
  @Nullable
  public ObjectProvenance getProvenance(MdoReference mdoRef) {
    return provenance.get(mdoRef);
  }

  /**
   * Возвращает ссылку на конфигурацию или расширение, которому принадлежит
   * указанный объект метаданных.
   *
   * @param mdoRef ссылка на объект метаданных в объединенной конфигурации
   * @return ссылка на владельца, или {@link MdoReference#EMPTY}, если не найден
   */
  public MdoReference getOwnerRef(MdoReference mdoRef) {
    var prov = provenance.get(mdoRef);
    return prov != null ? prov.getOwnerRef() : MdoReference.EMPTY;
  }

  /**
   * Возвращает конфигурацию или расширение ({@link MDClass}), которому принадлежит
   * указанный объект метаданных.
   * <p>
   * Поиск выполняется сначала в базовой конфигурации, затем последовательно в
   * каждом расширении.
   *
   * @param mdoRef ссылка на объект метаданных в эффективной конфигурации
   * @return владелец {@link MDClass}, или {@code null}, если владелец не найден
   */
  @Nullable
  public MDClass getOwner(MdoReference mdoRef) {
    var ownerRef = getOwnerRef(mdoRef);
    if (ownerRef.equals(baseConfiguration.getMdoReference())) {
      return baseConfiguration;
    }
    return extensions.stream()
      .filter(ext -> ext.getMdoReference().equals(ownerRef))
      .findFirst()
      .orElse(null);
  }

  /**
   * Возвращает перечень возможных прав доступа
   */
  public static List<RoleRight> possibleRights() {
    return Configuration.possibleRights();
  }
}

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

import com.github._1c_syntax.bsl.types.MdoReference;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Singular;
import lombok.Value;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Состав автономной конфигурации. Отсутствие файла не означает, что включена вся конфигурация.
 */
@Value
@Builder
public class StandaloneConfigurationContent {

  /**
   * Состав отсутствует: файл не найден или не удалось прочитать
   */
  public static final StandaloneConfigurationContent EMPTY = StandaloneConfigurationContent.builder().build();

  /**
   * Ссылки на объекты, включённые в состав
   */
  @Singular("addUsedItem")
  Set<MdoReference> usedItems;

  /**
   * Ссылки на объекты, явно исключённые из состава
   */
  @Singular("addUnusedItem")
  Set<MdoReference> unusedItems;

  /**
   * Приоритеты сервера для объектов состава
   */
  @Singular("addPriorityItem")
  Map<MdoReference, StandaloneServerPriority> priorityItems;

  /**
   * Выгружать объект при изменении данных
   */
  @Default
  boolean exchangeOnChangeData = false;

  /**
   * Период выгрузки данных
   */
  @Default
  int exchangePeriod = 300;

  /**
   * Количество транзакций
   */
  @Default
  int transactionCount = 1000;

  /**
   * Таймаут очистки неактивных узлов
   */
  @Default
  int inactiveNodesCleanupTimeout = 0;

  /**
   * Состав отсутствует: файл не найден или не удалось прочитать
   *
   * @return Признак отсутствия состава
   */
  public boolean isEmpty() {
    return this == EMPTY;
  }

  /**
   * Объект или его предок явно исключён из состава
   *
   * @param ref Ссылка на объект
   * @return Признак исключения
   */
  public boolean isExplicitlyUnused(MdoReference ref) {
    return containsAncestorOrSelf(unusedItems, ref);
  }

  /**
   * Объект включён в состав: сам объект или его предок есть в списке включённых,
   * и ни объект, ни его предки не исключены.
   *
   * @param ref Ссылка на объект
   * @return Признак включения
   */
  public boolean isIncluded(MdoReference ref) {
    return !isEmpty() && !isExplicitlyUnused(ref) && containsAncestorOrSelf(usedItems, ref);
  }

  /**
   * Приоритет сервера для объекта. Сначала проверяется точное совпадение, затем ближайший предок.
   *
   * @param ref Ссылка на объект
   * @return Приоритет, если задан
   */
  public Optional<StandaloneServerPriority> getPriority(MdoReference ref) {
    return priorityItems.entrySet().stream()
      .filter(entry -> isAncestorOrSelf(entry.getKey(), ref))
      .max(Comparator.comparingInt(entry -> entry.getKey().getMdoRef().length()))
      .map(Map.Entry::getValue);
  }

  private static boolean containsAncestorOrSelf(Set<MdoReference> items, MdoReference ref) {
    return items.stream().anyMatch(item -> isAncestorOrSelf(item, ref));
  }

  private static boolean isAncestorOrSelf(MdoReference item, MdoReference ref) {
    var prefix = item.getMdoRef();
    var value = ref.getMdoRef();
    return value.equals(prefix) || value.startsWith(prefix + ".");
  }
}

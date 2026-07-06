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
package com.github._1c_syntax.bsl.test_utils.assertions;

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.TabularSection;
import org.assertj.core.api.AbstractAssert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ассерты для сравнения коллекций {@code MD} объектов.
 * <p>
 * Используется для проверки {@code children}, {@code plainChildren}, {@code allAttributes} и т.д.
 * <p>
 * Сравнение выполняется по {@code MD#equals(Object)} — без проверки размера.
 * При несовпадении выводится диф:
 * <ul>
 *   <li>элементы, которые есть в expected, но нет в actual</li>
 *   <li>элементы, которые есть в actual, но нет в expected</li>
 * </ul>
 *
 * <pre>{@code
 * // children / plainChildren
 * assertThat(catalog.getPlainChildren(), false)
 *     .isEqual(catalog.getAttributes(), catalog.getTabularSections(), catalog.getForms());
 *
 * // allAttributes
 * assertThat(chartOfAccounts.getAllAttributes(), false)
 *     .isEqual(chartOfAccounts.getAttributes(), chartOfAccounts.getAccountingFlags());
 * }</pre>
 */
public class MDCollectionAssert extends AbstractAssert<MDCollectionAssert, List<MD>> {

  private MDCollectionAssert(List<MD> actual) {
    super(actual, MDCollectionAssert.class);
  }

  /**
   * Factory-метод для начала цепочки {@code assertThat(actual, ignored).isEqual(...)}.
   *
   * @param actual  коллекция «фактическая» (left side of comparison)
   * @param ignored параметр только для перегрузки, не используется
   * @return ассерт
   */
  public static MDCollectionAssert assertThat(List<MD> actual, boolean ignored) {
    return new MDCollectionAssert(actual);
  }

  /**
   * Сравнивает {@code actual} с суммой (объединением) переданных {@code collections}.
   * <p>
   * Проверяет совпадение элементов по {@code MD#equals(Object)}.
   * При несовпадении выводится диф — что ожидается, чего не хватает.
   */
  public MDCollectionAssert containsAll(Collection<?>... collections) {
    check(flatten(false, collections));
    return this;
  }

  /**
   * Сравнивает {@code actual} с суммой (объединением) переданных {@code collections}.
   * Используется для сравнения развернутых коллекций (plain).
   * Содержимое каждой коллекции добавляется в результат без рекурсивного раскрытия.
   * <p>
   * Проверяет совпадение элементов по {@code MD#equals(Object)}.
   * При несовпадении выводится диф — что ожидается, чего не хватает.
   */
  public MDCollectionAssert containsAllPlain(Collection<?>... collections) {
    check(flatten(true, collections));
    return this;
  }

  private List<MD> flatten(boolean plain, Collection<?>... collections) {
    List<MD> result = new ArrayList<>();
    for (Collection<?> collection : collections) {
      if (collection == null || collection.isEmpty()) {
        continue;
      }
      var first = collection.iterator().next();
      if (first instanceof MD) {
        // коллекция содержит MD — просто добавляем
        for (var item : collection) {
          if (item instanceof MD md) {
            result.add(md);
          }
          if (!plain) {
            continue;
          }

          if (item instanceof TabularSection tabularSection) {
            result.addAll(flatten(false, tabularSection.getChildren()));
          }
        }
      }
    }
    return result;
  }

  /**
   * Фактическая проверка: сравнение двух коллекций MD по {@code equals()}.
   * При несовпадении выводит диф — что ожидается, чего не хватает.
   */
  private void check(List<MD> expected) {
    List<MD> actual = this.actual;

    if (actual == null) {
      failWithMessage("Фактические элементы null, ожидаются:\n%s", formatCollection(expected));
      return;
    }

    List<MD> missing = new ArrayList<>();
    for (MD m : expected) {
      if (!actual.contains(m)) {
        missing.add(m);
      }
    }

    List<MD> extra = new ArrayList<>();
    for (MD m : actual) {
      if (!expected.contains(m)) {
        extra.add(m);
      }
    }

    if (missing.isEmpty() && extra.isEmpty()) {
      return;
    }

    String msg = String.format(
      "Ожидаемые и фактические элементы различаются:\n  отсутствуют в expected (missing):\n%s\n  отсутствуют в actual (extra):\n%s",
      formatCollection(missing),
      formatCollection(extra)
    );
    failWithMessage(msg);
  }

  /**
   * Форматирует коллекцию элементов для вывода в сообщении об ошибке.
   */
  private String formatCollection(Collection<? extends MD> items) {
    if (items == null || items.isEmpty()) {
      return "  (пусто)";
    }
    var sb = new StringBuilder();
    for (MD md : items) {
      sb.append("    - ").append(toSummary(md)).append(System.lineSeparator());
    }
    return sb.toString();
  }

  /**
   * Создаёт лаконичное представление элемента для вывода в diff.
   */
  private String toSummary(MD md) {
    return String.format("%s{name=%s, uuid=%s}", md.getClass().getSimpleName(), md.getName(), md.getUuid());
  }
}

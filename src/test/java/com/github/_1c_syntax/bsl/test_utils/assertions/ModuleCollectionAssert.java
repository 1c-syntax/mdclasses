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

import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import org.assertj.core.api.AbstractAssert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ассерты для сравнения коллекций модулей объектов метаданных.
 * <p>
 * Используется для проверки того, что {@code allModules} (все модули объекта)
 * совпадает с объединением модулей собственных и дочерних элементов.
 * <p>
 * Сравнение выполняется по {Module#equals(Object)} (Object)} — без проверки размера.
 * При несовпадении выводится диф:
 * <ul>
 *   <li>модули, которые есть в expected, но нет в actual</li>
 *   <li>модули, которые есть в actual, но нет в expected</li>
 * </ul>
 *
 * <pre>{@code
 * // только модули
 * assertThat(catalog.getAllModules(), false)
 *     .isEqualToSumOf(catalog.getModules(), anotherModules);
 *
 * // модули + владельцы модулей (из форм, команд извлекается getModules())
 * assertThat(catalog.getAllModules(), false)
 *     .isEqualToSumOf(catalog.getModules(), catalog.getForms(), catalog.getCommands());
 *
 * // только владельцы модулей
 * assertThat(catalog.getAllModules(), false)
 *     .isEqualToSumOf(catalog.getForms(), catalog.getCommands());
 * }</pre>
 */
public class ModuleCollectionAssert extends AbstractAssert<ModuleCollectionAssert, Collection<Module>> {

  private ModuleCollectionAssert(Collection<Module> actual) {
    super(actual, ModuleCollectionAssert.class);
  }

  /**
   * Factory-метод для начала цепочки {@code assertThat(allModules, ignored).isEqualTo(...)}.
   *
   * @param actual      коллекция «все модули» (left side of comparison)
   * @param ignored     параметр только для перегрузки, не используется
   * @return ассерт
   */
  public static ModuleCollectionAssert assertThat(Collection<Module> actual, boolean ignored) {
    return new ModuleCollectionAssert(actual);
  }

   /**
    * Сравнивает {@code actual} с суммой (объединением) переданных {@code collections}.
    * <p>
    * Если элемент коллекции — {@link ModuleOwner}, извлекаются модули через {@code getModules()}.
    * Если элемент — {@link Module}, добавляется сам модуль.
    * <p>
    * Проверяет совпадение элементов по {Module#equals(Object)}.
    * При несовпадении выводится диф: что ожидается, но отсутствует в actual, и что есть в actual, но не ожидается.
    */
   public ModuleCollectionAssert containsAll(Collection<?>... collections) {
    check(flatten(collections));
    return this;
  }

  private List<Module> flatten(Collection<?>... collections) {
    List<Module> result = new ArrayList<>();
    for (var collection : collections) {
      if (collection.isEmpty()) {
        continue;
      }
      var first = collection.iterator().next();
      if (first instanceof ModuleOwner) {
        // коллекция содержит ModuleOwner (Form, Command, и т.д.) — извлекаем модули
        for (var item : collection) {
          if (item instanceof ModuleOwner owner) {
            result.addAll(owner.getAllModules());
          }
        }
      } else if (first instanceof Module) {
        // коллекция содержит Module — просто добавляем
        for (var item : collection) {
          if (item instanceof Module module) {
            result.add(module);
          }
        }
      }
    }
    return result;
  }

  /**
   * Фактическая проверка: сравнение двух коллекций модулей по {@code equals()}.
   * При несовпадении выводит диф — что ожидается, чего не хватает.
   */
  private void check(List<Module> expected) {
    Collection<Module> actual = this.actual;

    if (actual == null) {
      failWithMessage("Фактические модули null, ожидаются:\n%s", formatCollection(expected));
      return;
    }

    List<Module> missing = new ArrayList<>();
    for (Module m : expected) {
      if (!actual.contains(m)) {
        missing.add(m);
      }
    }

    List<Module> extra = new ArrayList<>();
    for (Module m : actual) {
      if (!expected.contains(m)) {
        extra.add(m);
      }
    }

    if (missing.isEmpty() && extra.isEmpty()) {
      return;
    }

    String msg = String.format(
      """
      Ожидаемые и фактические модули различаются:
        ожидаются (expected):
        %s
      
        отсутствуют в expected (extra):
        %s
      """,
      formatCollection(missing),
      formatCollection(extra)
    );
    failWithMessage(msg);
  }

  /**
   * Форматирует коллекцию модулей для вывода в сообщении об ошибке.
   */
  private String formatCollection(Collection<Module> modules) {
    if (modules == null || modules.isEmpty()) {
      return "  (пусто)";
    }
    var sb = new StringBuilder();
    for (Module m : modules) {
      sb.append("    - ").append(toSummary(m)).append(System.lineSeparator());
    }
    return sb.toString();
  }

  /**
   * Создаёт лаконичное представление модуля для вывода в diff.
   */
  private String toSummary(Module m) {
    return String.format("Module{\n\t\t\turi=%s,\n\t\t\ttype=%s, protected=%s}",
      m.getUri().toString().replace("%", "%%"), m.getModuleType(), m.isProtected());
  }
}

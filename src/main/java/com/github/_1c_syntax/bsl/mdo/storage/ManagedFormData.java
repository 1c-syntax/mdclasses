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
package com.github._1c_syntax.bsl.mdo.storage;

import com.github._1c_syntax.bsl.mdo.storage.form.FormAttribute;
import com.github._1c_syntax.bsl.mdo.storage.form.FormCommand;
import com.github._1c_syntax.bsl.mdo.storage.form.FormElement;
import com.github._1c_syntax.bsl.mdo.storage.form.FormEventHandler;
import com.github._1c_syntax.bsl.mdo.storage.form.FormEventHandlerOwner;
import com.github._1c_syntax.bsl.mdo.storage.form.FormParameter;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.types.MultiLanguageString;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Реализация содержимого управляемой формы
 */
@Value
@Builder(toBuilder = true)
public class ManagedFormData implements FormData {

  /**
   * Заголовок формы
   */
  @Default
  MultiLanguageString title = MultiLanguageString.EMPTY;

  /**
   * Обработчики событий формы
   */
  @Singular("addEventHandlers")
  List<FormEventHandler> eventHandlers;

  /**
   * Элементы формы первого уровня
   */
  @Singular("addElements")
  List<FormElement> elements;

  /**
   * Реквизиты формы
   */
  @Singular("addAttributes")
  List<FormAttribute> attributes;

  /**
   * Команды формы
   */
  @Singular("addCommands")
  List<FormCommand> commands;

  /**
   * Параметры формы
   */
  @Singular("addParameters")
  List<FormParameter> parameters;

  /**
   * Пути к данным, названные в условном оформлении формы
   */
  @Singular("addConditionalAppearanceFields")
  List<String> conditionalAppearanceFields;

  /**
   * Все элементы формы (включая вложенные)
   */
  @Getter(lazy = true)
  List<FormElement> plainElements = LazyLoader.computePlainFormElements(this);

  /**
   * Плоское представление всех атрибутов и их колонок (включая вложенные)
   */
  @Getter(lazy = true)
  Map<String, FormAttribute> plainAttributes = computePlainFormAttributes();

  /**
   * Плоское представление обработчиков событий формы,
   * где ключ — имя события
   */
  @Getter(lazy = true)
  Map<String, FormEventHandler> plainEventHandlers = computePlainFormEventHandlers();

  private Map<String, FormEventHandler> computePlainFormEventHandlers() {
    Map<String, FormEventHandler> result = new CaseInsensitiveMap<>();
    getEventHandlers().forEach(handler -> result.put("Form." + handler.event(), handler));
    getPlainElements().forEach((FormElement element) -> {
      if (element instanceof FormEventHandlerOwner owner) {
        owner.getEventHandlers().forEach(handler ->
          result.put(element.getName() + "." + handler.event(), handler));
      }
    });
    return Collections.unmodifiableMap(result);
  }

  private Map<String, FormAttribute> computePlainFormAttributes() {
    Map<String, FormAttribute> result = new CaseInsensitiveMap<>();
    getAttributes().forEach(attr -> putFlattened(result, attr.getName(), attr));
    return Collections.unmodifiableMap(result);
  }

  private void putFlattened(Map<String, FormAttribute> map, String prefix, FormAttribute attr) {
    map.put(prefix, attr);
    attr.getColumns().forEach((FormAttribute col) -> {
      var colName = col.getName();
      // дополнительные колонки имеют полное имя (путь к табличной части)
      var key = colName.contains(".") ? colName : prefix + "." + colName;
      putFlattened(map, key, col);
    });
  }
}

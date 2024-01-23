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
package com.github._1c_syntax.bsl.mdo.support;

import com.github._1c_syntax.utils.StringInterner;
import lombok.NonNull;
import lombok.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Используется для хранения текстовой строки на разных языках
 */
@Value
public class MultiLanguageString {

  /**
   * Ссылка на пустой элемент
   */
  public static final MultiLanguageString EMPTY = new MultiLanguageString(Collections.emptyMap());

  private static final StringInterner stringInterner = new StringInterner();

  /**
   * Содержимое описания для каждого языка
   */
  Map<String, String> content;

  public MultiLanguageString(Map<String, String> source) {
    Map<String, String> newContent = new HashMap<>();
    source.forEach(
      (langKey, text) -> newContent.put(stringInterner.intern(langKey), text));
    content = newContent;
  }

  public MultiLanguageString(@NonNull MultiLanguageString first, @NonNull MultiLanguageString second) {
    var fullContent = new HashMap<>(first.getContent());
    putContent(fullContent, second);
    content = fullContent;
  }

  /**
   * Создание мультиязычной строки из списка (объединение).
   * Если передан пустой список, то вернет ссылку на пустой объект.
   * Если в параметрах передан список из одного элемента, то он и будет возвращен как результат.
   *
   * @param strings Список мультиязычных строк
   * @return Объединенное значение
   */
  public static MultiLanguageString of(@NonNull List<MultiLanguageString> strings) {
    if (strings.isEmpty()) {
      return EMPTY;
    } else if (strings.size() == 1) {
      return strings.get(0);
    } else {
      Map<String, String> content = new HashMap<>();
      strings.forEach(string -> putContent(content, string));
      return new MultiLanguageString(content);
    }
  }

  /**
   * Возвращает содержимое для указанного языка
   *
   * @param lang Требуемый язык
   * @return Содержимое для указанного языка
   */
  public @NonNull String get(@NonNull String lang) {
    return content.getOrDefault(lang, "");
  }

  /**
   * Возвращает первое попавшееся содержимое мультиязычной строки
   *
   * @return Одно из значений мультиязычной строки
   */
  public @NonNull String getAny() {
    if (content.isEmpty()) {
      return "";
    }
    return content.entrySet().iterator().next().getValue();
  }

  /**
   * Возвращает признак пустоты мультиязычной строки
   *
   * @return Если пустая, тогда true
   */
  public boolean isEmpty() {
    return this == EMPTY;
  }

  private static void putContent(Map<String, String> destination, MultiLanguageString source) {
    source.getContent().forEach(
      (langKey, text) -> destination.put(stringInterner.intern(langKey), text));
  }
}

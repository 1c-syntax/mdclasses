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

import com.github._1c_syntax.utils.GenericInterner;
import com.github._1c_syntax.utils.StringInterner;
import edu.umd.cs.findbugs.annotations.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Используется для хранения текстовой строки на разных языках
 */
@Value
@EqualsAndHashCode
public class MultiLanguageString implements Comparable<MultiLanguageString> {

  /**
   * Ссылка на пустой элемент
   */
  public static final MultiLanguageString EMPTY = new MultiLanguageString(Collections.emptyMap());
  private static final GenericInterner<MultiLanguageString> interner = new GenericInterner<>();

  /**
   * Содержимое описания для каждого языка
   */
  Set<Entry> content;

  private MultiLanguageString(@NonNull Map<String, String> source) {
    Set<Entry> newContent = new HashSet<>();
    source.forEach(
      (langKey, text) -> newContent.add(Entry.create(langKey, text)));
    content = Collections.unmodifiableSet(newContent);
  }

  private MultiLanguageString(@NonNull String langKey, @NonNull String value) {
    this(Set.of(Entry.create(langKey, value)));
  }

  private MultiLanguageString(@NonNull MultiLanguageString first, @NonNull MultiLanguageString second) {
    var fullContent = new HashSet<>(first.getContent());
    fullContent.addAll(second.getContent());
    content = Collections.unmodifiableSet(fullContent);
  }

  private MultiLanguageString(Set<Entry> content) {
    this.content = Collections.unmodifiableSet(content);
  }

  /**
   * Создание мультиязычной строки из списка (объединение).
   * Если передан пустой список, то вернет ссылку на пустой объект.
   * Если в параметрах передан список из одного элемента, то он и будет возвращен как результат.
   *
   * @param strings Список мультиязычных строк
   * @return Объединенное значение
   */
  public static MultiLanguageString create(@NonNull List<MultiLanguageString> strings) {
    if (strings.isEmpty()) {
      return EMPTY;
    } else if (strings.size() == 1) {
      return strings.get(0);
    } else {
      Set<Entry> content = new HashSet<>();
      strings.forEach(string -> content.addAll(string.getContent()));
      return new MultiLanguageString(content).intern();
    }
  }

  public static MultiLanguageString create(@NonNull Set<Entry> langContent) {
    return new MultiLanguageString(langContent).intern();
  }

  public static MultiLanguageString create(@NonNull MultiLanguageString first, @NonNull MultiLanguageString second) {
    return new MultiLanguageString(first, second).intern();
  }

  public static MultiLanguageString create(@NonNull String langKey, @NonNull String value) {
    return new MultiLanguageString(langKey, value).intern();
  }

  /**
   * Возвращает содержимое для указанного языка
   *
   * @param lang Требуемый язык
   * @return Содержимое для указанного языка
   */
  public @NonNull String get(@NonNull String lang) {
    return content.stream()
      .filter(entry -> entry.getLangKey().equals(lang))
      .map(Entry::getValue)
      .findFirst()
      .orElse("");
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
    return content.iterator().next().getValue();
  }

  /**
   * Возвращает признак пустоты мультиязычной строки
   *
   * @return Если пустая, тогда true
   */
  public boolean isEmpty() {
    return this == EMPTY;
  }

  @Override
  public int compareTo(@Nullable MultiLanguageString multiLanguageString) {
    if (multiLanguageString == null) {
      return 1;
    }

    if (this.equals(multiLanguageString)) {
      return 0;
    }

    int compareResult = content.size() - multiLanguageString.content.size();
    if (compareResult != 0) {
      return compareResult;
    }

    // количество равно, но списки не равны
    // попробуем оставить в списках только уникальные элементы
    // если останется больше 0 (а странно будет, если не так), то сравним по первому элементу
    var left = new HashSet<>(content);
    var right = new HashSet<>(multiLanguageString.content);
    left.removeAll(right);
    right.removeAll(left);
    if (left.isEmpty() && right.isEmpty()) {
      return 0; // хз как это получилось
    } else if (left.isEmpty()) {
      return -1;
    } else if (right.isEmpty()) {
      return 1;
    } else {
      var leftOne = left.iterator().next();
      var rightOne = right.iterator().next();
      return leftOne.compareTo(rightOne);
    }
  }

  private MultiLanguageString intern() {
    return interner.intern(this);
  }

  @EqualsAndHashCode
  public static class Entry implements Comparable<Entry> {
    @Getter
    private final String langKey;
    @Getter
    private final String value;
    private static final StringInterner stringInterner = new StringInterner();
    private static final GenericInterner<Entry> interner = new GenericInterner<>();

    private Entry(String langKey, String value) {
      this.langKey = stringInterner.intern(langKey);
      this.value = value;
    }

    public static Entry create(String langKey, String value) {
      return new Entry(langKey, value).intern();
    }

    @Override
    public int compareTo(@Nullable Entry entry) {
      if (entry == null) {
        return 1;
      }

      if (this.equals(entry)) {
        return 0;
      }

      int compareResult = langKey.compareTo(entry.langKey);
      if (compareResult != 0) {
        return compareResult;
      }

      return value.compareTo(entry.value);
    }

    private Entry intern() {
      return interner.intern(this);
    }
  }
}

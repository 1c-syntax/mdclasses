/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.types.MultiLanguageString;

/**
 * Вспомогательные методы для записи JAXB в формате Designer XML.
 */
public final class JaxbWriteUtils {

  private static final String DEFAULT_LANG = "ru";

  private JaxbWriteUtils() {
  }

  /**
   * Возвращает текст для поля v8:content в LocalStringType: значение для языка {@value #DEFAULT_LANG},
   * при отсутствии — первое доступное через {@link MultiLanguageString#getAny()}.
   *
   * @param ml многоязычная строка (синоним, пояснение и т.д.)
   * @return текст для v8:content или пустая строка
   */
  public static String contentForLocalString(MultiLanguageString ml) {
    if (ml == null || ml.isEmpty()) {
      return "";
    }
    String forLang = ml.get(DEFAULT_LANG);
    if (forLang != null && !forLang.isEmpty()) {
      return forLang;
    }
    return ml.getAny();
  }
}

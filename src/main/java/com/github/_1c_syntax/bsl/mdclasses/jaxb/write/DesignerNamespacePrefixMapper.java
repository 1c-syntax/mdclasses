/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

/**
 * Задаёт префиксы неймспейсов при маршалинге Designer XML (дефолтный неймспейс MDClasses без префикса).
 */
public final class DesignerNamespacePrefixMapper extends NamespacePrefixMapper {

  private static final String MDCLASSES_NS = "http://v8.1c.ru/8.3/MDClasses";

  @Override
  public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
    if (MDCLASSES_NS.equals(namespaceUri)) {
      return requirePrefix ? "v8" : "";
    }
    return suggestion != null ? suggestion : null;
  }
}

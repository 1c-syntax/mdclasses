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
package com.github._1c_syntax.bsl.reader.common.xstream;

import com.thoughtworks.xstream.io.xml.QNameMap;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link QNameMap} с потокобезопасными картами без блокировок на чтении.
 * <p>
 * Штатный {@link QNameMap} хранит соответствия в {@code Collections.synchronizedMap}, а
 * {@link #getJavaClassName(QName)} вызывается на каждый XML-элемент. При параллельном чтении
 * (метаданные читаются {@code parallelStream}) все потоки сериализуются на мониторе этой общей
 * карты — по профилю это заметная доля CPU. Здесь карты — {@link ConcurrentHashMap}, чтение
 * которых свободно от блокировок; отображения регистрируются один раз до начала чтения.
 */
public class ConcurrentQNameMap extends QNameMap {

  private final Map<QName, String> qnameToJava = new ConcurrentHashMap<>();
  private final Map<String, QName> javaToQName = new ConcurrentHashMap<>();

  @Override
  public String getJavaClassName(QName qname) {
    var answer = qnameToJava.get(qname);
    return answer != null ? answer : qname.getLocalPart();
  }

  @Override
  public QName getQName(String javaClassName) {
    var answer = javaToQName.get(javaClassName);
    return answer != null ? answer : new QName(getDefaultNamespace(), javaClassName, getDefaultPrefix());
  }

  @Override
  public void registerMapping(QName qname, String javaClassName) {
    javaToQName.put(javaClassName, qname);
    qnameToJava.put(qname, javaClassName);
  }
}

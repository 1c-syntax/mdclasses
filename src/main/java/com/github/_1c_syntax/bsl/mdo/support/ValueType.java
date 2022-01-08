/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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

import com.github._1c_syntax.utils.CaseInsensitivePattern;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Класс для хранения типа значения, атрибута, константы и т.д.
 * <p>
 * Текущая реализация примитивна
 */
@Value
public class ValueType {

  public static final ValueType EMPTY = new ValueType();

  /**
   * Строковое представление типа
   */
  List<Type> types;

  /**
   * Квалификаторы
   */
  List<Qualifier> qualifiers;

  public ValueType(@NonNull List<Type> types, @NonNull List<Qualifier> qualifiers) {
    this.types = types;
    this.qualifiers = qualifiers;
  }

  private ValueType() {
    this.types = Collections.emptyList();
    this.qualifiers = Collections.emptyList();
  }

  public interface Qualifier {
    QualifierType getType();
  }

  @Value
  @AllArgsConstructor
  public static class StringQualifier implements Qualifier {
    int length;
    AllowedLength allowedLength;

    @Override
    public QualifierType getType() {
      return QualifierType.STRING;
    }
  }

  @Value
  @AllArgsConstructor
  public static class DateQualifier implements Qualifier {
    DateFraction dateFraction;

    @Override
    public QualifierType getType() {
      return QualifierType.DATE;
    }
  }

  @Value
  @AllArgsConstructor
  public static class NumberQualifier implements Qualifier {
    int digits;
    int fractionDigits;
    AllowedSign allowedSign;

    @Override
    public QualifierType getType() {
      return QualifierType.DATE;
    }
  }

  @Value
  public static class Type {
    String name;
    TypeKind kind;

    private static final Map<String, Type> TYPES = new ConcurrentHashMap<>(baseTypes());
    private static final List<String> primitiveTypes = List.of("");
    private static final Pattern TYPE_KIND_PATTERN = CaseInsensitivePattern
      .compile("(?:cfg)\\:([A-Za-z]+)(Object|Ref|ValueManager|RecordSet|Type|haracteristic)(\\.(?:.*)|$)");
    private static final Pattern OBJECT_KIND_PATTERN = CaseInsensitivePattern
      .compile("Object|ValueManager|RecordSet");

    private Type(String name, TypeKind kind) {
      this.name = name.intern();
      this.kind = kind;
    }

    public static Type create(String name) {
      var type = TYPES.get(name);
      if (type == null) {
        var kind = TypeKind.UNKNOWN;
        var matcher = TYPE_KIND_PATTERN.matcher(name);
        if (matcher.find()) {
          kind = TypeKind.REFERENCE;
          var group = matcher.group(2);
          if (OBJECT_KIND_PATTERN.matcher(group).find()) {
            kind = TypeKind.OBJECT;
          }
        }
        type = new Type(name, kind);
        TYPES.put(name, type);
      }

      return type;
    }

    private static Map<String, Type> baseTypes() {
      Map<String, Type> types = new HashMap<>();
      List<String> names = List.of("xs:string", "xs:boolean", "xs:decimal", "xs:dateTime");
      names.forEach(name -> types.put(name, new Type(name, TypeKind.PRIMITIVE)));
      names = List.of("v8:ValueStorage", "v8:FixedStructure", "v8:FixedArray", "v8:UUID", "d7p1:Chart",
        "v8:ValueListType", "cfg:ReportBuilder", "v8:FixedMap", "dcsset:SettingsComposer", "v8:StandardPeriod",
        "cfg:ConstantsSet", "v8:ValueTree", "v8:ValueTable");
      names.forEach(name -> types.put(name, new Type(name, TypeKind.V8)));
      return types;
    }
  }

  public enum QualifierType {
    STRING,
    DATE,
    NUMBER
  }

  public enum AllowedLength {
    VARIABLE,
    FIXED
  }

  public enum DateFraction {
    DATE,
    DATETIME,
    TIME
  }

  public enum AllowedSign {
    ANY,
    NONNEGATIVE
  }

  public enum TypeKind {
    PRIMITIVE,
    REFERENCE,
    OBJECT,
    V8,
    UNKNOWN
  }
}

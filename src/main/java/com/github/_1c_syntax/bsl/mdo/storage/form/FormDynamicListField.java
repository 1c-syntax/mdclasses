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
package com.github._1c_syntax.bsl.mdo.storage.form;

import com.github._1c_syntax.bsl.mdo.ValueTypeOwner;
import com.github._1c_syntax.bsl.types.ValueTypeDescription;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.ToString;
import lombok.Value;

/**
 * Поле состава динамического списка - запись, которой список объявляет
 * доступное поле поверх основной таблицы или текста запроса
 */
@Value
@Builder
@ToString(of = "dataPath")
public class FormDynamicListField implements FormDataPathOwner, ValueTypeOwner {

  /**
   * Путь к данным поля
   */
  @Default
  String dataPath = "";

  /**
   * Имя поля набора данных.
   * Совпадает с путем к данным везде, кроме полей, сгруппированных под общим
   * префиксом: у такого поля путь {@code СубконтоДт.СубконтоДт1}, а имя - {@code СубконтоДт1}
   */
  @Default
  String name = "";

  /**
   * Тип значения. Заполняется, только если поле объявляет его явно
   */
  @Default
  ValueTypeDescription type = ValueTypeDescription.EMPTY;

  @Override
  public ValueTypeDescription getValueType() {
    return type;
  }
}

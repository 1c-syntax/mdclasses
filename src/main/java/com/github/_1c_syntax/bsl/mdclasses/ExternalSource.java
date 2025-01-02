/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.TabularSectionOwner;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.support.SupportVariant;
import lombok.NonNull;

import java.util.List;

/**
 * Базовый интерфейс для внешних отчетов и обработок
 */
public interface ExternalSource extends MDClass, CommandOwner, AttributeOwner,
  TabularSectionOwner, FormOwner, TemplateOwner {

  /**
   * Список реквизитов объекта
   */
  List<Attribute> getAttributes();

  /**
   * У внешних нет конфигурации поставщика
   */
  @Override
  @NonNull
  default SupportVariant getSupportVariant() {
    return SupportVariant.NONE;
  }

  /**
   * Внешние не входят в состав расширения
   */
  @Override
  @NonNull
  default ObjectBelonging getObjectBelonging() {
    return ObjectBelonging.OWN;
  }

  @Override
  @NonNull
  default List<Attribute> getAllAttributes() {
    return getAttributes();
  }
}

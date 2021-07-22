/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2021
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
package com.github._1c_syntax.bsl.mdo.children;

import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.ModuleOwner;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.support_configuration.SupportVariant;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.net.URI;

@Value
@Builder
public class ObjectModule implements Module {
  /**
   * Тип модуля
   */
  ModuleType moduleType;

  /**
   * Ссылка на файл bsl модуля
   */
  URI uri;

  /**
   * Ссылка на объект метаданных которому принадлежит модуль
   */
  @NonFinal
  ModuleOwner owner;

  @Override
  public SupportVariant getSupportVariant() {
    if (owner instanceof MDObject) {
      return ((MDObject) owner).getSupportVariant();
    }
    return SupportVariant.NONE;
  }

  public void setOwner(ModuleOwner owner) {
    if (this.owner == null) {
      this.owner = owner;
    }
  }
}

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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReuseSessions;
import com.github._1c_syntax.bsl.mdo.utils.LazyLoader;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.utils.Lazy;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder
@ToString(of = {"name", "uuid"})
@EqualsAndHashCode(of = {"name", "uuid"})
public class WebService implements MDObject, ChildrenOwner, ModuleOwner {

  /*
   * MDObject
   */

  @Default
  String uuid = "";
  @Default
  String name = "";
  @Default
  MdoReference mdoReference = MdoReference.EMPTY;
  @Default
  ObjectBelonging objectBelonging = ObjectBelonging.OWN;
  @Default
  String comment = "";
  @Default
  MultiLanguageString synonym = MultiLanguageString.EMPTY;
  @Default
  SupportVariant supportVariant = SupportVariant.NONE;

  Lazy<List<MD>> plainChildren = new Lazy<>(this::computePlainChildren);

  /*
   * ModuleOwner
   */

  @Default
  List<Module> modules = Collections.emptyList();

  /*
   * Свое
   */

  /**
   * URI Пространства имен
   */
  @Default
  String namespace = "";

  /**
   * Имя файла публикации
   */
  @Default
  String descriptorFileName = "";

  @Default
  ReuseSessions reuseSessions = ReuseSessions.DONT_USE;

  /**
   * Время жизни сеанса
   */
  int sessionMaxAge;

  /**
   * Операции веб-сервиса
   */
  @Singular("operation")
  List<WebServiceOperation> operations;

  /*
   * Для ChildrenOwner
   */

  @Override
  public List<MD> getChildren() {
    return Collections.unmodifiableList(operations);
  }

  @Override
  public List<MD> getPlainChildren() {
    return plainChildren.getOrCompute();
  }

  private List<MD> computePlainChildren() {
    return LazyLoader.computePlainChildren(this);
  }
}

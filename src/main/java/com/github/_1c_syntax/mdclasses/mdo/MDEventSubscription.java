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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.bsl.mdo.support.Handler;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.unmarshal.converters.MethodHandlerConverter;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.EVENT_SUBSCRIPTION,
  name = "EventSubscription",
  nameRu = "ПодпискаНаСобытие",
  groupName = "EventSubscriptions",
  groupNameRu = "ПодпискиНаСобытия"
)
public class MDEventSubscription extends AbstractMDObjectBase {

  /**
   * Обработчик подписки на событие
   */
  @XStreamConverter(MethodHandlerConverter.class)
  @XStreamAlias("Handler")
  private Handler handler;

  public MDEventSubscription(DesignerMDO designerMDO) {
    super(designerMDO);
    handler = new Handler(designerMDO.getProperties().getHandler());
  }

  @Override
  public Object buildMDObject() {
    builder = super.buildMDObject();
    TransformationUtils.setValue(builder, "handler", handler);
    return builder;
  }
}

/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static com.github._1c_syntax.mdclasses.utils.MapExtension.getOrEmptyString;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@JsonDeserialize(builder = EventSubscription.EventSubscriptionBuilderImpl.class)
@SuperBuilder
public class EventSubscription extends MDObjectBase {

  /**
   * Обработчик подписки на событие. Пока строкой
   * Формат mdoRef + имя метода
   * Пример CommonModule.ПростойОбщийМодуль.ПодпискаНаСобытие1ПередЗаписью
   */
  String handler;

  @Override
  public MDOType getType() {
    return MDOType.EVENT_SUBSCRIPTION;
  }

  @JsonPOJOBuilder(withPrefix = "")
  @JsonIgnoreProperties(ignoreUnknown = true)
  static final class EventSubscriptionBuilderImpl extends EventSubscription.EventSubscriptionBuilder<EventSubscription, EventSubscription.EventSubscriptionBuilderImpl> {
    @Override
    protected MDObjectBaseBuilder<EventSubscription, EventSubscriptionBuilderImpl> properties(Map<String, Object> properties) {
      super.properties(properties);
      handler(getOrEmptyString(properties, "Handler"));
      return this.self();
    }
  }

}

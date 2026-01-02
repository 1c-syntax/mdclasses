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
package com.github._1c_syntax.bsl.mdclasses;

import lombok.Builder;

/**
 * Настройки чтения MDC
 *
 * @param skipSupport               Пропускать чтение настроек поставки конфигурации
 * @param skipRoleData              Пропускать чтение содержимого ролей
 * @param skipXdtoPackage           Пропускать чтение содержимого xdto пакетов
 * @param skipFormElementItems      Пропускать чтение элементов форм
 * @param skipDataCompositionSchema Пропускать чтение элементов макетов системы компоновки
 */
@Builder
public record MDCReadSettings(boolean skipSupport, boolean skipRoleData, boolean skipXdtoPackage,
                              boolean skipFormElementItems, boolean skipDataCompositionSchema) {
  /**
   * Настройки по умолчанию
   */
  public static final MDCReadSettings DEFAULT = MDCReadSettings.builder().build();

  /**
   * Шаблон с отключением только чтения поддержки
   */
  public static final MDCReadSettings SKIP_SUPPORT = MDCReadSettings.builder().skipSupport(true).build();
}

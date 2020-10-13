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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Pattern;

/**
 * POJO представление свойств типа «Обработчики событий» для таких типов метаданных как
 * «Регламентные задания» или «Подписки на события»
 */
@Getter
@Setter
@NoArgsConstructor
public class Handler {

    private static final String METHOD_HANDLER_SPLIT_REGEX = "\\.";
    private static final Pattern METHOD_HANDLER_SPLIT_PATTERN = Pattern.compile(METHOD_HANDLER_SPLIT_REGEX);

    private String methodPath = "";
    private String moduleName = "";
    private String methodName = "";

    public Handler(String methodPath) {
        methodPath = methodPath == null ? "" : methodPath;

        this.methodPath = methodPath;
        String[] data = METHOD_HANDLER_SPLIT_PATTERN.split(methodPath);
        if (data.length > 1) {
            this.methodName = data[2];
            this.moduleName = data[1];
        }
    }

    public boolean isEmpty() {
        return methodPath.isBlank();
    }
}

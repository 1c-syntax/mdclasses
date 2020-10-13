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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;


/**
 * POJO представление данных конкретной роли
 */
@NoArgsConstructor
@Getter
@Setter
public class RoleData {

    @XStreamAlias("setForNewObjects")
    private boolean setForNewObjects = false;
    @XStreamAlias("setForAttributesByDefault")
    private boolean setForAttributesByDefault = false;
    @XStreamAlias("independentRightsOfChildObjects")
    private boolean independentRightsOfChildObjects = false;

    @XStreamAlias("object")
    @XStreamImplicit
    List<ObjectRight> objectRights = Collections.emptyList();
}

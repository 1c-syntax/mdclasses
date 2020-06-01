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

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class CommonTemplateTest extends AbstractMDOTest {
  CommonTemplateTest() {
    super(MDOType.COMMON_TEMPLATE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("CommonTemplates/Макет/Макет.mdo");
    checkBaseField(mdo, CommonTemplate.class, "Макет",
      "799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("CommonTemplates/Макет.xml");
    checkBaseField(mdo, CommonTemplate.class, "Макет",
      "799e0ae7-f5ea-4b50-8853-e2c58ef5d9cd");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}

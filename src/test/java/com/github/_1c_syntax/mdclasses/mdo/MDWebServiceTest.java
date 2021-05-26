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

import com.github._1c_syntax.mdclasses.mdo.children.WEBServiceOperation;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDWebServiceTest extends AbstractMDOTest {
  MDWebServiceTest() {
    super(MDOType.WEB_SERVICE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("WebServices/WebСервис1/WebСервис1.mdo");
    checkBaseField(mdo, MDWebService.class, "WebСервис1",
      "d7f9b06b-0799-486e-adff-c45a2d5b8101");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "WebServices/WebСервис1",
      ModuleType.WEBServiceModule);
    assertThat(((MDWebService) mdo).getOperations()).hasSize(2);
    assertThat(((MDWebService) mdo).getOperations()).extracting(WEBServiceOperation::getHandler)
      .anyMatch("Операция1"::equals)
      .anyMatch("Операция11"::equals);
    ((MDWebService) mdo).getOperations().forEach((WEBServiceOperation webServiceOperation) ->
      checkChild(mdo.getMdoReference(), MDOType.WS_OPERATION, ModuleType.UNKNOWN, webServiceOperation));
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("WebServices/WebСервис1.xml");
    checkBaseField(mdo, MDWebService.class, "WebСервис1",
      "d7f9b06b-0799-486e-adff-c45a2d5b8101");
    checkNoChildren(mdo);
    assertThat(((AbstractMDObjectBSL) mdo).getModules()).isEmpty();
    assertThat(((MDWebService) mdo).getOperations()).hasSize(2);
    assertThat(((MDWebService) mdo).getOperations()).extracting(WEBServiceOperation::getHandler)
      .anyMatch("Операция1"::equals)
      .anyMatch("Операция2"::equals);
    ((MDWebService) mdo).getOperations().forEach((WEBServiceOperation webServiceOperation) ->
      checkChild(mdo.getMdoReference(), MDOType.WS_OPERATION, ModuleType.UNKNOWN, webServiceOperation));
  }
}

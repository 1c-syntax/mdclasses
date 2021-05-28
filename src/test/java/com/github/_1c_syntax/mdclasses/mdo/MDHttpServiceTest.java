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

import com.github._1c_syntax.mdclasses.mdo.children.HTTPServiceMethod;
import com.github._1c_syntax.mdclasses.mdo.children.HTTPServiceURLTemplate;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDHttpServiceTest extends AbstractMDOTest {
  MDHttpServiceTest() {
    super(MDOType.HTTP_SERVICE);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("HTTPServices/HTTPСервис1/HTTPСервис1.mdo");
    checkBaseField(mdo, MDHttpService.class, "HTTPСервис1",
      "3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "HTTPServices/HTTPСервис1",
      ModuleType.HTTPServiceModule);
    assertThat(((MDHttpService) mdo).getUrlTemplates()).hasSize(1);
    ((MDHttpService) mdo).getUrlTemplates().forEach((HTTPServiceURLTemplate httpServiceURLTemplate) -> {
      checkChild(mdo.getMdoReference(), MDOType.HTTP_SERVICE_URL_TEMPLATE,
        ModuleType.UNKNOWN, httpServiceURLTemplate);
      httpServiceURLTemplate.getHttpServiceMethods().forEach((HTTPServiceMethod httpServiceMethod) ->
        checkChild(httpServiceURLTemplate.getMdoReference(), MDOType.HTTP_SERVICE_METHOD,
          ModuleType.UNKNOWN, httpServiceMethod));
    });
    assertThat(((MDHttpService) mdo).getUrlTemplates())
      .flatExtracting(HTTPServiceURLTemplate::getHttpServiceMethods)
      .hasSize(2)
      .extracting(HTTPServiceMethod::getHandler)
      .anyMatch("ШаблонURLМетод"::equals)
      .anyMatch("ШаблонURLМетод1"::equals);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("HTTPServices/HTTPСервис1.xml");
    checkBaseField(mdo, MDHttpService.class, "HTTPСервис1",
      "3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe");
    checkNoChildren(mdo);
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "HTTPServices/HTTPСервис1",
      ModuleType.HTTPServiceModule);
    assertThat(((MDHttpService) mdo).getUrlTemplates()).hasSize(1);
    ((MDHttpService) mdo).getUrlTemplates().forEach((HTTPServiceURLTemplate httpServiceURLTemplate) -> {
      checkChild(mdo.getMdoReference(), MDOType.HTTP_SERVICE_URL_TEMPLATE,
        ModuleType.UNKNOWN, httpServiceURLTemplate);
      httpServiceURLTemplate.getHttpServiceMethods().forEach((HTTPServiceMethod httpServiceMethod) ->
        checkChild(httpServiceURLTemplate.getMdoReference(), MDOType.HTTP_SERVICE_METHOD,
          ModuleType.UNKNOWN, httpServiceMethod));
    });
    assertThat(((MDHttpService) mdo).getUrlTemplates())
      .flatExtracting(HTTPServiceURLTemplate::getHttpServiceMethods)
      .hasSize(2)
      .extracting(HTTPServiceMethod::getHandler)
      .anyMatch("ШаблонURL1Метод2"::equals)
      .anyMatch("ШаблонURL1Метод1"::equals);
  }
}

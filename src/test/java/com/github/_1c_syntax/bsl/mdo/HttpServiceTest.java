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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpServiceTest extends AbstractMDObjectTest<HttpService> {
  HttpServiceTest() {
    super(HttpService.class);
  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "HTTPСервис1,3f029e1e-5a9e-4446-b74f-cbcb79b1e2fe,,,HTTPService,HTTPСервис,0,0,0,0,0,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("HTTPServices/" + name + "/" + name);
    mdoTest(mdo, MDOType.HTTP_SERVICE, argumentsAccessor);
    assertThat(mdo.getUrlTemplates()).hasSize(1);

    var httpServiceUrlTemplate = mdo.getUrlTemplates().get(0);
    assertThat(httpServiceUrlTemplate.getName()).isEqualTo("ШаблонURL");
    assertThat(httpServiceUrlTemplate.getType()).isEqualTo(MDOType.HTTP_SERVICE_URL_TEMPLATE);
    assertThat(httpServiceUrlTemplate.getUuid()).isEqualTo("4d97db36-cfbf-4f11-9647-d95677380b8f");
    assertThat(httpServiceUrlTemplate.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(httpServiceUrlTemplate.getMetadataName()).isEqualTo("URLTemplate");
    assertThat(httpServiceUrlTemplate.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Шаблон URL"));
    assertThat(httpServiceUrlTemplate.getMdoReference().getMdoRef())
      .isEqualTo("HTTPService.HTTPСервис1.URLTemplate.ШаблонURL");
    assertThat(httpServiceUrlTemplate.getOwner()).isEqualTo(mdo);
    assertThat(httpServiceUrlTemplate.getHttpServiceMethods()).hasSize(2);

    var httpServiceMethod = httpServiceUrlTemplate.getHttpServiceMethods().get(0);
    assertThat(httpServiceMethod.getName()).isEqualTo("Метод");
    assertThat(httpServiceMethod.getType()).isEqualTo(MDOType.HTTP_SERVICE_METHOD);
    assertThat(httpServiceMethod.getUuid()).isEqualTo("c2b0e62c-9d2e-4bd8-8bee-3213ee460ffa");
    assertThat(httpServiceMethod.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(httpServiceMethod.getMetadataName()).isEqualTo("Method");
    assertThat(httpServiceMethod.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Метод"));
    assertThat(httpServiceMethod.getMdoReference().getMdoRef())
      .isEqualTo("HTTPService.HTTPСервис1.URLTemplate.ШаблонURL.Method.Метод");
    assertThat(httpServiceMethod.getHandler()).isEqualTo("ШаблонURLМетод");
    assertThat(httpServiceMethod.getOwner()).isEqualTo(httpServiceUrlTemplate);
  }
}

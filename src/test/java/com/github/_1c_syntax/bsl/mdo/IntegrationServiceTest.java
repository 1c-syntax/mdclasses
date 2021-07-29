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

import com.github._1c_syntax.bsl.mdo.support.MessageDirection;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class IntegrationServiceTest extends AbstractMDObjectTest<IntegrationService> {
  IntegrationServiceTest() {
    super(IntegrationService.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "СервисИнтеграции1,94ed2401-fd3c-4e92-b34d-1cdad2d8ee42,,,IntegrationService,СервисИнтеграции,0,0,0,0,0,1"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject18("IntegrationServices/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.INTEGRATION_SERVICE, argumentsAccessor);
    assertThat(mdo.getIntegrationChannels()).hasSize(2);
    assertThat(mdo.getExternalIntegrationServiceAddress()).isEqualTo("http://");

    var integrationServiceChannel = mdo.getIntegrationChannels().get(0);
    assertThat(integrationServiceChannel.getMessageDirection()).isEqualTo(MessageDirection.RECEIVE);
    assertThat(integrationServiceChannel.getName()).isEqualTo("КаналСервисаИнтеграции1");
    assertThat(integrationServiceChannel.getType()).isEqualTo(MDOType.INTEGRATION_SERVICE_CHANNEL);
    assertThat(integrationServiceChannel.getHandler()).isEqualTo("КаналСервисаИнтеграции1ОбработкаПолученияСообщения");
    assertThat(integrationServiceChannel.getExternalIntegrationServiceChannelName()).isEqualTo("Имя");
    assertThat(integrationServiceChannel.getUuid()).isEqualTo("fd0a0d02-c211-4985-ab9e-f0e7e63a6943");
    assertThat(integrationServiceChannel.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(integrationServiceChannel.getMetadataName()).isEqualTo("IntegrationServiceChannel");
    assertThat(integrationServiceChannel.getSynonyms().getContent()).isEmpty();
    assertThat(integrationServiceChannel.getMdoReference().getMdoRef())
      .isEqualTo("IntegrationService.СервисИнтеграции1.IntegrationServiceChannel.КаналСервисаИнтеграции1");

  }
}

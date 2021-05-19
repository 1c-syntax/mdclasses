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
package com.github._1c_syntax.mdclasses.mdo.children;

import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.MessageDirection;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.INTEGRATION_SERVICE_CHANNEL,
  name = "IntegrationServiceChannel",
  nameRu = "КаналСервисаИнтеграции",
  groupName = "",
  groupNameRu = ""
)
public class IntegrationServiceChannel extends AbstractMDObjectBase {

  /**
   * Направление сообщения
   */
  private MessageDirection messageDirection = MessageDirection.SEND;

  /**
   * Обработчик получения сообщения
   */
  private String receiveMessageProcessing = "";

  /**
   * Имя канала внешнего сервиса интеграции
   */
  private String externalIntegrationServiceChannelName = "";

  public IntegrationServiceChannel(DesignerMDO designerMDO) {
    super(designerMDO);
    messageDirection = designerMDO.getProperties().getMessageDirection();
    receiveMessageProcessing = designerMDO.getProperties().getReceiveMessageProcessing();
    externalIntegrationServiceChannelName = designerMDO.getProperties().getExternalIntegrationServiceChannelName();
  }
}

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
package com.github._1c_syntax.mdclasses.unmarshal.wrapper;

import com.github._1c_syntax.mdclasses.mdo.AbstractMDObjectBase;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Враппер для дочерних элементов объекта в формате конфигуратора
 */
@Data
@NoArgsConstructor
public class DesignerChildObjects {

  @XStreamImplicit(itemFieldName = "Form")
  private List<String> forms = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Template")
  private List<String> templates = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Command")
  private List<DesignerMDO> commands = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Dimension")
  private List<DesignerMDO> dimensions = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Resource")
  private List<DesignerMDO> resources = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Recalculation")
  private List<String> recalculations = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Attribute")
  private List<DesignerMDO> attributes = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "TabularSection")
  private List<DesignerMDO> tabularSections = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "AccountingFlag")
  private List<DesignerMDO> accountingFlags = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "ExtDimensionAccountingFlag")
  private List<DesignerMDO> extDimensionAccountingFlags = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Column")
  private List<DesignerMDO> columns = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "AddressingAttribute")
  private List<DesignerMDO> addressingAttributes = Collections.emptyList();

  @XStreamImplicit
  private List<Either<String, AbstractMDObjectBase>> children = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "IntegrationServiceChannel")
  private List<DesignerMDO> integrationChannels = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Operation")
  private List<DesignerMDO> operations = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "URLTemplate")
  private List<DesignerMDO> httpUrlTemplates = Collections.emptyList();

  @XStreamImplicit(itemFieldName = "Method")
  private List<DesignerMDO> httpServiceMethods = Collections.emptyList();

}

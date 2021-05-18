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

import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.mdo.support.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDBusinessProcessTest extends AbstractMDOTest {
  MDBusinessProcessTest() {
    super(MDOType.BUSINESS_PROCESS);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("BusinessProcesses/БизнесПроцесс1/БизнесПроцесс1.mdo");
    checkBaseField(mdo, MDBusinessProcess.class, "БизнесПроцесс1",
      "560a32ca-028d-4b88-b6f2-6b7212bf31f8");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 1, "BusinessProcesses/БизнесПроцесс1",
      ModuleType.ObjectModule);

  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("BusinessProcesses/БизнесПроцесс1.xml");
    checkBaseField(mdo, MDBusinessProcess.class, "БизнесПроцесс1",
      "560a32ca-028d-4b88-b6f2-6b7212bf31f8");
    checkForms(mdo);
    checkTemplates(mdo);
    checkCommands(mdo);
    assertThat(((AbstractMDObjectComplex) mdo).getAttributes()).isEmpty();
    checkModules(((AbstractMDObjectBSL) mdo).getModules(), 0, "0", ModuleType.UNKNOWN);
  }

}

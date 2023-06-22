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

import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.mdclasses.Configuration;
import com.github._1c_syntax.mdclasses.mdo.attributes.Recalculation;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ProtectedModuleTest extends AbstractMDOTest {

  private static final String EDT_PROJECT = "src/test/resources/metadata/protected_modules/edt";
  private static final String SRC_EDT = EDT_PROJECT + "/src";
  private static final String SRC_DESIGNER = "src/test/resources/metadata/protected_modules/original";

  ProtectedModuleTest() {
    super(MDOType.CATALOG);
  }

  @Override
  @Test
  void testEDT() {

    File srcPath = new File(EDT_PROJECT);
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration.getProtectedModules()).hasSize(2);

    var mdCatalog = (MDCatalog) getMDObjectEDT("Catalogs/ПервыйСправочник/ПервыйСправочник.mdo",
      SRC_EDT);
    assertThat(mdCatalog.getModules()).isEmpty();
    assertThat(mdCatalog.getProtectedModules()).hasSize(1);
    assertThat(mdCatalog.getProtectedModules().get(0).isProtected())
      .isTrue();
    checkModules(mdCatalog.getProtectedModules(), 1,
      "Catalogs/ПервыйСправочн", ModuleType.ObjectModule);

    final String partPath = "CalculationRegisters/РегистрРасчета1/РегистрРасчета1.mdo";
    var mdCalculationRegister = (MDCalculationRegister) getMDObjectEDT(partPath, SRC_EDT);
    var mdRecalculation = getRecalculation(mdCalculationRegister);
    assertThat(mdRecalculation.getModules()).isEmpty();
    assertThat(mdRecalculation.getProtectedModules()).hasSize(1);
    assertThat(mdRecalculation.getProtectedModules().get(0).isProtected())
      .isTrue();
    checkModules(mdRecalculation.getProtectedModules(), 1,
      "CalculationRegisters/РегистрРасчета1", ModuleType.RecalculationModule);

    var mdoCommonModule = (MDCommonModule) getMDObjectEDT("CommonModules/ГлобальныйОбщийМодуль/ГлобальныйОбщийМодуль.mdo");
    assertThat(mdoCommonModule.getModules()).hasSize(1);
    assertThat(mdoCommonModule.getProtectedModules()).isEmpty();
    assertThat(mdoCommonModule.getModules().get(0).isProtected())
      .isFalse();
    checkModules(mdoCommonModule.getModules(), 1,
      "CommonModules/Глобальны", ModuleType.CommonModule);
  }

  @Override
  @Test
  void testDesigner() {

    File srcPath = new File(SRC_DESIGNER);
    Configuration configuration = Configuration.create(srcPath.toPath());
    assertThat(configuration.getProtectedModules()).hasSize(2);

    var mdCatalog = (MDCatalog)getMDObjectDesigner("Catalogs/ПервыйСправочник.xml",
      SRC_DESIGNER);
    assertThat(mdCatalog.getModules()).isEmpty();
    assertThat(mdCatalog.getProtectedModules()).hasSize(1);
    assertThat(mdCatalog.getProtectedModules().get(0).isProtected())
      .isTrue();
    checkModules(mdCatalog.getProtectedModules(), 1,
      "Catalogs/ПервыйСправочн", ModuleType.ObjectModule);

    final String partPath = "CalculationRegisters/РегистрРасчета1.xml";
    var mdCalculationRegister = (MDCalculationRegister) getMDObjectDesigner(partPath, SRC_DESIGNER);
    var mdRecalculation = getRecalculation(mdCalculationRegister);
    assertThat(mdRecalculation.getModules()).isEmpty();
    assertThat(mdRecalculation.getProtectedModules()).hasSize(1);
    assertThat(mdRecalculation.getProtectedModules().get(0).isProtected())
      .isTrue();
    checkModules(mdRecalculation.getProtectedModules(), 1,
      "CalculationRegisters/РегистрРасчета1", ModuleType.RecalculationModule);

    var mdoCommonModule = (MDCommonModule)getMDObjectDesigner("CommonModules/ГлобальныйОбщийМодуль.xml");
    assertThat(mdoCommonModule.getProtectedModules()).isEmpty();
    assertThat(mdoCommonModule.getModules()).hasSize(1);
    final var mdoModule = mdoCommonModule.getModules().get(0);
    assertThat(mdoModule.isProtected()).isFalse();
    checkModules(mdoCommonModule.getModules(), 1,
        "CommonModules/ГлобальныйОбщийМ", ModuleType.CommonModule);
  }

  private static Recalculation getRecalculation(MDCalculationRegister mdCalculationRegister) {
    return mdCalculationRegister
    .getChildren().stream()
    .filter(Recalculation.class::isInstance)
    .map(Recalculation.class::cast)
    .findFirst().orElseThrow();
  }
}

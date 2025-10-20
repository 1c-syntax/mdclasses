/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2025
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
package com.github._1c_syntax.bsl.mdclasses;

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import com.github._1c_syntax.bsl.test_utils.assertions.Assertions;
import com.github._1c_syntax.bsl.types.MdoReference;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class MDClassesSolutionTest {

  @Test
  void createSolutionEmpty() {
    var solutionEmpty = MDClasses.createSolution(Path.of("src/test/resources/fixtures"));
    assertThat(solutionEmpty).isEqualTo(Configuration.EMPTY);
  }

  @ParameterizedTest
  @CsvSource(
    {
      "src/test/resources/ext/designer/mdclasses/src/cf",
      "src/test/resources/ext/edt/mdclasses_3_27/configuration",
      "src/test/resources/ext/designer/mdclasses_ext/src/cf"
    }
  )
  void createSolutionSimple(ArgumentsAccessor argumentsAccessor) {
    var path = Path.of(argumentsAccessor.getString(0));
    var solutionCf = MDClasses.createSolution(path);
    var cf = MDClasses.createConfigurations(path).get(0);
    Assertions.assertThat(MDTestUtils.createJson(solutionCf), true)
      .isEqual(MDTestUtils.createJson(cf));
  }

  @Test
  void createSolutionCf_2_exts() {
    var solution = MDClasses.createSolution(Path.of("src/test/resources/solutions/sol1"));
    assertThat(solution).isInstanceOf(Configuration.class);
    var cf = (Configuration) solution;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules()).hasSize(3);
    assertThat(cf.getChildren()).hasSize(10);

    assertThat(cf.getAllModules()).hasSize(7);

    assertThat(cf.getRoles()).hasSize(2);
    assertThat(cf.getLanguages()).hasSize(1);
    assertThat(cf.getConstants()).hasSize(1);
    assertThat(cf.getCatalogs()).hasSize(3);
    assertThat(cf.getDocuments()).hasSize(1);
    assertThat(cf.getEnums()).hasSize(1);
    assertThat(cf.getDataProcessors()).hasSize(1);
    assertThat(cf.getPlainChildren().stream().map(MD::getMdoRef).sorted()).hasSize(53);

    assertThat(cf.getModulesByType()).hasSize(7)
      .containsValue(ModuleType.FormModule)
    ;

    assertThat(cf.getModulesByObject())
      .hasSize(7)
      .containsValue(cf.findChild(MdoReference.create("Catalog.Расш1_Справочник3.Form.ФормаЭлемента")).get())
    ;
  }

  @Test
  void createSolutionCf_2_exts_empty_cf() {
    var solution = MDClasses.createSolution(Path.of("src/test/resources/solutions/sol2"));
    assertThat(solution).isInstanceOf(Configuration.class);
    var cf = (Configuration) solution;
    assertThat(cf.getSupportVariant()).isEqualTo(SupportVariant.NONE);
    assertThat(cf.getModules()).hasSize(1);
    assertThat(cf.getChildren()).hasSize(8);

    assertThat(cf.getAllModules()).hasSize(4);

    assertThat(cf.getRoles()).hasSize(2);
    assertThat(cf.getLanguages()).hasSize(1);
    assertThat(cf.getConstants()).hasSize(1);
    assertThat(cf.getCatalogs()).hasSize(2);
    assertThat(cf.getDocuments()).hasSize(1);
    assertThat(cf.getEnums()).isEmpty();
    assertThat(cf.getDataProcessors()).hasSize(1);
    assertThat(cf.getPlainChildren().stream().map(MD::getMdoRef).sorted()).hasSize(39);

    assertThat(cf.getModulesByType()).hasSize(4)
      .containsValue(ModuleType.FormModule)
    ;

    assertThat(cf.getModulesByObject())
      .hasSize(4)
      .containsValue(cf.findChild(MdoReference.create("Catalog.Расш1_Справочник3.Form.ФормаЭлемента")).get())
    ;
  }
}
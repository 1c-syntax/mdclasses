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

import com.github._1c_syntax.bsl.test_utils.MDTestUtils;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class MDClassesTest {
  @Test
  void createConfigurations() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.createConfigurations(srcPath);
    assertThat(mdcs).hasSize(11);

    // каталоги с обработками не читаются
    srcPath = Paths.get("src/test/resources/ext/edt/external");
    mdcs = MDClasses.createConfigurations(srcPath);
    assertThat(mdcs).isEmpty();
  }

  @Test
  void createExternalSources() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.createExternalSources(srcPath);
    assertThat(mdcs).hasSize(4);

    // каталоги с конфигурацией не читаются
    srcPath = Paths.get("src/test/resources/ext/edt/ssl_3_1");
    mdcs = MDClasses.createExternalSources(srcPath);
    assertThat(mdcs).isEmpty();
  }

  @Test
  void create() {
    var srcPath = Paths.get("src/test/resources/ext");
    var mdcs = MDClasses.create(srcPath);
    assertThat(mdcs).hasSize(15);
  }

  @Test
  void createProjectEmpty() {
    var srcPath = Paths.get("src/test/resources/ext/mdclasses_ext");
    var project = MDClasses.createProject(srcPath, false);
    assertThat(project.getConfiguration().isEmpty()).isTrue();
  }

  @Test
  void createProject() {
    var srcPath = Paths.get("src/test/resources/project");
    var project = MDClasses.createProject(srcPath, false);
    assertThat(project.getConfiguration().isEmpty()).isFalse();
    assertThat(project.getExtensions()).hasSize(2);
    assertThat(project.getSubsystems()).hasSize(4);
    assertThat(project.getCommonModules()).hasSize(3);
    assertThat(project.getSessionParameters()).hasSize(2);
    assertThat(project.getRoles()).hasSize(2);
    assertThat(project.getCommonAttributes()).hasSize(0);
    assertThat(project.getExchangePlans()).hasSize(2);
    assertThat(project.getFilterCriteria()).hasSize(0);
    assertThat(project.getEventSubscriptions()).hasSize(0);
    assertThat(project.getScheduledJobs()).hasSize(0);
    assertThat(project.getBots()).hasSize(0);
    assertThat(project.getFunctionalOptions()).hasSize(0);
    assertThat(project.getFunctionalOptionsParameters()).hasSize(0);
    assertThat(project.getDefinedTypes()).hasSize(0);
    assertThat(project.getSettingsStorages()).hasSize(0);
    assertThat(project.getCommonForms()).hasSize(0);
    assertThat(project.getCommonCommands()).hasSize(0);
    assertThat(project.getCommandGroups()).hasSize(0);
    assertThat(project.getCommonTemplates()).hasSize(0);
    assertThat(project.getCommonPictures()).hasSize(0);
    assertThat(project.getInterfaces()).hasSize(0);
    assertThat(project.getXDTOPackages()).hasSize(0);
    assertThat(project.getWebServices()).hasSize(0);
    assertThat(project.getHttpServices()).hasSize(0);
    assertThat(project.getWsReferences()).hasSize(0);
    assertThat(project.getIntegrationServices()).hasSize(0);
    assertThat(project.getStyleItems()).hasSize(0);
    assertThat(project.getPaletteColors()).hasSize(0);
    assertThat(project.getStyles()).hasSize(0);
    assertThat(project.getLanguages()).hasSize(3);
    assertThat(project.getConstants()).hasSize(0);
    assertThat(project.getCatalogs()).hasSize(3);
    assertThat(project.getDocuments()).hasSize(1);
    assertThat(project.getDocumentNumerators()).hasSize(0);
    assertThat(project.getSequences()).hasSize(0);
    assertThat(project.getDocumentJournals()).hasSize(0);
    assertThat(project.getEnums()).hasSize(0);
    assertThat(project.getReports()).hasSize(0);
    assertThat(project.getDataProcessors()).hasSize(0);
    assertThat(project.getChartsOfCharacteristicTypes()).hasSize(0);
    assertThat(project.getChartsOfAccounts()).hasSize(0);
    assertThat(project.getChartsOfCalculationTypes()).hasSize(0);
    assertThat(project.getInformationRegisters()).hasSize(1);
    assertThat(project.getAccumulationRegisters()).hasSize(0);
    assertThat(project.getAccountingRegisters()).hasSize(0);
    assertThat(project.getCalculationRegisters()).hasSize(0);
    assertThat(project.getBusinessProcesses()).hasSize(0);
    assertThat(project.getTasks()).hasSize(0);
    assertThat(project.getExternalDataSources()).hasSize(0);

    assertThat(project.getChildren()).hasSize(21);
    assertThat(project.getPlainChildren()).hasSize(46);
    assertThat(project.getChildrenByMdoRef()).hasSize(38);
    assertThat(project.getModulesByType()).hasSize(4);
    assertThat(project.getModulesByURI()).hasSize(4);
    assertThat(project.getModulesByObject()).hasSize(4);
    assertThat(project.getCommonModulesByName()).hasSize(2);
    assertThat(project.getAllModules()).hasSize(4);
  }
}

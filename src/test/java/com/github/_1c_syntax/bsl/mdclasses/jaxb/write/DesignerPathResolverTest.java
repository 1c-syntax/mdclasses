/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
 * Tymko Oleg <olegtymko@yandex.ru>, Maximov Valery <maximovvalery@gmail.com> and contributors
 *
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */
package com.github._1c_syntax.bsl.mdclasses.jaxb.write;

import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdclasses.MDCReadSettings;
import com.github._1c_syntax.bsl.reader.MDOReader;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DesignerPathResolverTest {

  private static final Path DESIGNER_CF = Path.of("src/test/resources/ext/designer/mdclasses/src/cf");
  private static final Path ROOT = Path.of("out");

  @Test
  void configurationPath() {
    Path p = DesignerPathResolver.configurationPath(ROOT);
    assertThat(p).isEqualTo(ROOT.resolve("Configuration.xml"));
  }

  @Test
  void pathForObjectSubsystemTopLevel() {
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Subsystems.ПерваяПодсистема", MDCReadSettings.DEFAULT);
    assertThat(mdo).isNotNull();

    Path p = DesignerPathResolver.pathForObject(ROOT, mdo);
    assertThat(p).isEqualTo(ROOT.resolve("Subsystems").resolve("ПерваяПодсистема.xml"));
  }

  @Test
  void pathForObjectCatalog() {
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Catalogs.Справочник1", MDCReadSettings.DEFAULT);
    assertThat(mdo).isNotNull();

    Path p = DesignerPathResolver.pathForObject(ROOT, mdo);
    assertThat(p).isEqualTo(ROOT.resolve("Catalogs").resolve("Справочник1.xml"));
  }

  @Test
  void pathForObjectNestedSubsystem() {
    MD parent = (MD) MDOReader.read(DESIGNER_CF, "Subsystems.ПерваяПодсистема", MDCReadSettings.DEFAULT);
    assertThat(parent).isInstanceOf(Subsystem.class);
    Subsystem parentSub = (Subsystem) parent;
    var children = parentSub.getSubsystems();
    if (children == null || children.isEmpty()) {
      return;
    }
    MD child = children.get(0);
    Path parentPath = ROOT.resolve("Subsystems").resolve("ПерваяПодсистема.xml");

    Path childPath = DesignerPathResolver.pathForObject(ROOT, child, parentPath);
    assertThat(childPath).isEqualTo(ROOT.resolve("Subsystems").resolve("ПерваяПодсистема")
      .resolve("Subsystems").resolve(child.getName() + ".xml"));
  }

  @Test
  void getPathForObjectViaFacade() {
    MD mdo = (MD) MDOReader.read(DESIGNER_CF, "Documents.Документ1", MDCReadSettings.DEFAULT);
    assertThat(mdo).isNotNull();

    Path p = MDClassesJaxbWriter.getPathForObject(ROOT, mdo);
    assertThat(p).isEqualTo(ROOT.resolve("Documents").resolve("Документ1.xml"));
  }
}

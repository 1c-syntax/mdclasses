/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2020
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
package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonModuleTest {

  @Test
  void testBuilderEDT() {

    File srcPath = new File("src/test/resources/metadata/edt");
    Configuration configuration = Configuration.create(srcPath.toPath());

    CommonModule commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ПростойОбщийМодуль"))
      .findFirst().get();

    assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");
    assertThat(commonModule.getComment()).isEmpty();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ОбщийМодульПовт�?спСеанс"))
      .findFirst().get();
    assertThat(commonModule.getName()).isEqualTo("ОбщийМодульПовт�?спСеанс");
    assertThat(commonModule.getComment()).isEmpty();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ГлобальныйОбщийМодуль"))
      .findFirst().get();
    assertThat(commonModule.getName()).isEqualTo("ГлобальныйОбщийМодуль");
    assertThat(commonModule.getComment()).isEmpty();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isTrue();
    assertThat(commonModule.isGlobal()).isTrue();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
  }

  @Test
  void testBuilderOrigin() {
    File srcPath = new File("src/test/resources/metadata/original");
    Configuration configuration = Configuration.create(srcPath.toPath());

    CommonModule commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ПростойОбщийМодуль"))
      .findFirst().get();

    assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");
    assertThat(commonModule.getComment()).isNullOrEmpty();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ГлобальныйКлиент"))
      .findFirst().get();
    assertThat(commonModule.getName()).isEqualTo("ГлобальныйКлиент");
    assertThat(commonModule.getComment()).isEqualTo("Комментарий");
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isTrue();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isFalse();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (CommonModule) configuration.getChildren().stream().filter(mdObject ->
      mdObject instanceof CommonModule && mdObject.getName().equals("ОбщегоНазначенияПовторного�?спользования"))
      .findFirst().get();
    assertThat(commonModule.getName()).isEqualTo("ОбщегоНазначенияПовторного�?спользования");
    assertThat(commonModule.getComment()).isNullOrEmpty();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isTrue();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
  }
}

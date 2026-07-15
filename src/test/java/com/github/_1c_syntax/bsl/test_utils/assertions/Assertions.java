/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2026
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
package com.github._1c_syntax.bsl.test_utils.assertions;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.MD;
import com.github._1c_syntax.bsl.mdo.Module;

import java.util.Collection;
import java.util.List;

public class Assertions extends org.assertj.core.api.Assertions {

  public static JsonAssert assertThat(String actual, boolean ignored) {
    return new JsonAssert(actual);
  }

  public static ModuleCollectionAssert assertThat(List<Module> actual, boolean ignored) {
    return ModuleCollectionAssert.assertThat(actual, ignored);
  }

  @SuppressWarnings("unchecked")
  public static MDCollectionAssert assertThat(Collection<? extends MD> actual, boolean ignored) {
    return MDCollectionAssert.assertThat((List<MD>) actual, ignored);
  }

  public static ConfigurationCollectionAssert assertThat(Configuration actual, boolean ignored) {
    return ConfigurationCollectionAssert.assertThat(actual);
  }
}

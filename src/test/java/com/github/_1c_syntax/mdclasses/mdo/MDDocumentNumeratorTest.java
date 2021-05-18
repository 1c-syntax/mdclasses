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
import org.junit.jupiter.api.Test;

class MDDocumentNumeratorTest extends AbstractMDOTest {
  MDDocumentNumeratorTest() {
    super(MDOType.DOCUMENT_NUMERATOR);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("DocumentNumerators/НумераторДокументов1/НумераторДокументов1.mdo");
    checkBaseField(mdo, MDDocumentNumerator.class, "НумераторДокументов1",
      "e401f835-6bfc-4cd4-8d87-5e6b6332a3f6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("DocumentNumerators/НумераторДокументов1.xml");
    checkBaseField(mdo, MDDocumentNumerator.class, "НумераторДокументов1",
      "e401f835-6bfc-4cd4-8d87-5e6b6332a3f6");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

}

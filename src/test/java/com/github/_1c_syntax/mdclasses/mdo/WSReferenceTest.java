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
package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class WSReferenceTest extends AbstractMDOTest {
  WSReferenceTest() {
    super(MDOType.WS_REFERENCE);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("WSReferences/WSСсылка/WSСсылка.mdo");
    checkBaseField(mdo, WSReference.class, "WSСсылка",
      "95b745f2-e1fa-4f94-b7f9-f3f0224fc8c7");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("WSReferences/WSСсылка1.xml");
    checkBaseField(mdo, WSReference.class, "WSСсылка1",
      "7b8d6924-7aa9-4699-b794-6797c79d83c7");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}

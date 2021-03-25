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

class MDScheduledJobTest extends AbstractMDOTest {
  MDScheduledJobTest() {
    super(MDOType.SCHEDULED_JOB);
  }

  @Override
  @Test
  void testEDT() {
    var mdo = getMDObjectEDT("ScheduledJobs/РегламентноеЗадание1/РегламентноеЗадание1.mdo");
    checkBaseField(mdo, MDScheduledJob.class, "РегламентноеЗадание1",
      "0de0c839-4427-46d9-be68-302f88ac162c");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  void testDesigner() {
    var mdo = getMDObjectDesigner("ScheduledJobs/РегламентноеЗадание1.xml");
    checkBaseField(mdo, MDScheduledJob.class, "РегламентноеЗадание1",
      "0de0c839-4427-46d9-be68-302f88ac162c");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}

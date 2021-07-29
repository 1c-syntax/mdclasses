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
package com.github._1c_syntax.bsl.mdo;

import com.github._1c_syntax.bsl.mdo.children.ObjectAttribute;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.mdo.children.TaskAddressingAttribute;
import com.github._1c_syntax.bsl.mdo.support.AttributeKind;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.test_utils.AbstractMDObjectTest;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTest extends AbstractMDObjectTest<Task> {
  TaskTest() {
    super(Task.class);
  }

  @ParameterizedTest(name = "DESIGNER {index}: {0}")
  @CsvSource(
    {
      "Задача1,c251fcec-ec02-4ef4-8f70-4d70db6631ea,,,Task,Задача,1,0,0,0,0,0"
    }
  )
  void testDesigner(ArgumentsAccessor argumentsAccessor) {
    var mdo = getMDObject("Tasks/" + argumentsAccessor.getString(0));
    mdoTest(mdo, MDOType.TASK, argumentsAccessor);
    assertThat(mdo.getAttributes()).hasSize(1);
    var attribute = mdo.getAttributes().get(0);
    assertThat(attribute).isInstanceOf(TaskAddressingAttribute.class);
    assertThat(attribute.getKind()).isEqualTo(AttributeKind.CUSTOM);
    assertThat(attribute.getName()).isEqualTo("РеквизитАдресации");
    assertThat(attribute.getType()).isEqualTo(MDOType.ATTRIBUTE);
    assertThat(attribute.isPasswordMode()).isFalse();
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
    assertThat(attribute.getUuid()).isEqualTo("bf531047-7ec7-4c74-bfdb-917138b2127c");
    assertThat(attribute.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(attribute.getMetadataName()).isEqualTo("AddressingAttribute");
    assertThat(attribute.getSynonyms().getContent()).isEmpty();
    assertThat(attribute.getMdoReference().getMdoRef()).isEqualTo("Task.Задача1.AddressingAttribute.РеквизитАдресации");

  }

  @ParameterizedTest(name = "EDT {index}: {0}")
  @CsvSource(
    {
      "Задача1,c251fcec-ec02-4ef4-8f70-4d70db6631ea,,,Task,Задача,2,1,1,1,1,1"
    }
  )
  void testEdt(ArgumentsAccessor argumentsAccessor) {
    var name = argumentsAccessor.getString(0);
    var mdo = getMDObjectEDT("Tasks/" + name + "/" + name);
    mdoTest(mdo, MDOType.TASK, argumentsAccessor);

    checkAttributeField(mdo.getAttributes().get(0),
      "Реквизит", "9ec8c522-8479-4733-b120-699dc9fe6018");

    checkChildField(mdo.getForms().get(0),
      "Форма", "d7cd2a53-f2a3-4c49-bf19-7762b0565a42");

    checkChildField(mdo.getTemplates().get(0),
      "Макет", "187ca6a7-be26-48e0-8b48-1fb79a797c0d");

    checkChildField(mdo.getCommands().get(0),
      "Команда", "7eab1b3a-f23c-4185-9b81-a5c757c5507c");

    checkChildField(mdo.getTabularSections().get(0),
      "ТабличнаяЧасть", "8b67da9e-2ce2-42db-a12a-d21f9a563afe");

    var attribute = mdo.getAttributes().get(1);
    assertThat(attribute).isInstanceOf(TaskAddressingAttribute.class);
    assertThat(attribute.getKind()).isEqualTo(AttributeKind.CUSTOM);
    assertThat(attribute.getName()).isEqualTo("РеквизитАдресации");
    assertThat(attribute.getType()).isEqualTo(MDOType.ATTRIBUTE);
    assertThat(attribute.isPasswordMode()).isFalse();
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
    assertThat(attribute.getUuid()).isEqualTo("bf531047-7ec7-4c74-bfdb-917138b2127c");
    assertThat(attribute.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(attribute.getMetadataName()).isEqualTo("AddressingAttribute");
    assertThat(attribute.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Реквизит адресации"));
    assertThat(attribute.getMdoReference().getMdoRef()).isEqualTo("Task.Задача1.AddressingAttribute.РеквизитАдресации");

    attribute = mdo.getAttributes().get(0);
    assertThat(attribute).isInstanceOf(ObjectAttribute.class);
    assertThat(attribute.getKind()).isEqualTo(AttributeKind.CUSTOM);
    assertThat(attribute.getName()).isEqualTo("Реквизит");
    assertThat(attribute.getType()).isEqualTo(MDOType.ATTRIBUTE);
    assertThat(attribute.isPasswordMode()).isTrue();
    assertThat(attribute.getIndexing()).isEqualTo(IndexingType.DONT_INDEX);
    assertThat(attribute.getUuid()).isEqualTo("9ec8c522-8479-4733-b120-699dc9fe6018");
    assertThat(attribute.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(attribute.getMetadataName()).isEqualTo("Attribute");
    assertThat(attribute.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Реквизит"));
    assertThat(attribute.getMdoReference().getMdoRef()).isEqualTo("Task.Задача1.Attribute.Реквизит");

    var tabularSection = mdo.getTabularSections().get(0);
    assertThat(tabularSection.getType()).isEqualTo(MDOType.ATTRIBUTE);
    assertThat(tabularSection.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(tabularSection.getMetadataName()).isEqualTo("TabularSection");
    assertThat(tabularSection.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Табличная часть"));
    assertThat(tabularSection.getMdoReference().getMdoRef()).isEqualTo("Task.Задача1.TabularSection.ТабличнаяЧасть");
    assertThat(tabularSection.getAttributes()).hasSize(1);

    var module = mdo.getModules().get(0);
    assertThat(module).isInstanceOf(ObjectModule.class);
    assertThat(module.getModuleType()).isEqualTo(ModuleType.ManagerModule);
    assertThat(module.getUri().toString()).endsWith("ManagerModule.bsl");
    assertThat(((ObjectModule) module).getOwner()).isEqualTo(mdo);

    var form = mdo.getForms().get(0);
    assertThat(form).isInstanceOf(ObjectForm.class);
    assertThat(form.getFormType()).isEqualTo(FormType.MANAGED);
    assertThat(form.getName()).isEqualTo("Форма");
    assertThat(form.getType()).isEqualTo(MDOType.FORM);
    assertThat(form.getUuid()).isEqualTo("d7cd2a53-f2a3-4c49-bf19-7762b0565a42");
    assertThat(form.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(form.getMetadataName()).isEqualTo("Form");
    assertThat(form.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Форма"));
    assertThat(form.getMdoReference().getMdoRef()).isEqualTo("Task.Задача1.Form.Форма");
    assertThat(form.getModules()).hasSize(1);

    var command = mdo.getCommands().get(0);
    assertThat(command).isInstanceOf(ObjectCommand.class);
    assertThat(command.getName()).isEqualTo("Команда");
    assertThat(command.getType()).isEqualTo(MDOType.COMMAND);
    assertThat(command.getUuid()).isEqualTo("7eab1b3a-f23c-4185-9b81-a5c757c5507c");
    assertThat(command.getObjectBelonging()).isEqualTo(ObjectBelonging.OWN);
    assertThat(command.getMetadataName()).isEqualTo("Command");
    assertThat(command.getSynonyms().getContent())
      .hasSize(1)
      .contains(Map.entry("ru", "Команда"));
    assertThat(command.getMdoReference().getMdoRef()).isEqualTo("Task.Задача1.Command.Команда");
    assertThat(command.getModules()).hasSize(1);
  }
}

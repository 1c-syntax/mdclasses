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

import com.github._1c_syntax.mdclasses.mdo.children.form.DynamicListExtInfo;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormAttribute;
import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FormDataTest {

  @Test
  void testEDT() {
    var path = Path.of("src/test/resources/metadata/formdata/edt/ЖурналРегистрации/Form.form");
    var formDataEDT = MDOFactory.readFormData(path).get();
    checkFormData(formDataEDT);
  }

  @Test
  void testDesigner() {
    var path = Path.of("src/test/resources/metadata/formdata/original/ЖурналРегистрации/Ext/Form.xml");
    var formDataOrigin = MDOFactory.readFormData(path).get();
    checkFormData(formDataOrigin);
  }

  @Test
  void testBrokenForm() {
    var path = Path.of("src/test/resources/metadata/original_broken/Form/Form.xml");
    var dataOptional = MDOFactory.readFormData(path);
    assertThat(dataOptional).isPresent();

    var formData = dataOptional.get();
    var command = formData.getCommands().stream()
      .filter(formCommand -> formCommand.getId() == 800)
      .findAny();

    assertThat(command).isPresent();
    assertThat(command.get().getAction()).isEqualTo("ЗаявкиНаСогласованиеУправленческойОтсрочкиПосле");

  }

  @Test
  void testDynamicList() {
    var path = Path.of("src/test/resources/metadata/formdata/dynamic_list/edt/Form.form");
    var formData = MDOFactory.readFormData(path).get();
    checkExtInfo(formData);

    path = Path.of("src/test/resources/metadata/formdata/dynamic_list/original/Form.xml");
    formData = MDOFactory.readFormData(path).get();
    checkExtInfo(formData);
  }

  private void checkExtInfo(FormData formData) {
    var extInfo = (DynamicListExtInfo) formData.getAttributes().get(1).getExtInfo();
    assertThat(extInfo)
      .isNotNull()
      .isInstanceOf(DynamicListExtInfo.class);

    assertThat(extInfo.isCustomQuery()).isTrue();
    assertThat(extInfo.getQuery().getTextQuery()).isNotEmpty();
  }

  private void checkFormData(FormData formData) {
    assertThat(formData.getPlainChildren())
      .hasSize(133)
      .anyMatch(formItem -> formItem.getName().equals("ГруппаОтбора") && formItem.getId() == 103);

    var item = formData.getPlainChildren().stream()
      .filter(formItem -> formItem.getName().equals("Критичность")).findAny().get();

    assertThat(item.getDataPath()).isNotNull();
    assertThat(item.getDataPath().getSegment()).isEqualTo("Критичность");
    assertThat(item.getHandlers())
      .hasSizeGreaterThan(0)
      .anyMatch(handler -> handler.getName().equals("КритичностьПриИзменении") && handler.getEvent().equals("OnChange"));

    var findEmptyType = formData.getPlainChildren().stream()
      .anyMatch(formItem -> formItem.getType().isEmpty());
    assertThat(findEmptyType).isFalse();

    assertThat(formData.getChildren())
      .hasSize(3)
      .anyMatch(formItem -> formItem.getName().equals("ГруппаОтбора")
        && formItem.getId() == 103
        && formItem.getChildren().size() == 4);

    assertThat(formData.getHandlers())
      .hasSize(3)
      .anyMatch(handler -> handler.getEvent().equals("ChoiceProcessing")
        && handler.getName().equals("ОбработкаВыбора"));

    assertThat(formData.getAttributes()).hasSize(12)
      .anyMatch(attribute -> attribute.getName().equals("Объект") && attribute.getId() == 1 && attribute.isMain())
      .anyMatch(attribute -> attribute.getName().equals("Журнал") && attribute.getId() == 4 && !attribute.isMain());

    FormAttribute attribute = formData.getAttributes().stream()
      .filter(formAttribute -> formAttribute.getName().equals("Журнал")).findAny().get();

    assertThat(attribute.getChildren()).hasSize(26)
      .anyMatch(formAttribute -> formAttribute.getName().equals("ВспомогательныйIPПорт"));

    attribute = formData.getAttributes().stream()
      .filter(formAttribute -> formAttribute.getName().equals("Объект"))
      .findAny().get();

    assertThat(attribute.getValueTypes()).hasSize(1);
    assertThat(attribute.getValueTypes().get(0)).isEqualTo("DataProcessorObject.ЖурналРегистрации");

    assertThat(formData.getCommands()).hasSize(8)
      .anyMatch(formCommand -> formCommand.getName().equals("ВыгрузитьЖурналДляПередачиВТехподдержку")
        && formCommand.getAction().equals("ВыгрузитьЖурналДляПередачиВТехподдержку"));

  }

}

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

import com.github._1c_syntax.mdclasses.mdo.wrapper.form.DesignerForm;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class FormDataTest {

  @Test
  void testEDT() {
    var path = Path.of("src/test/resources/metadata/formdata/edt/ЖурналРегистрации/Form.form");
    FormData formDataEDT = (FormData) XStreamFactory.fromXML(path.toFile());
    formDataEDT.fillPlainChildren(formDataEDT.getChildren());
    checkFormData(formDataEDT);
  }

  @Test
  void testDesigner() {
    var path = Path.of("src/test/resources/metadata/formdata/original/ЖурналРегистрации/Ext/Form.xml");
    var designerForm = (DesignerForm) XStreamFactory.fromXML(path.toFile());
    FormData formDataOrigin = new FormData(designerForm);
    checkFormData(formDataOrigin);
  }

  private void checkFormData(FormData formData) {
    assertThat(formData.getPlainChildren())
      .hasSize(127)
      .anyMatch(formItem -> formItem.getName().equals("ГруппаОтбора") && formItem.getId() == 103);

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
      .anyMatch(attribute -> attribute.getName().equals("Объект") && attribute.getId() == 1 && attribute.isMain());
  }

}

/*
 * This file is a part of MDClasses.
 *
 * Copyright (c) 2019 - 2022
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
package com.github._1c_syntax.mdclasses.mdo.children.form;

import com.github._1c_syntax.mdclasses.unmarshal.converters.CommandActionConverter;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerFormCommand;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FormCommand {
  private String name;
  // TODO: title
  private int id;
  @XStreamConverter(value = CommandActionConverter.class)
  private String action = "";

  public FormCommand(DesignerFormCommand formCommand) {
    setName(formCommand.getName());
    setId(formCommand.getId());
    if (!formCommand.getActions().isEmpty()) {
      setAction(formCommand.getActions().get(0));
    }
  }
}

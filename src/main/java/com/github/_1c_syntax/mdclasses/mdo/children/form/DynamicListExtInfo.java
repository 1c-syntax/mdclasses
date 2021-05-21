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
package com.github._1c_syntax.mdclasses.mdo.children.form;

import com.github._1c_syntax.mdclasses.mdo.support.QuerySource;
import com.github._1c_syntax.mdclasses.unmarshal.converters.QuerySourceConverter;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerAttributeSetting;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class DynamicListExtInfo extends ExtInfo {
  // TODO: MDORef
  private String mainTable = "";
  private boolean dynamicDataRead = true;
  private boolean autoFillAvailableFields = true;
  private boolean autoSaveUserSettings = true;
  private boolean getInvisibleFieldPresentations = true;
  private boolean customQuery;
  @XStreamAlias("queryText")
  @XStreamConverter(QuerySourceConverter.class)
  private QuerySource query;

  public DynamicListExtInfo(DesignerAttributeSetting setting) {
    setMainTable(setting.getMainTable());
    setDynamicDataRead(setting.isDynamicDataRead());
    setCustomQuery(setting.isCustomQuery());
    setQuery(setting.getQuery());
    setGetInvisibleFieldPresentations(setting.isGetInvisibleFieldPresentations());
    setAutoSaveUserSettings(setting.isAutoSaveUserSettings());
  }
}

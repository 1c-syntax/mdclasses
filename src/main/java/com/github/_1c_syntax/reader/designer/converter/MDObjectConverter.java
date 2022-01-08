/*
 * This file is a part of MDClasses.
 *
 * Copyright © 2019 - 2022
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
package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.CommonModule;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.Role;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.XdtoPackage;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.designer.DesignerPaths;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.reader.designer.wrapper.DesignerProperties;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@DesignerConverter
@Slf4j
public class MDObjectConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var designerProperties = new DesignerProperties(reader, context);

    if (XdtoPackage.class.isAssignableFrom(designerProperties.getRealClass())) {
      var dataPath = DesignerPaths.packageDataPath(designerProperties.getCurrentPath(),
        designerProperties.getName());
      readXDTOPackageData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
    } else if (Role.class.isAssignableFrom(designerProperties.getRealClass())) {
      var dataPath = DesignerPaths.roleDataPath(designerProperties.getCurrentPath(),
        designerProperties.getName());
      readData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
    } else if (Template.class.isAssignableFrom(designerProperties.getRealClass())) {
      if (designerProperties.getProperties().get("TemplateType") == TemplateType.DATA_COMPOSITION_SCHEME) {
        var dataPath = DesignerPaths.templateDataPath(designerProperties.getCurrentPath(),
          designerProperties.getName());
        readData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
        designerProperties.getProperties().put("templateDataPath", dataPath);
      }
    } else if (ExchangePlan.class.isAssignableFrom(designerProperties.getRealClass())) {
      var dataPath = DesignerPaths.exchangePlanContentPath(designerProperties.getCurrentPath(),
        designerProperties.getName());
      readData(dataPath).ifPresent(designerProperties.getProperties()::putAll);
    } else if (CommonModule.class.isAssignableFrom(designerProperties.getRealClass())) {
      var modules = designerProperties.getProperties().get("modules");
      if (modules instanceof List && !((List<?>) modules).isEmpty()) {
        var module = (Module) ((List<?>) modules).get(0);
        designerProperties.getProperties().put("uri", module.getUri());
      }
    }

    if (MDOType.valuesWithoutChildren().contains(designerProperties.getMdoType())) {
      return designerProperties.computeAndBuild();
    } else {
      return designerProperties;
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return MDObject.class.isAssignableFrom(type);
  }

  private Optional<Map> readXDTOPackageData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of(Map.of("data", DesignerXStreamFactory.fromXML(path.toFile())));
  }

  private Optional<Map> readData(Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    return Optional.of((Map) DesignerXStreamFactory.fromXML(path.toFile()));
  }
}

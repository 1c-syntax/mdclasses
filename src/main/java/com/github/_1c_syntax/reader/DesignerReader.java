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
package com.github._1c_syntax.reader;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdclasses.MDClasses;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.designer.DesignerPaths;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.supconf.ParseSupportData;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;

public class DesignerReader implements MDReader {
  private final Path rootPath;

  public DesignerReader(Path path) {
    this(path, false);
  }

  public DesignerReader(Path path, boolean skipSupport) {
    rootPath = path;
    if (!skipSupport) {
      ParseSupportData.readSimple(DesignerPaths.parentConfigurationsPath(rootPath));
    }
  }

  @Override
  public MDClass readConfiguration() {
    var mdc = readConfiguration(
      DesignerPaths.mdoPath(rootPath, MDOType.CONFIGURATION, MDOType.CONFIGURATION.getName())
    );
    return mdc.orElse(MDClasses.createConfiguration());
  }

  @Override
  @Nullable
  public MDObject readMDObject(String fullName) {
    var dotPosition = fullName.indexOf('.');
    var type = MDOType.fromValue(fullName.substring(0, dotPosition));
    var name = fullName.substring(dotPosition + 1);

    if (type.isPresent()) {
      var path = DesignerPaths.mdoPath(rootPath, type.get(), name);
      return (MDObject) DesignerXStreamFactory.fromXML(path.toFile());
    }
    return null;
  }

  private static Optional<MDClass> readConfiguration(Path path) {
    return Optional.ofNullable((MDClass) DesignerXStreamFactory.fromXML(path.toFile()));
  }
}

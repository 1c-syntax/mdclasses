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
package com.github._1c_syntax.reader.designer.wrapper;

import com.github._1c_syntax.bsl.mdclasses.MDClass;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.Module;
import com.github._1c_syntax.bsl.mdo.Sequence;
import com.github._1c_syntax.bsl.mdo.Subsystem;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectModule;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.children.RegisterDimension;
import com.github._1c_syntax.bsl.mdo.children.SequenceDimension;
import com.github._1c_syntax.bsl.mdo.support.ApplicationUsePurpose;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.bsl.types.ModuleType;
import com.github._1c_syntax.reader.common.TransformationUtils;
import com.github._1c_syntax.reader.designer.DesignerPaths;
import com.github._1c_syntax.reader.designer.DesignerXStreamFactory;
import com.github._1c_syntax.supconf.ParseSupportData;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Data
@Slf4j
public class DesignerProperties {

  private static final String PROPERTIES_NODE_NAME = "Properties";
  private static final String CHILD_OBJECTS_NODE_NAME = "ChildObjects";
  private static final String DIMENSION_NODE_NAME = "Dimension";
  private static final String NAME_PROPERTY = "Name";
  private static final String UUID_PROPERTY = "uuid";
  private static final String OWNER_PROPERTY = "owner";
  private static final String MODULES_PROPERTY = "modules";

  private Map<String, Object> properties;
  private Map<String, Object> unknownProperties;
  private List<Object> children;

  private Class<?> realClass;
  private Object builder;

  private MDOType mdoType;
  private Path currentPath;

  private String name;
  private SupportVariant supportVariant;

  private MdoReference mdoReference;

  public DesignerProperties(@NonNull HierarchicalStreamReader reader, @NonNull UnmarshallingContext context) {
    this(reader, context, null, null);
  }

  public DesignerProperties(@NonNull HierarchicalStreamReader reader,
                            @NonNull UnmarshallingContext context,
                            @Nullable Class<?> clazz,
                            @Nullable MDOType type) {

    properties = new HashMap<>();
    unknownProperties = new HashMap<>();
    children = new ArrayList<>();

    currentPath = DesignerXStreamFactory.getCurrentPath(reader);

    if (clazz == null) {
      mdoType = getMdoType(reader);
      var realClassName = reader.getNodeName();
      if (DIMENSION_NODE_NAME.equals(realClassName)) {
        var mdoTypeName = DesignerXStreamFactory.getCurrentPath(reader).getParent().getFileName().toString();
        var parentMDOType = MDOType.fromValue(mdoTypeName);
        if (parentMDOType.isPresent() && parentMDOType.get() == MDOType.SEQUENCE) {
          realClass = SequenceDimension.class;
          mdoType = MDOType.SEQUENCE_DIMENSION;
        } else {
          realClass = RegisterDimension.class;
          mdoType = MDOType.REGISTER_DIMENSION;
        }
      } else {
        realClass = DesignerXStreamFactory.getRealClass(realClassName);
      }
    } else {
      realClass = clazz;
      requireNonNull(type);
      mdoType = type;
    }

    builder = TransformationUtils.builder(realClass);
    requireNonNull(builder);

    var uuid = reader.getAttribute(UUID_PROPERTY);

    properties.put(UUID_PROPERTY, uuid);
    supportVariant = ParseSupportData.getSupportVariantByMDO(uuid, currentPath);

    readFile(reader, context);

    readModules();
  }


  public Object computeAndBuild() {
    properties.forEach((key, value) -> TransformationUtils.setValue(builder, key, value));
    TransformationUtils.setValue(builder, "mdoReference", mdoReference);
    TransformationUtils.setValue(builder, "mdoType", mdoType);
    TransformationUtils.setValue(builder, "supportVariant", supportVariant);
    TransformationUtils.setValue(builder, "children", children);

    return TransformationUtils.build(builder);
  }

  private void readFile(HierarchicalStreamReader reader, UnmarshallingContext context) {
    // линейно читаем файл
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();
      switch (nodeName) {
        case PROPERTIES_NODE_NAME:
          readProperties(reader, context);

          name = (String) properties.get(NAME_PROPERTY);
          mdoReference = MdoReference.create(mdoType, name);

          break;
        case CHILD_OBJECTS_NODE_NAME:
          if (MDClass.class.isAssignableFrom(realClass)) {
            readChildrenMDC(reader);
            break;
          }

          readChildren(reader, context);
          if (MDOType.valuesWithoutChildren().contains(mdoType)) {
            buildWithChildren();
          }
          break;
        default:
          // no-op
          break;
      }

      reader.moveUp();
    }
  }

  private void updateOwner(MdoReference owner) {
    setMdoReference(MdoReference.create(owner, mdoType, name));
    properties.put(OWNER_PROPERTY, owner);

    // если есть дочерние, им тоже стоит обновиться
    children.stream().filter(DesignerProperties.class::isInstance)
      .forEach(child -> ((DesignerProperties) child).updateOwner(mdoReference));
  }

  private Object buildWithChildren() {
    if (!children.isEmpty()) {
      var correctChildren = children.stream()
        .filter(MDObject.class::isInstance)
        .collect(Collectors.toList());

      if (correctChildren.size() != children.size()) {
        correctChildren.addAll(children.stream()
          .filter(DesignerProperties.class::isInstance)
          .map(DesignerProperties.class::cast)
          .map(DesignerProperties::buildWithChildren)
          .collect(Collectors.toList()));

        setChildren(correctChildren);
      }
    }

    if (!MDOType.valuesWithoutChildren().contains(mdoType)) {
      return computeAndBuild();
    }

    // эти билдятся отдельно
    return this;
  }

  private void readProperties(HierarchicalStreamReader reader, UnmarshallingContext context) {
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var propertyName = reader.getNodeName();
      var propertyValue = reader.getValue();
      var value = readValue(reader, context, propertyName);
      if (value != null) {
        properties.put(propertyName, value);
      } else {
        unknownProperties.put(propertyName, propertyValue);
      }
      reader.moveUp();
    }
  }

  private void readChildren(HierarchicalStreamReader reader, UnmarshallingContext context) {

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var nodeName = reader.getNodeName();

      var childRealClass = getChildRealClass(nodeName);
      if (childRealClass == null) {
        // для неизвестных типов делаем пропуск, они останутся в логах
        reader.moveUp();
        break;
      }
      var child = readChild(reader, context, childRealClass);

      if (child instanceof DesignerProperties) {
        // нужно обновить ссылки на родителя
        ((DesignerProperties) child).updateOwner(mdoReference);
      }

      children.add(child);
      reader.moveUp();
    }
  }

  private void readChildrenMDC(HierarchicalStreamReader reader) {
    List<String> childrenNames = new ArrayList<>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      childrenNames.add(reader.getNodeName() + "." + reader.getValue());
      reader.moveUp();
    }

    properties.put("childrenNames", childrenNames);
  }

  private Class<?> getChildRealClass(String nodeName) {
    Class<?> childRealClass;
    if (DIMENSION_NODE_NAME.equals(nodeName)) {
      if (Sequence.class.isAssignableFrom(realClass)) {
        childRealClass = SequenceDimension.class;
      } else {
        childRealClass = RegisterDimension.class;
      }
    } else {
      try {
        childRealClass = DesignerXStreamFactory.getRealClass(nodeName);
      } catch (CannotResolveClassException e) {
        LOGGER.error("Cannot resolve class {}, file {}:\n", nodeName, currentPath, e);
        childRealClass = null;
      }
    }
    return childRealClass;
  }

  private Object readChild(HierarchicalStreamReader reader, UnmarshallingContext context, Class<?> childRealClass) {
    Object child;
    if (ObjectTemplate.class.isAssignableFrom(childRealClass)) {
      child = readChild(reader, MDOType.TEMPLATE);
    } else if (ObjectForm.class.isAssignableFrom(childRealClass)) {
      child = readChild(reader, MDOType.FORM);
    } else if (Subsystem.class.isAssignableFrom(childRealClass)) {
      child = readChild(reader, MDOType.SUBSYSTEM);
    } else if (Recalculation.class.isAssignableFrom(childRealClass)) {
      child = readChild(reader, MDOType.RECALCULATION);
    } else {
      child = context.convertAnother(reader, childRealClass);
    }
    return child;
  }

  private Object readChild(HierarchicalStreamReader reader, MDOType type) {
    var mdoFolderPath = DesignerPaths.childrenFolder(currentPath, type);
    if (!mdoFolderPath.toFile().exists()) {
      throw new IllegalArgumentException("Missing folder " + type.getGroupName());
    }

    var childPath = DesignerPaths.mdoPath(mdoFolderPath, reader.getValue());
    return DesignerXStreamFactory.fromXML(childPath.toFile());
  }

  private void readModules() {
    Path folder;
    if (!MDOType.valuesWithoutChildren().contains(mdoType)) {
      folder = DesignerPaths.childrenFolder(currentPath, mdoType);
    } else {
      folder = DesignerPaths.mdoTypeFolderPathByMDOPath(currentPath);
    }

    if (!folder.toFile().exists()) {
      return;
    }

    var moduleTypes = ModuleType.byMDOType(mdoType);
    if (moduleTypes.isEmpty()) {
      return;
    }

    List<Module> modules = new ArrayList<>();
    moduleTypes.forEach((ModuleType moduleType) -> {
        var modulePath = DesignerPaths.modulePath(folder, name, moduleType);

        if (modulePath.toFile().exists()) {
          modules.add(ObjectModule.builder()
            .moduleType(moduleType)
            .uri(modulePath.toUri())
            .owner(mdoReference)
            .supportVariant(supportVariant)
            .build());
        }
      }
    );
    properties.put(MODULES_PROPERTY, modules);
  }

  @Nullable
  private Object readValue(HierarchicalStreamReader reader, UnmarshallingContext context, String propertyName) {
    var methodType = TransformationUtils.fieldType(builder, propertyName);
    if (methodType == null) {
      return null;
    }

    Object value;

    if (methodType instanceof ParameterizedType
      && ApplicationUsePurpose.class
      .isAssignableFrom((Class<?>) ((ParameterizedType) methodType)
        .getActualTypeArguments()[0])) {

      value = context.convertAnother(reader, (Class<?>) ((ParameterizedType) methodType)
        .getActualTypeArguments()[0]);

    } else if (methodType instanceof ParameterizedType) {
      List<Object> values = new ArrayList<>();
      var clazz = (Class<?>) ((ParameterizedType) methodType)
        .getActualTypeArguments()[0];
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        try {
          values.add(context.convertAnother(reader, clazz));
        } catch (ConversionException e) {
          LOGGER.error("Parsing error, file {}:\n", currentPath, e);
        }
        reader.moveUp();
      }
      value = values;
    } else {
      value = context.convertAnother(reader, (Class<?>) methodType);
    }

    return value;
  }

  private static MDOType getMdoType(HierarchicalStreamReader reader) {
    var type = MDOType.UNKNOWN;
    var computedMdoType = MDOType.fromValue(reader.getNodeName());
    if (computedMdoType.isPresent()) {
      type = computedMdoType.get();
    }
    return type;
  }

}

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

import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerChildObjects;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Базовый класс для сложных объектов 1с, т.е. имеющих дочерние объекты
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class MDObjectComplex extends MDObjectBSL {

  /**
   * Подчиненные формы
   */
  @NonNull
  @XStreamImplicit
  List<Form> forms = Collections.emptyList();

  /**
   * Подчиненные макеты
   */
  @NonNull
  @XStreamImplicit
  List<Template> templates = Collections.emptyList();

  /**
   * Подчиненные команды
   */
  @NonNull
  @XStreamImplicit
  List<Command> commands = Collections.emptyList();

  /**
   * Реквизиты, табличные части и их реквизиты объекта
   */
  @NonNull
  @XStreamImplicit
  List<MDOAttribute> attributes = Collections.emptyList();

  public MDObjectComplex(DesignerMDO designerMDO) {
    super(designerMDO);

    // формирование mdo ссылки, которая будет использована в дочерних объектах
    setMdoReference(new MDOReference(this));

    // для конфигуратора необходимо прочитать дочерние из каталога рядом
    MDOPathUtils.getMDOTypeFolderByMDOPath(ConfigurationSource.DESIGNER, designerMDO.getMdoPath())
      .ifPresent(mdoFolder -> {
        computeForms(mdoFolder, designerMDO.getChildObjects().getForms());
        computeTemplates(mdoFolder, designerMDO.getChildObjects().getTemplates());
      });

    // эти данные лежат сразу в файле описания,
    computeCommands(designerMDO.getChildObjects().getCommands());
    computeChildren(designerMDO.getChildObjects());
  }

  private void computeForms(Path folder, List<String> formNames) {
    MDOPathUtils.getChildrenFolder(getName(), folder, MDOType.FORM)
      .ifPresent(childrenFolder ->
        setForms(readDesignerMDOChildren(childrenFolder, MDOType.FORM, Form.class, formNames)));
  }

  private void computeTemplates(Path folder, List<String> templateNames) {
    MDOPathUtils.getChildrenFolder(getName(), folder, MDOType.TEMPLATE)
      .ifPresent(childrenFolder ->
        setTemplates(readDesignerMDOChildren(childrenFolder, MDOType.TEMPLATE, Template.class, templateNames)));
  }

  private void computeCommands(List<DesignerMDO> commandsDesigner) {
    List<Command> computedCommands = new ArrayList<>();
    commandsDesigner.forEach(designerMDO -> computedCommands.add(new Command(designerMDO)));
    setCommands(computedCommands);
  }

  private void computeChildren(DesignerChildObjects childObjects) {
    List<MDOAttribute> computedAttributes = new ArrayList<>();
    childObjects.getAccountingFlags().forEach(designerMDO -> computedAttributes.add(new AccountingFlag(designerMDO)));
    childObjects.getAddressingAttributes().forEach(designerMDO ->
      computedAttributes.add(new AddressingAttribute(designerMDO)));
    childObjects.getAttributes().forEach(designerMDO -> computedAttributes.add(new Attribute(designerMDO)));
    childObjects.getColumns().forEach(designerMDO -> computedAttributes.add(new Column(designerMDO)));
    childObjects.getDimensions().forEach(designerMDO -> computedAttributes.add(new Dimension(designerMDO)));
    childObjects.getExtDimensionAccountingFlags().forEach(designerMDO ->
      computedAttributes.add(new ExtDimensionAccountingFlag(designerMDO)));
    childObjects.getRecalculations().forEach(designerMDO -> computedAttributes.add(new Recalculation(designerMDO)));
    childObjects.getResources().forEach(designerMDO -> computedAttributes.add(new Resource(designerMDO)));
    childObjects.getTabularSections().forEach(designerMDO -> computedAttributes.add(new TabularSection(designerMDO)));
    setAttributes(computedAttributes);
  }

  private <T extends MDObjectBase> List<T> readDesignerMDOChildren(Path childrenFolder,
                                                                   MDOType type,
                                                                   Class<T> childClass,
                                                                   List<String> childNames) {
    List<T> children = new ArrayList<>();
    var configurationSource = ConfigurationSource.DESIGNER;
    if (childrenFolder != null) {
      getMDOFilesInFolder(childrenFolder, childNames)
        .forEach(mdoFile -> {
          var child = MDOFactory.readMDObjectFromFile(configurationSource, type, mdoFile);
          child.ifPresent(mdo -> {
              mdo.setMdoReference(new MDOReference(mdo, this));
              children.add(childClass.cast(mdo));
            }
          );
        });
    }
    return children;
  }

  @SneakyThrows
  private List<Path> getMDOFilesInFolder(Path folder, List<String> childNames) {
    List<Path> childrenNames = new ArrayList<>();
    if (folder.toFile().exists()) {
      int maxDepth = 1;
      AtomicReference<String> extension = new AtomicReference<>(MDOPathUtils.mdoExtension(
        ConfigurationSource.DESIGNER, true));
      try (Stream<Path> files = Files.walk(folder, maxDepth)) {
        childrenNames = files
          .parallel()
          .filter(f -> f.toString().endsWith(extension.get()))
          .filter(f -> childNames.contains(FilenameUtils.getBaseName(f.toString())))
          .collect(Collectors.toList());
      }
    }

    return childrenNames;
  }
}

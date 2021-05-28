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

import com.github._1c_syntax.mdclasses.mdo.metadata.Metadata;
import com.github._1c_syntax.mdclasses.mdo.support.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerMDO;
import com.github._1c_syntax.mdclasses.utils.MDOFactory;
import com.github._1c_syntax.mdclasses.utils.MDOPathUtils;
import com.github._1c_syntax.mdclasses.utils.MDOUtils;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import io.vavr.control.Either;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Metadata(
  type = MDOType.SUBSYSTEM,
  name = "Subsystem",
  nameRu = "Подсистема",
  groupName = "Subsystems",
  groupNameRu = "Подсистемы"
)
public class MDSubsystem extends AbstractMDObjectBase {

  /**
   * Дочерние объекты подсистемы, включает в себя как дочерние подсистемы, так и и другие объекты,
   * включенные в подсистему
   * Для объектов, которые не удалось прочитать (при загрузке конфигурации) хранит только строки
   */
  @XStreamImplicit
  private List<Either<String, AbstractMDObjectBase>> children = Collections.emptyList();

  /**
   * Признак "Включать в командный интерфейс"
   */
  private boolean includeInCommandInterface;

  public MDSubsystem(DesignerMDO designerMDO) {
    super(designerMDO);
    List<Either<String, AbstractMDObjectBase>> newChildren = new ArrayList<>();
    designerMDO.getProperties().getContent()
      .getItems().forEach(item -> newChildren.add(Either.left(item.getValue())));
    includeInCommandInterface = designerMDO.getProperties().isIncludeInCommandInterface();
    newChildren.addAll(designerMDO.getChildObjects().getChildren());
    setChildren(newChildren);
  }

  @Override
  public void supplement() {
    super.supplement();
    computeSubsystemChildren();
  }

  @Override
  public void supplement(AbstractMDObjectBase parent) {
    super.supplement(parent);
    computeSubsystemChildren();
  }

  public void linkToChildren(Map<String, AbstractMDObjectBase> allMDO) {
    List<Either<String, AbstractMDObjectBase>> localChildren = new ArrayList<>();

    children.forEach((Either<String, AbstractMDObjectBase> mdoPair) -> {
      if (mdoPair.isLeft()) {
        var mdo = allMDO.get(mdoPair.getLeft());
        if (mdo != null) {
          localChildren.add(Either.right(mdo));
          setSubsystemForChild(allMDO, mdo);
        } else {
          localChildren.add(mdoPair);
        }
      } else {
        var mdo = mdoPair.get();
        if (!mdo.getIncludedSubsystems().contains(this)) {
          localChildren.add(mdoPair);
          setSubsystemForChild(allMDO, mdo);
        }
      }
    });

    setChildren(localChildren);
  }

  private void computeSubsystemChildren() {
    if (children.isEmpty()) {
      return;
    }

    var configurationSource = MDOUtils.getConfigurationSourceByMDOPath(getPath());
    var rootFolder = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, getPath());
    if (rootFolder.isEmpty()) {
      return;
    }

    List<Either<String, AbstractMDObjectBase>> newChildren = new ArrayList<>();
    var folder = Paths.get(rootFolder.get().toString(), getName(), MDOType.SUBSYSTEM.getGroupName());
    final var startName = MDOType.SUBSYSTEM.getName() + ".";

    children.stream() // параллелизм нельзя!!!! может произойти чтение подсистем из одного фала разными потоками
      .filter(Either::isLeft)
      .filter((Either<String, AbstractMDObjectBase> child) -> child.getLeft().startsWith(startName)
        && !child.getLeft().contains("-")) // для исключения битых ссылок сразу

      .forEach((Either<String, AbstractMDObjectBase> child) -> {
        // для обработки имен Subsystem._ДемоСервисныеПодсистемы.Subsystem._ДемоПолучениеФайловИзИнтернета
        var subsystemObjectLastPosition = child.getLeft().lastIndexOf(startName);
        var subsystemName = child.getLeft().substring(subsystemObjectLastPosition + startName.length());

        MDOPathUtils.getMDOPath(configurationSource, folder, subsystemName)
          .ifPresent((Path mdoPath) -> {
            var childSubsystem = MDOFactory.readMDO(mdoPath);
            if (childSubsystem != null) {
              childSubsystem.supplement(this);
              newChildren.add(Either.right(childSubsystem));
            } else {
              if (!child.getLeft().equals(getMdoReference().getMdoRef())) {
                // ссылку на самого себя исключаем
                // вернем несуществующий объект обратно в набор
                newChildren.add(child);
              }
            }
          });
      });

    newChildren.addAll(children.stream()
      .filter(Either::isLeft)
      .filter((Either<String, AbstractMDObjectBase> child) -> !child.getLeft().startsWith(startName))
      .collect(Collectors.toList()));

    setChildren(newChildren);
  }

  private void setSubsystemForChild(Map<String, AbstractMDObjectBase> allMDO, AbstractMDObjectBase mdo) {
    mdo.addIncludedSubsystem(this);
    if (mdo instanceof MDSubsystem) {
      ((MDSubsystem) mdo).linkToChildren(allMDO);
    }
  }
}

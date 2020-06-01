package com.github._1c_syntax.mdclasses.utils;

import com.github._1c_syntax.mdclasses.mdo.MDOConfiguration;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBSL;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.MDObjectComplex;
import com.github._1c_syntax.mdclasses.mdo.Subsystem;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.github._1c_syntax.mdclasses.mdo.wrapper.DesignerWrapper;
import com.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOModule;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.unmarshal.XStreamFactory;
import io.vavr.control.Either;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Фабрика для создания MDO объекта
 */
@UtilityClass
public class MDOFactory {

  /**
   * Читает объект по его файлу описания, а также его дочерние при наличии
   *
   * @param configurationSource - формат исходных файлов
   * @param type                - тип читаемого объекта
   * @param mdoPath             - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<MDObjectBase> readMDObject(ConfigurationSource configurationSource,
                                             MDOType type,
                                             Path mdoPath) {
    Optional<MDObjectBase> mdo = readMDObjectFromFile(configurationSource, type, mdoPath);
    mdo.ifPresent(mdoValue -> {
      // проставляем mdo ссылку для объекта
      if (mdoValue.getMdoReference() == null) {
        mdoValue.setMdoReference(new MDOReference(mdoValue));
      }
      // проставляем mdo ссылку для дочерних объектов
      if (mdoValue instanceof MDObjectComplex) {
        computeMdoReference((MDObjectComplex) mdoValue);
      }
      // загружаем дочерние подсистемы для каждой подсистемы
      if (mdoValue instanceof Subsystem) {
        computeSubsystemChildren(configurationSource, (Subsystem) mdoValue, mdoPath);
      }
      // заполняем модули объекта
      if (mdoValue instanceof MDObjectBSL) {
        computeMdoModules(configurationSource, (MDObjectBSL) mdoValue, mdoPath);
      }
      // загрузка всех объектов конфигурации
      if (mdoValue instanceof MDOConfiguration) {
        var rootPath = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, mdoPath);
        if (configurationSource == ConfigurationSource.EDT) {
          rootPath = Paths.get(FilenameUtils.getFullPathNoEndSeparator(rootPath.toString()));
        }
        computeAllMDObject((MDOConfiguration) mdoValue, configurationSource, rootPath);
        setIncludedSubsystems((MDOConfiguration) mdoValue);
      }
    });

    return mdo;
  }

  /**
   * Читает объект по его файлу описания, но не выполняет чтение дочерних элементов
   *
   * @param configurationSource - формат исходных файлов
   * @param type                - тип читаемого объекта
   * @param mdoPath             - путь к файлу описания объекта
   * @return - прочитанный объект
   */
  public Optional<MDObjectBase> readMDObjectFromFile(ConfigurationSource configurationSource, MDOType type, Path mdoPath) {
    Optional<MDObjectBase> mdo = Optional.empty();
    var mdoFile = mdoPath.toFile();
    if (mdoFile.exists()) {
      if (configurationSource == ConfigurationSource.EDT) {
        mdo = Optional.of((MDObjectBase) XStreamFactory.fromXML(mdoFile));
      } else if (configurationSource == ConfigurationSource.DESIGNER) {
        DesignerWrapper metaDataObject = (DesignerWrapper) XStreamFactory.fromXML(mdoFile);
        mdo = metaDataObject.getPropertyByType(type, mdoPath);
      }
    }
    return mdo;
  }

  // TODO перенести отседова
  public Path getChildrenFolder(String mdoName, Path folder, MDOType type) {
    if (folder != null) {
      var formFolder = Paths.get(folder.toString(), mdoName, type.getGroupName());
      if (formFolder.toFile().exists()) {
        return formFolder;
      }
    }
    return null;
  }

  private void computeAllMDObject(MDOConfiguration configuration,
                                  ConfigurationSource configurationSource,
                                  Path rootPath) {
    List<Either<String, MDObjectBase>> children = new ArrayList<>();
    configuration.getChildren().forEach(child -> {
      if (child.isRight()) {
        children.add(child);
      } else {
        var value = child.getLeft();
        var dotPosition = value.indexOf(".");
        var type = MDOType.fromValue(value.substring(0, dotPosition));
        var name = value.substring(dotPosition + 1);

        if (type.isPresent()) {
          var mdo = readMDObject(configurationSource, type.get(),
            MDOPathUtils.getMDOPath(configurationSource, rootPath, type.get(), name));
          if (mdo.isPresent()) {
            children.add(Either.right(mdo.get()));
          } else {
            // мусор сохраним
            children.add(child);
          }
        } else {
          // мусор сохраним
          children.add(child);
        }
      }
    });

    configuration.setChildren(children);
  }

  private void computeMdoReference(MDObjectComplex mdoValue) {
    mdoValue.getForms().forEach(child -> computeMdoReferenceForChild(mdoValue, child));
    mdoValue.getTemplates().forEach(child -> computeMdoReferenceForChild(mdoValue, child));
    mdoValue.getCommands().forEach(child -> computeMdoReferenceForChild(mdoValue, child));
    mdoValue.getAttributes().forEach(child -> {
      if (child.getMdoReference() == null) {
        child.setMdoReference(new MDOReference(child, mdoValue));
      }
      // для табличной части надо дополнительно по реквизитам заполнить
      if (child instanceof TabularSection) {
        ((TabularSection) child).getAttributes()
          .forEach(childTabular -> {
            if (childTabular.getMdoReference() == null) {
              childTabular.setMdoReference(new MDOReference(childTabular, child));
            }
          });
      }
    });
  }

  private void computeMdoReferenceForChild(MDObjectComplex mdoValue, MDObjectBase child) {
    if (child.getMdoReference() == null) {
      child.setMdoReference(new MDOReference(child, mdoValue));
    }
  }

  private void computeSubsystemChildren(ConfigurationSource configurationSource,
                                        Subsystem subsystem, Path subsystemMDOPath) {
    var children = subsystem.getChildren();
    if (children.isEmpty()) {
      return;
    }

    List<Either<String, MDObjectBase>> newChildren = new ArrayList<>();
    var rootFolder = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, subsystemMDOPath);
    if (rootFolder == null) {
      return;
    }

    var folder = Paths.get(rootFolder.toString(), subsystem.getName(), MDOType.SUBSYSTEM.getGroupName());
    final var startName = MDOType.SUBSYSTEM.getClassName() + ".";
    children.stream()
      .filter(Either::isLeft)
      .filter(child -> child.getLeft().startsWith(startName)
        && !child.getLeft().contains("-")) // для исключения битых ссылок сразу
      .forEach(child -> {
        var subsystemName = child.getLeft().substring(startName.length());
        var mdoPath = MDOPathUtils.getMDOPath(configurationSource, folder, subsystemName);
        if (mdoPath != null) {
          var childSubsystem = readMDObjectFromFile(configurationSource, MDOType.SUBSYSTEM, mdoPath);
          childSubsystem.ifPresent(mdoValue -> {
            if (mdoValue.getMdoReference() == null) {
              mdoValue.setMdoReference(new MDOReference(mdoValue, subsystem));
            }
            newChildren.add(Either.right(mdoValue));
            computeSubsystemChildren(configurationSource, (Subsystem) mdoValue, mdoPath);
          });
          if (childSubsystem.isEmpty()) {
            // вернем несуществующий объект обратно в набор
            newChildren.add(child);
          }
        }
      });
    children.stream()
      .filter(Either::isLeft)
      .filter(child -> !child.getLeft().startsWith(startName))
      .forEach(newChildren::add);

    subsystem.setChildren(newChildren);
  }

  private void computeMdoModules(ConfigurationSource configurationSource, MDObjectBSL mdo, Path mdoPath) {
    var folder = MDOPathUtils.getMDOTypeFolderByMDOPath(configurationSource, mdoPath, mdo.getType());
    setModules(mdo, configurationSource, folder);

    if (mdo instanceof MDObjectComplex) {
      var formFolder = getChildrenFolder(mdo.getName(), folder, MDOType.FORM);
      var commandFolder = getChildrenFolder(mdo.getName(), folder, MDOType.COMMAND);
      ((MDObjectComplex) mdo).getCommands().forEach(command ->
        setModules(command, configurationSource, commandFolder));
      ((MDObjectComplex) mdo).getForms().forEach(form -> setModules(form, configurationSource, formFolder));
    }
  }

  private void setModules(MDObjectBSL mdo, ConfigurationSource configurationSource, Path folder) {
    if (folder == null) {
      return;
    }

    List<MDOModule> modules = new ArrayList<>();

    var moduleTypes = Common.getModuleTypesForMdoTypes().getOrDefault(mdo.getType(), Collections.emptySet());
    if (!moduleTypes.isEmpty()) {
      moduleTypes.forEach(moduleType -> {
        var mdoName = mdo.getName();
        if (mdo.getType() == MDOType.CONFIGURATION) {
          mdoName = "";
        }
        var modulePath = MDOPathUtils.getModulePath(configurationSource, folder, mdoName, moduleType);
        if (modulePath != null && modulePath.toFile().exists()) {
          modules.add(new MDOModule(moduleType, modulePath.toUri()));
        }
      });
    }

    mdo.setModules(modules);
  }

  private void setIncludedSubsystems(MDOConfiguration configuration) {
    Map<String, MDObjectBase> children = configuration.getChildren().stream().filter(Either::isRight)
      .map(Either::get).collect(Collectors.toMap(mdo -> mdo.getMdoReference().getMdoRef(), mdo -> mdo));

    configuration.getChildren().stream().filter(Either::isRight).map(Either::get)
      .filter(mdo -> mdo.getType() == MDOType.SUBSYSTEM)
      .forEach(subsystem -> setSubsystemForChildren((Subsystem) subsystem, children));
  }

  private void setSubsystemForChildren(Subsystem subsystem, Map<String, MDObjectBase> allChildren) {
    List<Either<String, MDObjectBase>> children = new ArrayList<>();

    subsystem.getChildren().forEach(mdoPair -> {
      if (mdoPair.isLeft()) {
        var mdo = allChildren.get(mdoPair.getLeft());
        if (mdo != null) {
          children.add(Either.right(mdo));
          setSubsystemForChild(subsystem, allChildren, mdo);
        } else {
          children.add(mdoPair);
        }
      } else {
        children.add(mdoPair);
        var mdo = mdoPair.get();
        setSubsystemForChild(subsystem, allChildren, mdo);
      }
    });

    subsystem.setChildren(children);
  }

  private void setSubsystemForChild(Subsystem subsystem, Map<String, MDObjectBase> allChildren, MDObjectBase mdo) {
    mdo.addIncludedSubsystem(subsystem);
    if (mdo instanceof Subsystem) {
      setSubsystemForChildren((Subsystem) mdo, allChildren);
    }
  }
}

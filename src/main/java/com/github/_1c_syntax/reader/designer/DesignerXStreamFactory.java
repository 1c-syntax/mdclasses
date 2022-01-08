package com.github._1c_syntax.reader.designer;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.ExchangePlan;
import com.github._1c_syntax.bsl.mdo.HttpService;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.XdtoPackage;
import com.github._1c_syntax.bsl.mdo.children.AccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.DocumentJournalColumn;
import com.github._1c_syntax.bsl.mdo.children.EnumValue;
import com.github._1c_syntax.bsl.mdo.children.ExtDimensionAccountingFlag;
import com.github._1c_syntax.bsl.mdo.children.HttpServiceMethod;
import com.github._1c_syntax.bsl.mdo.children.HttpServiceUrlTemplate;
import com.github._1c_syntax.bsl.mdo.children.IntegrationServiceChannel;
import com.github._1c_syntax.bsl.mdo.children.ObjectAttribute;
import com.github._1c_syntax.bsl.mdo.children.ObjectCommand;
import com.github._1c_syntax.bsl.mdo.children.ObjectForm;
import com.github._1c_syntax.bsl.mdo.children.ObjectTabularSection;
import com.github._1c_syntax.bsl.mdo.children.ObjectTemplate;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.children.RegisterDimension;
import com.github._1c_syntax.bsl.mdo.children.RegisterResource;
import com.github._1c_syntax.bsl.mdo.children.SequenceDimension;
import com.github._1c_syntax.bsl.mdo.children.TaskAddressingAttribute;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperation;
import com.github._1c_syntax.bsl.mdo.children.WebServiceOperationParameter;
import com.github._1c_syntax.bsl.mdo.storages.DataCompositionSchema;
import com.github._1c_syntax.bsl.mdo.storages.RoleRight;
import com.github._1c_syntax.bsl.mdo.storages.XdtoPackageData;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.AutoRecordType;
import com.github._1c_syntax.bsl.mdo.support.ConfigurationExtensionPurpose;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.DataSeparation;
import com.github._1c_syntax.bsl.mdo.support.FormType;
import com.github._1c_syntax.bsl.mdo.support.Handler;
import com.github._1c_syntax.bsl.mdo.support.IndexingType;
import com.github._1c_syntax.bsl.mdo.support.MessageDirection;
import com.github._1c_syntax.bsl.mdo.support.ObjectBelonging;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.TemplateType;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.reader.common.converter.MethodHandlerConverter;
import com.github._1c_syntax.reader.common.converter.XdtoPackageDataConverter;
import com.github._1c_syntax.reader.designer.converter.DesignerConverter;
import com.github._1c_syntax.reader.common.converter.EnumConverter;
import com.github._1c_syntax.reader.designer.wrapper.DesignerRootWrapper;
import com.github._1c_syntax.reader.xstream.ExtendReaderWrapper;
import com.github._1c_syntax.reader.xstream.ExtendStaxDriver;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class DesignerXStreamFactory {
  @Getter(lazy = true)
  private final XStream xstream = createXMLMapper();

  /**
   * Выполняет чтение объекта из XML файла
   */
  public Object fromXML(File file) {
    Object result;
    try {
      result = getXstream().fromXML(file);
    } catch (ConversionException e) {
      LOGGER.error("Can't read file '{}' - it's broken \n: ", file.toString(), e);
      throw e;
    }
    return result;
  }

  public Object unmarshal(HierarchicalStreamReader reader, String rootNode, DataHolder dataHolder) {
    return getXstream().unmarshal(reader, rootNode, dataHolder);
  }

  public Class<?> getRealClass(String className) {
    return getXstream().getMapper().realClass(className);
  }

  public Path getCurrentPath(HierarchicalStreamReader reader) {
    return ((ExtendReaderWrapper) reader).getPath();
  }

  private XStream createXMLMapper() {
    var xStream = new XStream(new PureJavaReflectionProvider(), new ExtendStaxDriver());

    // автоопределение аннотаций
    xStream.autodetectAnnotations(false);

    // игнорирование неизвестных тегов
    xStream.ignoreUnknownElements();
    // настройки безопасности доступа к данным
    xStream.setMode(XStream.NO_REFERENCES);
//    xStream.addPermission(NoTypePermission.NONE);
    xStream.addPermission(new WildcardTypePermission(new String[]{"com.github._1c_syntax.**"}));
//    xStream.addPermission(new ExplicitTypePermission(new String[]{"java.time.Period"}));

    // необходимо зарегистрировать все используемые классы
    registerClasses(xStream);
    // для каждого типа данных или поля необходимо зарегистрировать конвертер
    registerConverters(xStream);

    return xStream;
  }

  private void registerClasses(XStream xStream) {
    xStream.alias("MetaDataObject", DesignerRootWrapper.class);
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .acceptPackages("com.github._1c_syntax.bsl.mdo")
      .rejectPackages("com.github._1c_syntax.bsl.mdo.children")
      .scan()) {

      scanResult.getClassesImplementing(MDObject.class.getName())
        .filter(classInfo -> !classInfo.isInterface())
        .forEach(clazzInfo -> xStream.alias(clazzInfo.getSimpleName(), getClassFromClassInfo(clazzInfo)));
    }

    xStream.alias(Configuration.class.getSimpleName(), Configuration.class);
    xStream.alias("XDTOPackage", XdtoPackage.class);
    xStream.alias("HTTPService", HttpService.class);
    xStream.alias("package", XdtoPackageData.class);
    xStream.alias("Operation", WebServiceOperation.class);
    xStream.alias("Parameter", WebServiceOperationParameter.class);
    xStream.alias("Column", DocumentJournalColumn.class);
    xStream.alias("Attribute", ObjectAttribute.class);
    xStream.alias("Resource", RegisterResource.class);
    xStream.alias("AddressingAttribute", TaskAddressingAttribute.class);
    xStream.alias("Command", ObjectCommand.class);
    xStream.alias("Template", ObjectTemplate.class);
    xStream.alias("TabularSection", ObjectTabularSection.class);
    xStream.alias("Form", ObjectForm.class);
    xStream.alias("IntegrationServiceChannel", IntegrationServiceChannel.class);
    xStream.alias("URLTemplate", HttpServiceUrlTemplate.class);
    xStream.alias("Method", HttpServiceMethod.class);
    xStream.alias("EnumValue", EnumValue.class);
    xStream.alias("AccountingFlag", AccountingFlag.class);
    xStream.alias("ExtDimensionAccountingFlag", ExtDimensionAccountingFlag.class);
    xStream.alias("Recalculation", Recalculation.class);

    xStream.alias("Rights", RoleRight.class);
    xStream.alias("ExchangePlanContent", ExchangePlan.Item.class);

    xStream.alias("SequenceDimension", SequenceDimension.class);
    xStream.alias("RegisterDimension", RegisterDimension.class);

    xStream.alias("DataCompositionSchema", DataCompositionSchema.class);
    xStream.alias("dataSet", DataCompositionSchema.DataSet.class);

  }

  private void registerConverters(XStream xStream) {
    try (var scanResult = new ClassGraph()
      .enableClassInfo()
      .enableAnnotationInfo()
      .acceptPackages("com.github._1c_syntax.reader.designer.converter")
      .scan()) {

      var annotation = DesignerConverter.class;

      var classes = scanResult.getClassesWithAnnotation(annotation.getName());
      classes.stream()
        .map(getObjectsFromInfoClass())
        .filter(Objects::nonNull)
        .forEach(xStream::registerConverter);
    }

    xStream.registerConverter(new EnumConverter<>(ReturnValueReuse.class));
    xStream.registerConverter(new EnumConverter<>(UseMode.class));
    xStream.registerConverter(new EnumConverter<>(ScriptVariant.class));
    xStream.registerConverter(new EnumConverter<>(MessageDirection.class));
    xStream.registerConverter(new EnumConverter<>(ConfigurationExtensionPurpose.class));
    xStream.registerConverter(new EnumConverter<>(ObjectBelonging.class));
    xStream.registerConverter(new EnumConverter<>(TemplateType.class));
    xStream.registerConverter(new EnumConverter<>(DataLockControlMode.class));
    xStream.registerConverter(new EnumConverter<>(DataSeparation.class));
    xStream.registerConverter(new EnumConverter<>(FormType.class));
    xStream.registerConverter(new EnumConverter<>(IndexingType.class));
    xStream.registerConverter(new EnumConverter<>(AutoRecordType.class));
    xStream.registerConverter(new EnumConverter<>(ApplicationRunMode.class));

    xStream.registerConverter(new MethodHandlerConverter());
    xStream.registerConverter(new XdtoPackageDataConverter());

  }

  private static Function<ClassInfo, Converter> getObjectsFromInfoClass() {
    return classInfo -> {
      try {
        var clazz = Class.forName(classInfo.getName());
        return (Converter) clazz.getDeclaredConstructors()[0].newInstance(null);
      } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
        LOGGER.error("Cannot resolve class: " + classInfo.getName());
        return null;
      }
    };
  }

  private static Class<?> getClassFromClassInfo(ClassInfo classInfo) {
    try {
      return Class.forName(classInfo.getName());
    } catch (ClassNotFoundException e) {
      LOGGER.error("Cannot resolve class: " + classInfo.getName());
      return null;
    }
  }
}
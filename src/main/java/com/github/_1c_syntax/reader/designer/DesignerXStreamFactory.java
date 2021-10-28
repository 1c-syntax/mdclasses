package com.github._1c_syntax.reader.designer;

import com.github._1c_syntax.bsl.mdclasses.Configuration;
import com.github._1c_syntax.bsl.mdo.HttpService;
import com.github._1c_syntax.bsl.mdo.MDObject;
import com.github._1c_syntax.bsl.mdo.XdtoPackage;
import com.github._1c_syntax.bsl.mdo.support.ApplicationRunMode;
import com.github._1c_syntax.bsl.mdo.support.DataLockControlMode;
import com.github._1c_syntax.bsl.mdo.support.ReturnValueReuse;
import com.github._1c_syntax.bsl.mdo.support.ScriptVariant;
import com.github._1c_syntax.bsl.mdo.support.UseMode;
import com.github._1c_syntax.mdclasses.unmarshal.ExtendReaderWrapper;
import com.github._1c_syntax.mdclasses.unmarshal.ExtendStaxDriver;
import com.github._1c_syntax.reader.common.converter.MethodHandlerConverter;
import com.github._1c_syntax.reader.designer.converter.ApplicationUsePurposeConverter;
import com.github._1c_syntax.reader.designer.converter.DesignerConverter;
import com.github._1c_syntax.reader.designer.converter.EnumConverter;
import com.github._1c_syntax.reader.designer.converter.MdoReferenceConverter;
import com.github._1c_syntax.reader.designer.wrapper.DesignerRootWrapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.security.ExplicitTypePermission;
import com.thoughtworks.xstream.security.NoTypePermission;
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
    // данный провайдер необходим для корректной обработки значений по умолчанию, чтобы не было null
    var qNameMap = new QNameMap();
//    qNameMap.registerMapping(new QName("http://g5.1c.ru/v8/dt/form", "Form", "form"), FormData.class);
//    qNameMap.registerMapping(new QName("http://v8.1c.ru/8.3/xcf/logform", "Form"), DesignerFormWrapper.class);

    var xStream = new XStream(new PureJavaReflectionProvider(), new ExtendStaxDriver(qNameMap)) {

      // TODO как починят https://github.com/x-stream/xstream/issues/101
      // После исправления бага (с 2017 года) убрать этот код

      /**
       * Переопределение списка регистрируемых конвертеров. Оставлены только те, что нужны, особенно исключены те,
       * что вызывают недовольство у JVM, в связи с неправильным доступом при рефлексии
       */
      @Override
      protected void setupConverters() {
//        reflectionConverter = new ReflectionConverter(getMapper(), getReflectionProvider());

        registerConverter(new NullConverter(), PRIORITY_VERY_HIGH);
        registerConverter(new IntConverter(), PRIORITY_NORMAL);
        registerConverter(new FloatConverter(), PRIORITY_NORMAL);
        registerConverter(new DoubleConverter(), PRIORITY_NORMAL);
        registerConverter(new LongConverter(), PRIORITY_NORMAL);
        registerConverter(new ShortConverter(), PRIORITY_NORMAL);
        registerConverter(new BooleanConverter(), PRIORITY_NORMAL);
        registerConverter(new ByteConverter(), PRIORITY_NORMAL);
        registerConverter(new StringConverter(), PRIORITY_NORMAL);
        registerConverter(new DateConverter(), PRIORITY_NORMAL);
        registerConverter(new CollectionConverter(getMapper()), PRIORITY_NORMAL);
//        registerConverter(reflectionConverter, PRIORITY_VERY_LOW);
      }
    };
    // автоопределение аннотаций
    xStream.autodetectAnnotations(false);

    // игнорирование неизвестных тегов
    xStream.ignoreUnknownElements();
    // настройки безопасности доступа к данным
    xStream.setMode(XStream.NO_REFERENCES);
    XStream.setupDefaultSecurity(xStream);
    xStream.addPermission(NoTypePermission.NONE);
    xStream.addPermission(new WildcardTypePermission(new String[]{"com.github._1c_syntax.**"}));
    xStream.addPermission(new ExplicitTypePermission(new String[]{"java.time.Period"}));

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

      var classes = scanResult.getClassesImplementing(MDObject.class.getName());
      classes
        .filter(classInfo -> !classInfo.isInterface())
        .forEach(clazzInfo -> xStream.alias(clazzInfo.getSimpleName(), getClassFromClassInfo(clazzInfo)));
    }

    xStream.alias(Configuration.class.getSimpleName(), Configuration.class);
    xStream.alias("XDTOPackage", XdtoPackage.class);
    xStream.alias("HTTPService", HttpService.class);
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
//    xStream.registerConverter(new EnumConverter<>(MessageDirection.class));
//    xStream.registerConverter(new EnumConverter<>(ConfigurationExtensionPurpose.class));
//    xStream.registerConverter(new EnumConverter<>(ObjectBelonging.class));
//    xStream.registerConverter(new EnumConverter<>(TemplateType.class));
    xStream.registerConverter(new EnumConverter<>(DataLockControlMode.class));
//    xStream.registerConverter(new EnumConverter<>(DataSeparation.class));
//    xStream.registerConverter(new EnumConverter<>(FormType.class));
//    xStream.registerConverter(new EnumConverter<>(IndexingType.class));
//    xStream.registerConverter(new EnumConverter<>(AutoRecordType.class));
//    xStream.registerConverter(new EnumConverter<>(BWAValue.class));
    xStream.registerConverter(new EnumConverter<>(ApplicationRunMode.class));
    xStream.registerConverter(new ApplicationUsePurposeConverter());
    xStream.registerConverter(new MethodHandlerConverter());
    xStream.registerConverter(new MdoReferenceConverter());
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
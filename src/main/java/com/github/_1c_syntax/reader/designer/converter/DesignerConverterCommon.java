package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.support.ApplicationUsePurpose;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.reader.TransformationUtils;
import com.github._1c_syntax.reader.designer.wrapper.DesignerProperties;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class DesignerConverterCommon {

  public Map<String, Map<String, Object>> readHead(HierarchicalStreamReader reader) {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("uuid", reader.getAttribute("uuid"));
    Map<String, Map<String, Object>> result = new HashMap<>();
    result.put("attributes", attributes);
    return result;
  }

  public static Map<String, Map<String, Object>> readProperties(Object builder,
                                                                MDOType mdoType,
                                                                HierarchicalStreamReader reader,
                                                                UnmarshallingContext context) {
    Map<String, Object> properties = new HashMap<>();
    Map<String, Object> undefinedProperties = new HashMap<>();

    while (reader.hasMoreChildren()) {
      reader.moveDown();
      var propertyName = reader.getNodeName();
      var methodType = TransformationUtils.fieldType(builder, propertyName);
      if (methodType != null) {
        try {
          Object value = null;
          if (methodType instanceof ParameterizedType
            && ApplicationUsePurpose.class
            .isAssignableFrom((Class<?>) ((ParameterizedType) methodType)
              .getActualTypeArguments()[0])) {

            value = context.convertAnother(reader, (Class<?>) ((ParameterizedType) methodType)
              .getActualTypeArguments()[0]);
          } else {
            value = context.convertAnother(reader, (Class<?>) methodType);
          }
          properties.put(propertyName, value);
        } catch (Exception e) {
          System.out.println("Cannot convert " + propertyName + " to type " + methodType + "\n" + e);
          undefinedProperties.put(propertyName, reader.getValue());
        }
      } else {
        System.out.println("Undefined node " + propertyName);
        undefinedProperties.put(propertyName, reader.getValue());
      }
      reader.moveUp();
    }

    // собственные поля на основании прочитанного
    properties.put("mdoReference", MdoReference.create(mdoType, (String) properties.get("Name")));

    properties.put("supportVariant", SupportVariant.NONE);// todo Заглушка

    Map<String, Map<String, Object>> result = new HashMap<>();
    result.put("properties", properties);
    result.put("undefinedProperties", undefinedProperties);
    return result;
  }

  public void computeBuilder(@NonNull Object builder, @NonNull DesignerProperties properties) {
    properties.getProperties().forEach((key, value) -> TransformationUtils.setValue(builder, key, value));
    TransformationUtils.setValue(builder, "mdoReference", properties.getMdoReference());
    TransformationUtils.setValue(builder, "mdoType", properties.getMdoType());
    TransformationUtils.setValue(builder, "supportVariant", properties.getSupportVariant());

    TransformationUtils.setValue(builder, "children", properties.getChildren());
  }

  public void computeBuilder(@NonNull Object builder, @NonNull Map<String, Map<String, Object>> properties) {
    if (properties.containsKey("attributes")) {
      properties.get("attributes").forEach((key, value) ->
        TransformationUtils.setValue(builder, key, value));
    }

    if (properties.containsKey("properties")) {
      properties.get("properties").forEach((key, value) ->
        TransformationUtils.setValue(builder, key, value));
    }
  }


}

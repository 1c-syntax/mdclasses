package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.Attribute;
import com.github._1c_syntax.bsl.mdo.AttributeOwner;
import com.github._1c_syntax.bsl.mdo.CalculationRegister;
import com.github._1c_syntax.bsl.mdo.Command;
import com.github._1c_syntax.bsl.mdo.CommandOwner;
import com.github._1c_syntax.bsl.mdo.Form;
import com.github._1c_syntax.bsl.mdo.FormOwner;
import com.github._1c_syntax.bsl.mdo.TabularSection;
import com.github._1c_syntax.bsl.mdo.TabularSectionOwner;
import com.github._1c_syntax.bsl.mdo.Template;
import com.github._1c_syntax.bsl.mdo.TemplateOwner;
import com.github._1c_syntax.bsl.mdo.children.Recalculation;
import com.github._1c_syntax.bsl.mdo.support.ApplicationUsePurpose;
import com.github._1c_syntax.bsl.mdo.support.MdoReference;
import com.github._1c_syntax.bsl.support.SupportVariant;
import com.github._1c_syntax.bsl.types.MDOType;
import com.github._1c_syntax.mdclasses.utils.TransformationUtils;
import com.github._1c_syntax.reader.designer.wrapper.DesignerProperties;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    if (CommandOwner.class.isAssignableFrom(properties.getRealClass())) {
      TransformationUtils.setValue(builder, "commands", properties.getChildren()
        .stream().filter(Command.class::isInstance).collect(Collectors.toList()));
    }

    if (TemplateOwner.class.isAssignableFrom(properties.getRealClass())) {
      TransformationUtils.setValue(builder, "templates", properties.getChildren()
        .stream().filter(Template.class::isInstance).collect(Collectors.toList()));
    }

    if (FormOwner.class.isAssignableFrom(properties.getRealClass())) {
      TransformationUtils.setValue(builder, "forms", properties.getChildren()
        .stream().filter(Form.class::isInstance).collect(Collectors.toList()));
    }

    if (AttributeOwner.class.isAssignableFrom(properties.getRealClass())) {
      TransformationUtils.setValue(builder, "attributes", properties.getChildren()
        .stream().filter(Attribute.class::isInstance).collect(Collectors.toList()));
    }

    if (TabularSectionOwner.class.isAssignableFrom(properties.getRealClass())) {
      TransformationUtils.setValue(builder, "tabularSections", properties.getChildren()
        .stream().filter(TabularSection.class::isInstance).collect(Collectors.toList()));
    }

    if (CalculationRegister.class.isAssignableFrom(properties.getRealClass())) {
      TransformationUtils.setValue(builder, "recalculations", properties.getChildren()
        .stream().filter(Recalculation.class::isInstance).collect(Collectors.toList()));
    }

    // остальные дочерние
    TransformationUtils.setValue(builder, "children", properties.getChildren()
      .stream().filter(child -> !(child instanceof Command
        || child instanceof Template
        || child instanceof Form
        || child instanceof Attribute
        || child instanceof TabularSection
        || child instanceof Recalculation))
      .collect(Collectors.toList()));
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

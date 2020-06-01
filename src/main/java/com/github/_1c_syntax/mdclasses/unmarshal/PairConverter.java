package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.Language;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOReference;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import io.vavr.control.Either;

/**
 * Конвертирует строки с mdoref в Either для последующей обработки
 */
public class PairConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if ("languages".equals(reader.getNodeName())) {
      var uuid = reader.getAttribute("uuid");
      var tmp = new MDObjectBase();
      var language = (Language) context.convertAnother(tmp, Language.class);
      language.setUuid(uuid);
      language.setMdoReference(new MDOReference(language));
      return Either.right(language);
    } else if (reader.getValue().contains(".")) { // уже лежит имя
      return Either.left(reader.getValue());
    } else {
      var type = MDOType.fromValue(reader.getNodeName());
      return type
        .map(mdoType -> Either.left(mdoType.getClassName() + "." + reader.getValue()))
        .orElseGet(() -> Either.left(reader.getNodeName() + "." + reader.getValue()));
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return Either.class.isAssignableFrom(type);
  }
}
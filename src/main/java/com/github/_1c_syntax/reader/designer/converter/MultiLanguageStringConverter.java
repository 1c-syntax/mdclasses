package com.github._1c_syntax.reader.designer.converter;

import com.github._1c_syntax.bsl.mdo.support.MultiLanguageString;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * Конвертер для строк на нескольких языках
 */
@DesignerConverter
@Slf4j
public class MultiLanguageStringConverter implements Converter {

  private static final String LANG_NODE_NAME = "lang";
  private static final String CONTENT_NODE_NAME = "content";

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    if (!reader.hasMoreChildren()) {
      return MultiLanguageString.EMPTY;
    }
    HashMap<String, String> langContent = new HashMap<>();

    while (reader.hasMoreChildren()) { // v8:item
      reader.moveDown();
      var lang = "";
      var content = "";
      while (reader.hasMoreChildren()) {
        reader.moveDown();
        var node = reader.getNodeName();
        if (LANG_NODE_NAME.equals(node)) {
          lang = reader.getValue();
        } else if (CONTENT_NODE_NAME.equals(node)) {
          content = reader.getValue();
        } else {
          // no-op
        }
        reader.moveUp();
      }
      reader.moveUp();
      langContent.put(lang, content);
    }
    return new MultiLanguageString(langContent);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == MultiLanguageString.class;
  }
}

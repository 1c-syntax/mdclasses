package com.github._1c_syntax.mdclasses.unmarshal.converters;

import com.github._1c_syntax.mdclasses.mdo.children.form.FormData;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.DesignerFormWrapper;
import com.github._1c_syntax.mdclasses.unmarshal.wrapper.form.DesignerForm;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Конвертор для форм объектов в формате конфигуратора, минуя класс враппер
 */
@Slf4j
public class DesignerFormConverter implements Converter {
  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // no-op
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var designerForm = (DesignerForm) context.convertAnother(reader, DesignerForm.class);
    return new FormData(designerForm);
  }

  @Override
  public boolean canConvert(Class type) {
    return type == DesignerFormWrapper.class;
  }
}

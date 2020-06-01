package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.SneakyThrows;

/**
 * Класс-конвертер из строкового значения в элемент перечисления.
 * Для каждого конкретного перечисления надо создать собственный класс, унаследованный от текущего.
 * Необходимо в конструкторе передать класс перечисления и зарегистрировать созданный класс конвертора в
 * XStreamFactory.
 *
 * Внимание!
 * В перечислении должен быть реализован метод "fromValue" со стоковым параметром, возвращающий элемент перечисления
 */
public class EnumConverter implements Converter {

  private final Class<?> mdoEnum;

  public EnumConverter(Class<?> mdoEnum) {
    this.mdoEnum = mdoEnum;
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @SneakyThrows
  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return mdoEnum.getMethod("fromValue", String.class).invoke(this, reader.getValue());
  }

  @Override
  public boolean canConvert(Class type) {
    return mdoEnum.isAssignableFrom(type);
  }
}

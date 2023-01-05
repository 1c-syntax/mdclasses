package com.github._1c_syntax.bsl.reader.edt.converter;

import com.github._1c_syntax.bsl.reader.common.TransformationUtils;
import com.github._1c_syntax.bsl.reader.common.xstream.ExtendXStream;
import com.github._1c_syntax.bsl.reader.common.xstream.ReadConverter;
import com.github._1c_syntax.bsl.reader.edt.EDTReader;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.nio.file.Path;

public abstract class AbstractReadConverter implements ReadConverter {

  protected String name;
  protected Class<?> realClass;
  protected Path currentPath;

  protected TransformationUtils.Context read(HierarchicalStreamReader reader, UnmarshallingContext context) {
    name = reader.getNodeName();
    realClass = EDTReader.getXstream().getRealClass(name);
    currentPath = ExtendXStream.getCurrentPath(reader);
    var readerContext = new TransformationUtils.Context(reader.getNodeName(), realClass, currentPath);
    Unmarshaller.unmarshal(reader, context, readerContext);
    return readerContext;
  }
}

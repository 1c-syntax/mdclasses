package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Расширенная модель StaxDriver для возможности пр чтении иметь доступ к файлу, который читается
 */
public class ExtendStaxDriver extends StaxDriver {

  public ExtendStaxDriver(QNameMap qNameMap) {
    super(qNameMap);
  }

  @Override
  public HierarchicalStreamReader createReader(File in) {
    final InputStream stream;
    try {
      stream = new FileInputStream(in);
      var xmlStreamReader = createParser(new StreamSource(
        stream, in.toURI().toASCIIString()));
      var reader = createStaxReader(xmlStreamReader);
      return new ExtendReaderWrapper(reader, in, xmlStreamReader) {
        @Override
        public void close() {
          super.close();
          try {
            stream.close();
          } catch (IOException e) {
            // ignore
          }
        }
      };
    } catch (XMLStreamException | FileNotFoundException e) {
      throw new StreamException(e);
    }
  }
}

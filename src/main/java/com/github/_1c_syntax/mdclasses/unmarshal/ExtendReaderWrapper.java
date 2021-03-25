package com.github._1c_syntax.mdclasses.unmarshal;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.ReaderWrapper;

import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.nio.file.Path;

/**
 * Реализация враппера ридера, позволяющего читать в прикладном коде путь к файлу
 */
public class ExtendReaderWrapper extends ReaderWrapper {
  private final File file;
  private final XMLStreamReader xmlStreamReader;

  public ExtendReaderWrapper(HierarchicalStreamReader reader, File in, XMLStreamReader xmlStreamReader) {
    super(reader);
    this.file = in;
    this.xmlStreamReader = xmlStreamReader;
  }

  public Path getPath() {
    return file.toPath();
  }

  public XMLStreamReader getXMLStreamReader() {
    return xmlStreamReader;
  }
}

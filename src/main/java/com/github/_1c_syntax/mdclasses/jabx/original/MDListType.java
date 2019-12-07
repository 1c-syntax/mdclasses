

package com.github._1c_syntax.mdclasses.jabx.original;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;


@Getter
public class MDListType {

  @XmlElement(name = "Item")
  protected List<Object> item;

}

package com.github._1c_syntax.mdclasses.unmarshal;

import com.github._1c_syntax.mdclasses.mdo.AccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.AddressingAttribute;
import com.github._1c_syntax.mdclasses.mdo.Attribute;
import com.github._1c_syntax.mdclasses.mdo.Column;
import com.github._1c_syntax.mdclasses.mdo.Dimension;
import com.github._1c_syntax.mdclasses.mdo.ExtDimensionAccountingFlag;
import com.github._1c_syntax.mdclasses.mdo.MDOAttribute;
import com.github._1c_syntax.mdclasses.mdo.MDObjectBase;
import com.github._1c_syntax.mdclasses.mdo.Recalculation;
import com.github._1c_syntax.mdclasses.mdo.Resource;
import com.github._1c_syntax.mdclasses.mdo.TabularSection;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Используется для чтения атрибутов объекта, включая табличные части и их атрибуты
 */
public class AttributeConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    // noop
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    var uuid = reader.getAttribute("uuid");
    switch (reader.getNodeName()) {
      case "dimensions":
        return getMdoAttribute(context, uuid, Dimension.class);
      case "resources":
        return getMdoAttribute(context, uuid, Resource.class);
      case "recalculations":
        return getMdoAttribute(context, uuid, Recalculation.class);
      case "attributes":
        return getMdoAttribute(context, uuid, Attribute.class);
      case "tabularSections":
        return getMdoAttribute(context, uuid, TabularSection.class);
      case "accountingFlags":
        return getMdoAttribute(context, uuid, AccountingFlag.class);
      case "extDimensionAccountingFlags":
        return getMdoAttribute(context, uuid, ExtDimensionAccountingFlag.class);
      case "columns":
        return getMdoAttribute(context, uuid, Column.class);
      case "addressingAttributes":
        return getMdoAttribute(context, uuid, AddressingAttribute.class);
      default:
        throw new IllegalStateException("Unexpected value: " + reader.getNodeName());
    }
  }

  @Override
  public boolean canConvert(Class type) {
    return type == MDOAttribute.class;
  }

  private MDOAttribute getMdoAttribute(UnmarshallingContext context, String uuid, Class<? extends MDOAttribute> clazz) {
    var tmp = new MDObjectBase();
    MDOAttribute attribute = clazz.cast(context.convertAnother(tmp, clazz));
    attribute.setUuid(uuid);
    return attribute;
  }
}

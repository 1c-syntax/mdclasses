//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jackson.designer;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManagedFormGroupType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ManagedFormGroupType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CommandBar"/&gt;
 *     &lt;enumeration value="Popup"/&gt;
 *     &lt;enumeration value="ColumnGroup"/&gt;
 *     &lt;enumeration value="Pages"/&gt;
 *     &lt;enumeration value="Page"/&gt;
 *     &lt;enumeration value="UsualGroup"/&gt;
 *     &lt;enumeration value="ButtonGroup"/&gt;
 *     &lt;enumeration value="ContextMenu"/&gt;
 *     &lt;enumeration value="AutoCommandBar"/&gt;
 *     &lt;enumeration value="Navigator"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType("ManagedFormGroupType", namespace = "http://v8.1c.ru/8.2/managed-application/logform")
@XmlEnum
public enum ManagedFormGroupType {

    @JsonProperty("CommandBar")
    COMMAND_BAR("CommandBar"),
    @JsonProperty("Popup")
    POPUP("Popup"),
    @JsonProperty("ColumnGroup")
    COLUMN_GROUP("ColumnGroup"),
    @JsonProperty("Pages")
    PAGES("Pages"),
    @JsonProperty("Page")
    PAGE("Page"),
    @JsonProperty("UsualGroup")
    USUAL_GROUP("UsualGroup"),
    @JsonProperty("ButtonGroup")
    BUTTON_GROUP("ButtonGroup"),
    @JsonProperty("ContextMenu")
    CONTEXT_MENU("ContextMenu"),
    @JsonProperty("AutoCommandBar")
    AUTO_COMMAND_BAR("AutoCommandBar"),
    @JsonProperty("Navigator")
    NAVIGATOR("Navigator");
    private final String value;

    ManagedFormGroupType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ManagedFormGroupType fromValue(String v) {
        for (ManagedFormGroupType c: ManagedFormGroupType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
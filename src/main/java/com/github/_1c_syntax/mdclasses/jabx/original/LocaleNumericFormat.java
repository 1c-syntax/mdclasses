//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jabx.original;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>Java class for LocaleNumericFormat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocaleNumericFormat"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="decimalSeparator" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="groupingSeparator" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="groupingSize" type="{http://www.w3.org/2001/XMLSchema}decimal" /&gt;
 *       &lt;attribute name="secondaryGroupingSize" type="{http://www.w3.org/2001/XMLSchema}decimal" /&gt;
 *       &lt;attribute name="negativePrefix" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="negativeSuffix" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="positivePrefix" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="positiveSuffix" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocaleNumericFormat", namespace = "http://v8.1c.ru/8.2/managed-application/core")
public class LocaleNumericFormat {

    @XmlAttribute(name = "decimalSeparator")
    protected String decimalSeparator;
    @XmlAttribute(name = "groupingSeparator")
    protected String groupingSeparator;
    @XmlAttribute(name = "groupingSize")
    protected BigDecimal groupingSize;
    @XmlAttribute(name = "secondaryGroupingSize")
    protected BigDecimal secondaryGroupingSize;
    @XmlAttribute(name = "negativePrefix")
    protected String negativePrefix;
    @XmlAttribute(name = "negativeSuffix")
    protected String negativeSuffix;
    @XmlAttribute(name = "positivePrefix")
    protected String positivePrefix;
    @XmlAttribute(name = "positiveSuffix")
    protected String positiveSuffix;

    /**
     * Gets the value of the decimalSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * Sets the value of the decimalSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecimalSeparator(String value) {
        this.decimalSeparator = value;
    }

    /**
     * Gets the value of the groupingSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupingSeparator() {
        return groupingSeparator;
    }

    /**
     * Sets the value of the groupingSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupingSeparator(String value) {
        this.groupingSeparator = value;
    }

    /**
     * Gets the value of the groupingSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGroupingSize() {
        return groupingSize;
    }

    /**
     * Sets the value of the groupingSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGroupingSize(BigDecimal value) {
        this.groupingSize = value;
    }

    /**
     * Gets the value of the secondaryGroupingSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSecondaryGroupingSize() {
        return secondaryGroupingSize;
    }

    /**
     * Sets the value of the secondaryGroupingSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSecondaryGroupingSize(BigDecimal value) {
        this.secondaryGroupingSize = value;
    }

    /**
     * Gets the value of the negativePrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNegativePrefix() {
        return negativePrefix;
    }

    /**
     * Sets the value of the negativePrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNegativePrefix(String value) {
        this.negativePrefix = value;
    }

    /**
     * Gets the value of the negativeSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNegativeSuffix() {
        return negativeSuffix;
    }

    /**
     * Sets the value of the negativeSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNegativeSuffix(String value) {
        this.negativeSuffix = value;
    }

    /**
     * Gets the value of the positivePrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositivePrefix() {
        return positivePrefix;
    }

    /**
     * Sets the value of the positivePrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositivePrefix(String value) {
        this.positivePrefix = value;
    }

    /**
     * Gets the value of the positiveSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositiveSuffix() {
        return positiveSuffix;
    }

    /**
     * Sets the value of the positiveSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositiveSuffix(String value) {
        this.positiveSuffix = value;
    }

}
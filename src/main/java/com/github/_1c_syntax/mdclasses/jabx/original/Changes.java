//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jabx.original;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for Changes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Changes"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="structure" type="{http://v8.1c.ru/8.2/uobjects}StructureChanges" minOccurs="0"/&gt;
 *         &lt;element name="collection" type="{http://v8.1c.ru/8.2/uobjects}CollectionChanges" minOccurs="0"/&gt;
 *         &lt;element name="structureAndCollection" type="{http://v8.1c.ru/8.2/uobjects}StructureAndCollectionChanges" minOccurs="0"/&gt;
 *         &lt;element name="tree" type="{http://v8.1c.ru/8.2/uobjects}TreeChanges" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="sinDesc" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="sin" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="seq" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Changes", namespace = "http://v8.1c.ru/8.2/uobjects", propOrder = {
    "structure",
    "collection",
    "structureAndCollection",
    "tree"
})
public class Changes {

    protected StructureChanges structure;
    protected CollectionChanges collection;
    protected StructureAndCollectionChanges structureAndCollection;
    protected TreeChanges tree;
    @XmlAttribute(name = "sinDesc")
    @XmlSchemaType(name = "unsignedInt")
    protected Long sinDesc;
    @XmlAttribute(name = "sin")
    @XmlSchemaType(name = "unsignedInt")
    protected Long sin;
    @XmlAttribute(name = "seq")
    @XmlSchemaType(name = "unsignedInt")
    protected Long seq;

    /**
     * Gets the value of the structure property.
     * 
     * @return
     *     possible object is
     *     {@link StructureChanges }
     *     
     */
    public StructureChanges getStructure() {
        return structure;
    }

    /**
     * Sets the value of the structure property.
     * 
     * @param value
     *     allowed object is
     *     {@link StructureChanges }
     *     
     */
    public void setStructure(StructureChanges value) {
        this.structure = value;
    }

    /**
     * Gets the value of the collection property.
     * 
     * @return
     *     possible object is
     *     {@link CollectionChanges }
     *     
     */
    public CollectionChanges getCollection() {
        return collection;
    }

    /**
     * Sets the value of the collection property.
     * 
     * @param value
     *     allowed object is
     *     {@link CollectionChanges }
     *     
     */
    public void setCollection(CollectionChanges value) {
        this.collection = value;
    }

    /**
     * Gets the value of the structureAndCollection property.
     * 
     * @return
     *     possible object is
     *     {@link StructureAndCollectionChanges }
     *     
     */
    public StructureAndCollectionChanges getStructureAndCollection() {
        return structureAndCollection;
    }

    /**
     * Sets the value of the structureAndCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link StructureAndCollectionChanges }
     *     
     */
    public void setStructureAndCollection(StructureAndCollectionChanges value) {
        this.structureAndCollection = value;
    }

    /**
     * Gets the value of the tree property.
     * 
     * @return
     *     possible object is
     *     {@link TreeChanges }
     *     
     */
    public TreeChanges getTree() {
        return tree;
    }

    /**
     * Sets the value of the tree property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreeChanges }
     *     
     */
    public void setTree(TreeChanges value) {
        this.tree = value;
    }

    /**
     * Gets the value of the sinDesc property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSinDesc() {
        return sinDesc;
    }

    /**
     * Sets the value of the sinDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSinDesc(Long value) {
        this.sinDesc = value;
    }

    /**
     * Gets the value of the sin property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSin() {
        return sin;
    }

    /**
     * Sets the value of the sin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSin(Long value) {
        this.sin = value;
    }

    /**
     * Gets the value of the seq property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getSeq() {
        return seq;
    }

    /**
     * Sets the value of the seq property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setSeq(Long value) {
        this.seq = value;
    }

}
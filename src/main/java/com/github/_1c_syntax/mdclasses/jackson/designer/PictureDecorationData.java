//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.20 at 09:46:06 PM KRAT 
//


package com.github._1c_syntax.mdclasses.jackson.designer;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PictureDecorationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PictureDecorationData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pic" type="{http://v8.1c.ru/8.1/data/ui}Picture" minOccurs="0"/&gt;
 *         &lt;element name="brdClr" type="{http://v8.1c.ru/8.1/data/ui}Color" minOccurs="0"/&gt;
 *         &lt;element name="brd" type="{http://v8.1c.ru/8.1/data/ui}Border" minOccurs="0"/&gt;
 *         &lt;element name="event" type="{http://v8.1c.ru/8.2/managed-application/logform}Event" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="hyper" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="pictureSize" type="{http://v8.1c.ru/8.2/managed-application/logform}PictureSize" /&gt;
 *       &lt;attribute name="zoomable" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="picText" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="startDrag" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="drag" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="clearEvents" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType("PictureDecorationData", namespace = "http://v8.1c.ru/8.2/managed-application/logform", propOrder = {
    "pic",
    "brdClr",
    "brd",
    "event"
})
public class PictureDecorationData {

    protected Picture pic;
    @XmlSchemaType("anySimpleType")
    protected String brdClr;
    protected Border brd;
    protected List<Event> event;
    @JsonProperty("hyper")
    protected Boolean hyper;
    @JsonProperty("pictureSize")
    protected PictureSize pictureSize;
    @JsonProperty("zoomable")
    protected Boolean zoomable;
    @JsonProperty("picText")
    protected String picText;
    @JsonProperty("startDrag")
    protected Boolean startDrag;
    @JsonProperty("drag")
    protected Boolean drag;
    @JsonProperty("clearEvents")
    protected Boolean clearEvents;

    /**
     * Gets the value of the pic property.
     * 
     * @return
     *     possible object is
     *     {@link Picture }
     *     
     */
    public Picture getPic() {
        return pic;
    }

    /**
     * Sets the value of the pic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Picture }
     *     
     */
    public void setPic(Picture value) {
        this.pic = value;
    }

    /**
     * Gets the value of the brdClr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrdClr() {
        return brdClr;
    }

    /**
     * Sets the value of the brdClr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrdClr(String value) {
        this.brdClr = value;
    }

    /**
     * Gets the value of the brd property.
     * 
     * @return
     *     possible object is
     *     {@link Border }
     *     
     */
    public Border getBrd() {
        return brd;
    }

    /**
     * Sets the value of the brd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Border }
     *     
     */
    public void setBrd(Border value) {
        this.brd = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the event property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Event }
     * 
     * 
     */
    public List<Event> getEvent() {
        if (event == null) {
            event = new ArrayList<Event>();
        }
        return this.event;
    }

    /**
     * Gets the value of the hyper property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHyper() {
        return hyper;
    }

    /**
     * Sets the value of the hyper property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHyper(Boolean value) {
        this.hyper = value;
    }

    /**
     * Gets the value of the pictureSize property.
     * 
     * @return
     *     possible object is
     *     {@link PictureSize }
     *     
     */
    public PictureSize getPictureSize() {
        return pictureSize;
    }

    /**
     * Sets the value of the pictureSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link PictureSize }
     *     
     */
    public void setPictureSize(PictureSize value) {
        this.pictureSize = value;
    }

    /**
     * Gets the value of the zoomable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isZoomable() {
        return zoomable;
    }

    /**
     * Sets the value of the zoomable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setZoomable(Boolean value) {
        this.zoomable = value;
    }

    /**
     * Gets the value of the picText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPicText() {
        return picText;
    }

    /**
     * Sets the value of the picText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicText(String value) {
        this.picText = value;
    }

    /**
     * Gets the value of the startDrag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStartDrag() {
        return startDrag;
    }

    /**
     * Sets the value of the startDrag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStartDrag(Boolean value) {
        this.startDrag = value;
    }

    /**
     * Gets the value of the drag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDrag() {
        return drag;
    }

    /**
     * Sets the value of the drag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDrag(Boolean value) {
        this.drag = value;
    }

    /**
     * Gets the value of the clearEvents property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClearEvents() {
        return clearEvents;
    }

    /**
     * Sets the value of the clearEvents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClearEvents(Boolean value) {
        this.clearEvents = value;
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.17 at 10:32:56 PM MSK 
//


package com.github._1c_syntax.mdclasses.jabx.edt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.github._1c_syntax.mdclasses.jabx.edt package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CommonModule_QNAME = new QName("http://g5.1c.ru/v8/dt/metadata/mdclass", "CommonModule");
    private final static QName _Configuration_QNAME = new QName("http://g5.1c.ru/v8/dt/metadata/mdclass", "Configuration");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.github._1c_syntax.mdclasses.jabx.edt
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Configuration }
     */
    public Configuration createConfiguration() {
        return new Configuration();
    }

    /**
     * Create an instance of {@link CommonModule }
     */
    public CommonModule createCommonModule() {
        return new CommonModule();
    }

    /**
     * Create an instance of {@link SynonymType }
     */
    public SynonymType createSynonymType() {
        return new SynonymType();
    }

    /**
     * Create an instance of {@link MDObjectBase }
     */
    public MDObjectBase createMDObjectBase() {
        return new MDObjectBase();
    }

    /**
     * Create an instance of {@link Language }
     */
    public Language createLanguage() {
        return new Language();
    }

    /**
     * Create an instance of {@link ContainedObject }
     */
    public ContainedObject createContainedObject() {
        return new ContainedObject();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommonModule }{@code >}}
     */
    @XmlElementDecl(namespace = "http://g5.1c.ru/v8/dt/metadata/mdclass", name = "CommonModule")
    public JAXBElement<CommonModule> createCommonModule(CommonModule value) {
        return new JAXBElement<CommonModule>(_CommonModule_QNAME, CommonModule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Configuration }{@code >}}
     */
    @XmlElementDecl(namespace = "http://g5.1c.ru/v8/dt/metadata/mdclass", name = "Configuration")
    public JAXBElement<Configuration> createConfiguration(Configuration value) {
        return new JAXBElement<Configuration>(_Configuration_QNAME, Configuration.class, null, value);
    }

}

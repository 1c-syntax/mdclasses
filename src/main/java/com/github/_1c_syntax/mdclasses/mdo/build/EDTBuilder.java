package com.github._1c_syntax.mdclasses.mdo.build;

import com.github._1c_syntax.mdclasses.jabx.edt.MDObjectBase;
import com.github._1c_syntax.mdclasses.jabx.edt.ObjectFactory;
import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.utils.MDO;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class EDTBuilder implements MDOBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(EDTBuilder.class.getSimpleName());

    private File getMDOPath(MDOType type, String path) {
        if (type == MDOType.CONFIGURATION) {
            return new File(path, MDO.EDT_CONFIGURATION_FILENAME);
        } else {
            return new File(path);
        }
    }

    private Class getClassByType(MDOType type) {
        Class edtClass = null;
        String tName = type.name();
        tName = tName.substring(0, 1).toUpperCase() + tName.substring(1).toLowerCase();
        try {
            edtClass = Class.forName("com.github._1c_syntax.mdclasses.jabx.edt." + tName);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return edtClass;
    }

    private MDObjectBase getObject(MDOType type, String path) {
        Class edtClass = getClassByType(type);
        if (edtClass != null) {
            return (MDObjectBase) MDO.unmarshalXML(getMDOPath(type, path), edtClass, ObjectFactory.class);
        }
        return null;
    }

    private void initializeAbstractMDO(AbstractMDO mdoObject, MDObjectBase edtObject) {
        mdoObject.setUuid(ObjectUtils.defaultIfNull(edtObject.getUuid(), ""));
        mdoObject.setName(ObjectUtils.defaultIfNull(edtObject.getName(), ""));
        mdoObject.setComment(ObjectUtils.defaultIfNull(edtObject.getComment(), ""));
//        mdoObject.compatibilityMode = new CompatibilityMode(mdoObject.getConfigurationExtensionCompatibilityMode());

        ////                scriptVariant = ScriptVariant.valueOf(scriptVariantString);
////                uuid = mdoObject.getUuid();
////                name = mdoObject.getName();
////                comment = mdoObject.getComment();

    }

    @Override
    public AbstractMDO build(MDOType type, String path) {
        MDObjectBase edtObject = getObject(type, path);
        if (edtObject != null) {
            AbstractMDO mdoObject = MDO.getMDOObject(type);
            if (mdoObject != null) {
                initializeAbstractMDO(mdoObject, edtObject);
                return mdoObject;
            }
        }
        return null;
    }

//    public static Configuration buildConfiguration(Path mdoPath) {
//        File xml = mdoPath.toFile();
//        Configuration mdoObject = MDO.unmarshalXML(xml, Configuration.class, ObjectFactory.class);
//        if (mdoObject != null) {
//
//            // режим совместимости
////
////
////                // режим встроенного языка
////                String scriptVariantString = mdoObject.getScriptVariant().toUpperCase();
////                scriptVariant = ScriptVariant.valueOf(scriptVariantString);
////                uuid = mdoObject.getUuid();
////                name = mdoObject.getName();
////                comment = mdoObject.getComment();
//
//
//        }
//        return new Configuration();
//    }
}

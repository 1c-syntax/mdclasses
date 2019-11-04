package com.github._1c_syntax.mdclasses.mdo.builder;

import com.github._1c_syntax.mdclasses.jabx.edt.MDObjectBase;
import com.github._1c_syntax.mdclasses.jabx.edt.ObjectFactory;
import com.github._1c_syntax.mdclasses.mdo.classes.Configuration;
import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDO;
import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDOComplex;
import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDOSimple;
import com.github._1c_syntax.mdclasses.mdo.core.CompatibilityMode;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.core.ScriptVariant;
import com.github._1c_syntax.mdclasses.mdo.utils.MDO;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class EDTBuilder implements MDOBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(EDTBuilder.class.getSimpleName());

    private File getMDOPath(MDOType type, String path, String name) {
        if (type == MDOType.CONFIGURATION) {
            return new File(path, MDO.EDT_CONFIGURATION_FILENAME);
        } else if (type == MDOType.COMMON_MODULE) {
            return new File(path, "src/" + type.getMdoClassName() + "s/" + name + "/" + name + ".mdo");
        } else {
            return new File(path);
        }
    }

    private Class getClassByType(MDOType type) {
        Class edtClass = null;
        try {
            edtClass = Class.forName("com.github._1c_syntax.mdclasses.jabx.edt." + type.getMdoClassName());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return edtClass;
    }

    private MDObjectBase getObject(MDOType type, String path, String name) {
        Class edtClass = getClassByType(type);
        if (edtClass != null) {
            return (MDObjectBase) MDO.unmarshalXML(getMDOPath(type, path, name), edtClass, ObjectFactory.class);
        }
        return null;
    }

    @Override
    public AbstractMDO build(MDOType type, String path, String name) {
        MDObjectBase edtObject = getObject(type, path, name);
        if (edtObject != null) {
            AbstractMDO mdoObject = MDO.getMDOObject(type);
            initialize(mdoObject, edtObject);
            return mdoObject;
        }
        return null;
    }

    private void initialize(AbstractMDO mdoObject, MDObjectBase edtObject) {
        if (mdoObject == null) {
            return;
        }

        initializeAbstractMDO(mdoObject, edtObject);
        if (mdoObject instanceof AbstractMDOSimple) {
            initializeAbstractMDOSimple(mdoObject, edtObject);
        }
        if (mdoObject instanceof AbstractMDOComplex) {
            initializeAbstractMDOComplex(mdoObject, edtObject);
        }

        if (mdoObject instanceof Configuration) {
            initializeConfiguration((Configuration) mdoObject, edtObject);
        }

    }

    private void initializeAbstractMDO(AbstractMDO mdoObject, MDObjectBase edtObject) {
        mdoObject.setUuid(ObjectUtils.defaultIfNull(edtObject.getUuid(), ""));
        mdoObject.setName(ObjectUtils.defaultIfNull(edtObject.getName(), ""));
        mdoObject.setComment(ObjectUtils.defaultIfNull(edtObject.getComment(), ""));
    }

    private void initializeAbstractMDOSimple(AbstractMDO mdoObject, MDObjectBase edtObject) {
        try {
            Field[] fields = mdoObject.getClass().getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.set(mdoObject, edtObject.getClass().getField(field.getName()));
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void initializeAbstractMDOComplex(AbstractMDO mdoObject, MDObjectBase edtObject) {

    }

    private void initializeConfiguration(Configuration mdoObject, MDObjectBase edtObject) {
        try {
            Class edtObjectClass = edtObject.getClass();
            mdoObject.setCompatibilityMode(new CompatibilityMode(
                    edtObjectClass.getMethod("getConfigurationExtensionCompatibilityMode")
                    .invoke(edtObject).toString()));

            mdoObject.setScriptVariant(ScriptVariant.valueOf(
                    edtObjectClass.getMethod("getScriptVariant")
                    .invoke(edtObject).toString().toUpperCase()));
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}

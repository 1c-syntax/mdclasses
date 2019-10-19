package com.github._1c_syntax.mdclasses.mdo.classes;

import com.github._1c_syntax.mdclasses.mdo.core.AbstractMDOSimple;
import com.github._1c_syntax.mdclasses.mdo.core.CompatibilityMode;
import com.github._1c_syntax.mdclasses.mdo.core.ConfigurationSource;
import com.github._1c_syntax.mdclasses.mdo.core.MDOType;
import com.github._1c_syntax.mdclasses.mdo.core.ScriptVariant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@EqualsAndHashCode(callSuper = false)
public class Configuration extends AbstractMDOSimple {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class.getSimpleName());

    private final Path rootPath;
    protected CompatibilityMode compatibilityMode;
    protected ScriptVariant scriptVariant;

    public Configuration(Path rootPath) {
        super(MDOType.CONFIGURATION);
        this.rootPath = rootPath;

        configurationSource = ConfigurationSource.EMPTY;

        String rootPathString = rootPath.toAbsolutePath().toString();

        File rootConfiguration = new File(rootPathString, "Configuration.xml");
        if (rootConfiguration.exists()) {
            configurationSource = ConfigurationSource.DESIGNER;
        } else {
            rootConfiguration = Paths.get(rootPathString, "src", "Configuration", "Configuration.mdo").toFile();
            if (rootConfiguration.exists()) {
                configurationSource = ConfigurationSource.EDT;
            }
        }
        if (configurationSource != ConfigurationSource.EMPTY) {
            mdoPath = rootConfiguration.toPath();
        }

        initialize();
    }

    @Override
    public void initialize() {
        if (configurationSource == ConfigurationSource.EMPTY) {
            return;
        }

        File xml = mdoPath.toFile();

        if (configurationSource == ConfigurationSource.EDT) {
            com.github._1c_syntax.mdclasses.jabx.edt.Configuration mdoObject = AbstractMDOSimple.unmarshalMDO(xml, com.github._1c_syntax.mdclasses.jabx.edt.Configuration.class, com.github._1c_syntax.mdclasses.jabx.edt.ObjectFactory.class);
            if (mdoObject != null) {
                // режим совместимости
                compatibilityMode = new CompatibilityMode(mdoObject.getConfigurationExtensionCompatibilityMode());

                // режим встроенного языка
                String scriptVariantString = mdoObject.getScriptVariant().toUpperCase();
                scriptVariant = ScriptVariant.valueOf(scriptVariantString);
                uuid = mdoObject.getUuid();
                name = mdoObject.getName();
                comment = mdoObject.getComment();
            }
        } else {
            com.github._1c_syntax.mdclasses.jabx.original.MetaDataObject mdoObject = AbstractMDOSimple.unmarshalMDO(xml, com.github._1c_syntax.mdclasses.jabx.original.MetaDataObject.class, com.github._1c_syntax.mdclasses.jabx.original.ObjectFactory.class);
            if (mdoObject != null) {
                com.github._1c_syntax.mdclasses.jabx.original.Configuration mdoConfiguration = mdoObject.getConfiguration();
                uuid = mdoConfiguration.getUuid();

                if (mdoConfiguration != null) {
                    com.github._1c_syntax.mdclasses.jabx.original.ConfigurationProperties configurationProperties = mdoConfiguration.getProperties();
                    if (configurationProperties != null) {
                        // режим совместимости
                        if (configurationProperties.getCompatibilityMode() != null) {
                            compatibilityMode = new CompatibilityMode(configurationProperties.getCompatibilityMode().name());
                        }

                        // режим встроенного языка
                        if (configurationProperties.getScriptVariant() != null) {
                            String scriptVariantString = configurationProperties.getScriptVariant().name().toUpperCase();
                            scriptVariant = ScriptVariant.valueOf(scriptVariantString);
                        }

                        name = configurationProperties.getName();
                        comment = configurationProperties.getComment();

                    }
                }
            }
        }

        //                setModulesByType(Common.getModuleTypesByPath(rootPath));

    }

}

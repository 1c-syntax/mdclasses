package org.github._1c_syntax.mdclasses.metadata;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class Configuration {
    private final ConfigurationSource configurationSource;
    private CompatibilityMode compatibilityMode;
    private ScriptVariant scriptVariant;
    @Getter
    @Setter // TODO: а надо ли?
    private Map<URI, ModuleType> modulesByType = new HashMap<>();

    public ModuleType getModuleTypeByURI(URI uri) {
        return modulesByType.get(uri);
    }
}

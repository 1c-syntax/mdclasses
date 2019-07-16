package org.github._1c_syntax.mdclasses.metadata;

import lombok.*;
import org.github._1c_syntax.mdclasses.metadata.additional.ConfigurationSource;
import org.github._1c_syntax.mdclasses.metadata.additional.CompatibilityMode;
import org.github._1c_syntax.mdclasses.metadata.additional.ScriptVariant;

@Data
@RequiredArgsConstructor
public class Configuration {
    private final ConfigurationSource configurationSource;
    private CompatibilityMode compatibilityMode;
    private ScriptVariant scriptVariant;
}

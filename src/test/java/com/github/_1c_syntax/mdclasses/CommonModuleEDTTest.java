package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.AbstractCommonModule;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonModuleEDTTest {
    @Test
    void test() {
        File file = new File("src/test/resources/metadata/original/Documents/ПоступлениеТоваровУслуг/Ext/ManagerModule.bsl");
        AbstractCommonModule commonModule;
        assertThat(configuration.getModuleType(file.toURI())).isEqualTo(ModuleType.ManagerModule);
    }
}

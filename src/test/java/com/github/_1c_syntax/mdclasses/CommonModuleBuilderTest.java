package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.metadata.CommonModuleBuilder;
import com.github._1c_syntax.mdclasses.metadata.additional.*;
import com.github._1c_syntax.mdclasses.metadata.commonmodules.AbstractCommonModule;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonModuleBuilderTest {

    @Test
    void testBuilderEDT() {
        File file = new File("src/test/resources/metadata/edt/src/CommonModules/ПростойОбщийМодуль/Module.bsl");
        CommonModuleBuilder commonModuleBuilder = new CommonModuleBuilder(file.toPath());
        AbstractCommonModule commonModule = commonModuleBuilder.build();

        assertThat(commonModule.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.isClientManagedApplication()).isFalse();
        assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
        assertThat(commonModule.isExternalConnection()).isFalse();
        assertThat(commonModule.isGlobal()).isFalse();
        assertThat(commonModule.isPrivileged()).isFalse();
        assertThat(commonModule.isServer()).isTrue();
        assertThat(commonModule.isServerCall()).isFalse();

        file = new File("src/test/resources/metadata/edt/src/CommonModules/ОбщийМодульПовтИспСеанс/Module.bsl");
        commonModuleBuilder = new CommonModuleBuilder(file.toPath());
        commonModule = commonModuleBuilder.build();

        assertThat(commonModule.getName()).isEqualTo("ОбщийМодульПовтИспСеанс");
        assertThat(commonModule.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);
        assertThat(commonModule.isClientManagedApplication()).isFalse();
        assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
        assertThat(commonModule.isExternalConnection()).isFalse();
        assertThat(commonModule.isGlobal()).isFalse();
        assertThat(commonModule.isPrivileged()).isFalse();
        assertThat(commonModule.isServer()).isTrue();
        assertThat(commonModule.isServerCall()).isFalse();

        file = new File("src/test/resources/metadata/edt/src/CommonModules/ГлобальныйОбщийМодуль/Module.bsl");
        commonModuleBuilder = new CommonModuleBuilder(file.toPath());
        commonModule = commonModuleBuilder.build();
        assertThat(commonModule.getName()).isEqualTo("ГлобальныйОбщийМодуль");
        assertThat(commonModule.getConfigurationSource()).isEqualTo(ConfigurationSource.EDT);
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.isClientManagedApplication()).isTrue();
        assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
        assertThat(commonModule.isExternalConnection()).isTrue();
        assertThat(commonModule.isGlobal()).isTrue();
        assertThat(commonModule.isPrivileged()).isFalse();
        assertThat(commonModule.isServer()).isTrue();
        assertThat(commonModule.isServerCall()).isFalse();
    }

    @Test
    void testBuilderOrigin() {
        File file = new File("src/test/resources/metadata/original/CommonModules/ПростойОбщийМодуль/Ext/Module.bsl");
        CommonModuleBuilder commonModuleBuilder = new CommonModuleBuilder(file.toPath());
        AbstractCommonModule commonModule = commonModuleBuilder.build();

        assertThat(commonModule.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
        assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.isClientManagedApplication()).isFalse();
        assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
        assertThat(commonModule.isExternalConnection()).isFalse();
        assertThat(commonModule.isGlobal()).isFalse();
        assertThat(commonModule.isPrivileged()).isFalse();
        assertThat(commonModule.isServer()).isTrue();
        assertThat(commonModule.isServerCall()).isFalse();

        file = new File("src/test/resources/metadata/original/CommonModules/ГлобальныйКлиент/Ext/Module.bsl");
        commonModuleBuilder = new CommonModuleBuilder(file.toPath());
        commonModule = commonModuleBuilder.build();

        assertThat(commonModule.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
        assertThat(commonModule.getName()).isEqualTo("ГлобальныйКлиент");
        assertThat(commonModule.getComment()).isEqualTo("Комментарий");
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.isClientManagedApplication()).isTrue();
        assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
        assertThat(commonModule.isExternalConnection()).isFalse();
        assertThat(commonModule.isGlobal()).isTrue();
        assertThat(commonModule.isPrivileged()).isFalse();
        assertThat(commonModule.isServer()).isFalse();
        assertThat(commonModule.isServerCall()).isFalse();

        file = new File("src/test/resources/metadata/original/CommonModules/ОбщегоНазначенияПовторногоИспользования/Ext/Module.bsl");
        commonModuleBuilder = new CommonModuleBuilder(file.toPath());
        commonModule = commonModuleBuilder.build();

        assertThat(commonModule.getConfigurationSource()).isEqualTo(ConfigurationSource.DESIGNER);
        assertThat(commonModule.getName()).isEqualTo("ОбщегоНазначенияПовторногоИспользования");
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);
        assertThat(commonModule.isClientManagedApplication()).isTrue();
        assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
        assertThat(commonModule.isExternalConnection()).isTrue();
        assertThat(commonModule.isGlobal()).isFalse();
        assertThat(commonModule.isPrivileged()).isFalse();
        assertThat(commonModule.isServer()).isTrue();
        assertThat(commonModule.isServerCall()).isFalse();
    }
}

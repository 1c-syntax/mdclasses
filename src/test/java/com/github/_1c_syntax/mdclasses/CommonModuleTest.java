package com.github._1c_syntax.mdclasses;

import com.github._1c_syntax.mdclasses.mdo.CommonModule;
import com.github._1c_syntax.mdclasses.metadata.Configuration;
import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonModuleTest {

    @Test
    void testBuilderEDT() {

        File srcPath = new File("src/test/resources/metadata/edt");
        Configuration configuration = Configuration.newBuilder(srcPath.toPath()).build();

        CommonModule commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE,
                "ПростойОбщийМодуль");
        assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.getClientManagedApplication()).isFalse();
        assertThat(commonModule.getClientOrdinaryApplication()).isFalse();
        assertThat(commonModule.getExternalConnection()).isFalse();
        assertThat(commonModule.getGlobal()).isFalse();
        assertThat(commonModule.getPrivileged()).isFalse();
        assertThat(commonModule.getServer()).isTrue();
        assertThat(commonModule.getServerCall()).isFalse();

        commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE,
                "ОбщийМодульПовтИспСеанс");
        assertThat(commonModule.getName()).isEqualTo("ОбщийМодульПовтИспСеанс");
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);
        assertThat(commonModule.getClientManagedApplication()).isFalse();
        assertThat(commonModule.getClientOrdinaryApplication()).isFalse();
        assertThat(commonModule.getExternalConnection()).isFalse();
        assertThat(commonModule.getGlobal()).isFalse();
        assertThat(commonModule.getPrivileged()).isFalse();
        assertThat(commonModule.getServer()).isTrue();
        assertThat(commonModule.getServerCall()).isFalse();

        commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE,
                "ГлобальныйОбщийМодуль");
        assertThat(commonModule.getName()).isEqualTo("ГлобальныйОбщийМодуль");
        assertThat(commonModule.getComment()).isEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.getClientManagedApplication()).isTrue();
        assertThat(commonModule.getClientOrdinaryApplication()).isTrue();
        assertThat(commonModule.getExternalConnection()).isTrue();
        assertThat(commonModule.getGlobal()).isTrue();
        assertThat(commonModule.getPrivileged()).isFalse();
        assertThat(commonModule.getServer()).isTrue();
        assertThat(commonModule.getServerCall()).isFalse();
    }

    @Test
    void testBuilderOrigin() {
        File srcPath = new File("src/test/resources/metadata/original");
        Configuration configuration = Configuration.newBuilder(srcPath.toPath()).build();

        CommonModule commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE,
                "ПростойОбщийМодуль");

        assertThat(commonModule.getName()).isEqualTo("ПростойОбщийМодуль");
        assertThat(commonModule.getComment()).isNullOrEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.getClientManagedApplication()).isFalse();
        assertThat(commonModule.getClientOrdinaryApplication()).isFalse();
        assertThat(commonModule.getExternalConnection()).isFalse();
        assertThat(commonModule.getGlobal()).isFalse();
        assertThat(commonModule.getPrivileged()).isFalse();
        assertThat(commonModule.getServer()).isTrue();
        assertThat(commonModule.getServerCall()).isFalse();

        commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE,
                "ГлобальныйКлиент");
        assertThat(commonModule.getName()).isEqualTo("ГлобальныйКлиент");
        assertThat(commonModule.getComment()).isEqualTo("Комментарий");
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
        assertThat(commonModule.getClientManagedApplication()).isTrue();
        assertThat(commonModule.getClientOrdinaryApplication()).isTrue();
        assertThat(commonModule.getExternalConnection()).isFalse();
        assertThat(commonModule.getGlobal()).isTrue();
        assertThat(commonModule.getPrivileged()).isFalse();
        assertThat(commonModule.getServer()).isFalse();
        assertThat(commonModule.getServerCall()).isFalse();

        commonModule = (CommonModule) configuration.getChild(MDOType.COMMON_MODULE,
                "ОбщегоНазначенияПовторногоИспользования");
        assertThat(commonModule.getName()).isEqualTo("ОбщегоНазначенияПовторногоИспользования");
        assertThat(commonModule.getComment()).isNullOrEmpty();
        assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);
        assertThat(commonModule.getClientManagedApplication()).isTrue();
        assertThat(commonModule.getClientOrdinaryApplication()).isTrue();
        assertThat(commonModule.getExternalConnection()).isTrue();
        assertThat(commonModule.getGlobal()).isFalse();
        assertThat(commonModule.getPrivileged()).isFalse();
        assertThat(commonModule.getServer()).isTrue();
        assertThat(commonModule.getServerCall()).isFalse();
    }
}

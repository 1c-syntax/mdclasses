package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import com.github._1c_syntax.mdclasses.metadata.additional.ReturnValueReuse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommonModuleTest extends AbstractMDOTest {

  CommonModuleTest() {
    super(MDOType.COMMON_MODULE);
  }

  @Override
  @Test
  protected void testEDT() {

    var mdo = getMDObjectEDT("CommonModules/ГлобальныйОбщийМодуль/ГлобальныйОбщийМодуль.mdo");
    checkBaseField(mdo, CommonModule.class, "ГлобальныйОбщийМодуль",
      "9e9c021c-bdbd-4804-a53a-9442ba9eb18c");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "CommonModules/ГлобальныйОбщийМодуль", ModuleType.CommonModule);

    var commonModule = (CommonModule) mdo;
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isTrue();
    assertThat(commonModule.isGlobal()).isTrue();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);

    commonModule = (CommonModule) getMDObjectEDT("CommonModules/ПростойОбщийМодуль/ПростойОбщийМодуль.mdo");
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);

    commonModule = (CommonModule) getMDObjectEDT("CommonModules/ОбщийМодульПовтИспСеанс/ОбщийМодульПовтИспСеанс.mdo");
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DURING_SESSION);

  }

  @Override
  @Test
  protected void testDesigner() {

    var mdo = getMDObjectDesigner("CommonModules/ГлобальныйОбщийМодуль.xml");
    checkBaseField(mdo, CommonModule.class, "ГлобальныйОбщийМодуль",
      "9e9c021c-bdbd-4804-a53a-9442ba9eb18c");
    checkNoChildren(mdo);
    checkModules(((MDObjectBSL) mdo).getModules(), 1,
      "CommonModules/ГлобальныйОбщийМодуль", ModuleType.CommonModule);

    var commonModule = (CommonModule) mdo;
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isTrue();
    assertThat(commonModule.isClientOrdinaryApplication()).isTrue();
    assertThat(commonModule.isExternalConnection()).isTrue();
    assertThat(commonModule.isGlobal()).isTrue();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (CommonModule) getMDObjectDesigner("CommonModules/ПростойОбщийМодуль.xml");
    assertThat(commonModule.getReturnValuesReuse()).isEqualTo(ReturnValueReuse.DONT_USE);
    assertThat(commonModule.isClientManagedApplication()).isFalse();
    assertThat(commonModule.isClientOrdinaryApplication()).isFalse();
    assertThat(commonModule.isExternalConnection()).isFalse();
    assertThat(commonModule.isGlobal()).isFalse();
    assertThat(commonModule.isPrivileged()).isFalse();
    assertThat(commonModule.isServer()).isTrue();
    assertThat(commonModule.isServerCall()).isFalse();

    commonModule = (CommonModule) getMDObjectDesigner("CommonModules/ОбщегоНазначенияПовторногоИспользования.xml");
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

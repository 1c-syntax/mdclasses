package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MDOSynonymTest extends AbstractMDOTest {

    public MDOSynonymTest() {
        super(MDOType.ACCOUNTING_REGISTER);
    }

    @Override
    @Test
    void testEDT() {
        var mdo = getMDObjectEDT("AccountingRegisters/РегистрБухгалтерии1/РегистрБухгалтерии1.mdo");
        performTwoLanguagesCheck(mdo);

        var secondMdo = getMDObjectEDT("AccountingRegisters/РегистрБухгалтерии2/РегистрБухгалтерии2.mdo");
        performOneLanguageCheck(secondMdo);

        var thirdMdo = getMDObjectEDT("AccountingRegisters/РегистрБухгалтерии3/РегистрБухгалтерии3.mdo");
        performEmptyLanguageCheck(thirdMdo);
    }

    @Override
    @Test
    void testDesigner() {
        var mdo = getMDObjectDesigner("AccountingRegisters/РегистрБухгалтерии1.xml");
        performTwoLanguagesCheck(mdo);

        var secondMdo = getMDObjectDesigner("AccountingRegisters/РегистрБухгалтерии2.xml");
        performOneLanguageCheck(secondMdo);

        var thirdMdo = getMDObjectDesigner("AccountingRegisters/РегистрБухгалтерии3.xml");
        performEmptyLanguageCheck(thirdMdo);
    }

    private void performEmptyLanguageCheck(MDObjectBase mdo) {
        assertThat(mdo.getSynonyms()).isEmpty();
    }

    private void performOneLanguageCheck(MDObjectBase mdo) {
        assertThat(mdo.getSynonyms())
                .hasSize(1);
        assertThat(mdo.getSynonyms().get(0).getLanguage()).isEqualTo("ru");
        assertThat(mdo.getSynonyms().get(0).getContent()).isEqualTo("Регистр бухгалтерии");
    }

    private void performTwoLanguagesCheck(MDObjectBase mdo) {
        assertThat(mdo.getSynonyms())
                .hasSize(2);
        assertThat(mdo.getSynonyms().get(0).getLanguage()).isEqualTo("ru");
        assertThat(mdo.getSynonyms().get(0).getContent()).isEqualTo("Регистр бухгалтерии");
        assertThat(mdo.getSynonyms().get(1).getLanguage()).isEqualTo("en");
        assertThat(mdo.getSynonyms().get(1).getContent()).isEqualTo("Accounting register");
    }
}

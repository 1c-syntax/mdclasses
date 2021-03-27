package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import com.github._1c_syntax.mdclasses.metadata.additional.ModuleType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BotTest extends AbstractMDOTest {

    BotTest() {
        super(MDOType.BOT);
    }

    @Override
    @Test
    void testEDT() {
        var mdo = getMDObjectEDT("Bots/Бот1/Бот1.mdo");
        checkBaseField(mdo, Bot.class, "Бот1",
                "89c58e6a-00ee-49b9-8717-d1dd272f9b96");

        checkNoChildren(mdo);
        checkModules(((MDObjectBSL) mdo).getModules(), 1,
                "Bots/Бот1", ModuleType.BotModule);
        var bot = (Bot) mdo;
        assertThat(bot.isPredefined()).isTrue();
    }

    @Override
    @Test
    void testDesigner() {
        var mdo = getMDObjectDesigner("Bots/Бот1.xml");
        checkBaseField(mdo, Bot.class, "Бот1",
                "89c58e6a-00ee-49b9-8717-d1dd272f9b96");

        checkNoChildren(mdo);
        checkModules(((MDObjectBSL) mdo).getModules(), 1,
                "Bots/Бот1", ModuleType.BotModule);
        var bot = (Bot) mdo;
        assertThat(bot.isPredefined()).isTrue();
    }
}
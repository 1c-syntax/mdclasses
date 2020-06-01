package com.github._1c_syntax.mdclasses.mdo;

import com.github._1c_syntax.mdclasses.metadata.additional.MDOType;
import org.junit.jupiter.api.Test;

class ScheduledJobTest extends AbstractMDOTest {
  ScheduledJobTest() {
    super(MDOType.SCHEDULED_JOB);
  }

  @Override
  @Test
  protected void testEDT() {
    var mdo = getMDObjectEDT("ScheduledJobs/РегламентноеЗадание1/РегламентноеЗадание1.mdo");
    checkBaseField(mdo, ScheduledJob.class, "РегламентноеЗадание1",
      "0de0c839-4427-46d9-be68-302f88ac162c");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }

  @Override
  @Test
  protected void testDesigner() {
    var mdo = getMDObjectDesigner("ScheduledJobs/РегламентноеЗадание1.xml");
    checkBaseField(mdo, ScheduledJob.class, "РегламентноеЗадание1",
      "0de0c839-4427-46d9-be68-302f88ac162c");
    checkNoChildren(mdo);
    checkNoModules(mdo);
  }
}

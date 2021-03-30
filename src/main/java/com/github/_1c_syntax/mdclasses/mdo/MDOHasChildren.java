package com.github._1c_syntax.mdclasses.mdo;

import java.util.Set;

/**
 * Интерфейс объектов, имеющих дочерние
 */
public interface MDOHasChildren {
  Set<AbstractMDObjectBase> getChildren();
}

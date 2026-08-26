package com.github._1c_syntax.bsl.mdclasses;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Временный инструментарий: трекинг уникальных (с учётом регистра) имён,
 * по которым выполнялся поиск общего модуля через findCommonModule.
 */
public final class CommonModuleSearchTracker {

  /**
   * Множество уникальных ключей поиска с учётом регистра символов.
   */
  public static final Set<String> KEYS = ConcurrentHashMap.newKeySet();

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(() ->
      System.err.println("UNIQUE_COMMON_MODULE_SEARCH_KEYS=" + KEYS.size())));
  }

  private CommonModuleSearchTracker() {
    // utility
  }

  /**
   * Зафиксировать ключ поиска общего модуля.
   *
   * @param name имя, по которому выполняется поиск (с учётом регистра)
   */
  public static void track(String name) {
    if (name != null) {
      KEYS.add(name);
    }
  }
}

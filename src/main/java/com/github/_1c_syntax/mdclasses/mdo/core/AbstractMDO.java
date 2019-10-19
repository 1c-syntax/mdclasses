package com.github._1c_syntax.mdclasses.mdo.core;

import lombok.Data;

import java.util.Map;

/**
 * Базовый класс всех метаданных
 */
@Data
public abstract class AbstractMDO {
    protected final MDOType mdoType;
    protected String uuid;
    protected String name;
    protected Map<Language, String> synonym; // TODO Набо будет реализовать
    protected String comment;
}

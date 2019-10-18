package com.github._1c_syntax.mdclasses.metadata.additional;

public enum ReturnValueReuse {

    DURING_REQUEST("На время вызова"),
    DURING_SESSION("На время сессии"),
    DONT_USE("Не используется");

    ReturnValueReuse(String ru) {
    }
}

package com.github.jdussouillez.testapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Loggers {

    public static final Logger MAIN = LogManager.getLogger("com.github.jdussouillez.testapp");

    private Loggers() {
        // Nothing to do here
    }
}

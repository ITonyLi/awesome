package com.github.itonyli;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hua {

    private static final Logger logger = LoggerFactory.getLogger(Hua.class);

    @Test
    public void test() {
        int result = -1;
        try {
            t();
        } catch (Exception e) {
            logger.debug("test: {}", result, e);
        }

        // commit first

        // commit second

        // commit third

    }


    public void t() {
        throw new RuntimeException("hh");
    }

}



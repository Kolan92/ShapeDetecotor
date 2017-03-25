package com.filtertes;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.io.File;
import java.time.LocalDateTime;

@RunWith(Suite.class)
@SuiteClasses({FiltersTests.class})
public class TestSuite {

    @BeforeClass
    public static void setUp() {
        System.out.println("setting up\n");
        String today = LocalDateTime.now().toString().replace(':', '.');
        File dir = new File("TestOutput\\FilterTests_" + today);
        dir.mkdir();
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("tearing down\n");
    }

}
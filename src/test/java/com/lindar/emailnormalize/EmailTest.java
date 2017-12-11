package com.lindar.emailnormalize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class EmailTest {

    @Parameterized.Parameters(name = "{index}: ({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "test@gmail.com", "test@gmail.com" },
                { "test@googlemail.com", "test@gmail.com" },
                { "test.demo@gmail.com", "testdemo@gmail.com" },
                { "test+filter@googlemail.com", "test@gmail.com" },
                { "test.demo+filter@gmail.com", "testdemo@gmail.com" },
        });
    }

    private String fInput;

    private String fExpected;

    public EmailTest(String input, String expected) {
        fInput= input;
        fExpected= expected;
    }


    @Test
    public void testNormalizeEmail() {
        assertEquals(fExpected, Email.normalize(fInput));
    }

}
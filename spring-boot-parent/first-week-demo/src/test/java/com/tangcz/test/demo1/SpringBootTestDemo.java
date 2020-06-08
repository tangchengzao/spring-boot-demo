package com.tangcz.test.demo1;

import com.tangcz.springbootdemo.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class SpringBootTestDemo {

    @Test
    public void testFirst() {
        System.out.println("Hello test");
        assertEquals(1, 1);
    }

}

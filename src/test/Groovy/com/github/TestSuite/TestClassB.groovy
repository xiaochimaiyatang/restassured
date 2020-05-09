package com.github.TestSuite

import org.junit.Test
import org.junit.experimental.categories.Category

@Category([FirstCategory])
class TestClassB {

    @Test
    void firstMethod(){
        println("this is first method from TestClassB")
    }

    @Test
    void secondMethod(){
        print("this is second method from TestClassB")
    }
}

package com.github.TestSuiteclass

import com.github.TestSuite.FirstCategory
import com.github.TestSuite.SecondCategory
import org.junit.Test
import org.junit.experimental.categories.Category

//@Category([SecondCategory])
class TestClassA {
       //这里表示firstMethod属于FirstCategory
    @Category([FirstCategory])
    @Test()
    void firstMethod() {
        println("this is first method from TestClassA")
    }
       //这里表示secondMethod属于SecondCategory
    @Category([SecondCategory])
    @Test()
    void secondMethod() {
        println("this is second method from TestClassA")
    }
}
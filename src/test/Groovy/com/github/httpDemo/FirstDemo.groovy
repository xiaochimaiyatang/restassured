package com.github.httpDemo


import spock.lang.Specification

import static io.restassured.RestAssured.given

class FirstDemo extends Specification {
    def "should call mock api successfully"() {     //spock框架（BDD框架）语法，所有case都是def开头，def后面是该case的描述信息
        given: "no given"                           //spock框架语法，given-when-then三段式写法，given/when/then后是描述信息
        when: "call mock api api"
        given().baseUri("http://localhost:9090")    //这里输入接口的baseUri
                .when()
                .get("api/getUserDetails")          //输入接口的地址
                .then()
                .assertThat().statusCode(200)      //这里校验调用接口后返回的状态码是200，如果不是200，调用会失败
        then: "no then"
    }


}


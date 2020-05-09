package com.github.httpDemo

import spock.lang.Specification

import static io.restassured.RestAssured.given

class PostHeaderSettingDemo extends Specification {
    def "should add user successfully"() {
        given: "no given"
        def body = "{\"name\": {\"mainName\": \"zhangshang\",\"alias\": \"zhangshangalias\"},\"age\":123}"
        when: "call get user by name api"
        given().log().all()
                .baseUri("http://localhost:9090")
                .auth().preemptive().basic("root", "root123")  //对basicAuth的的设置
                .header("Content-Type", "application/json")   //设置header中Content-Type
                .cookie("session", "123456")                 //设置session中的cookie
                .body(body)                                 //传入Body Sring方式
                .when()
                .post("/api/addUser")
                .then()
                .log().all()
                .assertThat().statusCode(200)
        then: "no then"
    }
}

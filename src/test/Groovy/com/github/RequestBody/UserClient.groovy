package com.github.RequestBody


import org.junit.Assert
import spock.lang.Specification
import utils.FileService

import static io.restassured.RestAssured.given

class UserClient {
    def addUserWithFile(File file) {

        def res = given().log().all().baseUri("http://localhost:9090")
                .contentType("application/json")
                .when()
                .body(file)                                    //body参数中传入File对象
                .post("/api/addUserDetails")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().getBody().asString()    //获取接口的response body
        res

    }

    def addUserWithString(String body) {
        def res = given().log().all().baseUri("http://localhost:9090")
                .contentType("application/json")
                .when()
                .body(body)                                  //body参数中传入接口的request body字符串
                .post("/api/addUserDetails")
                .then().log().all().assertThat().statusCode(200)
                .extract(). response().getBody().asString()  //获取接口的response body
        res
    }

}

class Case extends Specification {
    FileService fileService
    UserClient userClient

    def setup() {
        fileService = new FileService()
        userClient = new UserClient()
    }

    def "should add user successfully"() {
        given: "no given"

        when: "call the add user api"
        // 创建addUser.json文件，将前面列出的request body内容放到该文件中。
        def file = fileService.createFile("./src/test/resources/addUser.json")  //获取文件对象


        then: "get the correct response"
        Assert.assertEquals(userClient.addUserWithFile(file), "add user successfully")
        //将文件对象传入userClient.addUserWithFile()中，这里可以开启.log().all()查看接口是否返回正确的response body。
    }

}
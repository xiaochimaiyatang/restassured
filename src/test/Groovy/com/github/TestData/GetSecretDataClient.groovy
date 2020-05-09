package com.github.TestData

import org.junit.Test
import utils.ConfigParse

import static io.restassured.RestAssured.given

class GetSecretDataClient {
    ConfigParse configParse
    TestDataService userTestData
    def configs
    def users


    void getDataWithEncryptPassword() {
        configParse = new ConfigParse()
        userTestData = new TestDataService()
        configs = configParse.getGlobalConfig()                      //获取yaml中的配置信息
        users = userTestData.getUserDataByRole("ForGetDataApiSec")


        def res = given().baseUri((String) configs.mockServerUrl)
                .auth().preemptive().basic(users.username, userTestData.getPasswordByUserName(users.username))   //这里调用userTestData.getPasswordByUserName解密
                .when()
                .get("/api/getData")
                .then().assertThat().statusCode(200)
                .extract().response().getBody().asString()
        println res
    }
    @Test()
    void callGetDataWithEncryptPassword() {
        getDataWithEncryptPassword()  //编写测试脚本验证上面的接口调用是否正确
    }


}
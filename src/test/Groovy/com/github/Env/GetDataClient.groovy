package com.github.Env

import org.junit.Test
import utils.ConfigParse

import static io.restassured.RestAssured.given

class GetDataClient {
    ConfigParse configParse
    def configs

    GetDataClient(){
        configParse=new ConfigParse()
        configs=configParse.getGlobalConfig()
    }

    void getData(){
        def res = given().log().all().baseUri((String) configs.mockServerUrl)     //调用接口时，接口的baseUri来自yaml文件
                .auth().preemptive().basic("apiUsername", "apiPassword")
                .when()
                .get("/api/getData")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().getBody().asString()
        println res
    }

    @Test
    void callGetData(){
        getData()

    }
}

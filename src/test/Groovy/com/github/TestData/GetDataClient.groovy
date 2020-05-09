package com.github.TestData

import org.junit.Test
import spock.lang.Specification
import utils.ConfigParse

import static io.restassured.RestAssured.given

class GetDataClient {
    ConfigParse configParse
    TestDataService testDataService
    def configs
    def users

//    GetDataClient(roleName){
//        configParse=new ConfigParse()
//        testDataService=new TestDataService()
//        configs=configParse.getGlobalConfig()
//        users=testDataService.getUserDataByRole(roleName)
//    }

    void getDataWithCsvUser(roleName, statucode) {
        configParse = new ConfigParse()
        testDataService = new TestDataService()
        configs = configParse.getGlobalConfig()
        users = testDataService.getUserDataByRole(roleName)
        def res = given().log().all().baseUri((String) configs.mockServerUrl)
                .auth().preemptive().basic(users.username, users.password)
                .when()
                .get("/api/getData")
                .then().log().all().assertThat().statusCode(statucode)
                .extract().response().getBody().asString()
        println res
    }

    @Test
    void callGetDataWithCsvUser() {
        getDataWithCsvUser("ForGetDataApi", 200)
    }

    @Test
    void callGetDataWithCsvUserSce() {
        getDataWithCsvUser("ForGetDataApiSec", 404)
    }

    @Test
    void callGetDataWithCsvUser1() {
        getDataWithCsvUser("ForOther", 404)
    }

}

class GetDataTest extends Specification{
    def "assert user role authority"(){
        given:"no given"
        GetDataClient gc=new GetDataClient()
        when:"call get data api"
        gc.getDataWithCsvUser(roleName,statuscode)
        then:""
        where:
        roleName|statuscode
        "ForGetDataApi"|200
        "ForOther"|404
        "ForGetDataApiSec"|404
    }
}

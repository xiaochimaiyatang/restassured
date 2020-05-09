package com.github.RequestBody

import groovy.json.JsonSlurper
import spock.lang.Specification

import static io.restassured.RestAssured.given
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath
import static org.junit.Assert.assertThat

class ResumeClient {
    def getResumeDetails(){
        def res = given().baseUri("http://localhost:9090")
                .when()
                .get("/api/getResume")
                .then().assertThat().statusCode(200)
                .extract().response().getBody().asString()       //获取接口的response body
        res
    }

    def getResumeSchemaValidate(filePath) {
        given().log().all().baseUri("http://localhost:9090")
                .when()
                .get("/api/getResume")
                .then().log().all().assertThat()
                .body(matchesJsonSchemaInClasspath((String)filePath))    //在调用接口时通过matchsJsonSchemaInClassPath校验接口的response schema
    }

    def getResume2() {
        def payLoad=  given().baseUri("http://localhost:9090")
                .when()
                .get("/api/getResume2")
                .then().assertThat().statusCode(200)
                .extract()
                .response()
                .path("payLoad")            //返回接口response中payLoad的值
        payLoad
    }
}

// ResumeService代码
class ResumeService {
    JsonSlurper jsonSlurper

    ResumeService() {
        jsonSlurper = new JsonSlurper()
    }

    def getPersonByCountry(String res, country) {
        def resumeDetails = jsonSlurper.parseText(res)          //将String类型的json字符串转换为数据对象，转换为数据对象后才能轻松获取对应的属性值
        resumeDetails.birthPlace.country == country ? resumeDetails.name: "no person"
        //获取接口response body中的contry信息，如果与传入的country一致则返回该值，否则返回“no person”
    }

    def getContactPhone(String res) {
        def resumeDetails = jsonSlurper.parseText(res)
        resumeDetails.contacts.size() > 0 ? resumeDetails.contacts[0].phone : "no contact"
        //这里做了空保护，先判断contacts.size()>0再获取phone信息，在实际项目中为了保证接口测试在流水线上稳定运行，在平时编写脚本时一定要注意进行空保护，否则某些情况下可能就报空指针异常了
    }

    void printIfPersonWithSpecialSkill(String res, language) {
        def resumeDetails = jsonSlurper.parseText(res)
        if (resumeDetails.skills.tech.size() > 0) {
            def programmingSkill = resumeDetails.skills.tech.find { it -> it.language == language }     //使用了find方法
            println "--programmingSkill:${programmingSkill.language}--level:${programmingSkill.level}"    //使用了groovy中“”中可以带参数的特性
        }
    }

    void printWorkingDetails(String res) {
        def resumeDetails = jsonSlurper.parseText(res)
        if (resumeDetails.working.workingProject.size() > 0) {
            resumeDetails.working.workingProject.each { it ->
                println "--projectName:${it.projectName}--jobTitle:${it.jobTitle}--responseibility:${it.responsibility}" }
            // 使用each遍历，且使用了groovy中“”中可以带参数的方式打印获取到的内容
        }
    }
}

class resumeCaseTest extends Specification {
    ResumeClient resumeClient
    ResumeService resumeService

    def setup() {
        resumeClient = new ResumeClient()
        resumeService = new ResumeService()
    }

    def "get person from different country"() {
        given: "no given"
        when: "call the get resume api"
        def res = resumeClient.getResumeDetails()
        then: "println  out the person name from different country"
        println resumeService.getPersonByCountry(res, country)
        println resumeService.getContactPhone(res)
        where:
        country | placeHolder
        "China" | ""
        "USA" | ""
    }

    def "get person PersonWithSpecialSkill"() {
        given: "no given"
        when: "call the get resume PersonWithSpecialSkill"
        def res = resumeClient.getResumeDetails()
        then: "println  out the SpecialSkill"
        println resumeService.printIfPersonWithSpecialSkill(res,language)
        where:
        language | placeHolder
        "Java" | ""

    }

    def "get person workingProject"() {
        given: "no given"
        when: "call the get resume PersonWithSpecialSkill"
        def res = resumeClient.getResumeDetails()
        then: "println  out the SpecialSkill"
        println resumeService.printWorkingDetails(res)

    }



    def "call the api"() {
        given: "no given"
        when: "call the get resume api"
        resumeClient.getResumeSchemaValidate(filePath)    //编写case调用上面的getResumeSchemaValidate方法
        then: "no then"
        where:
        filePath|placeHolder
        "schema/getResumeSchema.json"|""        //存放shema文件地址
    }

    def "validate schema of getResume2 api"() {
        given: "no given"
        when: "call the get resume2 api"
        def payLoad= resumeClient.getResume2()        //获取接口中payLoad的值
        then: "check the schema"
        assertThat(payLoad, matchesJsonSchemaInClasspath(filePath))    //assertThat中使用matchesJsonSchemaInClasspath校验payLoad的值是否与getResumeSchema2中定义的schema一致
        where:
        filePath|placeHolder
        "schema/getResumeSchema.json"|""
    }
}

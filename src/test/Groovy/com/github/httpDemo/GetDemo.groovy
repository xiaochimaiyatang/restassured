package com.github.httpDemo

import spock.lang.Specification
import static io.restassured.RestAssured.given

class GetDemo extends Specification{

    def "should get user details by user name successfully"(){
        given:"no given"
        when:"call get user by name api"
        given().baseUri("http://localhost:9090/")
                .when().log().all()
                .pathParam("bookName",bookName)
                .get("api/getBook/{bookName}")
                .then().log().all()
                .assertThat().statusCode(200)
        then:"no then"

        where:
        bookName|placeHolder
        "tom"|""
        "dave"|""

    }
}

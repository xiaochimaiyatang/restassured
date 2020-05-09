package com.github.Files

import spock.lang.Specification
import utils.FileService

class ReadAndWriteFileCase extends Specification {
    FileService fileService

    def setup() {
        fileService = new FileService()
    }

    def "should read yml file successfully"() {
        given: "no given"
        when: "get the csv data "
        def configs = fileService.getConfigs('./src/test/resources/config.yml')     //文件路径
        then: "print data"
        println configs.stable.db.url   //打印的值与config.yml文件中的值相同则说明成功获取到了yml文件内容
        println configs.active
    }

    def "should read csv file successfully"() {
        given: "no given"
        when: "get the csv file data"
        def csvContent = fileService.getCsvFileContent('./src/test/resources/test.csv', ',')   //这里请写入自己创建的文件路径
        then: "println the data"
        csvContent.each{ it -> println it.name + ":" + it.age + ":" + it.address }
        //这里使用了groovy自带的处理数据集闭包each{}，打印csv文件中的所有name、age、address列内容
        //打印的值与csv文件内容一致则说明获取到了csv文件内容
    }

    def "should read json file successfully"() {
        given: ""
        when: "get json file data"
        def jsonContent = fileService.getCollectionFromJsonFile('./src/test/resources/test.json')   //这里请写入自己创建的文件路径
        then: "println the data"
        println jsonContent.pipelineName   //打印的值与json文件内容相同则说明正确获取到文件内容
        println jsonContent.sonar.coverage
        def stage = jsonContent.stages.find { it -> it.name == "stage2" }  //通过find方法查找json文件中stages对象下name等于“stage2”的对象
        println stage.id
        println stage.duration
    }

    def "should read xml file successfully"() {
        given: ""
        when: "get json file data"
        def xmlContent = fileService.getCollectionFromXMLFile('./src/test/resources/test.xml')   //这里请写入自己创建的文件路径
        then: "println the data"
        xmlContent.person.each{ println it }
        println xmlContent.person.find{ it -> it.name == "DAVE" }.age  //通过find方法查找XML文件中name等于“DAVE”的person对象，然后获取该对象下age的值
    }
}

package com.github.Files

import spock.lang.Specification
import utils.FileService

class NewFileCaseTest extends Specification {
    FileService fileService

    def setup() {
        fileService = new FileService()
    }

    def "should create and read txt file successfully"() {
        given: "create txt file"
        def file = fileService.createFile("./src/test/resources/test.txt")    //文件路径
        when: "write some content to the file"
        // 支持通过<<写入文件内容
        file << "name,age,address\n"
        file << "Tom,100,chengdu\n"
        then: "print file content"
        // 读取txt文件内容
        def lines = file.readLines()
        lines.each{println it}
        and: "delete file"
        file.delete()
    }
}
package com.github.TestData

import utils.FileService
import utils.Secret

class TestDataService {
    FileService fileService
    Secret secret


    TestDataService() {
        fileService = new FileService()
        secret = new Secret()
    }

    def getUserFileData() {
        def userData = fileService.getCsvFileContent("src/test/resources/user.csv", "")
        userData
    }

    def getUserDataByRole(roleName) {
        getUserFileData().find { it -> it.roleName == roleName }
    }

    def getPasswordByUserName(userName) {
        // 这个方法是通过用户名称获取解密后的用户密码
        Secret.decrypt("apiTestStudy", (String) getUserFileData().find { it -> it.username == userName }.password)
        //Secret中都是静态方法，所以可以直接类名.方法名进行调用
//        Secret.decrypt("apiTestStudy","/DgMfJk8Y7YvMrMBiD881A==")

    }
}


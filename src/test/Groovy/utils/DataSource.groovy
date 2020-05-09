package utils

import groovy.sql.Sql

class DataSource {
    Sql sql
    Sql getSql() {
        if (!sql) {
            def mysqlDB = [
                    driver  : 'com.mysql.jdbc.Driver',
                    url     : 'jdbc:mysql://127.0.0.1:3306/apitestdb',
                    user    : 'root',          //这里写入安装mysql是设置的用户名
                    password: 'root12345'     //这里写入安装mysql时设置的密码
            ]
            sql = Sql.newInstance(mysqlDB.url, mysqlDB.user, mysqlDB.password, mysqlDB.driver)
            // 连接mysql数据库固定写法
        }
        sql
    }
}
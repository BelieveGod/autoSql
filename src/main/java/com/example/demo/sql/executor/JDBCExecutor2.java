package com.example.demo.sql.executor;

import com.example.demo.sql.other.CodeTimer;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LTJ
 * @date 2021/9/30
 */
@Slf4j
public class JDBCExecutor2 {

    public static void main(String[] args) {
        String userName="sa";
        String password = "command";
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=monitor_test";
        Connection connection=null;
        BufferedWriter bufferedWriter=null;
        try {
            DriverManager.registerDriver(new SQLServerDriver());
            Instant instant = Instant.now();
            connection = DriverManager.getConnection(url, userName, password);
            Instant instant2 = Instant.now();
            long nano1 = Duration.between(instant, instant2).toNanos();
            long millis1 = Duration.between(instant, instant2).toMillis();
            log.info("连接时间：{} ms ,{} ns", millis1,nano1);
            log.info("schema:{}", connection.getSchema());
            log.info("catalog:{}", connection.getCatalog());

            List<String> sqls = new LinkedList();
            int count=0;
            String line = "";
            Statement statement = connection.createStatement();
            CodeTimer codeTimer = new CodeTimer();
            StringBuilder stringBuilder = new StringBuilder();
            String format = "('%s')";
            StringBuilder sb = new StringBuilder("INSERT INTO test(name) VALUES");
            for(int i=0;i<10000;i++){
                if(count<1000){
                    String sql = String.format(format, i);
//                statement.addBatch(sql);
                    sb.append(sql).append(",");
                    count++;
//                statement.executeUpdate(sql);
                }else{
                    sb.deleteCharAt(sb.length() - 1);
                    statement.executeUpdate(sb.toString());
                    sb = new StringBuilder("INSERT INTO test(name) VALUES");
                    count=0;
                    String sql = String.format(format, i);
                    sb.append(sql).append(",");
                    count++;
                }
            }
            if(count>0){
                sb.deleteCharAt(sb.length() - 1);
                statement.executeUpdate(sb.toString());
            }
            codeTimer.record();
            log.info("{}",codeTimer.print("jdbc 插入"));

        } catch (SQLException throwables) {
            log.error("执行数据库连接异常",throwables);
        } finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    log.error("关闭数据库异常",throwables);
                }
            }
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error("关闭文件输出流错误", e);
                }
            }
        }

    }



}

package com.example.demo.sql.executor;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.sql.other.CodeTimer;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @author LTJ
 * @date 2021/9/30
 */
@Slf4j
public class JDBCExecutor {

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

            int fileNo=318;
            int endNo=2425;
            Statement statement = connection.createStatement();
            String prefix = "INSERT INTO check_data_log(train_log_id,train_no,point_type_id,data_value,mutation,part_id,device_type_id,da_value)VALUES";
            CodeTimer codeTimer2 = new CodeTimer();
            for(int i=fileNo;i<=endNo;i++){
                StringBuilder stringBuilder = new StringBuilder(prefix);
                File file = new File("e:\\mocksql\\checkData"+i+".sql");
                log.info("开始读取：{} 总进度：{}%",file.getName(),((double)(i-fileNo))/(endNo-fileNo+1)*100);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                int count=0;
                String line = "";
                CodeTimer codeTimer = new CodeTimer();
                while((line=bufferedReader.readLine())!=null){
                    if(count<250){
                        line =line.substring(121, line.length() - 1);
                        stringBuilder.append(line).append(",");
                        count++;
                    }else{
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//                        statement.addBatch(stringBuilder.toString());
                    statement.executeUpdate(stringBuilder.toString());
                        stringBuilder= new StringBuilder(prefix);;
                        // 不要漏了添加当前行
                        line =line.substring(121, line.length() - 1);
                        stringBuilder.append(line).append(",");
                        count=1;
                    }
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                statement.executeUpdate(stringBuilder.toString());
//                statement.addBatch(stringBuilder.toString());
//                statement.executeBatch();
//                statement.clearBatch();

                if(count>0){
//                int[] ints = statement.executeBatch();
//                long fail = Arrays.stream(ints).filter(value -> Objects.equals(value, Statement.EXECUTE_FAILED)).count();
//                log.info("执行失败{}个",fail);
//                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
//            statement.executeUpdate(stringBuilder.toString());
                    count=0;
                }
                codeTimer.record();
                log.info("{}",codeTimer.print("jdbc单文件 插入"));
            }
            codeTimer2.record();
            log.info("{}",codeTimer2.print("总执行"));


        } catch (SQLException throwables) {
            log.error("执行数据库连接异常",throwables);
        } catch (IOException e) {
            log.error("打开文件输出流错误", e);
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

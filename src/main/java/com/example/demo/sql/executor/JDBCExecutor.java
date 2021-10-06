package com.example.demo.sql.executor;

import com.alibaba.fastjson.JSONObject;
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
//            connection = DriverManager.getConnection(url, userName, password);
//            Instant instant2 = Instant.now();
//            long nano1 = Duration.between(instant, instant2).toNanos();
//            long millis1 = Duration.between(instant, instant2).toMillis();
//            log.info("连接时间：{} ms ,{} ns", millis1,nano1);
//            log.info("schema:{}", connection.getSchema());
//            log.info("catalog:{}", connection.getCatalog());
//            DatabaseMetaData metaData = connection.getMetaData();
//            Map<String, Class<?>> typeMap = connection.getTypeMap();
            File file = new File("e:\\bakcheckData.sql");
            File outFile = new File("e:\\lineStatistic.txt");
            bufferedWriter = new BufferedWriter(new FileWriter(outFile,false));
            RandomAccessFile raf=null;
            try {
                 raf = new RandomAccessFile(file, "r");
                long length = raf.length();
                long pos = raf.getFilePointer();
                byte[] bytes = new byte[4096];
                int readed=1;
                Map<Integer, Long> posMap = new LinkedHashMap<>();
                int rowCnt=0;
                int percent = (int) ((double) pos / length * 100);
                Instant readTimebegin = Instant.now();
                Instant readPercentTimebegin = Instant.now();
                raf.seek(5_779_460_069L);
                while((readed=raf.read(bytes,0,4096))!=-1){
                    pos = raf.getFilePointer();
                    long stand=pos-readed;
                    for(int i=0;i<readed-1;i++){
                        if(bytes[i]==13 && bytes[i+1]==10){
                            // 这里也会因为要存储的行号结构占用内存太大，导致OOM。如果在IDE中没有限制JVM最大堆空间，则会导致频繁FULL GC，开辟新空间，造成的假卡顿，假死
                            if(posMap.size()>=4096){
                                for (Map.Entry<Integer, Long> entry : posMap.entrySet()) {
                                    bufferedWriter.write(String.format("%d行位置:%d", entry.getKey(), entry.getValue()));
                                    bufferedWriter.newLine();
                                }
                                posMap.clear();
                            }
                            posMap.put(rowCnt,stand+i+1);
                            rowCnt++;
                        }
                    }
                    int newPercent = (int) ((double) pos / length * 100);
                    if(newPercent-percent>0){
                        Instant readTimeEnd = Instant.now();
                        long millis = Duration.between(readTimebegin, readTimeEnd).toMillis();
                        percent = newPercent;
                        log.info("进度======={} % 耗时：{}ms",percent,millis);
                    }
                }
                log.info("总行数:{}",rowCnt);
            } catch (IOException e) {

                e.printStackTrace();
            }finally {
                if(raf!=null){
                    try {
                        raf.close();
                    } catch (IOException e) {
                        log.error("关闭随机读写文件错误",e);
                    }
                }
            }
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

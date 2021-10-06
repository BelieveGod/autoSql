package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LTJ
 * @date 2021/8/27
 */
public class MockMileage {

    public static List<String> mockMileage(){
        ArrayList<String> trainNos = CollUtil.newArrayList("5001", "5002", "5003", "5004");
        Stream<Integer> mileageStream = Stream.iterate(10000, mileage -> mileage + 1000);
        Stream<LocalDateTime> dayStream = Stream.iterate(LocalDateTime.of(2021, 8, 1,11,0,0), day -> day.plusDays(1));
        final int N=30;
        List<Integer> mileageList = mileageStream.limit(N).collect(Collectors.toList());
        List<LocalDateTime> dayList = dayStream.limit(N).collect(Collectors.toList());
        List<String> dayString = dayList.stream().map(day -> DateUtil.format(day, "yyyy-MM-dd HH:mm:ss")).collect(Collectors.toList());


        List<String> sqls = new ArrayList<>(N * trainNos.size());
        for(int i=0;i<trainNos.size();i++){
            for(int j=0;j<N;j++){
                String sql = new StringBuilder("INSERT INTO check_mileage(train_no,mileage,recorder,record_time)")
                        .append(" VALUES(")
                        .append("'").append(trainNos.get(i)).append("'").append(",")
                        .append(mileageList.get(j)).append(",")
                        .append("'").append("SYS").append("'").append(",")
                        .append("'").append(dayString.get(j)).append("'")
                        .append(");")
                        .toString();
                sqls.add(sql);
            }
        }
        return sqls;
    }

    public static void main(String[] args) {
        List<String> sqls = mockMileage();
        FileWriter fileWriter = new FileWriter("e:\\mileage.sql");
        fileWriter.writeLines(sqls);
    }
}

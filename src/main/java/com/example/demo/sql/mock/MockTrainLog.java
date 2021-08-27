package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LTJ
 * @date 2021/8/26
 */
public class MockTrainLog {

    public static List<String> mockTrainLog(){
        ArrayList<String> trainNos = CollUtil.newArrayList("G5001","G5002","G5003","G5004");
        Stream<LocalDateTime> infiniteStream = Stream.iterate(LocalDateTime.of(2021, 7, 1, 0, 0, 0),
                localDateTime -> localDateTime.plusHours(4)
        );
        int perTrainCount=24;
        List<String> insertTrainlogSqls = new ArrayList<>(trainNos.size());
        List<LocalDateTime> dateTimeList = infiniteStream.limit(perTrainCount).collect(Collectors.toList());
        for(int i=0;i<trainNos.size();i++) {

            List<String> timeList = dateTimeList.stream().map(localDateTime -> DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss"))
                    .collect(Collectors.toList());
            List<String> timeList2 = dateTimeList.stream().map(localDateTime -> DateUtil.format(localDateTime, "yyyyMMddHHmmss"))
                    .collect(Collectors.toList());
            List<String> timeList3 = dateTimeList.stream().map(localDateTime -> DateUtil.format(localDateTime, "yyyy\\MM\\dd"))
                    .collect(Collectors.toList());
                for (int j = 0; j < perTrainCount; j++) {
                    String sb = new StringBuilder("INSERT INTO train_log(train_no,station_id,direction,trace_time,alarm_count,picture_count,trace_file,trance_status)")
                            .append(" ")
                            .append("VALUES(")
                            .append("'").append(trainNos.get(i)).append("'").append(",")
                            .append(1).append(",")
                            .append(1).append(",")
                            .append("'").append(timeList.get(j)).append("'").append(",")
                            .append(0).append(",")
                            .append(0).append(",")
                            .append("'").append(timeList3.get(j)).append("\\").append(timeList2.get(j)).append("_").append(1).append("_").append(trainNos.get(i)).append("'").append(",")
                            .append(100)
                            .append(");")
                            .toString();
                    insertTrainlogSqls.add(sb);
                }
        }
        insertTrainlogSqls.forEach(System.out::println);
        return insertTrainlogSqls;
    }

    public static void main(String[] args) {
        mockTrainLog();
    }
}

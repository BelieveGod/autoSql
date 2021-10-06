package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;

import java.time.Duration;
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


    public static void main(String[] args) {
        List<String> trainNos = MockTrainInfo.generateTrainNos("G5", 100);
        List<String> sqls = mockTrainLog(trainNos);
        FileWriter fileWriter = new FileWriter("e:\\trainLog.sql");
        fileWriter.writeLines(sqls);
    }

    public static List<String> mockTrainLog(List<String> trainNos){
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        Duration duration = Duration.between(begin, end);
        long hourDiff = duration.toHours();
        int gap=4;
        long trainLogCnt = hourDiff / gap ;

        Stream<LocalDateTime> infiniteStream = Stream.iterate(begin,
                localDateTime -> localDateTime.plusHours(gap)
        );
        List<LocalDateTime> dateTimeList = infiniteStream.limit(trainLogCnt).collect(Collectors.toList());

        List<String> timeList = dateTimeList.stream().map(localDateTime -> DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss"))
                .collect(Collectors.toList());
        List<String> timeList2 = dateTimeList.stream().map(localDateTime -> DateUtil.format(localDateTime, "yyyyMMddHHmmss"))
                .collect(Collectors.toList());
        List<String> timeList3 = dateTimeList.stream().map(localDateTime -> DateUtil.format(localDateTime, "yyyy\\MM\\dd"))
                .collect(Collectors.toList());


        List<String> insertTrainlogSqls = new ArrayList<>(trainNos.size());
        for(int i=0;i<timeList.size();i++){
            for (String trainNo : trainNos) {
                String sb = new StringBuilder("INSERT INTO train_log(train_no,station_id,direction,trace_time,alarm_count,picture_count,trace_file,trance_status)")
                        .append(" ")
                        .append("VALUES(")
                        .append("'").append(trainNo).append("'").append(",")
                        .append(1).append(",")
                        .append(1).append(",")
                        .append("'").append(timeList.get(i)).append("'").append(",")
                        .append(0).append(",")
                        .append(0).append(",")
                        .append("'").append(timeList3.get(i)).append("\\").append(timeList2.get(i)).append("_").append(1).append("_").append(trainNo).append("'").append(",")
                        .append(100)
                        .append(");")
                        .toString();
                insertTrainlogSqls.add(sb);
            }
        }
        return insertTrainlogSqls;
    }

}

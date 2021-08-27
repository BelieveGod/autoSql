package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LTJ
 * @date 2021/8/27
 */
public class MockTrainInfo {

    public static List<String> mockTrainInfo(){
        ArrayList<String> trainNos = CollUtil.newArrayList("G5001", "G5002", "G5003", "G5004");
        // 车厢数
        int groupCount = 6;
        int status=1;
        int pantographCount=2;
        // 每节车厢轮子数
        int wheelCntPerCarriage=8;

        List<String> sqls = new ArrayList<>(trainNos.size());
        for (String trainNo : trainNos) {
            String sql = new StringBuilder("INSERT INTO train_info(train_no,group_count,status,pantograph_count,per_wheel_count)")
                    .append(" VALUES(")
                    .append("'").append(trainNo).append("'").append(",")
                    .append(groupCount).append(",")
                    .append(status).append(",")
                    .append(pantographCount).append(",")
                    .append(wheelCntPerCarriage)
                    .append(");")
                    .toString();
            sqls.add(sql);
        }
        return sqls;
    }

    public static void main(String[] args) {
        List<String> strings = mockTrainInfo();
        FileWriter fileWriter = new FileWriter("e:\\mockTrainInfo.sql");
        fileWriter.writeLines(strings);
    }
}

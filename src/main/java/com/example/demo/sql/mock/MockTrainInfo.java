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
        List<String> trainNos = generateTrainNos("G5", 100);
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
        FileWriter fileWriter = new FileWriter("e:\\mock\\mockTrainInfo.sql");
        fileWriter.writeLines(strings);
    }

    /**
     * 生成列车号
     * @param prefix
     * @param num
     * @return
     */
    public static List<String> generateTrainNos(final String prefix,int num){
        List<String> trainNos = new ArrayList<>(num);
        for(int i=1;i<=num;i++){
            String trainNo = String.format("%s%03d", prefix, i);
            trainNos.add(trainNo);
        }
        return trainNos;
    }
}

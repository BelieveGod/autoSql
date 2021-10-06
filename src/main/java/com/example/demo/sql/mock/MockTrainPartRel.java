package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LTJ
 * @date 2021/9/13
 */
public class MockTrainPartRel {
    public static void main(String[] args) {
        List<String> strings = mockTrainPartRel();
        FileWriter fileWriter = new FileWriter("e:\\trainPartRel.sql");
        fileWriter.writeLines(strings);
    }

    public static List<String> mockTrainPartRel(){
        int groupCount=6;
        int motorCntPerGroup=4;
        int carriagePartStartId=8401;
        int motorPartStartId = 9201;
        List<String> trainNos = MockTrainInfo.generateTrainNos("G5", 100);

        List<String> sqls = new ArrayList<>(250);
        for(int i=0;i<trainNos.size();i++){
            for(int j=0;j<groupCount;j++){
                if(j==0 || j==groupCount-1){
                    carriagePartStartId++;
                    continue;
                }
                for(int z=0;z<motorCntPerGroup;z++){
                    String sql = new StringBuilder("INSERT INTO train_parts_rel(c_id,m_id)")
                            .append(" VALUES(")
                            .append(carriagePartStartId).append(",")
                            .append(motorPartStartId)
                            .append(");")
                            .toString();
                    sqls.add(sql);
                    motorPartStartId++;
                }
                carriagePartStartId++;
            }
        }
        return sqls;
    }
}

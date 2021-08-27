package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileWriter;

import java.io.IOException;
import java.util.*;

/**
 * @author LTJ
 * @date 2021/8/27
 */
public class MockTrainPart {
    public static List<String> mockTrainPart(){
        int trainIdStartIdx=72;
        ArrayList<String> trainNos = CollUtil.newArrayList("G5001","G5002","G5003","G5004");
        int trainIdEndIdx=trainIdStartIdx+trainNos.size()-1;

        int groupCont=6;
        int perwheelCount=8;
        int pantographCnt=2;
        Map<Integer, String> partCode2PartNameOfWheelMapping = new TreeMap<>();
        Map<Integer, String> partCode2PartNameOfAxleMapping = new TreeMap<>();
        Map<Integer, String> partCode2PartNameOfBogieMapping = new TreeMap<>();
        Map<Integer, String> partCode2PartNameOfCarriageMapping = new TreeMap<>( );
        Map<Integer, String> partCode2PartNameOfPantographMapping = new TreeMap<>( );

        Properties properties = new Properties();
        try {
            properties.load(MockTrainPart.class.getResourceAsStream("/wheelNameMapping.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = ((String) enumeration.nextElement());
            Integer partCode = Integer.valueOf(key);
            String partName = properties.getProperty(key);
            partCode2PartNameOfWheelMapping.put(partCode, partName);
        }

        int partType = 1;
        List<String> sqls = new ArrayList<>(250);
        Set<Map.Entry<Integer, String>> entries = partCode2PartNameOfWheelMapping.entrySet();
        for(int i=0;i<trainNos.size();i++){
            Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, String> item = iterator.next();
                String sql = new StringBuilder("INSERT INTO train_parts(train_no_id,train_no,part_code,part_name,status,part_type)")
                        .append(" VALUES(")
                        .append(trainIdStartIdx + i).append(",")
                        .append("'").append(trainNos.get(i)).append("'").append(",")
                        .append(item.getKey()).append(",")
                        .append("'").append(item.getValue()).append("'").append(",")
                        .append(1).append(",")
                        .append(partType)
                        .append(");")
                        .toString();
                sqls.add(sql);
            }
        }
        return sqls;
    }

    public static void main(String[] args) {
        List<String> strings = mockTrainPart();
        FileWriter fileWriter = new FileWriter("e:\\trainPart-wheel.sql");
        fileWriter.writeLines(strings);
    }
}

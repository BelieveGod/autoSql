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

    public static final int WHEEL = 1;
    public static final int BOGIE = 2;
    public static final int AXLE = 3;
    public static final int CARRIAGE = 5;
    public static final int PANTOGRAPH = 6;

    public static List<String> mockTrainPart(){
        int trainIdStartIdx=72;
        ArrayList<String> trainNos = CollUtil.newArrayList("G5001","G5002","G5003","G5004");
        int trainIdEndIdx=trainIdStartIdx+trainNos.size()-1;

        int groupCont=6;
        int perwheelCount=8;
        int pantographCnt=2;

        Map<Integer, String> partCode2PartNameOfPantographMapping = new TreeMap<>( );

        /* begin ==== 读取轮子 ============*/

        Map<Integer, String> partCode2PartNameOfWheelMapping=readPropertisAsMap("/wheelNameMapping.properties");
        /* end ==== 读取轮子 ============*/
        Map<Integer, String> partCode2PartNameOfAxleMapping = readPropertisAsMap("/axleNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfBogieMapping = readPropertisAsMap("/bogieNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfCarriageMapping = readPropertisAsMap("/carriageNameMapping.properties");




//        int partType = 1;
//        List<String> sqls = new ArrayList<>(250);
//        Set<Map.Entry<Integer, String>> entries = partCode2PartNameOfWheelMapping.entrySet();
//        for(int i=0;i<trainNos.size();i++){
//            Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();
//            while (iterator.hasNext()){
//                Map.Entry<Integer, String> item = iterator.next();
//                String sql = new StringBuilder("INSERT INTO train_parts(train_no_id,train_no,part_code,part_name,status,part_type)")
//                        .append(" VALUES(")
//                        .append(trainIdStartIdx + i).append(",")
//                        .append("'").append(trainNos.get(i)).append("'").append(",")
//                        .append(item.getKey()).append(",")
//                        .append("'").append(item.getValue()).append("'").append(",")
//                        .append(1).append(",")
//                        .append(partType)
//                        .append(");")
//                        .toString();
//                sqls.add(sql);
//            }
//        }
//        List<String> sqls = mockTrainPart(trainIdStartIdx, trainNos, WHEEL, partCode2PartNameOfAxleMapping);
//        List<String> sqls = mockTrainPart(trainIdStartIdx, trainNos, AXLE, partCode2PartNameOfAxleMapping);
//        List<String> sqls = mockTrainPart(trainIdStartIdx, trainNos, BOGIE, partCode2PartNameOfBogieMapping);
        List<String> sqls = mockTrainPart(trainIdStartIdx, trainNos, CARRIAGE, partCode2PartNameOfCarriageMapping);
        return sqls;
    }

    public static void main(String[] args) {
        List<String> strings = mockTrainPart();
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-wheel.sql");
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-axle.sql");
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-bogie.sql");
        FileWriter fileWriter = new FileWriter("e:\\trainPart-carriage.sql");
        fileWriter.writeLines(strings);
    }

    private static Map<Integer, String> readPropertisAsMap(String fileName){
        Properties properties = new Properties();
        try {
            properties.load(MockTrainPart.class.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
        Map<Integer, String> partCode2PartNamMap = new TreeMap<>();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = ((String) enumeration.nextElement());
            Integer partCode = Integer.valueOf(key);
            String partName = properties.getProperty(key);
            partCode2PartNamMap.put(partCode, partName);
        }
        return partCode2PartNamMap;
    }


    private static List<String> mockTrainPart(final int trainIdStartIdx,final ArrayList<String> trainNos,final int partType,final Map<Integer, String> partCode2PartNameMap){
        List<String> sqls = new ArrayList<>(250);
        Set<Map.Entry<Integer, String>> entries = partCode2PartNameMap.entrySet();
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
}

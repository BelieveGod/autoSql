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
    public static final int MOTOR = 8;

    public static void main(String[] args) {
        List<String> trainNos = MockTrainInfo.generateTrainNos("G5", 100);
        List<Integer> partTypes = CollUtil.newArrayList(WHEEL, BOGIE, AXLE, CARRIAGE, PANTOGRAPH, MOTOR);
        List<String> sqls = mockTrainPart(1, trainNos, partTypes);
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-wheel.sql");
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-axle.sql");
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-bogie.sql");
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-carriage.sql");
//        FileWriter fileWriter = new FileWriter("e:\\trainPart-pantograph.sql");
        FileWriter fileWriter = new FileWriter("e:\\trainPart.sql");
        fileWriter.writeLines(sqls);
    }

    public static List<String> mockTrainPart(final int trainIdStartIdx,final List<String> trainNos,List<Integer> partTypes){
//        int trainIdStartIdx=72;
//        ArrayList<String> trainNos = CollUtil.newArrayList("G5001","G5002","G5003","G5004");

        int trainIdEndIdx=trainIdStartIdx+trainNos.size()-1;

        int groupCont=6;
        int perwheelCount=8;
        int pantographCnt=2;




        Map<Integer, String> partCode2PartNameOfWheelMapping=readPropertisAsMap("/wheelNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfAxleMapping = readPropertisAsMap("/axleNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfBogieMapping = readPropertisAsMap("/bogieNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfCarriageMapping = readPropertisAsMap("/carriageNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfPantographMapping = readPropertisAsMap("/pantographMapping.properties");
        Map<Integer, String> partCode2PartNameOfMotorMapping = readPropertisAsMap("/motorNameMapping.properties");

        Map<Integer, Map<Integer, String>> partTypeMap = new LinkedHashMap<>();
        partTypeMap.put(WHEEL, partCode2PartNameOfWheelMapping);
        partTypeMap.put(AXLE, partCode2PartNameOfAxleMapping);
        partTypeMap.put(BOGIE, partCode2PartNameOfBogieMapping);
        partTypeMap.put(CARRIAGE, partCode2PartNameOfCarriageMapping);
        partTypeMap.put(PANTOGRAPH, partCode2PartNameOfPantographMapping);
        partTypeMap.put(MOTOR, partCode2PartNameOfMotorMapping);




        List<String> sqls = new ArrayList<>(250);

        for (Integer partType : partTypes) {
            for (int i = 0; i < trainNos.size(); i++) {
                Map<Integer, String> partCode2NameMapping = partTypeMap.get(partType);
                for (Map.Entry<Integer, String> partCodeEntry : partCode2NameMapping.entrySet()) {
                    Integer partCode = partCodeEntry.getKey();
                    String partName = partCodeEntry.getValue();
                    String sql = new StringBuilder("INSERT INTO train_parts(train_no_id,train_no,part_code,part_name,status,part_type)")
                            .append(" VALUES(")
                            .append(trainIdStartIdx + i).append(",")
                            .append("'").append(trainNos.get(i)).append("'").append(",")
                            .append(partCode).append(",")
                            .append("'").append(partName).append("'").append(",")
                            .append(1).append(",")
                            .append(partType)
                            .append(");")
                            .toString();
                    sqls.add(sql);
                }
            }
        }



        return sqls;
    }



    public static Map<Integer, String> readPropertisAsMap(String fileName){
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

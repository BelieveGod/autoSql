package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.demo.sql.mock.MockTrainPart.*;

/**
 * @author LTJ
 * @date 2021/8/27
 */

public class MockCheckData {
    public static  Map<String, String> telemetryMap;
    private static Random random = new Random();
    static{
       telemetryMap = readPropertisAsMap("/telemetry.properties");

    }

    public static void main(String[] args) {
        List<String> trainNos = MockTrainInfo.generateTrainNos("G5", 100);
        File file = new File("e:\\mocksql");
         mockCheckData(trainNos,file);
    }

    public static List<String> mockCheckData(List<String> trainNos, File file){
        int start=1;
        int end=219600;

        List<Integer> deviceTypeList = CollUtil.newArrayList(302, 101, 306, 305);
        Map<Integer, List<Integer>> deviceTypeMappingCkItemMap = new LinkedHashMap<>();
        ArrayList<Integer> wheelCkItemList = CollUtil.newArrayList(1, 2, 3, 7, 8,6,9,10,11,61,64,65);
        deviceTypeMappingCkItemMap.put(302, wheelCkItemList);

        ArrayList<Integer> pantographCkItemList = CollUtil.newArrayList(0,4,6,9,10,11,12,13,31,32,37,38,201,240,241,242,243);
        deviceTypeMappingCkItemMap.put(101, pantographCkItemList);

        ArrayList<Integer> motorCkItemList = CollUtil.newArrayList(1,2,3);
        deviceTypeMappingCkItemMap.put(306, motorCkItemList);

        ArrayList<Integer> axleTempCkItemList = CollUtil.newArrayList(1,3,4,9);
        deviceTypeMappingCkItemMap.put(305, axleTempCkItemList);



        Map<Integer, String> partCode2PartNameOfWheelMapping=MockTrainPart.readPropertisAsMap("/wheelNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfAxleMapping = MockTrainPart.readPropertisAsMap("/axleNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfBogieMapping = MockTrainPart.readPropertisAsMap("/bogieNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfCarriageMapping = MockTrainPart.readPropertisAsMap("/carriageNameMapping.properties");
        Map<Integer, String> partCode2PartNameOfPantographMapping = MockTrainPart.readPropertisAsMap("/pantographMapping.properties");
        Map<Integer, String> partCode2PartNameOfMotorMapping = MockTrainPart.readPropertisAsMap("/motorNameMapping.properties");

        Map<Integer, Map<Integer, String>> partTypeMap = new LinkedHashMap<>();
        partTypeMap.put(WHEEL, partCode2PartNameOfWheelMapping);
        partTypeMap.put(AXLE, partCode2PartNameOfAxleMapping);
        partTypeMap.put(BOGIE, partCode2PartNameOfBogieMapping);
        partTypeMap.put(CARRIAGE, partCode2PartNameOfCarriageMapping);
        partTypeMap.put(PANTOGRAPH, partCode2PartNameOfPantographMapping);
        partTypeMap.put(MOTOR, partCode2PartNameOfMotorMapping);


        List<String> sqls = new ArrayList<>(2<<14);
        int cnt=0;
        int fileCnt=0;
        File f = new File(file, "checkData" + fileCnt + ".sql");
        FileWriter fileWriter = new FileWriter(f);
        for(int tid=start;tid<=end;tid++){
            String trainNo = getTrainNo(start, tid, trainNos);
            for (Integer deviceType : deviceTypeList) {
                List<Integer> ckIdList = deviceTypeMappingCkItemMap.get(deviceType);
                for (Integer ckId : ckIdList) {
                    TrainPartTypeEnum partTypeEnum = getTrainPartTypeByDeviceTypeAndPointTypeId(deviceType, ckId);
                    Map<Integer, String> code2NameMap = partTypeMap.get(partTypeEnum.getCode());
                    for (Integer partCode : code2NameMap.keySet()) {
                        Double value=generateValue(deviceType,ckId);
                        String sql = new StringBuilder("INSERT INTO check_data_log(train_log_id,train_no,point_type_id,data_value,mutation,part_id,device_type_id,da_value)")
                                .append("VALUES(")
                                .append(tid).append(",")
                                .append("'").append(trainNo).append("'").append(",")
                                .append(ckId).append(",")
                                .append(value).append(",")
                                .append(0).append(",")
                                .append(partCode).append(",")
                                .append(deviceType).append(",")
                                .append(value)
                                .append(");")
                                .toString();
                        sqls.add(sql);
                        if(sqls.size()>=2<<14){

                            if(f.exists()){
                                long size = FileUtil.size(f);
                                if(size>=2<<22){
                                    fileCnt++;
                                    f = new File(file, "checkData" + fileCnt + ".sql");
                                    fileWriter = new FileWriter(f);
                                }
                            }

                            fileWriter.appendLines(sqls);
                            sqls.clear();

                        }
                    }
                }
            }
            int percent = (int) (((double) tid) / end * 100);
            System.out.println(String.format("进度==== %d %%",percent));
        }
        if(sqls.size()>0){
            fileWriter.appendLines(sqls);
        }
        return sqls;
    }

    private static Double generateValue(Integer deviceType, Integer ckId) {
        String key = deviceType + "+" + ckId;
        String telemetry = telemetryMap.get(key);
        if(telemetry==null){
            int i = random.nextInt(40);
            return (double)i;
        }else if(telemetry.contains("-")){
            String[] split = telemetry.split("-");
            Double low = Double.valueOf(split[0]);
            Double high = Double.valueOf(split[1]);
            int lowint = (int) (low * 100);
            int highint = (int) (high * 100);
            int diff = highint - lowint;
            int value = random.nextInt(diff) + lowint + 1;
            double v = value / 100.0;
            return v;
        }else if(telemetry.contains("/")){
            String[] split = telemetry.split("/");
            int i = random.nextInt(split.length);
            return Double.valueOf(i);
        }else{
            int i = random.nextInt(40);
            return (double)i;
        }
    }



    /**
     * 因为生成的来车记录是按时间，车号这样迭代生成的
     * @param start
     * @param tid
     * @param trainNos
     * @return
     */
    public static String getTrainNo(int start,int tid,List<String> trainNos){
        int diff=tid-start;
        int i = diff % trainNos.size();
        return trainNos.get(i);
    }

    /**
     * 根据检测类型和检测项 获取 对应检测的类型部位
     *
     * @param deviceType 检测类型
     * @param pointTypeId 检测项
     * @return
     */
    public static TrainPartTypeEnum getTrainPartTypeByDeviceTypeAndPointTypeId(int deviceType,Integer pointTypeId){
        Map<Integer, TrainPartTypeEnum> deviceType2PartType = new HashMap<>();
        deviceType2PartType.put(302, TrainPartTypeEnum.WHEEL_CODE);
        deviceType2PartType.put(305, TrainPartTypeEnum.WHEEL_CODE);
        deviceType2PartType.put(306, TrainPartTypeEnum.MOTOR_CODE);
        deviceType2PartType.put(307, TrainPartTypeEnum.CARRIAGE_CODE);
        deviceType2PartType.put(101, TrainPartTypeEnum.PANTOGRAPH_CODE);
        deviceType2PartType.put(308, TrainPartTypeEnum.BOGIE_CODE);
        deviceType2PartType.put(360, TrainPartTypeEnum.CARRIAGE_CODE);
        deviceType2PartType.put(310, TrainPartTypeEnum.AXLE_CODE);
        deviceType2PartType.put(311, TrainPartTypeEnum.ROBOT_AXLE_CODE);

        Map<String, TrainPartTypeEnum> specialCheck2Map = new HashMap<>();
        specialCheck2Map.put("302_6", TrainPartTypeEnum.AXLE_CODE);
        specialCheck2Map.put("302_9", TrainPartTypeEnum.AXLE_CODE);
        specialCheck2Map.put("302_10", TrainPartTypeEnum.BOGIE_CODE);
        specialCheck2Map.put("302_11", TrainPartTypeEnum.CARRIAGE_CODE);
        TrainPartTypeEnum trainPartTypeEnum=null;
        if(Objects.equals(302,deviceType)){
            String key = deviceType + "_" + pointTypeId;
            trainPartTypeEnum = specialCheck2Map.get(key);
        }
        // 看是否能获取到特殊项
        if (trainPartTypeEnum==null) {
            trainPartTypeEnum = deviceType2PartType.get(deviceType);
        }
        return trainPartTypeEnum;
    }

    public static Map<String, String> readPropertisAsMap(String fileName){
        Properties properties = new Properties();
        try {
            properties.load(MockTrainPart.class.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
        Map<String, String> partCode2PartNamMap = new TreeMap<>();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = ((String) enumeration.nextElement());
            String partName = properties.getProperty(key);
            partCode2PartNamMap.put(key, partName);
        }
        return partCode2PartNamMap;
    }

}



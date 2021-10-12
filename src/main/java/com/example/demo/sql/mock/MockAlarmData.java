package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.util.*;

import static com.example.demo.sql.mock.MockCheckData.*;
import static com.example.demo.sql.mock.MockTrainPart.*;
import static com.example.demo.sql.mock.MockTrainPart.MOTOR;

/**
 * @author LTJ
 * @date 2021/10/12
 */
public class MockAlarmData {
    public static Map<String, String> telemetryMap;
    private static Random random = new Random();
    static{
        telemetryMap = MockCheckData.readPropertisAsMap("/telemetry.properties");

    }

    public static void main(String[] args) {
        List<String> trainNos = MockTrainInfo.generateTrainNos("G5", 100);
        File file = new File("e:\\alarmSql");
        mockAlarmData(trainNos,file);
    }

    public static List<String> mockAlarmData(List<String> trainNos, File file){
        int start=219598;
        int end=219600;

//        List<Integer> deviceTypeList = CollUtil.newArrayList(302, 101, 306, 305);
        List<Integer> deviceTypeList = CollUtil.newArrayList(302);
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
        File f = new File(file, "alarmData" + fileCnt + ".sql");
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
                        String sql = new StringBuilder("INSERT INTO da_alarm_log(train_log_id,train_no,check_id,alarm_value,part_code,device_type_id,da_alarm_value,alarm_status,alam_level,da_alarm_level)")
                                .append("VALUES(")
                                .append(tid).append(",")
                                .append("'").append(trainNo).append("'").append(",")
                                .append(ckId).append(",")
                                .append(value).append(",")
                                .append(partCode).append(",")
                                .append(deviceType).append(",")
                                .append(value).append(",")
                                .append(0).append(",")
                                .append(0).append(",")
                                .append(0)
                                .append(");")
                                .toString();
                        sqls.add(sql);
                        if(sqls.size()>=2<<14){

                            if(f.exists()){
                                long size = FileUtil.size(f);
                                if(size>=2<<22){
                                    fileCnt++;
                                    f = new File(file, "alarmData" + fileCnt + ".sql");
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
}

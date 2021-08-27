package com.example.demo.sql.mock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author LTJ
 * @date 2021/8/27
 */
public class MockCheckData {
    public static List<String> mockCheckData(){
        int start=15819;
        int end=15842;
        int step=24;
        ArrayList<String> trainNos = CollUtil.newArrayList("G5001","G5002","G5003","G5004");
        ArrayList<Integer> deviceTypeList = CollUtil.newArrayList(302);
        ArrayList<Integer> wheelCkItemList = CollUtil.newArrayList(1, 2, 3, 7, 8);
        Map<Integer, List<Integer>> deviceTypeMappingCkItemMap = new TreeMap<>();
        deviceTypeMappingCkItemMap.put(302, wheelCkItemList);

        // 6编组轮子
        List<Integer> wheelPartList = new ArrayList<>(48);
        for(int i=0;i<24;i++){
            // 右轮子
            int rightPartid=10*(i+1)+1;
            // 唑仑兹
            int leftPartId=10*(i+1)+2;
            wheelPartList.add(rightPartid);
            wheelPartList.add(leftPartId);
        }
        Map<String, List<Integer>> ckItemMappingPartMap = new TreeMap<>();
        for(int i=0;i<wheelCkItemList.size();i++){
            ckItemMappingPartMap.put("302_" + wheelCkItemList.get(i), wheelPartList);
        }


        List<String> sqls = new ArrayList<>(24000);
        for(int i=0;i<trainNos.size();i++){
            for(int j=start;j<=end;j++){
                for(int k=0;k<deviceTypeList.size();k++){
                    Integer deviceType = deviceTypeList.get(k);
                    List<Integer> ckItemList = deviceTypeMappingCkItemMap.get(deviceType);
                    for(int z=0;z<ckItemList.size();z++){
                        List<Integer> partList = ckItemMappingPartMap.get(deviceType + "_" + ckItemList.get(i));
                        for(int x=0;x<partList.size();x++){
                            String sql = new StringBuilder("INSERT INTO check_data_log(train_log_id,train_no,point_type_id,data_value,mutation,part_id,device_type_id,da_value)")
                                    .append("VALUES(")
                                    .append(j).append(",")
                                    .append("'").append(trainNos.get(i)).append("'").append(",")
                                    .append(ckItemList.get(z)).append(",")
                                    .append(20).append(",")
                                    .append(0).append(",")
                                    .append(partList.get(x)).append(",")
                                    .append(deviceType).append(",")
                                    .append(20)
                                    .append(");")
                                    .toString();
                            sqls.add(sql);
                        }
                    }
                }
            }
            // 增加部长
            start+=step;
            end+=step;
        }

//        sqls.forEach(System.out::println);
        return sqls;
    }

    public static void main(String[] args) {
        List<String> sqls = mockCheckData();
        File file = new File("e:\\checkData.sql");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.writeLines(sqls);
    }
}

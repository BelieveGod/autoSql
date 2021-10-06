package com.example.demo.sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * 生成电机部位名称和部位电机关系
 * 
 * @author pwf 2020-11-15 16:19:00
 * 
 */
public class GenMontorPart {

	public static void main(String[] args) {
		List<String> sqlScript = new ArrayList<>();// sql语句脚本
		List<String> trainsNo = new ArrayList<>();//车号
		//------------------------------>生成列车信息
	  final	int groupCount =6;// 一列车有多少节车厢//----------------------------------------->注意配置17-0
	  final	int tainTotal = 10;// 总共车数量//----------------------------------------->注意配置17-1
	   final	int perMonitorCount =4;//每节车厢共有多少个电机
		String trainNoPre = "GF0";//------------------------------车号前缀 注意配置17-2
		
		//符合abc车厢命名列车号
		for (int i = 1; i <= tainTotal; i++) {
			String tNo = "";
			tNo = trainNoPre + String.format("%03d%03d",(2*i-1),(2*i));//------------------------->保留4位可配置%02d 代表保留2位，%03d代表保留4位
			trainsNo.add(tNo);	
//			sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('"+ tNo + "',"+groupCount+",2,8)");// 写入列车信息
		}
		trainsNo.add("02xx");
//		sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('02xx',"+groupCount+",2,8)");// 写入02xx列车信息
		
		//常规列车号
		/***for (int i = 1; i <= tainTotal; i++) {
			String tNo = "";
			tNo = trainNoPre + String.format("%02d",i);//------------------------->保留4位可配置%02d 代表保留2位，%03d代表保留4位
			trainsNo.add(tNo);
			//sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('"+ tNo + "',"+groupCount+",2,8)");// 写入列车信息
		}
		trainsNo.add("02xx");
		//sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('02xx',"+groupCount+",2,8)");// 写入02xx列车信息
*/		
		//---------------------------------->额外添加注意删除
		//trainsNo.add("1018");
		
		//-----------------------------------写入车辆被检部位信息
		int traininfoid = 1;//列车数据库id从3开始,要看数据库具体插入情况（train_info表的主键id）       -------------- >    注意配置17-3
		//一个车厢4个电机，前后拖车无电机
		final  int monitorCount = (groupCount-2)*perMonitorCount;//一列车总共有多少个电机  ------------------------------------ >  注意配置
	
		if(monitorCount ==0){
			System.out.println("无电机");
			return;
		}
		
		//-------------------------->注意不车厢数这里需要配置
		List<String> gpName = new ArrayList<>(monitorCount);//电机名称
		List<String> gpNameSelf = new ArrayList<>(monitorCount);//电机别名
		//添加电机部位名称
		for(int i =1 ;i<=monitorCount;i++){
			gpName.add("电机"+i);
			gpNameSelf.add("");
		}
		
		//生成电机部位sql脚本
 	/*	for(String no:trainsNo){
        	String trins = "INSERT INTO train_parts  (train_no_id, train_no, part_code, part_name, part_type, part_name_self) VALUES  ("+(traininfoid++)+", '"+no+ "', ";
        	StringBuffer sb = new StringBuffer();
            for(int i =0; i<gpName.size();i++){
            	sb.append(trins);
        		sb.append((i+1)+", '"+gpName.get(i)+"',8,'"+gpNameSelf.get(i)+"')");
        		System.out.println(sb.toString());
        		sqlScript.add(sb.toString());//车厢信息
        		sb.setLength(0);
        	}
        }
*/
		//生产电机与车厢之间的关系sql脚本
	/*	traininfoid =1;//列车数据库id从3开始,要看数据库具体插入情况 （train_info表的主键id）      -------------- >    注意配置17-3
		sqlScript.clear();
		int beginCarriage = 1;//车厢在train_parts表的初始id和车厢数对应----------->需要配置
		int beginMotor =4403 ;//电机在train_parts表的初始id----------->需要配置
	 
		
		String sqlPartRel = "INSERT INTO train_parts_rel  (c_id, m_id) VALUES  (";
		StringBuffer sb = new StringBuffer();
		
		for(int i=1;i<=trainsNo.size();i++){//总列数
			carfor:for(int gpIndex=1;gpIndex<=groupCount; gpIndex++){//一列车总车厢数
				if(gpIndex==1 || (gpIndex==groupCount) ){   //排除首节车厢或者最后一节车厢
					   beginCarriage++;
					   continue carfor;
				 }else{
					   //生成车厢与电机的id关系
					   for(int z=0; z<perMonitorCount;z++){
						   sb.append(sqlPartRel).append(beginCarriage).append(",").append(beginMotor).append(");");
						   sqlScript.add(sb.toString());//车厢信息
						   beginMotor ++;
						   sb.setLength(0);
					   }
					   beginCarriage++;
				 }
			}
		}*/
		
		
		 writeFile( sqlScript);//生成列车信息脚本
	 
	}
	
	   /**
     * 写入TXT文件
     */
    public static void writeFile( List<String> sqlScript) {
        try {
            File writeName = new File("E://output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            if(writeName.exists()){
            	writeName.delete();
            }
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
            	for (String string : sqlScript) {
            		out.write(string+"\r\n"); // \r\n即为换行
				}
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	//文件关闭
        }
    }

}

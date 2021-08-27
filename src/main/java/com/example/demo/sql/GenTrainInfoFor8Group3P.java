package com.example.demo.sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenTrainInfoFor8Group3P {

	/****
	 *  生成一般平常相关的车辆，被检测部位基本信息数据库脚本(8节车厢,3个弓)
	 *  注意起始id
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> sqlScript = new ArrayList<>();// sql语句脚本
		List<String> trainsNo = new ArrayList<>();//车号
		//------------------------------>生成列车信息
		int groupCount = 8;// 一列车有多少节车厢//----------------------------------------->注意配置17-0
		int tainTotal = 100;// 总共车数量//----------------------------------------->注意配置17-1
		String trainNoPre = "01";//------------------------------车号前缀 注意配置17-2
		for (int i = 1; i <= tainTotal; i++) {
			String tNo = "";
			tNo = trainNoPre + String.format("%02d",i);//保留4位
			trainsNo.add(tNo);
//		    sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('"+ tNo + "',"+groupCount+",3,8)");// 写入列车信息
		}
		trainsNo.add("02xx");//设备不能识别的车号
//		sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('02xx',"+groupCount+",3,8)");// 写入02xx列车信息
	
		
		//-----------------------------------写入车辆被检部位信息
		int traininfoid = 1;//列车数据库id从3开始,要看数据库具体插入情况       -------------- >    注意配置17-3
		
		//-------------begin车厢信息8节车厢
		/*String []  gpName  = new String []{"TC1","MP1","M1","MP2","M2","M3","MP3","TC2"};//----------------------------------------->注意配置
        String []  gpNameSelf  = new String []{"","","","","","","",""};//----------------------------------------->注意配置
    	for(String no:trainsNo){
        	String trins = "INSERT INTO train_parts  (train_no_id, train_no, part_code, part_name, part_type, part_name_self) VALUES  ("+(traininfoid++)+", '"+no+ "', ";
        	StringBuffer sb = new StringBuffer();
            for(int i =0; i<gpName.length;i++){
            	sb.append(trins);
        		sb.append((i+1)+", '"+gpName[i]+"',5,'"+gpNameSelf[i]+"')");
        		System.out.println(sb.toString());
        		sqlScript.add(sb.toString());//车厢信息
        		sb.setLength(0);
        	}
        }*/
		//-------------end车厢信息8节车厢
		
		
		///-------------------------------------->begin8节车厢通用车轮
		
/***String [] lunName = new String [] { 
			 "1车1转W左4","1车1转W右1","1车1转W左3","1车1转W右2",     "1车2转W左4","1车2转W右1","1车2转W左3","1车2转W右2",
			 "2车1转W左4","2车1转W右1","2车1转W左3","2车1转W右2",     "2车2转W左4","2车2转W右1","2车2转W左3","2车2转W右2",
			 "3车1转W左4","3车1转W右1","3车1转W左3","3车1转W右2",     "3车2转W左4","3车2转W右1","3车2转W左3","3车2转W右2",
			 "4车1转W左4","4车1转W右1","4车1转W左3","4车1转W右2",     "4车2转W左4","4车2转W右1","4车2转W左3","4车2转W右2",
			
			 "5车2转W左2","5车2转W右3","5车2转W左1","5车2转W右4",     "5车1转W左2","5车1转W右3","5车1转W左1","5车1转W右4",
			 "6车2转W左2","6车2转W右3","6车2转W左1","6车2转W右4",     "6车1转W左2","6车1转W右3","6车1转W左1","6车1转W右4",
			 "7车2转W左2","7车2转W右3","7车2转W左1","7车2转W右4",     "7车1转W左2","7车1转W右3","7车1转W左1","7车1转W右4",
			 "8车2转W左2","8车2转W右3","8车2转W左1","8车2转W右4",     "8车1转W左2","8车1转W右3","8车1转W左1","8车1转W右4"};
		
		
       String [] lunNameSelf = new String [] {"","","","","","","","",
        		                                                                       "","","","","","","","" ,
        		                                                                       "","","","","","","","",
                                                                                       "","","","","","","","",
                                                                                       "","","","","","","","",
        		                                                                       "","","","","","","","" ,
        		                                                                       "","","","","","","","",
                                                                                       "","","","","","","","" };*///地铁公司地方车轮名称//----------------------------------------->注意配置17-7


		//按国标名称命名
		/*String [] lunName = new String [] {
				"TC1-2","TC1-1","TC1-4","TC1-3","TC1-6","TC1-5","TC1-8","TC1-7",
				"MP1-2","MP1-1","MP1-4","MP1-3","MP1-6","MP1-5","MP1-8","MP1-7",
				"M1-2","M1-1","M1-4","M1-3","M1-6","M1-5","M1-8","M1-7",
				"MP2-2","MP2-1","MP2-4","MP2-3","MP2-6","MP2-5","MP2-8","MP2-7",
				
				"M2-7","M2-8","M2-5","M2-6","M2-3","M2-4","M2-1","M2-2",
				"M3-7","M3-8","M3-5","M3-6","M3-3","M3-4","M3-1","M3-2",
				"MP3-7","MP3-8","MP3-5","MP3-6","MP3-3","MP3-4","MP3-1","MP3-2",
				"TC2-7","TC2-8","TC2-5","TC2-6","TC2-3","TC2-4","TC2-1","TC2-2"};
			
			
	       String [] lunNameSelf = new String [] {"","","","","","","","",
	        		                                                                       "","","","","","","","" ,
	        		                                                                       "","","","","","","","",
	                                                                                       "","","","","","","","",
	                                                                                       "","","","","","","","",
	        		                                                                       "","","","","","","","" ,
	        		                                                                       "","","","","","","","",
	                                                                                       "","","","","","","","" };
      int  []lunCode = new int []  {      11,12,21,22,31,32,41,42,
                          51,52,61,62,71,72,81,82,
                          91,92,101,102,111,112,121,122,
                          131,132,141,142,151,152,161,162,
                          
                          171,172,181,182,191,192,201,202,
                          211,212,221,222,231,232,241,242,
                          251,252,261,262,271,272,281,282,
                          291,292,301,302,311,312,321,322};
                            
            
     for(String no:trainsNo){
        	System.out.println("------------------>车号:"+no);
        	String trins = "INSERT INTO train_parts  (train_no_id, train_no, part_code, part_name, part_type, part_name_self) VALUES  ("+(traininfoid++)+", '"+no+ "', ";
        	StringBuffer sb = new StringBuffer();
            for(int i =0; i<lunCode.length;i++){
            	sb.append(trins);
            	sb.append(lunCode[i]+", '"+lunName[i]+"',1,'"+lunNameSelf[i]+"')");
        		System.out.println(sb.toString());
        		sqlScript.add(sb.toString());
        		sb.setLength(0);
        	}
        }*/
		///-------------------------------------->end8节车厢通用车轮
		
		
    	//车轴信息
    /*int  [] zhouCode = new int []  {    1,2,3,4,
	        		                                                      5,6,7,8,
	        		                                                      9,10,11,12,
	        		                                                      13,14,15,16,
	        		                                                      
	        		                                                      17,18,19,20, 
	        		                                                      21,22,23,24, 
	        		                                                      25,26,27,28, 
	        		                                                      29,30,31,32 };//车轴程序编码//----------------------------------------->注意配置17-9
	        
	        String []  zhouName = new  String [] {
																			        		"1轴","2轴","3轴","4轴",
																			        		"1轴","2轴","3轴","4轴",
																			        		"1轴","2轴","3轴","4轴",
																			        		"1轴","2轴","3轴","4轴",
																			        		
																			        		"4轴","3轴","2轴","1轴",
																			        		"4轴","3轴","2轴","1轴",
																			        		"4轴","3轴","2轴","1轴",
																			        		"4轴","3轴","2轴","1轴"}; //地铁车轴国标名称//----------------------------------------->注意配置17-10
	        
	        String []  zhouSelfName =new String [] {"","","","",
                     "","","","",
                     "","","","",
                    "","","",""
                    
                    ,"","","",""
                    ,"","","",""
                    ,"","","",""
                    ,"","","",""};//地方地铁车轴名称//----------------------------------------->注意配置17-11
	        
	        for(String no:trainsNo){
	        	System.out.println("------------------>车号:"+no);
	        	String trins = "INSERT INTO train_parts  (train_no_id, train_no, part_code, part_name, part_type, part_name_self) VALUES  ("+(traininfoid++)+", '"+no+ "', ";
	        	StringBuffer sb = new StringBuffer();
	            for(int i =0; i<zhouCode.length;i++){
	            	sb.append(trins);
	            	sb.append(zhouCode[i]+", '"+zhouName[i]+"',3,'"+zhouSelfName[i]+"')");//------------------------------>注意part_type 类型要改
	        		System.out.println(sb.toString());
	        		sqlScript.add(sb.toString());
	        		sb.setLength(0);
	        	}
	        }*/
		
		
	   	//转向架信息
	    /*int  [] jiaCode =  new int [] {1,2,
	        		 3,4,
	        		 5,6,
	        		 7,8,
	        		 
	        		 9,10,
	        		 11,12,
	        		 13,14,
	        		 15,16};//转向架程序编码//----------------------------------------->注意配置17-12
	        String [] jiaName = new String []{"1转向架","2转向架",
	        		"1转向架","2转向架",
	        		"1转向架","2转向架",
	        		"1转向架","2转向架",
	        		
	        		"2转向架","1转向架",
	        		"2转向架","1转向架",
	        		"2转向架","1转向架",
	        		"2转向架","1转向架"};//地铁转向架国标名称//----------------------------------------->注意配置17-13
	        String [] jiaSelfName = new String [] {"","",
	        		"","",
	        		"","",
	        		"","",
	        		
	        		"","",
	        		"","",
	        		"","",
	        		"",""};//地方地铁转向架名称//----------------------------------------->注意配置17-14
	        
	        for(String no:trainsNo){
	        	System.out.println("------------------>车号:"+no);
	        	String trins = "INSERT INTO train_parts  (train_no_id, train_no, part_code, part_name, part_type, part_name_self) VALUES  ("+(traininfoid++)+", '"+no+ "', ";
	        	StringBuffer sb = new StringBuffer();
	            for(int i =0; i<jiaCode.length;i++){
	            	sb.append(trins);
	            	sb.append(jiaCode[i]+", '"+jiaName[i]+"',2,'"+jiaSelfName[i]+"')");//------------------------------>注意part_type 类型要改
	        		System.out.println(sb.toString());
	        		sqlScript.add(sb.toString());
	        		sb.setLength(0);
	        	}
	        }*/
		
	        //-------------------------------->受电弓部件
		/*int [] pantographCode = new int [] {1,2,3};//------------->注意这里是3个弓//----------------------------------------->注意配置17-15
		   String [] pantographName = new String []{"MP1","MP2","MP3"};//地铁转向架国标名称//----------------------------------------->注意配置17-16
	       String [] pantographSelfName = new String [] {"","",""};//地方地铁转向架名称//----------------------------------------->注意配置17-17
	        
	        for(String no:trainsNo){
	        	System.out.println("------------------>车号:"+no);
	        	String trins = "INSERT INTO train_parts  (train_no_id, train_no, part_code, part_name, part_type, part_name_self) VALUES  ("+(traininfoid++)+", '"+no+ "', ";
	        	StringBuffer sb = new StringBuffer();
	            for(int i =0; i<pantographCode.length;i++){
	            	sb.append(trins);
	            	sb.append(pantographCode[i]+", '"+pantographName[i]+"',6,'"+pantographSelfName[i]+"')");//------------------------------>注意part_type 类型要改
	        		System.out.println(sb.toString());
	        		sqlScript.add(sb.toString());
	        		sb.setLength(0);
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

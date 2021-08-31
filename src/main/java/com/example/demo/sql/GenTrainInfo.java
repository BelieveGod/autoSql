package com.example.demo.sql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenTrainInfo {

	/****
	 *  生成一般平常相关的车辆，被检测部位基本信息数据库脚本(6节车厢)
	 *  注意起始id
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<String> sqlScript = new ArrayList<>();// sql语句脚本
		List<String> trainsNo = new ArrayList<>();//车号
		//------------------------------>生成列车信息
		int groupCount = 6;// 一列车有多少节车厢//----------------------------------------->注意配置17-0
		int tainTotal = 100;// 总共车数量//----------------------------------------->注意配置17-1
		String trainNoPre = "08";//------------------------------车号前缀 注意配置17-2
		
		//读取车号
		/*** List<String> lstTrainno = FileUtil.readLines(new File("E:\\trao.txt"),StandardCharsets.UTF_8);
		 trainsNo.addAll(lstTrainno);
			for (String no:trainsNo) {
				sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('"+ no + "',"+groupCount+",2,8)");// 写入列车信息
			}*/
		
		//生成车号
		for (int i = 1; i <= tainTotal; i++) {
			String tNo = "";
			tNo = trainNoPre + String.format("%02d",i);//------------------------->保留4位可配置%02d 代表保留2位，%03d代表保留4位
			trainsNo.add(tNo);
//			sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('"+ tNo + "',"+groupCount+",2,8)");// 写入列车信息
		}
		  trainsNo.add("02xx");
//		  sqlScript.add("INSERT INTO train_info (  train_no, group_count, pantograph_count, per_wheel_count) VALUES ('02xx',"+groupCount+",2,8)");// 写入02xx列车信息
		
		//-----------------------------------写入车辆被检部位信息
		int traininfoid = 1;//列车数据库id从3开始,要看数据库具体插入情况       -------------- >    注意配置17-3
		
    
     /***  String []  gpName  = new String []{"A1","B1","C1","C2","B2","A2"};//东莞命名*/
		
		
		//-------------begin车厢信息 6节车厢
		
/*	String []  gpName  = new String []{"TC1","MP1","M1","M2","MP2","TC2"};//----------------------------------------->注意配置17-4
        String []  gpNameSelf  = new String []{"","","","","",""};//----------------------------------------->注意配置17-5
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
    	
    	
    	/****String []  gpName  = new String []{"1车","2车","3车","4车","5车","6车"};//----------------------------------------->注意配置17-4
        String []  gpNameSelf  = new String []{"","","","","",""};//----------------------------------------->注意配置17-5
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
		//-------------end车厢信息 6节车厢
		
		
		//车轮信息
        //12,11,22,21,32,31,42,41,---tc1
          //52,51,62,61,72,71,82,81,---mp1
          //92,91,102,101,112,111,122,121,--m1
          //161,162,151,152,141,142,131,132,--m2
          //201,202,191,192,181,182,171,172,--mp2
          //241,242,231,232,221,222,211,212--tc2
          
		///-------------------------------------->东莞车轮，通用车轮命名也可以用
	/****  String [] lunName = new String [] { "TC1-1轴左轮","TC1-1轴右轮","TC1-2轴左轮","TC1-2轴右轮","TC1-3轴左轮","TC1-3轴右轮","TC1-4轴左轮","TC1-4轴右轮",
                  "MP1-1轴左轮","MP1-1轴右轮","MP1-2轴左轮","MP1-2轴右轮","MP1-3轴左轮","MP1-3轴右轮","MP1-4轴左轮","MP1-4轴右轮" ,
                  "M1-1轴左轮","M1-1轴右轮","M1-2轴左轮","M1-2轴右轮","M1-3轴左轮","M1-3轴右轮","M1-4轴左轮","M1-4轴右轮",
                  "M2-4轴左轮","M2-4轴右轮","M2-3轴左轮","M2-3轴右轮","M2-2轴左轮","M2-2轴右轮","M2-1轴左轮","M2-1轴右轮",
                  "MP2-4轴左轮","MP2-4轴右轮","MP2-3轴左轮","MP2-3轴右轮","MP2-2轴左轮","MP2-2轴右轮","MP2-1轴左轮","MP2-1轴右轮" ,
                  "TC2-4轴左轮","TC2-4轴右轮","TC2-3轴左轮","TC2-3轴右轮","TC2-2轴左轮","TC2-2轴右轮","TC2-1轴左轮","TC2-1轴右轮"};
		
		
         String [] lunNameSelf = new String [] {"","","","","","","","",
          		                                                                       "","","","","","","","" ,
          		                                                                       "","","","","","","","",
                                                                                         "","","","","","","","",
                                                                                         "","","","","","","","",
                                                                                         "","","","","","","",""};//地铁公司地方车轮名称//----------------------------------------->注意配置17-7


        int  []lunCode = new int []  {      11,12,21,22,31,32,41,42,
                            51,52,61,62,71,72,81,82,
                            91,92,101,102,111,112,121,122,
                            131,132,141,142,151,152,161,162,
                            171,172,181,182,191,192,201,202,
                            211,212,221,222,231,232,241,242};//轮子程序编码 6节车厢//----------------------------------------->注意配置17-6
*/
        
		///-------------------------------------->begin通用车轮
		
		//----180轮对顺序
	/*	int  []lunCode = new int []  {12,11,22,21,32,31,42,41,
                52,51,62,61,72,71,82,81,
               92,91,102,101,112,111,122,121,
               161,162,151,152,141,142,131,132,
               201,202,191,192,181,182,171,172,
               241,242,231,232,221,222,211,212};//轮子程序编码 6节车厢//----------------------------------------->注意配置17-6
	
	
	String [] lunNameSelf = new String [] {"","","","","","","","",
	                             "","","","","","","","" ,
	                             "","","","","","","","",
	                             "","","","","","","","",
	                             "","","","","","","","",
	                             "","","","","","","",""};//地铁公司地方车轮名称//----------------------------------------->注意配置17-7
	
String [] lunName = new String [] {	 "1车-2轮",	 "1车-1轮","1车-4轮","1车-3轮","1车-6轮","1车-5轮","1车-8轮","1车-7轮",
		                                                              "2车-2轮",   "2车-1轮","2车-4轮","2车-3轮","2车-6轮","2车-5轮","2车-8轮" ,"2车-7轮",
		                                                              "3车-2轮", "3车-1轮","3车-4轮","3车-3轮","3车-6轮","3车-5轮","3车-8轮","3车-7轮",
		                                                              
		                                                              "4车-2轮", "4车-1轮","4车-4轮","4车-3轮","4车-6轮","4车-5轮","4车-8轮","4车-7轮",
		                                                              "5车-2轮","5车-1轮","5车-4轮","5车-3轮","5车-6轮","5车-5轮","5车-8轮","5车-7轮",
		                                                              "6车-2轮",  "6车-1轮","6车-4轮","6车-3轮","6车-6轮","6车-5轮","6车-8轮","6车-7轮"
		                                                              };//地铁国标车轮名称//----------------------------------------->注意配置17-8
	                  
	  
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
	
		
		//通用

/*  int  []lunCode = new int []  {12,11,22,21,32,31,42,41,
                  52,51,62,61,72,71,82,81,
                 92,91,102,101,112,111,122,121,
                 161,162,151,152,141,142,131,132,
                 201,202,191,192,181,182,171,172,
                 241,242,231,232,221,222,211,212};//轮子程序编码 6节车厢//----------------------------------------->注意配置17-6


String [] lunNameSelf = new String [] {"","","","","","","","",
                                       "","","","","","","","" ,
                                       "","","","","","","","",
                                       "","","","","","","","",
                                       "","","","","","","","",
                                       "","","","","","","",""};//地铁公司地方车轮名称//----------------------------------------->注意配置17-7

String [] lunName = new String [] {"TC1-1","TC1-2","TC1-3","TC1-4","TC1-5","TC1-6","TC1-7","TC1-8",
                                "MP1-1","MP1-2","MP1-3","MP1-4","MP1-5","MP1-6","MP1-7","MP1-8" ,
                                "M1-1","M1-2","M1-3","M1-4","M1-5","M1-6","M1-7","M1-8",
                                "M2-1","M2-2","M2-3","M2-4","M2-5","M2-6","M2-7","M2-8",
                                "MP2-1","MP2-2","MP2-3","MP2-4","MP2-5","MP2-6","MP2-7","MP2-8",
                                "TC2-1","TC2-2","TC2-3","TC2-4","TC2-5","TC2-6","TC2-7","TC2-8"};//地铁国标车轮名称//----------------------------------------->注意配置17-8
                            
            
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
		///-------------------------------------->end通用车轮
		
		
		///-------------------------------------->begin--------------------------------- 813，205项目车轮
   /***int  []lunCode = new int []  {12,11,22,21,32,31,42,41,
                  52,51,62,61,72,71,82,81,
                 92,91,102,101,112,111,122,121,
                 161,162,151,152,141,142,131,132,
                 201,202,191,192,181,182,171,172,
                 241,242,231,232,221,222,211,212};//轮子程序编码 6节车厢//----------------------------------------->注意配置17-6


String [] lunNameSelf = new String [] {"","","","","","","","",
                                       "","","","","","","","" ,
                                       "","","","","","","","",
                                       "","","","","","","","",
                                       "","","","","","","","",
                                       "","","","","","","",""};//地铁公司地方车轮名称//----------------------------------------->注意配置17-7

String [] lunName = new String [] {	 "TC1-2",	 "TC1-1","TC1-4","TC1-3","TC1-6","TC1-5","TC1-8","TC1-7",
		                                                              "MP1-2",   "MP1-1","MP1-4","MP1-3","MP1-6","MP1-5","MP1-8" ,"MP1-7",
		                                                              "M1-2", "M1-1","M1-4","M1-3","M1-6","M1-5","M1-8","M1-7",
		                                                              
		                                                              "M2-2", "M2-1","M2-4","M2-3","M2-6","M2-5","M2-8","M2-7",
		                                                              "MP2-2","MP2-1","MP2-4","MP2-3","MP2-6","MP2-5","MP2-8","MP2-7",
		                                                              "TC2-2",  "TC2-1","TC2-4","TC2-3","TC2-6","TC2-5","TC2-8","TC2-7"
		                                                              };//地铁国标车轮名称//----------------------------------------->注意配置17-8
                            
            
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
		///-------------------------------------->end------------------------------------813项目车轮
		
		
		
    	//车轴信息
    /* int  [] zhouCode = new int []  {1,2,3,4,
	        		                                                      5,6,7,8,
	        		                                                      9,10,11,12,
	        		                                                      13,14,15,16,
	        		                                                      17,18,19,20,
	        		                                                      21,22,23,24};//车轴程序编码//----------------------------------------->注意配置17-9
	        
	        String []  zhouName = new  String [] {
																			        		"1轴","2轴","3轴","4轴",
																			        		"1轴","2轴","3轴","4轴",
																			        		"1轴","2轴","3轴","4轴",
																			        		"4轴","3轴","2轴","1轴",
																			        		"4轴","3轴","2轴","1轴",
																			        		"4轴","3轴","2轴","1轴"}; //地铁车轴国标名称//----------------------------------------->注意配置17-10
        
        
	        
	        String []  zhouSelfName =new String [] {"","","","",
                     "","","","",
                     "","","","",
                    "","","","",
                     "","","","",
                     "","","",""};//地方地铁车轴名称//----------------------------------------->注意配置17-11
	        
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
	        		 11,12};//转向架程序编码//----------------------------------------->注意配置17-12
	        String [] jiaName = new String []{"1转向架","2转向架",
	        		"1转向架","2转向架",
	        		"1转向架","2转向架",
	        		"2转向架","1转向架",
	        		"2转向架","1转向架",
	        		"2转向架","1转向架"};//地铁转向架国标名称//----------------------------------------->注意配置17-13


	        String [] jiaSelfName = new String [] {"","",
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
	/*int [] pantographCode = new int [] {1,2};//------------->注意这里是2个弓//----------------------------------------->注意配置17-15
		   String [] pantographName = new String []{"MP1","MP2"};//地铁转向架国标名称//----------------------------------------->注意配置17-16
	       String [] pantographSelfName = new String [] {"",""};//地方地铁转向架名称//----------------------------------------->注意配置17-17
	        
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

package com.hualife.wxhb.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class JsonToXml {
	
	public static String xml2JSON(String xml){
		return new XMLSerializer().read(xml).toString();
	}
	
	public static String json2XML(String json){
		JSONObject jobj = JSONObject.fromObject(json);
		String xml =  new XMLSerializer().write(jobj);
		return xml;
	}
	
	@SuppressWarnings({ "finally", "resource", "unused" })
	private static String readTxtFile(String filePath){
		String lineTxt = null;
		File file=new File(filePath);
        try {
            String encoding="GBK";
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                while((lineTxt = bufferedReader.readLine()) != null){
                	System.out.println("lineTxt="+lineTxt);
                	read.close();
                    return lineTxt;
                }
	        }else{
	            System.out.println("找不到指定的文件");
	            return null;
	        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        } finally{
        	return lineTxt;
        }
        
      
    }
      
	/*public static void main(String[] args) {
		String filePath = "E:\\test\\new.txt";
		String xml = json2XML(JsonToXml.readTxtFile(filePath));
		System.out.println("xml = "+xml);
	}*/
	
	
	//将txt转换为String
		public static String readTxt(String path) throws IOException{
			StringBuffer content = new StringBuffer(""); //文档内容
			try {
				FileReader reader = new FileReader(path);
				BufferedReader br = new BufferedReader(reader);
				String s1 = null;
				while((s1 = br.readLine()) != null) {
					content.append(s1+"\r");
				}
				br.close();
				reader.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return content.toString().trim();
			
		}
		
	
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {  
       /* String jsonStr = "[{\"Product_ID\":\"0501010093\",\"Product_name\":\"美素（金装美素乐）奶粉二段900g\",\"Product_Pic\":\"jpg\",\"Product_URL\":\"http\",\"Product_price\":216},"  
                + "{\"Product_ID\":\"0501010093\",\"Product_name\":\"美素（金装美素乐）奶粉二段900g\",\"Product_Pic\":\"jpg\",\"Product_URL\":\"http\",\"Product_price\":216},"  
                + "{\"Product_ID\":\"0501010093\",\"Product_name\":\"美素（金装美素乐）奶粉二段900g\",\"Product_Pic\":\"jpg\",\"Product_URL\":\"http\",\"Product_price\":216},]"; */ 
       /* JSONArray jsonObject = JSONArray.fromObject(json);  
        XMLSerializer xmlSerial = new XMLSerializer();  
        String xml = xmlSerial.write(jsonObject);  
        System.out.println(xml); */
        
/*        
        String jsonStr = JsonToXml.readTxtFile("E:\\test\\new.txt");
        System.out.println("jsonStr = "+jsonStr);
        JSONObject obj = new JSONObject().fromObject(jsonStr);
        NoteFromCoreRequestMessage jb = (NoteFromCoreRequestMessage)JSONObject.toBean(obj,NoteFromCoreRequestMessage.class);
        System.out.println(jb.getHead().getVersion());
        
        */
			String jsonStr = "{\"InputData\":{\"Body\":{\"Agent_name\":\"万超\",\"Agent_no\":\"110001798\",\"Agent_phone\":18173135774,\"Applicant_name\":\"余杰\",\"Applicant_no\":15475219,\"Applicant_phone\":18174406524,\"Branch_code\":\"86310101\",\"Branch_name\":\"结长此教算适\",\"Channel_type\":\"05\",\"Client_age\":20,\"Client_idno\":12071827,\"Client_name\":\"邓娜\",\"Client_no\":19378387,\"Client_sex\":\"1\",\"FinaOocuInfos\":{\"Count\":1,\"FinaOocuInfo\":{\"Deciding_date\":\"1991-04-1814:20:12\",\"FinaItems\":{\"Count\":2,\"FinaItem\":{\"Note_item_type\":\"01\"}},\"Insured_id_no\":17771686,\"Insured_id_type\":\"02\",\"Insured_phone\":18184714079,\"Is_agent_report\":false,\"Note_bar_code\":12876363,\"Note_reason\":\"基适电流再马\",\"Note_seq\":3,\"Special_desc\":\"名状队少儿\"}},\"HealthInfos\":{\"Count\":1,\"HealthInfo\":{\"Deciding_date\":\"2014-07-1803:34:38\",\"HealthItems\":{\"Count\":1,\"HealthItem\":{\"Note_item_type\":\"c\"}},\"Note_bar_code\":13674424,\"Note_reason\":\"装律或界采克效千\",\"Note_seq\":3,\"Other_invest\":\"无主变属真这\",\"Special_desc\":\"今到律\"}},\"PhysicalInfos\":{\"Count\":1,\"PhysicalInfo\":{\"Deciding_date\":\"1998-08-0600:43:41\",\"Is_self_health\":true,\"Note_bar_code\":12536774,\"Note_reason\":\"话照质主断点\",\"Note_seq\":2,\"PhysicalItems\":{\"Count\":2,\"PhysicalItem\":{\"Physicalitem\":\"02\"}}}},\"ProblemInfos\":{\"Count\":1,\"ProblemInfo\":{\"Agent_name\":\"蔡杰\",\"Agent_no\":\"110001798\",\"Agent_phone\":18127839831,\"Application_name\":\"史平\",\"Application_no\":12514799,\"Application_phone\":18152309288,\"Apply_bar_code\":17949894,\"Branch_code\":\"86010101\",\"Branch_name\":\"管号里立拉民样当起\",\"Channel_type\":\"05\",\"Deciding_date\":\"1995-10-1622:46:48\",\"Insured_age\":81,\"Insured_name\":\"韩静\",\"Insured_no\":15701226,\"Note_bar_code\":19221881,\"Note_reason\":\"问存九件们明\",\"Note_seq\":2,\"ProblemObjects\":{\"Count\":1,\"ProblemObject\":{\"Problem_object\":\"02\",\"Problems\":{\"Count\":1,\"Problem\":[{\"Problem_Column\":\"路约导千取\",\"Problem_desc\":\"离证青引置\",\"Problem_seq\":1}]}}}}},\"RelevanceInfos\":{\"Count\":1,\"RelevanceInfo\":{\"Applicant_name\":\"梁娟\",\"Applicant_phone\":18114318872,\"Apply_bar_code\":17590297,\"Insured_name\":\"杨明\"}},\"SurvivalInfos\":{\"Count\":1,\"SurvivalInfo\":{\"Agent_branch_address\":\"按基治习平再现民\",\"Agent_file\":\"且定级更算记报行\",\"Client_idno\":17964067,\"Deciding_date\":\"2008-09-1408:27:43\",\"Note_bar_code\":17612134,\"Note_reason\":\"效你特形\",\"Note_seq\":2,\"Other_invest\":\"角白该象布数能张\",\"Special_desc\":\"花需号电\",\"SurvivalItems\":{\"Count\":1,\"SurvivalItem\":{\"Note_item_type\":\"01\"}},\"Survival_branch_code\":\"86420101\",\"Survival_branch_province\":\"湖南\",\"Survival_date\":\"2013-06-2100:35:16\",\"Survival_mode\":\"02\",\"Survival_reason\":\"解究易过实线至名长\"}},\"Taskcode\":11352347,\"UndwrtInfos\":{\"Count\":1,\"UndwrtInfo\":{\"Account_no\":15882489,\"Agent_name\":\"贾娟\",\"Agent_no\":\"110001797\",\"Agent_phone\":18142204782,\"Application_name\":\"邱娜\",\"Application_no\":13460889,\"Application_phone\":18134365371,\"Apply_bar_code\":17092428,\"Branch_code\":\"86420101\",\"Branch_name\":\"教化热连等算\",\"Channel_type\":\"05\",\"Company_address\":\"口开资\",\"Company_postal_code\":\"100000\",\"Deciding_date\":\"1989-10-2413:53:07\",\"Insured_age\":72,\"Insured_name\":\"郑静\",\"Insured_no\":12806954,\"Note_bar_code\":19532745,\"Note_seq\":3,\"Note_type\":\"01\",\"ProductInfos\":{\"Count\":\"1\",\"ProductInfo\":{\"Add_extra_trem_period_type\":\"02\",\"Add_extra_trem_period\":8,\"Cut_face_amount\":591.3,\"Exclustion\":\"领什\",\"Limitd_face_amount\":604.4,\"Limitd_units\":2,\"Postponed_period_type\":\"02\",\"Postponed_period\":3,\"Product_code\":\"123123\",\"Product_name\":\"其果月\",\"Total_health_add_prem\":906.4,\"Total_occu_add_prem\":455.2,\"Total_prem\":567.73,\"Undwrt_result_reason\":\"油复\",\"Undwrt_result\":\"01\"}}}}},\"Head\":{\"Cn2utf8\":\"1\",\"Msg\":{\"CallType\":\"1\",\"ClientIP\":\"1\",\"EffectiveTime\":\"1\",\"FromSystemkey\":\"1\",\"Password\":\"1\",\"ServiceID\":\"1\",\"ServiceType\":\"1\",\"ToSystemKey\":\"1\",\"TransDate\":\"1\",\"TransNo\":\"1\",\"TransTime\":\"1\"},\"Version\":\"1\"}}}";
			JSONObject obj = new JSONObject().fromObject(jsonStr);
			XMLSerializer xmlSerial = new XMLSerializer();  
			xmlSerial.setTrimSpaces(false);
			xmlSerial.setTypeHintsEnabled(false);
	        String xml = xmlSerial.write(obj); 
	        xml = xml.replace("<o>", "");
	        xml = xml.replace("<e>", "");
	        xml = xml.replace("</e>", "");
	        xml = xml.replace("</o>", "");
	        System.out.println(xml);
	
    }  
	
}

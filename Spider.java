import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider {
	/*
	 * SendGet: method to take a web's content into a string
	 * RegexString: to fix patternStr in source targetStr, then return
	 * GetList: using a arraylist to store the fix result
	 * GetAllRecommendTopic
	 * GetDouBan
	 */
	
	// link the url and take all the source into a string
	public static String SendGet(String url, String decode){
		//定义一个字符串用来存储网页内容
		String result = "";
		//定义一个缓冲字符输入流
		BufferedReader in = null; //为什么要用buffered，因为是可追加的。为什么不用byte，因为是定长的。若未超长则可追加，若超长了则不能追加
		try{
			//将string 转成url对象
			URL realUrl = new URL(url);
			//初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			//开始实际的连接
			connection.connect();
			//初始化bufferedReader输入流来读取url的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), decode));
			//用来临时存储抓取到的每一行数据
			String line;
			while((line = in.readLine()) != null){
				//遍历抓取到的每一行都放到result中去
				result += line;
			}
		} catch (Exception e){
			System.out.println("something wrong when sending GET requirement! " +e);
			e.printStackTrace();
		}
		//无论成功与否，用finally来关闭输入流
		finally{
			try{
				if(in != null){
					in.close();
				}
			} catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	// to fix patternStr in source targetStr, then return
	public static String RegexString(String targetStr, String patternStr){
		//定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		Pattern pattern = Pattern.compile(patternStr);
		//定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		if(matcher.find()){
			//System.out.println(matcher.group(1));
			return matcher.group(1);
			}
		else{
			return "Nothing";
		}		
	}
	
	// using a arraylist to store the fix result
	public static ArrayList<String> GetList(String content, String patternStr){
		ArrayList<String> results = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(content);
		
		boolean isFind = matcher.find() ;

		while(isFind){
			results.add(matcher.group(1));
			isFind = matcher.find();
		}
		return results;
	}
	/*
	 * in the recommendation, we are given many topics and a best answers to it.
	 * what we going to do is to get all the topic into a ArrayList, and in this, 
	 * every element in list is a class called ZhiHU, which contains many detail description
	 */
	public static ArrayList<ZhiHu> GetAllRecommendTopic(String content){
		ArrayList<ZhiHu> TopicList = new ArrayList<ZhiHu>();
		Pattern pattern = Pattern.compile("<h2>.+?question_link.+?href=\"(.*?)\".+?</h2>");
		Matcher matcher = pattern.matcher(content);
		Boolean isFind = matcher.find();
		while(isFind){
			//System.out.println("the answer link to one topic is " + matcher.group(1));
			
			ZhiHu zhihuTemp = new ZhiHu(matcher.group(1));
			TopicList.add(zhihuTemp);
			isFind = matcher.find();
		}
		return TopicList;
	}
	
	public static void GetDouBan(String content){
		//ArrayList<douban> results = new ArrayList<douban>();
		//douban = subject-title.*?>(.*?)<.*?allstar(.*?)0 main-title-rating.*?short-content\">(.*?)<
		Pattern pattern = Pattern.compile("subject-title.*?>(.*?)<.*?allstar(.*?)0 main-title-rating.*?short-content\">(.*?)");
		Matcher matcher = pattern.matcher(content);
		boolean isfind = matcher.find();
		while(isfind){
			//douban doubanTemp = new douban();
			System.out.println("name: " + matcher.group(1));
			System.out.println("score: " + matcher.group(2));
			System.out.println("comment: " + matcher.group(3));
			isfind = matcher.find();
		}

	}
	/**/
	
}

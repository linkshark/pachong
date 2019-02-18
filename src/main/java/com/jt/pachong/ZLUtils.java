package com.jt.pachong;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ZLUtils {
	public boolean flag;

	private static final Logger log = Logger.getLogger(ZLUtils.class);

	@Test
	public void run() throws Exception {
		//PropertyConfigurator.configure("D:\\eclipse_workspace-jt\\jt1711-web\\src\\main\\resources\\log4j.properties");
		List<Job> allJobList = new ArrayList<>();
		List<String> allJobUrlList = new ArrayList<>();
		List<String> positionUrlList = ZLUtils.getPositionCategoryUrlList("http://sou.zhaopin.com");// 所有job入口
		int j = 1;
		for (String positionUrl : positionUrlList) {// 循环每个岗位
			List<String> pageUrlList = ZLUtils.getPageUrlList(positionUrl);
			int i = 1;
			for (String pageUrl : pageUrlList) {// 循环每个岗位的每一页
				List<String> pageJobUrlList = ZLUtils.getPageJobUrlList(pageUrl);
				for (String jobUrl : pageJobUrlList) {
					//log.debug(jobUrl+"\n\r");
					Job job = ZLUtils.getJob(jobUrl);
					ConnectDB con = new ConnectDB();
					con.init();
					con.insert(job);
					con.exit();
					//allJobUrlList.add(jobUrl);
					allJobList.add(job);
					log.debug(job);
					
				}
				log.debug("1111111 完成数据爬取页数： " + i++);
			}
			log.debug("----------22222完成了一个岗位的爬取工作: " + j++);
			log.debug("33333 job数据存储: " + allJobUrlList.size());
		}
	}

	//收集   杭州 职位：软件/互联网开发/系统集成 下的所有job对象
	@Test
	public  void testHZInternet(){
		List<Job> allJobList = new ArrayList<>();
		String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?bj=160000&jl=%E6%9D%AD%E5%B7%9E&isadv=1";
		List<String> pageUrlList = ZLUtils.getPageUrlList(url);
		
		for (String pageUrl : pageUrlList) {// 循环每个岗位的每一页
			List<String> pageJobUrlList = ZLUtils.getPageJobUrlList(pageUrl);
			for (String jobUrl : pageJobUrlList) {
//				log.debug(jobUrl);
				Job job = ZLUtils.getJob(jobUrl);
				
//				allJobList.add(job);
				
				log.debug(job.toString());
				ConnectDB con = new ConnectDB();
				con.init();
				con.insert(job);
				con.exit();
//				String jsonData = MAPPER.writeValueAsString(allJobList);
//				redis.set("zlwarm:"+i++, jsonData);
			}
			//log.debug("1111111 完成数据爬取页数： " + i++);
		}
	}

//	 @Test
	public void runTest() throws Exception {
		// ZLUtils.getPositonUrlList("http://sou.zhaopin.com/");
		// ZLUtils.getPageNum("http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1");
		// ZLUtils.getPageUrlList("http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1");
		// ZLUtils.getPageJobUrlList("http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1&p=4");

		// ZLUtils.getTitle("http://jobs.zhaopin.com/614487888250079.htm");
		// ZLUtils.getCompany("http://jobs.zhaopin.com/614487888250079.htm");
		// ZLUtils.getSalary("http://jobs.zhaopin.com/614487888250079.htm");
		// ZLUtils.getWorkLocation("http://jobs.zhaopin.com/614487888250079.htm");
		// ZLUtils.getJobDesc("http://jobs.zhaopin.com/152193715250023.htm");
		// ZLUtils.getJob("http://jobs.zhaopin.com/614487888250079.htm");
	}

	// 06 获取job
	public static Job getJob(String url) {
		Job job = new Job();
		job.setTitle(ZLUtils.getTitle(url));
		job.setCompany(ZLUtils.getCompany(url));
		job.setMinSalary(Integer.parseInt(ZLUtils.getSalary(url).split("-")[0]));
		job.setMaxSalary(Integer.parseInt(ZLUtils.getSalary(url).split("-")[1]));
		job.setWorkLocation(ZLUtils.getWorkLocation(url));
		job.setEducation(ZLUtils.getEducation(url));
		job.setWelfare(ZLUtils.getWelfare(url));
		job.setCreated(new Date());
		job.setUrl(url);
		job.setInfo(ZLUtils.getInfo(url));
		job.setExperience(ZLUtils.getExperience(url));
		//log.debug(job.toString());
		return job;
	}

	// 05.8抓取 职位描述
	public static String getInfo(String url) {
		String jobDesc = "+";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select(".tab-inner-cont");
			jobDesc = elements.get(0).text();
			// log.debug(jobDesc);
		} catch (IOException e) {
			log.error("getInfo错误_url:" + url);
		}
		return jobDesc;
	}
	
	//05.7 抓取 福利待遇
	public static String getWelfare(String url){
		String everyWelfare = "";
		String welfare = "";
		try{
			Elements elements = Jsoup.connect(url).get().select(".welfare-tab-box span");
			//log.debug("福利待遇标签个数："+elements.size());
			for(int i=0;i<elements.size();i++){
				everyWelfare = elements.get(i).text();
				welfare = welfare+everyWelfare+",";
			}
			//log.debug(welfare);
		}catch (Exception e) {
			log.error("getWelfare错误_url:" + url);
		}
		return welfare;
	}
	
	//05.6 抓取 工作经验
	public static String getExperience(String url){
		String experience = "+";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("li strong");
			experience = elements.get(4).text();
			// log.debug(experience);
		} catch (IOException e) {
			log.error("getExperience错误_url:" + url);
			
		}
		return experience;
	}
	
	//05.5 抓取 最低学历
	public static String getEducation(String url){
		String education = "+";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("li strong");
			education = elements.get(5).text();
			// log.debug(education);
		} catch (IOException e) {
			log.error("getEducation错误_url:" + url);
		}
		return education;
	}

	// 05.4 抓取 工作地点
	public static String getWorkLocation(String url) {
		String workLocation = "+";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("li strong");
			workLocation = elements.get(1).text();
			// log.debug(workLocation);
		} catch (IOException e) {
			log.error("getWorkLocation错误_url:" + url);
		}
		return workLocation;
	}

	// 05.3 抓取 职位月薪
	public static String getSalary(String url) {
		String salary = "+";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("li strong");
			salary = elements.get(0).text();
			// log.debug(salary);
			if(salary.length()>4){
				if(salary.contains("-")){
					salary=salary.substring(0, salary.length()-4).replace("-", ",");
					String minsalary=salary.substring(0,salary.indexOf(","));
					String maxsalary=salary.substring(salary.indexOf(",")+1,salary.length());
					salary = minsalary+"-"+maxsalary;//10000-20000元/月
				}
			}else{
				return "";
			}
		} catch (IOException e) {
			log.error("getSalary错误_url:" + url);
		}
		return salary;
	}

	// 05.2 抓取company
	public static String getCompany(String url) {
		String company = "+";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select(".fl").select("h2").select("a");
			company = elements.get(0).text();
			// log.debug(company);
		} catch (IOException e) {
			log.error("getCompany错误_url:" + url);
		}
		return company;
	}

	// 05.1 抓取某一个job的title
	public static String getTitle(String url) {
		String title = "+";
		try {
			// Elements elements =
			// Jsoup.connect(url).get().select(".fixed-inner-box").select("inner-left").select("fl").select("h1");
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select(".fl").select("h1");
			title = elements.get(0).text();
			// log.debug(title);
		} catch (IOException e) {
			log.error("getTitle错误_url:" + url);
		}
		return title;
	}

	// 04 获取当前职位的当前分页下的所有pageJobUrlList<String>
	// http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1&p=18
	public static List<String> getPageJobUrlList(String url){
		List<String> pageJobUrlList = new ArrayList<>();
		try {
			//设置 请求头，模拟浏览器访问
			 Connection conn = Jsoup.connect(url).timeout(5000);  
	         conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
	         conn.header("Accept-Encoding", "gzip, deflate, sdch");  
	         conn.header("Accept-Language", "zh-CN,zh;q=0.8");  
	         conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
	         
			Elements elements = Jsoup.connect(url).get().select(".zwmc div a");
			for (Element element : elements) {
				String jobUrl = element.attr("href");
				// 过滤无效连接
				if (jobUrl.startsWith("http://jobs.zhaopin.com/")) {
					//log.debug(jobUrl);//
					pageJobUrlList.add(jobUrl);
				}
			}
			// log.debug("pageUrlList的容量："+pageJobUrlList.size());
		} catch (Exception e) {
			log.error("获取当前分页下的所有job链接错误_url："+ url);
		}
		return pageJobUrlList;
	}

	// 03 获取当前职位下的每个分页的pageUrlList
	// http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1
	public static List<String> getPageUrlList(String url) {
		List<String> pageUrlList = new ArrayList<>();
		try {
			int pageNum = ZLUtils.getPageNum(url);
			for (int i = 1; i <= pageNum; i++) {// 得到每一页的所有目标元素
				String pageUrl = url + "&p=" + i;
				//log.debug(pageUrl);// http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1&p=4
				pageUrlList.add(pageUrl);
			}
			// log.debug("当前分类的页面总数： "+pageUrlList.size());
		} catch (Exception e) {
			log.error("获取每个分页链接错误_url："+ url);
		}
		return pageUrlList;
	}

	// 02 获取某个职位下的所有的“共***个职位”
	public static Integer getPageNum(String url) {
		Integer pageNum = null;
		try {
			Elements elements = Jsoup.connect(url).get().select(".seach_yx .search_yx_tj em");
			pageNum = Integer.parseInt(elements.get(0).text());
			// log.debug(pageNum);//2057
			pageNum = pageNum / 60 + 1;
			// log.debug(pageNum);//35
		} catch (Exception e) {
			log.error("获取职位总数错误_url："+ url);
		}
		return pageNum;
	}

	// 01 获取所有职位分类的url List<String> getPositionCategoryUrlList
	// http://sou.zhaopin.com/ 所有二级页面的入口
	public static List<String> getPositionCategoryUrlList(String url) throws Exception {
		List<String> positionUrlList = new ArrayList<>();
		Elements elements = Jsoup.connect(url).get().select("#search_bottom_content_demo .clearfixed h1 a");
		for (Element element : elements) {
			String positionUrl = element.attr("href");
			// 过滤无效连接
			if (positionUrl.startsWith("/jobs/searchresult")) {
				positionUrl = "http://sou.zhaopin.com" + positionUrl + "&isadv=1";
				positionUrlList.add(positionUrl);
				// log.debug(positionUrl);//http://sou.zhaopin.com/jobs/searchresult.ashx?jl=1&bj=100000&sj=655&isadv=1
			}
		}
		// log.debug("==============="+elements.size());//1034
		return positionUrlList;
	}
}
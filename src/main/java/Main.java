import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args)
    {
        PrintStream ps = null;
        try {
            ps = new PrintStream("./beijing.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(ps);//把创建的打印输出流赋给系统。即系统下次向 ps输出
        List<String> cityUrls = ConnectWEB.getAttributeByRule("https://bj.lianjia.com/ershoufang/", "div[data-role=ershoufang] div a", "href");
        List<String> cityName = ConnectWEB.getTextByRule("https://bj.lianjia.com/ershoufang/", "div[data-role=ershoufang] div a");
        List<String> regionUrls = new ArrayList<String>();
        List<String> regionName = new ArrayList<String>();
        List<String> homeUrls = new ArrayList<String>();
        for (int i = 0; i < cityUrls.size(); i++)
        {
            System.out.println("=================================================");//大区名
            System.out.println(cityName.get(i));//大区名
            System.out.println(cityUrls.get(i));//大区地址
            List<String> tempregionUrls = ConnectWEB.getAttributeByRule("https://bj.lianjia.com" + cityUrls.get(i), "dd div[data-role=ershoufang] div a", "href");
            List<String> tempregionName = ConnectWEB.getTextByRule("https://bj.lianjia.com" + cityUrls.get(i), "dd div[data-role=ershoufang] div a");
            regionName.clear();
            regionUrls.clear();
            for (int j = cityUrls.size(); j < tempregionUrls.size(); j++)
            {
                regionUrls.add(tempregionUrls.get(j));
                regionName.add(tempregionName.get(j));
                //System.out.println(tempregionName.get(j));//小区名
                //System.out.println(tempregionUrls.get(j));//小区地址
            }
            for (int j = 0; j < regionUrls.size(); j++ )
            {
                System.out.println("-------------------------------------");//大区名
                System.out.println(regionName.get(j));//小区名
                System.out.println(regionUrls.get(j));//小区地址
                List<String> nums = ConnectWEB.getAttributeByRule("https://bj.lianjia.com" + regionUrls.get(j), "div .page-box.house-lst-page-box", "page-data");
                int num = Integer.parseInt(ConnectWEB.getNum(nums.get(0)));
                for (int k = 1; k <= num; k++)
                {
                    homeUrls = ConnectWEB.getAttributeByRule("https://bj.lianjia.com"+ regionUrls.get(j) + "pg"+ k +"/", "div .info.clear .title a", "href");
                    for (int l=0; l<homeUrls.size(); l++)
                    {
                        System.out.println("...............................");//大区名
                        System.out.println(homeUrls.get(l));//房子地址
                        try {
                            List<String> title = ConnectWEB.getHouseInfo(homeUrls.get(l));
                            for (String temp: title) {
                                System.out.println(temp);//房子信息
                            }
                        }catch (Exception e)
                        {
                            System.out.println("出错了，跳过该项");
                            try {
                                sleep(5000);
                            } catch (InterruptedException e1) {

                            }
                            l++;
                            continue;
                        }

                    }
                }
            }
        }
    }
}

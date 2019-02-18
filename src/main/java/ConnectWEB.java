import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectWEB {
    public static List<String> getAttributeByRule(String url, String rule, String Attribute) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").get();
        } catch (IOException e) {
            return null;
        }
        Elements elements = doc.select(rule);
        //System.out.println(elements);
        List<String> stringList =new ArrayList<String>();
        for (Element ele : elements) {
            String string=ele.attr(Attribute);
            stringList.add(string);
        }
        return stringList;
    }
    public static List<String> getTextByRule(String url, String rule) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").get();
        } catch (IOException e) {
            return null;
        }
        Elements elements = doc.select(rule);
        //System.out.println(elements);
        List<String> stringList =new ArrayList<String>();
        for (Element ele : elements) {
            String string=ele.text();
            stringList.add(string);
        }
        return stringList;
    }

    public static String getNum(String num)
    {
        char[] temp = new char[100];
        boolean flag = false;
        int j = 0;
        for (int i=0; i<num.length(); i++)
        {
            if (num.charAt(i) == ':' || num.charAt(i) == ',' )
            {
                if (!flag)
                {
                    flag = true;
                    continue;
                }
                else break;
            }
            if (flag)
            {
                temp[j] = num.charAt(i);
                j++;
            }
        }
        return String.valueOf(temp).trim();
    }

    public static List<String> getHouseInfo(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(5000).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36").get();
        } catch (IOException e) {
            return null;
        }
        List<String> stringList =new ArrayList<String>();
        String temp;
        stringList.add(doc.select("div .content .title h1").text());


        temp = doc.select("div .houseInfo .area .mainInfo").text();
        stringList.add(temp.substring(0, temp.length()-2));

        stringList.add(doc.select("span[class=total]").text());
        Elements elements = doc.select("ul[class=smallpic]").select("li");
        stringList.add( elements.get(0).attr("data-src") );
        temp = doc.select("span[class=unitPriceValue]").text();
        stringList.add(temp.substring(0, temp.length()-4));
        temp = doc.select("div .houseInfo .room .mainInfo").text();
        temp += "#";
        temp += doc.select("div .houseInfo .room  .subInfo").text();
        temp += "#";
        temp += doc.select("div .houseInfo .type  .mainInfo").text();
        temp += "#";
        temp += doc.select("div .houseInfo .type  .subInfo").text();
        temp += "#";
        temp += doc.select("div .houseInfo .area  .subInfo").text();
        stringList.add(temp);

        stringList.add(doc.select("div .brokerInfoText.fr .brokerName a").get(0).text());
        stringList.add(doc.select("div[class=areaName]").select("span[class=info]").select("a").text());
        //System.out.println(elements);

        return stringList;
    }
}

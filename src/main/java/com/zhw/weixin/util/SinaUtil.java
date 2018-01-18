package com.zhw.weixin.util;

import com.zhw.weixin.bean.AccessToken;
import com.zhw.weixin.bean.Menu;
import com.zhw.weixin.logic.MyX509TrustManager;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhanghongwei on 17/12/31.
 */
public class SinaUtil {

    private static Logger log = LoggerFactory.getLogger(SinaUtil.class);

    // 股票接口
    public final static String get_gupiao_url = "http://hq.sinajs.cn/list=";


    /**
     * 创建菜单
     * @return 0表示成功，其他值表示失败
     */
    public static GupiaoInfo getGupiaoPrice(String code) {
        String temp = "";
        if (code.startsWith("60")) {
            temp = "sh";
        } else if (code.startsWith("00")) {
            temp = "sz";
        }
        // 拼装创建菜单的url
        String url = get_gupiao_url + temp + code;
        // 调用接口创建菜单
        String result = httpRequest(url, "GET", null);
        String[] infoArray = result.split(",");
        if (infoArray.length == 0) {
            return null;
        }
        GupiaoInfo info = new GupiaoInfo();
        info.setCode(code);
        info.setName(infoArray[0].substring(result.indexOf("\"") + 1));
        info.setPrice(Double.valueOf(infoArray[3]));
        return info;
    }

    public static void main(String[] args) {
        System.out.println(getGupiaoPrice("002080"));
    }

    /**
     * 描述:  发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    private static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("gbk"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return "";
    }

    public static class GupiaoInfo {
        private String name;

        private String code;

        private double price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "GupiaoInfo{" +
                    "price=" + price +
                    ", code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

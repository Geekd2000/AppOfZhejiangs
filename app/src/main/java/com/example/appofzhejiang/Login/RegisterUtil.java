package com.example.appofzhejiang.Login;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RegisterUtil {

    private final static String PATH = "http://120.26.172.104:9002//web/userRegiste";
    private static URL url;

    private static RegisterBean datas = null;

    public RegisterUtil() {

    }

    @SuppressWarnings("deprecation")
    public static RegisterBean sendHttpRequst(String username, String tel, String tell, String encode) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("tel", tel);
        params.put("tell", tell);
        // 对查询的参数进行封装
        StringBuffer buffer = new StringBuffer();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                buffer.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
            }
            buffer.deleteCharAt(buffer.length() - 1);
        }

        // 打印传递的参数
        Log.e("data", buffer.toString());

        try {
            url = new URL(PATH);
//          System.out.println("url:" + url.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            Log.e("TEST", urlConnection + "");
            if (urlConnection == null) {
                System.out.println("urlConnection: " + urlConnection);
                return null;
            }
            urlConnection.setConnectTimeout(3000);
            urlConnection.setRequestMethod("POST"); // 设置请求方式
            urlConnection.setDoInput(true); // 表示从服务器获取数据
            urlConnection.setDoOutput(true); // 表示向服务器发送数据
            byte[] data = buffer.toString().getBytes();

//              System.out.println("buffer:" + data.toString());

            // 设置请求体的是文本类型
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));

            // 获得输出流
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(data);
            outputStream.close();

            // 获得服务器的响应结果和状态码
            int responseCode = urlConnection.getResponseCode();
            Log.e("RESCODE", responseCode + "");
            // 服务器响应成功
            if (responseCode == 200) {
                // 获取查询结果
                InputStream in = urlConnection.getInputStream();

                // 结果为JSON格式，对JSON进行解析
                datas = parseJSON(in);
            }
            return datas;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("my error", "这里肯定发生了错误");
        }
        return null;
    }

    private static RegisterBean parseJSON(InputStream in) throws Exception {

        String str = read(in);

        Gson gson = new Gson();
        datas = gson.fromJson(str, new TypeToken<RegisterBean>() {
        }.getType());
        return datas;
    }

    private static String read(InputStream in) throws IOException {
        byte[] data;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            bout.write(buf, 0, len);
        }
        data = bout.toByteArray();
        return new String(data, "utf-8");
    }
}
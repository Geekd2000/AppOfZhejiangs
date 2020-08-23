package com.example.appofzhejiang.coolweather.util;


import java.util.HashMap;

public class ResponseToReal {

    public String toReal(String responseText, HashMap<String, String> nowMap, HashMap<String, String[]> forecastMap) {
        responseText = toReal1(responseText, nowMap);
        return toReal2(responseText, forecastMap);
    }

    private String toReal1(String responseText, HashMap<String, String> nowMap) {
        if (nowMap.size() != 0) {
            String updateTimeStr = nowMap.get("updateTimeStr");
            String temp = nowMap.get("temp");
            String textStr = nowMap.get("textStr");
            String humidityStr = nowMap.get("humidityStr");
            String winDirScale = nowMap.get("winDirScale");
            int firstIndex1 = responseText.indexOf("\"loc\"") + 7;
            int lastIndex1 = firstIndex1 + responseText.substring(firstIndex1).indexOf("\"");
            responseText = responseText.substring(0, firstIndex1) + updateTimeStr + responseText.substring(lastIndex1);

            int firstIndex2 = responseText.indexOf("\"tmp\"") + 7;
            int lastIndex2 = firstIndex2 + responseText.substring(firstIndex2).indexOf("\"");
            responseText = responseText.substring(0, firstIndex2) + temp + responseText.substring(lastIndex2);

            int firstIndex3 = responseText.indexOf("\"txt\"") + 7;
            int lastIndex3 = firstIndex3 + responseText.substring(firstIndex3).indexOf("\"");
            responseText = responseText.substring(0, firstIndex3) + textStr + responseText.substring(lastIndex3);

            int aqiFirstIndex1 = responseText.indexOf("aqi") + 6;
            int aqiFirstIndex2 = aqiFirstIndex1 + responseText.substring(aqiFirstIndex1).indexOf("aqi") + 6;
            int aqiLastIndex2 = aqiFirstIndex2 + responseText.substring(aqiFirstIndex2).indexOf("\"");
            responseText = responseText.substring(0, aqiFirstIndex2) + humidityStr + responseText.substring(aqiLastIndex2);

            int pm25FirstIndex = responseText.indexOf("pm25") + 7;
            int pm25LastIndex = pm25FirstIndex + responseText.substring(pm25FirstIndex).indexOf("\"");
            responseText = responseText.substring(0, pm25FirstIndex) + winDirScale + responseText.substring(pm25LastIndex);

        }
        return responseText;
    }

    private String toReal2(String responseText, HashMap<String, String[]> forecastMap) {
        if (forecastMap.size() != 0) {
            String[] txt = forecastMap.get("txt");
            String[] max = forecastMap.get("max");
            String[] min = forecastMap.get("min");

            // 天气状况
            int txtFirstIndex1 = responseText.indexOf("txt_d") + 8;
            int txtLastIndex1 = txtFirstIndex1 + responseText.substring(txtFirstIndex1).indexOf("\"");
            responseText = responseText.substring(0, txtFirstIndex1) + txt[0] + responseText.substring(txtLastIndex1);

            int txtFirstIndex2 = txtFirstIndex1 + responseText.substring(txtFirstIndex1).indexOf("txt_d") + 8;
            int txtLastIndex2 = txtFirstIndex2 + responseText.substring(txtFirstIndex2).indexOf("\"");
            responseText = responseText.substring(0, txtFirstIndex2) + txt[1] + responseText.substring(txtLastIndex2);

            int txtFirstIndex3 = txtFirstIndex2 + responseText.substring(txtFirstIndex2).indexOf("txt_d") + 8;
            int txtLastIndex3 = txtFirstIndex3 + responseText.substring(txtFirstIndex3).indexOf("\"");
            responseText = responseText.substring(0, txtFirstIndex3) + txt[2] + responseText.substring(txtLastIndex3);

            // 最高温度
            int maxFirstIndex1 = responseText.indexOf("max") + 6;
            int maxLastIndex1 = maxFirstIndex1 + responseText.substring(maxFirstIndex1).indexOf("\"");
            responseText = responseText.substring(0, maxFirstIndex1) + max[0] + responseText.substring(maxLastIndex1);

            int maxFirstIndex2 = maxFirstIndex1 + responseText.substring(maxFirstIndex1).indexOf("max") + 6;
            int maxLastIndex2 = maxFirstIndex2 + responseText.substring(maxFirstIndex2).indexOf("\"");
            responseText = responseText.substring(0, maxFirstIndex2) + max[1] + responseText.substring(maxLastIndex2);

            int maxFirstIndex3 = maxFirstIndex2 + responseText.substring(maxFirstIndex2).indexOf("max") + 6;
            int maxLastIndex3 = maxFirstIndex3 + responseText.substring(maxFirstIndex3).indexOf("\"");
            responseText = responseText.substring(0, maxFirstIndex3) + max[2] + responseText.substring(maxLastIndex3);

            // 最低温度
            int minFirstIndex1 = responseText.indexOf("\"min\"") + 7;
            int minLastIndex1 = minFirstIndex1 + responseText.substring(minFirstIndex1).indexOf("\"");
            responseText = responseText.substring(0, minFirstIndex1) + min[0] + responseText.substring(minLastIndex1);

            int minFirstIndex2 = minFirstIndex1 + responseText.substring(minFirstIndex1).indexOf("\"min\"") + 7;
            int minLastIndex2 = minFirstIndex2 + responseText.substring(minFirstIndex2).indexOf("\"");
            responseText = responseText.substring(0, minFirstIndex2) + min[1] + responseText.substring(minLastIndex2);

            int minFirstIndex3 = minFirstIndex2 + responseText.substring(minFirstIndex2).indexOf("\"min\"") + 7;
            int minLastIndex3 = minFirstIndex3 + responseText.substring(minFirstIndex3).indexOf("\"");
            responseText = responseText.substring(0, minFirstIndex3) + min[2] + responseText.substring(minLastIndex3);
        }
        return responseText;
    }


}

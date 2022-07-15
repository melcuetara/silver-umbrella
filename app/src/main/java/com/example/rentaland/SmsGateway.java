package com.example.rentaland;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Response;

public class SmsGateway {


    private final String API_KEY = "f48388598105a1516e489c527b15a94c46252cf3";
    private String message = "Rentaland%3A+someone+sent+you+a+booking+request+just+now%21+%0D%0AAccept+booking+request+to+start+chatting.";

    public void sendSms(String sendTo, String message) {
        try {
            URL url = new URL("https://sms.teamssprogram.com/api/send?key=" + API_KEY + "&phone=" + sendTo
                    + "&message=" + message);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");


            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String sendSmsFarmer(String sendTo) {
        String apiCall = "";
        try {
            URL url = new URL("https://sms.teamssprogram.com/api/send?key=" + API_KEY + "&phone=" + sendTo
                    + "&message=" + message);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            apiCall = url.toString();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

//            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//            String output;
//            while ((output = br.readLine()) != null) {
//                ObjectMapper mapper = new ObjectMapper();
//                newsResponse = mapper.readValue(output, NewsResponse.class);
//            }

            conn.disconnect();
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }


}

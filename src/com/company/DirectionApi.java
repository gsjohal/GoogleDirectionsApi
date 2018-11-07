package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DirectionApi {
    public static JsonObject getRoutes(String origin, String dest, String mode, String avoid){
        String apikey = "AIzaSyDe7LKFUmtNszOYq98Io8dlSD9JV7taCWc";
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try{

            String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin+
                    "&destination="+dest+"&avoid="+avoid+"&mode="+mode+"&key="+apikey;

            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("GET");

            // Time to respond bac;
            urlConnection.setReadTimeout(15*1000);
            urlConnection.connect();

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));



            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            Gson gson = new Gson();
            JsonElement element = gson.fromJson(stringBuilder.toString(), JsonElement.class);
            JsonObject jsonObject = element.getAsJsonObject();

            return jsonObject;

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();

                }
            }
        }
        return  null;
    }
}

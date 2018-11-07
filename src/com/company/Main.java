package com.company;

import java.util.Scanner;

import com.google.gson.*;

import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter origin: ");
        String origin = scanner.nextLine();

        System.out.print("\nEnter destination: ");
        String dest = scanner.nextLine();

        System.out.print("\nEnter mode: ");
        String mode = scanner.nextLine();

        System.out.print("\nEnter avoid: ");
        String avoid = scanner.nextLine();

        JsonObject jsonData = DirectionApi.getRoutes(origin, dest, mode, avoid);

        try{
            JsonArray routes = jsonData.getAsJsonArray("routes");

            JsonObject routeObject = routes.get(0).getAsJsonObject();

            JsonArray legs = routeObject.getAsJsonArray("legs");

            JsonObject stepsObject = legs.get(0).getAsJsonObject();

            JsonArray stepsArray = stepsObject.getAsJsonArray("steps");

            for(int i = 0; i < stepsArray.size(); i++ ){
                JsonObject stepsElement = stepsArray.get(i).getAsJsonObject();
                String distance = removeQuotes(stepsElement.get("distance").getAsJsonObject().get("text").toString());
                String direction = removeQuotes(stepsElement.get("html_instructions").toString());
                direction = direction.replaceAll("\\<[^>]*>","");
                String duration = removeQuotes(stepsElement.get("duration").getAsJsonObject().get("text").toString());
                System.out.println("Step "+(i+1)+":");
                System.out.println("In "+distance+", "+direction+". This should take you "+duration+".\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public static String removeQuotes(String str){
        return str.replaceAll("^\"+ \"+$","");
    }
}

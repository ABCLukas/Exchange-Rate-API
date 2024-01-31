/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.echangetracker;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.JOptionPane;
/**
 *
 * @author schne
 */
public class EchangeTracker {

    public static void main(String[] args) {
        //Create standart classes
        String api_key = "Your API KEY";
        String baseCurrency = JOptionPane.showInputDialog("Base Curreny");
        String targetCurrency = JOptionPane.showInputDialog("Target Currency");
        
        //Build the URL
        String apiUrl = "https://open.er-api.com/v6/latest/" + baseCurrency + "?apikey=" + api_key;
        //Create a Http Client
        HttpClient client = HttpClient.newHttpClient();
        //Create Request
        HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(apiUrl))
               .GET()
               .build();
        try {
            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if successful (code 200)
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                //Using gson
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                double exchangeRate = jsonObject.getAsJsonObject("rates").get(targetCurrency).getAsDouble();

                System.out.println("Exchange rate from " + baseCurrency + " to " + targetCurrency + ": " + exchangeRate);
            } else {
                System.out.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

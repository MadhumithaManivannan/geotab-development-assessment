package com.jokecompany;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class JsonFeed {
    private String url;

    JsonFeed(String endpoint) {
        url = endpoint;
    }

    public String getRandomJokes(String fullName, String gender, String category, int random) throws URISyntaxException, IOException, InterruptedException {
        String cn= "Chuck Norris";
    	HttpClient client = HttpClient.newHttpClient();
        url += "?random="+random;
        url +="&category=";
        url += category;
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String joke = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        if (fullName!=null && !fullName.isEmpty())
        {
        	String str="\"value\":";
            int index = joke.indexOf(str);
            String secondPart = joke.substring(index-3+cn.length(),joke.length()-2);
            
            if(gender.equals("female")) {
            	//Changes all male pronouns to female pronouns
            	secondPart =secondPart.replace(" he ", " she ");
            	secondPart =secondPart.replace(" He ", " She ");
            	secondPart =secondPart.replace(" Him ", " Her ");
            	secondPart =secondPart.replace(" him ", " her ");
            	secondPart =secondPart.replace(" His ", " Her ");
            	secondPart =secondPart.replace(" his ", " her ");
            	secondPart =secondPart.replace(" Himself ", " Herself ");
            	secondPart =secondPart.replace(" himself ", " herself ");
            }
            if(!cn.contentEquals(fullName)){
            	//Replaces the default name to the name obtained from the API
            	secondPart =secondPart.replace(cn, fullName);
            	secondPart =secondPart.replace("Chuck", fullName.substring(0, fullName.indexOf(' ')));
            	secondPart =secondPart.replace("Norris", fullName.substring(fullName.indexOf(' '),fullName.length()));
            }
            joke = secondPart;
        }
        Gson jsonobject = new GsonBuilder().disableHtmlEscaping().create();
        return jsonobject.toJson(joke);
    }

    public PersonDTO getnames() throws URISyntaxException, IOException, InterruptedException {
    	//Gets random name from the API
        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String person = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        Gson gson = new Gson();
        return gson.fromJson(person, PersonDTO.class);
    }

    public List<String> getCategories() throws IOException, InterruptedException, URISyntaxException {
    	//Gets all the categories from the API
        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String responsebody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return new Gson().fromJson(responsebody, ArrayList.class);
    }
}

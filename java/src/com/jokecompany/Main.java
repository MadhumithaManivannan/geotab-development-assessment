package com.jokecompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Main {

    static List<String> results = new ArrayList<>();
    static char key;
    static HashMap<String,List<Character>> validInputs= new HashMap<>();
    static String fullName;
    static String gender;
    static ConsolePrinter printer = new ConsolePrinter();


    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        
    	//Setting all options
    	validInputs.put("?", new ArrayList<>(Arrays.asList('?')));
    	validInputs.put("cORr", new ArrayList<>(Arrays.asList('C','R','c','r')));
    	validInputs.put("yORn", new ArrayList<>(Arrays.asList('Y','N','y','n')));
    	validInputs.put("numbers", 
    			new ArrayList<>(Arrays.asList('1','2','3','4','5','6','7','8','9')));
    	
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        printer.value("Press ? to get instructions.");
        String input = reader.readLine();
        input=validInput(input,validInputs.get("?"));
        	
        if(input.trim().charAt(0)=='?'){
            printer.value("Press c to get categories or Press r to get random jokes");
            key=validInput(reader.readLine(), validInputs.get("cORr")).charAt(0);
            //Prints all categories
            if (key == 'c'|| key=='C')
            {
                getCategories();
                printResults();
            }
            else if (key == 'r' || key=='R')
            {
                printer.value("Want to use a random name? y/n");
                key=validInput(reader.readLine(), validInputs.get("yORn")).charAt(0);
                if (key == 'y' || key=='Y')
                    getNames();
                else if(key=='n' || key== 'N'){
                	fullName="Chuck Norris";
                	gender="male";
                }
                
                printer.value("Want to specify a category? y/n");
                key=validInput(reader.readLine(), validInputs.get("yORn")).charAt(0);
                String category;
                if (key == 'y' || key=='Y')
                {
                	
                    printer.value("Enter a category from catogories:");
                    getCategories();
                    printer.value(Arrays.toString(results.toArray()));
                    category=reader.readLine().toLowerCase();
                    while(category==null || category.isEmpty() || !results.contains(category.trim().toLowerCase())){
                    	printer.value("Please enter valid input");
                    	category = reader.readLine().toLowerCase();
                    }  
                }
                else
                {
                	//Picks a random category
                	getCategories();
                	Random r = new Random();
                    int randomitem = r.nextInt(results.size());
                    category = results.get(randomitem);
                }
                
                printer.value("How many jokes do you want? (1-9)");
                String number= validInput(reader.readLine(), validInputs.get("numbers"));
                int n = Integer.parseInt(number);
                //Prints random jokes from the chosen category
                getRandomJokes(category, n);
                printResults();
            }
        }
    }

    private static void printResults()
    {
    	for(String result: results) {
    		printer.value(result);
    	}
    }

    private static void getRandomJokes(String category, int number) throws InterruptedException, IOException, URISyntaxException {
        results.clear();
    	for(int i=0; i<number; i++) {
    		//Gets a random joke
            String str=new JsonFeed("https://api.chucknorris.io/jokes/random")
            		.getRandomJokes(fullName, gender, category, i);
            results.add(str.replaceAll("\\\\", ""));
        }
    }

    private static void getCategories() throws InterruptedException, IOException, URISyntaxException {
    	//Gets all the category
        results = new JsonFeed("https://api.chucknorris.io/jokes/categories").getCategories();
    }

    private static void getNames() throws InterruptedException, IOException, URISyntaxException {
    	//Gets a random name
        PersonDTO dto = new JsonFeed("https://www.names.privserv.com/api/").getnames();
        fullName=dto.getFirstname()+" "+dto.getLastname();
        gender= dto.getGender()!=null|| !dto.getGender().isEmpty()?dto.getGender().toLowerCase():"male";
    }
    
    private static String validInput(String input, List<Character> chars) throws IOException {
    	//Checks if the input is valid
    	BufferedReader reader = 
    			  new BufferedReader(new InputStreamReader(System.in));
    	while(input==null || input.isEmpty() || input.trim().length()>1 
    			|| !chars.contains(input.trim().charAt(0))){
        	printer.value("Please enter valid input");
        	input = reader.readLine();
        }
    	return input;
    }
}

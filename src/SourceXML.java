
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bartosz
 */
public class SourceXML {
    
    private LocalDate localDate;
    private String sourceLink;
    
     //constructor
    public SourceXML(LocalDate localDate) throws IOException{
        setSourceLink(localDate, getTabelNumber(localDate));
    }
    
     //getters
    public LocalDate getLocalDate(){
        return localDate;
    }
    public String getSourceLink(){
        return sourceLink;
    }
    public String getTabelNumber(LocalDate localDate) throws FileNotFoundException, IOException{ 
        

// getting tabel number forom tabels list
//ex http://www.nbp.pl/kursy/xml/dir2002.txt
        int yyyy = localDate.getYear();
        String tabelsListSource;
       
        if(yyyy == LocalDate.now().getYear()){
            tabelsListSource = "https://www.nbp.pl/kursy/xml/dir.txt";
        } else {
            tabelsListSource = "https://www.nbp.pl/kursy/xml/dir" + yyyy + ".txt";
        }
        
        //System.out.println(tabelsListSource);
        URL url = new URL(tabelsListSource);
        URLConnection con = url.openConnection();
        
        ArrayList<String> tabelsList = new ArrayList<>();

        InputStream is = con.getInputStream(); 
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                tabelsList.add(line);
            }
        } finally {
            br.close();
        } 
        
//matching date to pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formatedDate = localDate.format(formatter);
        Pattern pattern = Pattern.compile("﻿c\\d{3}z"+formatedDate);
        //System.out.println(pattern);
        String tabelNumber = "";
        for(int i = 0; i < tabelsList.size(); i++){
            if(tabelsList.get(i).contains("c")){
                //System.out.println(tabelsList.get(i));
                Matcher matcher = pattern.matcher(tabelsList.get(i));
                if(matcher.find()){
                    System.out.println(tabelsList.get(i));
                    tabelNumber = tabelsList.get(i);
                } else if(matcher.matches()){
                    System.out.println(tabelsList.get(i));
                    
            }
            }
        }
        
        

    
        
        System.out.println(tabelNumber);
        return tabelNumber;
    }    

    //setters
    public void setSourceLink(LocalDate localDate, String tabelNumber){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formatedString = localDate.format(formatter);
        this.sourceLink = 
                "http://www.nbp.pl/kursy/xml/" + tabelNumber + ".xml";
        //ex http://www.nbp.pl/kursy/xml/c019z130128.xml
        //System.out.println(sourceLink);
    }
}

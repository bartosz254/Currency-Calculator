/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bartosz
 */


import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;


public class MainClass {
    
   
    /**
     * @param args the command line arguments 
     */

    
    public static void main(String[] args) throws IOException, ParserConfigurationException {

        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj walute");
        //String currency = scanner.nextLine();
        System.out.println("Podaj date początkową RRRR-MM-DDUS");
        //String startStringDate = scanner.nextLine();
        //LocalDate startDate = LocalDate.parse(startStringDate);
        LocalDate startDate = LocalDate.parse("2015-01-02");
        //System.out.println(startDate);
        System.out.println("Podaj date końcową RRRR-MM-DDUS");
        //String endStringDate = scanner.nextLine();
        //LocalDate endDate = LocalDate.parse(endStringDate);
        LocalDate endDate = LocalDate.parse("2015-01-31");
        
        SourceXML link = new SourceXML(startDate);
        System.out.println(link.getSourceLink());
        
        //Currency dolar = new Currency(CurrencyType.EUR, startDate);
        //Currency funty = new Currency(CurrencyType.GBP,startDate,endDate);
        //funty.setValues(startDate);
        //System.out.println(currency.getSredniKursKupna());
        //System.out.println(currency.getOdchylenieKursowSprzedazy());
        


    
    

        //               ===KOSZ===
        //  ZAKOMENTOWANE NIEPOTRZEBNE FRAGMENTY KODU

    }            
}

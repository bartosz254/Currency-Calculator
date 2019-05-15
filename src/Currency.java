import java.io.IOException;
import static java.lang.Math.sqrt;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bartosz
 */
public class Currency  {
    private float buyingRate;
    private float sellingRate;
    private CurrencyType currencyType;
    private LocalDate startDate;
    private LocalDate endDate;
    

    
    public Currency(CurrencyType currencyType, LocalDate startDate, LocalDate endDate) throws IOException, ParserConfigurationException{
        this.currencyType = this.currencyType;
        setStartDate(startDate);
        setEndDate(endDate);
        calcAvBuyingRate(currencyType,startDate,endDate);
        //calcDevSellingRate(currencyType,startDate,endDate);
    }
    
    public void setStartDate(LocalDate date){
        this.startDate = date;
    }
    public void setEndDate(LocalDate date){
        this.endDate = date;
    }
    
    public LocalDate getStertDate(){
        return startDate;
    }
    public LocalDate getEndDate(){
        return endDate;
    } 
    public CurrencyType getCurrencyType(){
        return currencyType;
    }
    public float getValue(){
        return buyingRate;
    }
    
    public float calcAvBuyingRate(CurrencyType currencyType, LocalDate start, LocalDate end) throws IOException, ParserConfigurationException{
        ArrayList<Float> values = new ArrayList<>();
        
        while(!(start.equals(end))){
            System.out.println(start);
            start = start.plusDays(1);
        }
        
        System.out.println(values.toString());
        float avBuyingRate = 0;
        for (int i = 0; i < values.size();i++) {
            avBuyingRate += values.get(i); 
        }
        System.out.println(avBuyingRate/values.size());
        return(avBuyingRate/values.size());
    }
    
//    public float calcDevSellingRate(CurrencyType currencyType, LocalDate start, LocalDate end)throws IOException, ParserConfigurationException{
//        float devSellingRate = 0;
//        ArrayList<Float> values = new ArrayList<>();
//        while(!(start.equals(end.plusDays(1)))){
//            System.out.println(sellingRate);
//                setValues(currencyType, start);
//                values.add(this.sellingRate);
//                start = start.plusDays(1);
//            }
//
//        System.out.println(values.toString());
//        float avSellingRate = 0;
//        for (int i = 0; i < values.size();i++) {
//            avSellingRate += values.get(i); 
//        }
//        float variance = 0;
//        for (int i = 0; i < values.size();i++){
//            variance += (values.get(i) - avSellingRate)*(values.get(i) - avSellingRate);
//        }
//        variance = variance/values.size();
//        devSellingRate = (float) sqrt(variance*variance);
//        System.out.println(devSellingRate);
//        return devSellingRate;
//    }
    
    // Function to setting Values of buyingRate and sellingRate
    // getting link by date 
    public ArrayList setValues( LocalDate date) throws IOException, ParserConfigurationException{
        
       SourceXML source = new SourceXML(date);
       String xml = source.getSourceLink();
       ArrayList<String> results = new ArrayList<>();
       
        //SAX Parser       
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            
            DefaultHandler handler = new DefaultHandler(){
                boolean bCurrencyType = false;
                boolean bBuyingRate = false;
                boolean bSellingRate = false;
                
                public void startElement(String uri, String localName, String qName,
                        Attributes attributes) throws SAXException{
                    
                    
                    if(qName.equalsIgnoreCase("kod_waluty")){
                        bCurrencyType = true;
                    } else if(qName.equalsIgnoreCase("kurs_kupna")){
                        bBuyingRate = true;
                    } else if(qName.equalsIgnoreCase("kurs_sprzedazy")){
                        bSellingRate = true;
                    }  
                }
               
                public void endElement(String uri, String localName,
                        String qName) throws SAXException{
                    if(qName.equalsIgnoreCase("/pozycja")){
                    }
                }
                
                public void characters( char ch[], int start, int length) throws SAXException{
                   if(bCurrencyType){
                        //System.out.println("Waluta: " + new String(ch, start, length));
                        results.add(new String(ch, start, length));
                        bCurrencyType = false;
                   } else if(bBuyingRate){
                        //System.out.println("Kurs kupna: " + new String (ch, start, length));
                        results.add(new String(ch, start, length));
                        bBuyingRate = false;
                    } else if(bSellingRate){
                        //System.out.println("Kurs sprzedazy: " + new String (ch, start, length));
                        results.add(new String(ch, start, length));
                        bSellingRate = false;
                        }
                   }
            
            };
            parser.parse(xml, handler);
            } catch (Exception e){
                e.printStackTrace();
            }

        for(int i = 0; i< results.size();i++){
            if(results.get(i).equals(this.currencyType.toString())){
                System.out.println(results.get(i));
                String replace = results.get(i+1).replace(',', '.');
                this.buyingRate = Float.valueOf(replace);
                String replace2 = results.get(i+2).replace(',', '.');
                this.sellingRate = Float.valueOf(replace2);
                System.out.println(buyingRate +" & "+ sellingRate);
                
            }
        }
        System.out.println(this.buyingRate + " " +this.sellingRate);
        return null;
    }
}

package pl.parser.nbp;


import static java.lang.Math.sqrt;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;



/**
 *
 * @author Bartosz Przybylowski
 */

public class Currency  {
    private float buyingRate;
    private float sellingRate;
    private CurrencyType currencyType;
    private LocalDate startDate;
    private LocalDate endDate;
    

    //constructor
    public Currency(CurrencyType currencyType, LocalDate startDate, LocalDate endDate){
        this.currencyType = currencyType;
        setStartDate(startDate);
        setEndDate(endDate);
        System.out.println(currencyType);
        calcAvBuyingRate(currencyType,startDate,endDate);
        calcDevSellingRate(currencyType,startDate,endDate);
    }
    //setters
    public void setStartDate(LocalDate date){
        this.startDate = date;
    }
    public void setEndDate(LocalDate date){
        this.endDate = date;
    }

    //getters
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


    //calculating average buying rate
    public float calcAvBuyingRate(CurrencyType currencyType, LocalDate start, LocalDate end){
        float avBuyingRate = 0;

        ArrayList<Float> values = new ArrayList<>();

        while(!(start.equals(end.plusDays(1)))){
           //System.out.println(buyingRate);
           setValues(start);
           values.add(this.buyingRate);
           start = start.plusDays(1);
        }

        float sum = 0;
        for (Float v: values) {
            sum += v;
        }
        avBuyingRate = sum/values.size();

        System.out.format("%.4f%n", avBuyingRate);
        return(avBuyingRate);
    }
    //calculating deviation of selling rate
    public float calcDevSellingRate(CurrencyType currencyType, LocalDate start, LocalDate end){
        float devSellingRate = 0;
        ArrayList<Float> values = new ArrayList<>();

        while(!(start.equals(end.plusDays(1)))){
            //System.out.println(sellingRate);
                setValues(start);
                values.add(this.sellingRate);
                start = start.plusDays(1);
            }

        //System.out.println(values.toString());
        float sum = 0;
        for (Float v: values) {
            sum += v;
        }
        float avSellingRate = sum/values.size();
        //System.out.println(avSellingRate);

        float variance = 0;
        for (Float v: values){
            variance += (v - avSellingRate)*(v - avSellingRate);
        }
        variance = variance/values.size();
        devSellingRate = (float) sqrt(variance);
        System.out.format("%.4f%n", devSellingRate);
        return devSellingRate;
    }
    
    // Method to setting values of buyingRate and sellingRate
    // getting link by date 
    public ArrayList setValues( LocalDate date) {
        
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
                        Attributes attributes) {
                    
                    
                    if(qName.equalsIgnoreCase("kod_waluty")){
                        bCurrencyType = true;
                    } else if(qName.equalsIgnoreCase("kurs_kupna")){
                        bBuyingRate = true;
                    } else if(qName.equalsIgnoreCase("kurs_sprzedazy")){
                        bSellingRate = true;
                    }  
                }
               
                public void endElement(String uri, String localName,
                        String qName) {
                    if(qName.equalsIgnoreCase("/pozycja")){
                    }
                }


                public void characters( char[] ch, int start, int length) {
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

        // searching currency type, buying and selling rate by first two result
        // after finding currencyType
        for(int i = 0; i< results.size();i++){
            if(results.get(i).equals(this.currencyType.toString())){
                //System.out.println(results.get(i));
                String replace = results.get(i+1).replace(',', '.');
                this.buyingRate = Float.valueOf(replace);
                String replace2 = results.get(i+2).replace(',', '.');
                this.sellingRate = Float.valueOf(replace2);
            }
        }
        //System.out.println(this.buyingRate + " & " +this.sellingRate);
        return null;
    }
}

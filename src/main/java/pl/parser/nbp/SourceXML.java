package pl.parser.nbp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author Bartosz Przybylowski
 */

public class SourceXML {

    private LocalDate localDate;
    private String sourceLink;

    //constructors
    public SourceXML(){

    }
    public SourceXML(LocalDate localDate) {
        setLocalDate(localDate);
        setSourceLink(getXmlSource(localDate));
    }

    //getters
    public LocalDate getLocalDate() {
        return localDate;
    }

    public String getSourceLink() {
        return sourceLink;
    }


    //setters
    public void setSourceLink(String tableName) {
        this.sourceLink = "http://www.nbp.pl/kursy/xml/" + tableName + ".xml";
        //ex http://www.nbp.pl/kursy/xml/c019z130128.xml
        //System.out.println(sourceLink);
    }

    public void setLocalDate(LocalDate date){
        this.localDate = date;
    }


    // getting tabels list
    //ex http://www.nbp.pl/kursy/xml/dir2002.txt
    private String getTableListSourceLink(LocalDate localDate) {

        int yyyy = localDate.getYear();
        String tablesListSource;

        if (yyyy == LocalDate.now().getYear()) {
            tablesListSource = "https://www.nbp.pl/kursy/xml/dir.txt";
        } else {
            tablesListSource = "https://www.nbp.pl/kursy/xml/dir" + yyyy + ".txt";
        }

        //System.out.println(tablesListSource);
        return tablesListSource;
    }


    //get connection for TabelsList
    private List<String> getTablesList(String tablesListSource) {

        List<String> tabelsList = new ArrayList<>();

        try {
            URL url = new URL(tablesListSource);
            URLConnection con = url.openConnection();

            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    tabelsList.add(line);
                }
                tabelsList.set(0, tabelsList.get(0).substring(1));
            } finally {
                br.close();
            }
            return tabelsList;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("NO CONNECTION");
        }
    }


    public String getTableNameByDate(LocalDate localDate, List<String> tabelsList) {

        //formatting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formatedDate = localDate.format(formatter);

        //formatting pattern
        String patternString = "c\\d{3}z" + formatedDate;
        Pattern pattern = Pattern.compile(patternString);

        //searching tabelsList for one tablename
        for (String t : tabelsList) {
            Matcher matcher = pattern.matcher(t.trim());
            if (matcher.matches()) {
                //System.out.println(t);
               return t;
            }
        }
        return null;
    }

    //method to service no table for date
    //takes previous nearest date
    public String getXmlSource(LocalDate localDate) {

        String sourceLink = getTableListSourceLink(localDate);
        List<String> tableList = getTablesList(sourceLink);
        String result = getTableNameByDate(localDate, tableList);

        while (result==null) {
            localDate = localDate.minusDays(1);
            result = getTableNameByDate(localDate, tableList);
        }
        //System.out.println(result);
        return result;
    }

}

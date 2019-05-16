package pl.parser.nbp;

import org.junit.Test;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;

public class SourceXMLTest {

    @Test
    public void getXmlSource() {
        SourceXML sourceXML = new SourceXML();
        String result = sourceXML.getXmlSource(LocalDate.of(2016, 1, 12));
        assertEquals(result, "c006z160112");
    }


    @Test
    public void patterns() {
        Pattern pattern = Pattern.compile("c001z160104");
        Matcher matcher = pattern.matcher("c001z160104");
        Boolean result = matcher.matches();
        assertEquals(result, Boolean.TRUE);

    }
}

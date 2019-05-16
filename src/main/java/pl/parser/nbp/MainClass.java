package pl.parser.nbp;

/**
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
    public static void main(String[] args){

        UserInterface userInterface = new UserInterface();
        userInterface.start(args);

    }
}

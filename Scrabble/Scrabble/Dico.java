package Scrabble;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
public class Dico {
    
    static ArrayList<String> motsDico = new ArrayList<String>();
    public void ajtMot(){
        try {
            File myObj = new File("Scrabble/dicoReference.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              motsDico.add(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    
 


    

}

package huffmanPack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadTextFile {
	
public static void readFromFile(String fileName) throws IOException {
	int nextLineCounter=0;
		try {
			

			File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		    	  if(nextLineCounter==0)
		        Main.text = Main.text + myReader.nextLine();
		    	  else
		    		  Main.text = Main.text +"\n" + myReader.nextLine();
		    	  nextLineCounter++;
		      }
		      myReader.close();
		     
		     
		     
		} catch (FileNotFoundException e) {
		      System.out.println("FILE NOT FOUND");
		      e.printStackTrace();
		    }
	}
}

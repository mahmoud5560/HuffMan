package huffmanPack;

import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;  // Import the IOException class to handle errors

public class CreateFile {
  public void MakeFile() {
    try {
      File myObj = new File("Encoded.txt");
      if (myObj.createNewFile()) {
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  public void writeToFile( Map<Character, String> map , int addZeros) {
	  try {
		  PrintWriter myWriter = new PrintWriter(new FileWriter("encoded.txt", true));
	      myWriter.append(addZeros+"->"+map);
	      myWriter.close();
	      System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
  }
  public void MakeDecodedFile() {
	    try {
	      File myObj = new File("Decoded.txt");
	      if (myObj.createNewFile()) {
	      } else {
	        System.out.println("File already exists.");
	      }
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }
	  public void writeToDecodedFile(String s) {
		  try {
		      FileWriter myWriter = new FileWriter("Decoded.txt");
		      myWriter.write(s);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	  }
}

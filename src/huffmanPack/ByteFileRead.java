package huffmanPack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ByteFileRead{
	
	
	
	
public static void readFromFile(String fileName) throws IOException {
	int nextLineCounter=0;
	int counter=0;
		
	try {
			
			Main.additionalZero = new byte[1];
			int k=0;
			Main.array = Files.readAllBytes(Paths.get(fileName));
			for(int i = 0 ;i<Main.array.length;i++) {
				
				k++;
				if(Main.array[i+2]==45&&Main.array[i+3]==62&&Main.array[i+4]==123) {
					
					break;	
				}

				
			}
			Main.binaryByteArray=new byte[k];
			for(int i = 0 ;i<Main.array.length;i++) {
				
				
				if(Main.array[i+1]==45&&Main.array[i+2]==62&&Main.array[i+3]==123) {

					counter=i+4;
					Main.additionalZero[0]=Main.array[i];
					break;	
				}
				Main.binaryByteArray[i]=Main.array[i];
			}
			Main.mapArray = new byte[Main.array.length];
			for( int j = counter ;j<Main.array.length;j++) {
				
				
				if(Main.array[j]==125) {
			
					break;	
			
				}
				
				Main.mapArray[j]=Main.array[j];
				
			}
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

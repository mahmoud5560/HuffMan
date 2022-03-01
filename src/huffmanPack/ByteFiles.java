package huffmanPack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class ByteFiles {
	static String FILEPATH = "Encoded.txt"; 
    static File file = new File(FILEPATH); 
    
    static void writeByte(byte[] bytes,Map<Character, String> map , int addZeros) 
    { 
        try { 
  
            OutputStream os = new FileOutputStream(file); 
            os.write(bytes);
            CreateFile c = new CreateFile();
            c.writeToFile( map, addZeros);
            System.out.println("Successfully" + " byte inserted"); 
            os.close(); 
        } 
  
        catch (Exception e) { 
            System.out.println("Exception: " + e); 
        } 
    } 
  
}

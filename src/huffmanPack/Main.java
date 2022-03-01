package huffmanPack;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

class Main
{
	
	public static String text="";
	public static byte[] array;
	public static byte[] mapArray;
	public static byte[] additionalZero;
	public static byte[] binaryByteArray;
	static float compressionRatio;
	
	public static Map<Character, String> huffmanCode;
	public static HashMap<Character,String> myMap = new HashMap<Character, String>();
	
    public static void encode(Node root, String str, Map<Character, String> huffmanCode)
    {
        if (root == null) {
            return;
        }
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }
 
        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }

    public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }

    public static void buildHuffmanTreeToEncode(String text)
    {
        if (text == null || text.length() == 0) {
            return;
        }
 
        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        
        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));            
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() != 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }
        Node root = pq.peek();
        huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);
        
        System.out.println("Huffman Codes are: " + huffmanCode);
        //System.out.println("Original string is: " + text);
        StringBuilder sb = new StringBuilder();
        for (char c: text.toCharArray()) {
            sb.append(huffmanCode.get(c));
    /*        System.out.println("char=("+c+")   "+"ascii code(Byte) =("+ (int)c+") "
            		+ "   "+"huffman code=("+huffmanCode.get(c)+")    "
            		+"normal code=(0"+Integer.toBinaryString((int)c)+")");
       */ }
        
        for ( Character key : huffmanCode.keySet() ) {
    	 System.out.println("BYTE= "+(int)key + " NORMAL CODE= 0"+Integer.toBinaryString((int)key) + " HUFFMAN CODE= "+huffmanCode.get(key));
    	}
 
        String temp = "";
        int decimal;
        int additionalZeros=0;
        byte[] bytes ;
        if(sb.length()%8==0) {
        	bytes = new byte[(sb.length()/8)];
        }
        else {
        	bytes = new byte[(sb.length()/8)+1];
        }
        int k=0;
        ByteFiles bfile = new ByteFiles();
        CreateFile createFile = new CreateFile();
        for(int i =0 ; i< sb.length();i++)
        {
        	
        	temp=temp+sb.charAt(i);
        	if(temp.length()==8) {
        	decimal = Integer.parseInt(temp, 2);
        	bytes[k]=(byte)decimal;
        	k++;
        		temp="";
        	}
        	if(i==sb.length()-1) {
        		additionalZeros = 8-temp.length();
        		if(temp!="") {
        		decimal = Integer.parseInt(temp, 2);
        		bytes[k]=(byte)decimal;
        		k++;
        			temp="";
        		}
        	}
        }
        compressionRatio=text.length()*8/bytes.length;
        createFile.MakeFile();
        ByteFiles byteFile = new ByteFiles();
        byteFile.writeByte(bytes, huffmanCode , additionalZeros);
    }
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static void buildHuffmanTreeToDecode(String text) {
    	String binaryString="";
    	String mapString= new String(mapArray);
    	for (int d = 0 ; d<mapString.length() ; d++) {
    	if(mapString.charAt(d)=='=') {
    		mapString=mapString.substring(d-1, mapString.length()-1);
    		break;
    	}
    	
    	}
    	String textToSave="";
    	String temp1="";
    	String carry = new String(additionalZero);
    	int additionalZeros=Integer.parseInt(carry);
    	huffmanCode = new HashMap<Character, String>();
    	for(int d = 0 ; d<binaryByteArray.length-1;d++) {
    				temp1=temp1+String.format("%8s", Integer.toBinaryString(binaryByteArray[d] & 0xFF)).replace(' ', '0');
    			}
			String x="";
			x=String.format("%8s", Integer.toBinaryString(binaryByteArray[binaryByteArray.length-1] & 0xFF)).replace(' ', '0');
			temp1=temp1+x.substring(additionalZeros);
			System.out.println("toBinaryString ended");
    	binaryString=temp1;
    	System.out.println("HUFFMAN MAP="+mapString);
    	huffmanCode=createMap(mapString);
    	 /*for ( Character key : huffmanCode.keySet() ) {
        	 System.out.println("BYTE= "+(int)key + " NORMAL CODE= 0"+Integer.toBinaryString((int)key) + " HUFFMAN CODE= "+huffmanCode.get(key));
        	}*/
    	String temp="";
    	for(int count = 0 ; count<binaryString.length();count++)
    	{
    	temp=temp + Character.toString(binaryString.charAt(count));
    	for ( Character key : huffmanCode.keySet() ) {
    	    if(temp.equals(huffmanCode.get(key))) {
    	    	if(key=='\n')
    	    	{
    	    		textToSave=textToSave+'\r';
    	    	}
    	    	textToSave=textToSave+Character.toString(key);
    	    	temp="";
    	    	break;
    	    }
    	}
    	
    	}
		System.out.println("textTosave ended");
    	/*String tempString1="";
		String tempString2="";
    	for(int i = 0; i< textToSave.length();i++) {
    		if(textToSave.charAt(i)=='\n') {
    			tempString1 = textToSave.substring(0, i);
    			tempString2 = textToSave.substring(i);
    			textToSave = tempString1+"\r"+tempString2;
    			i++;
    			tempString1="";
    			tempString2="";
    		}
    	}*/
    	CreateFile createFile = new CreateFile();
    	createFile.MakeDecodedFile();
    	createFile.writeToDecodedFile(textToSave);
    }
    
private static Map<Character,String> createMap(String mapString) {
	String temp="";	
	Map<Character, String> myMap = new HashMap<Character,String>();
		for(int i = 0 ; i < mapString.length()-1;i++) {
			if(mapString.charAt(i+1)=='=') {
				int j = i+2;
				while(!Character.toString(mapString.charAt(j)).equals(",")&&j<mapString.length()-1) {
					
					temp=temp+Character.toString(mapString.charAt(j));
					j++;
				}
				if(j==mapString.length()-1) {
					temp=temp+Character.toString(mapString.charAt(j));
				}
				myMap.put(mapString.charAt(i), temp);
				temp="";
			}
		}
		return myMap;
		
	}
private static String encodedTexttoBinary(String encodedText,int additionalZeros) {
		
	int tempDecimal;
	char x;
	String binary="";
		for(int j = 0; j < encodedText.length() ; j++ )
		{
			//System.out.println(encodedText.charAt(j));
			tempDecimal = (int)encodedText.charAt(j);
			System.out.println(encodedText.charAt(j)+"->"+tempDecimal);
			if(j==encodedText.length()-1&&additionalZeros!=0) {
				String temp;
				temp = Integer.toBinaryString(tempDecimal);
				temp = temp.substring(additionalZeros);
				binary =binary+ temp;
				break;
			}
			binary =binary+ Integer.toBinaryString(tempDecimal);
		}
		return binary;
	}
    
    
    
    public static void main(String[] args) throws IOException
    {
    	Scanner sc= new Scanner(System.in);
    	System.out.println("PLEASE ENTER 1 TO COMPRESS THE FILE");
    	System.out.println("PLEASE ENTER 2 TO DECOMPRESS THE FILE");
    	int a= sc.nextInt();
    	 long start = System.currentTimeMillis();
    	if(a==1) {
    		ReadTextFile.readFromFile("test.txt");
        	buildHuffmanTreeToEncode(text);	
        	System.out.println("COMPRESSION RATIO = "+compressionRatio);
    	}
    	else if(a==2){
    		ByteFileRead.readFromFile("Encoded.txt");
        	buildHuffmanTreeToDecode(text);
    	}
    	else {
    		System.out.println("INVALID INPUT");
    	}
    	 long end = System.currentTimeMillis();
         System.out.println("EXECUTE TIME = "+(end - start)+"ms");
  	   }
}












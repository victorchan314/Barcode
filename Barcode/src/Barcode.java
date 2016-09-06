import java.io.*;
import java.util.Scanner;
import java.awt.Graphics;
import javax.swing.*;
//Victor Chan, Anirudh Makineni, Kashyap Gummaraju

class Canvas extends JComponent {
	public void paint(Graphics g){//method that fills the JFrame window
		try {
			Scanner ZipCode = new Scanner(new FileReader("ZipCode.txt"));//Scanner for the input file
			//use C:\\Users\\Desktop\\ZipCode.txt for the file if you save the file on the desktop
			int yPosLong = 10;//yPositions for the bar codes
			int yPosShort = 16;
			while (ZipCode.hasNextLine()){//loops through the lines in the file
				String s = ZipCode.nextLine();
				int xPos = 60;//left side of barcode
				if (isInteger(s)&&s.length()==5){//checks to make sure the zip code is valid
					g.drawString(s+":",10,yPosLong+10);
					String n = BarCodeDigits(s);//converts the barcode to a string of digits
					for (int i=0;i<n.length();i++){//draws out the actual barcode
						if (Character.toString(n.charAt(i)).equals("1")){
							g.fillRect(xPos,yPosLong,2,10);
							xPos+=3;
						}
						else if (Character.toString(n.charAt(i)).equals("0")){
							g.fillRect(xPos,yPosShort,2,4);
							xPos+=3;
						}
						else;
					}
					yPosLong+=15;//moves the pointer down for the barcode
					yPosShort+=15;
				}
				else System.out.println("Error: "+s+" is not a valid zip code.");//error for if the zip code is not valid
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String BarCodeDigits(String n){//converts the bar code to a string
		if (isInteger(n)){//checks to make sure the zip code is an integer
			int zipcode = Integer.parseInt(n);
			String barcode = "1";
			barcode = getBarValue((10-( (zipcode%10) + ((zipcode/10)%10) + ((zipcode/100)%10) + ((zipcode/1000)%10) + ((zipcode/10000)%10) )%10)%10) + barcode;			
			barcode = getBarValue(zipcode%10) + barcode;
			zipcode /= 10;
			barcode = getBarValue(zipcode%10) + barcode;
			zipcode /= 10;
			barcode = getBarValue(zipcode%10) + barcode;
			zipcode /= 10;
			barcode = getBarValue(zipcode%10) + barcode;
			zipcode /= 10;
			barcode = "1" + getBarValue(zipcode%10) + barcode;
			return barcode;
		}
		else return null;
	}
	
	public static String getBarValue(int n){//gets the bar value for each digit in the zip code
		String bardigit;
		switch (n){//the 10 different cases for the zip code digits
			case 1: bardigit = "00011";
					break;
			case 2: bardigit = "00101";
					break;
			case 3: bardigit = "00111";
					break;
			case 4: bardigit = "01000";
					break;
			case 5: bardigit = "01011";
					break;
			case 6: bardigit = "01100";
					break;
			case 7: bardigit = "10000";
					break;
			case 8: bardigit = "10011";
					break;
			case 9: bardigit = "10100";
					break;
			case 0: bardigit = "11000";
					break;
			default: bardigit = "Error";
					break;
		}
		return bardigit;
	}
	
	public static boolean isInteger(String n) {//checks to make sure the string/zip code is an integer
		try {
	    	Integer.parseInt(n);
	    }
	    catch(NumberFormatException error) {
	    	return false;
	    }
	    return true;
	}
}

public class Barcode {//creates the window that displays the bar code images
	public static void main(String[] n) {
		JFrame window = new JFrame();
		window.setSize(200,150);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(new Canvas());//adds the bar code canvas
		window.setVisible(true);
	}
}
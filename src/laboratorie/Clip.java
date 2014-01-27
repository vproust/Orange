package laboratorie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Clip {
	public static void main(String[] args) {
		BufferedImage bi = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		BufferedImage bi2 = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		Graphics2D ig22 = bi2.createGraphics(); 

		int fontSize =20; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
	    ig2.setPaint(Color.orange);
	    
		ig2.drawString("coucou", 2, 2);
		ig2.translate(20, 20);
		ig2.drawString("kikou", 50, 50);
		ig2.translate(20, 20);
		
		java.awt.Image image = Toolkit.getDefaultToolkit().createImage(bi.getSource());
		ig22.drawImage(image,0,0,100,100,null);
		ig22.drawImage(image,100,0,100,100,null);
		ig22.drawImage(image,0,100,100,100,null);
		ig22.drawImage(image,100,100,100,100,null);
		
		try {
			ImageIO.write(bi2, "PNG", new File("./output/clip.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

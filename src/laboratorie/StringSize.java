package laboratorie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StringSize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedImage bi = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 

		int fontSize =50; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
		System.out.println(ig2.getFontMetrics().stringWidth("coucou2"));
	    ig2.setPaint(Color.orange);
	    
		ig2.drawString("coucou", 30, 30);
		ig2.drawString("c", 100, 100);
		ig2.drawString("kikou2", 100-ig2.getFontMetrics().stringWidth("kikou2"), 100);
		
		try {
			ImageIO.write(bi, "PNG", new File("./output/clip.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

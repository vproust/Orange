package laboratorie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Clip {
	public static void main(String[] args) {
		BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 
		int fontSize =20; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
	    ig2.setPaint(Color.red);
	    
		ig2.drawString(message, (int)Math.floor(XPositionOnMosaic*mosaicWidth), (int)Math.floor(YPositionOnMosaic*mosaicHeight));

	}

}

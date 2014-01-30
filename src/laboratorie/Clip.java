package laboratorie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Clip {
	public static int ARR_SIZE = 10;
	
	 static void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                      new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }
	public static void main(String[] args) {
		BufferedImage bi = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);

		Graphics2D ig2 = bi.createGraphics(); 

		int fontSize =50; //Taille de police défini en dur, a modifier ensuite..

		Font font = new Font("TimesRoman", Font.BOLD, fontSize);
		ig2.setFont(font);
	    ig2.setPaint(Color.orange);
	    
		ig2.drawString("coucou", 100, 30);
		ig2.drawString("kikou", 30, 100);
		
		// draw Line2D.Double
		//ig2.draw(new Line2D.Double(2, 2, 50, 50));
		
		drawArrow(ig2,100,30,30,100);
		
		try {
			ImageIO.write(bi, "PNG", new File("./output/clip.PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}

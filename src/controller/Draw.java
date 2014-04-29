package controller;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

public class Draw {
	public static int ARR_SIZE = 5;
	
	public static void drawArrow(Graphics2D g, double x1, double y1, double x2, double y2) {

       double dx = x2 - x1, dy = y2 - y1;
       double angle = Math.atan2(dy, dx);
       int len = (int) Math.sqrt(dx*dx + dy*dy);
       AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
       at.concatenate(AffineTransform.getRotateInstance(angle));
       g.transform(at);

       // Draw horizontal arrow starting in (0, 0)
       g.drawLine(0, 0, len, 0);
       
       //pour dessiner la pointe de la fleche
       g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
       AffineTransform at2;
	try {
		at2 = at.createInverse();
		g.transform(at2);
	} catch (NoninvertibleTransformException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
   }

}

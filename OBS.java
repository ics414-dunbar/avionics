import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class OBS extends JPanel{
		private BufferedImage img;
		
		//variable used in image rotation
		private AffineTransformOp op = null;
		
		public OBS(String filename){
			try {
		        img = ImageIO.read(new File(filename));
		        rotateDial(GUI.degree);
		      }
		      catch (IOException e) {
		        System.out.println("ERROR: Unable to open specified imagefile:" + filename);
	      }
	}
	
	public void rotateDial(int degree){
		double rotationRequired = Math.toRadians (- degree + GUI.VARIANCE);
		double locationX = img.getWidth() / 2;
		double locationY = img.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		repaint();
	}
	
	public void paintComponent(Graphics g){
		if(op != null){
			g.drawImage(op.filter(img, null), 0, 0, null);
		}
		else{
			g.drawImage(img, 0, 0, null);
		}
	}	
}

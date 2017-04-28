import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VOR extends JPanel{
	private BufferedImage img;
	
	VOR(String filename){
		try {
	        img = ImageIO.read(new File(filename));
	      }
	      catch (IOException e) {
	        System.out.println("ERROR: Unable to open specified imagefile:" + filename);
	      }
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(img, 0, 0, null);
		Font font = new Font("Veranda", Font.BOLD, 20);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("FR", 260, 260);
		g.drawString("TO", 256, 160);
	}		
}
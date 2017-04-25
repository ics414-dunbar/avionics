import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JPanel{
	
	//buttons used to rotate the OBS left and right
    private JButton OBSRight = new JButton("OBS Right 5 Degrees");
    private JButton OBSLeft = new JButton("OBS Left 5 Degrees");
    
    //the image starts at a 300 degree rotation, so this variable
    //is used to offset degree calculations
    public final static int VARIANCE = 300;
    
    //start at a random rotation
    static Random rand = new Random();
	public static int degree = rand.nextInt(72) * 5;
	
	//variables for line coordinates
	private int lx1 = 0, ly1 = 0, lx2 = 0, ly2 = 0;
	
	//variable used in image rotation
	private AffineTransformOp op = null;
	
	//variables used for the TO and FR triangle indicator
	private int tx = 0, ty = 0;
	public boolean flipped = false;
		
		
	public GUI(int w, int h){
		
		Triangle triangle = new Triangle(tx, ty);

		Line line = new Line(lx1, ly1, lx2, ly2);

		VORImage image = new VORImage("VOR.png");
		
		OBSImage dial = new OBSImage("Dial.png");

			
		String windowName = "VOR";
		JFrame frame = new JFrame(windowName);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //couldn't get images to overlap without using JLayeredPane, so using that
	    image.setOpaque(false);
	    image.setBounds(0, 0, w, h);
	    dial.setOpaque(false);
	    dial.setBounds(0, 0, w, h);
	    line.setOpaque(false);
	    line.setBounds(0, 0, w, h);
	    triangle.setOpaque(false);
	    triangle.setBounds(0, 0, w, h);
	    JLayeredPane images = new JLayeredPane();
	    images.setPreferredSize(new Dimension(w, h));
	    images.add(image, new Integer(1));
	    images.add(dial, new Integer(2));
	    images.add(triangle, new Integer(3));
	    images.add(line, new Integer(4));
	    frame.add(images, BorderLayout.CENTER);
	    
	    //add buttons to allow OBS rotation
	    JPanel OBSButtons = new JPanel(new FlowLayout());
	    OBSButtons.add(OBSLeft);
	    OBSLeft.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){ 
	    		degree += 5;
	    		if(degree > 360){
	    			degree -= 360;
	    		}
	    		dial.rotateDial(degree);
	    	}
	    });
	    OBSButtons.add(OBSRight);
	    OBSRight.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){ 
	    		degree -= 5;
	    		if(degree <= 0){
	    			degree += 360;
	    		}
	    		dial.rotateDial(degree);
	    	}
	    });
	    frame.add(OBSButtons, BorderLayout.PAGE_END);
	    frame.setResizable(false);
	    frame.pack();
	    frame.setVisible(true);
	}
	
	
	public void setLine(int x1, int y1, int x2, int y2){
		lx1 = x1;
		ly1 = y1;
		lx2 = x2;
		ly2 = y2;
		repaint();
	}
	
	public void setTriangle(int x, int y, boolean flip){
		tx = x;
		ty = y;
		flipped = flip;
		repaint();
	}
	
	//the background image
	private class VORImage extends JPanel{
		private BufferedImage img;
		
		private VORImage(String filename){
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
	
	//the dial image
	//this image has to be a different class than the other one because of the
	//different paint method
	private class OBSImage extends JPanel{
		private BufferedImage img;
		
		private OBSImage(String filename){
			try {
		        img = ImageIO.read(new File(filename));
		        rotateDial(degree);
		      }
		      catch (IOException e) {
		        System.out.println("ERROR: Unable to open specified imagefile:" + filename);
		      }
		}
		
		public void rotateDial(int degree){
			double rotationRequired = Math.toRadians (- degree + VARIANCE);
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
	
	//the line that serves as the needle
	public class Line extends JPanel{
		
		public Line(int x1, int y1, int x2, int y2){
			lx1 = x1;
			ly1 = y1;
			lx2 = x2;
			ly2 = y2;
		}
		
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(5));
			g2.drawLine(lx1, ly1, lx2, ly2);
		}
		
	}
	
	//the triangle that indicates to or from
	public class Triangle extends JPanel{
		
		public Triangle(int xPos, int yPos){
			tx = xPos;
			ty = yPos;
		}
		
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.RED);
			int[] xList = {tx-10, tx, tx+10}; 
			int[] yList = {ty, ty-20, ty}; 
			if(flipped){
				yList[0] = ty-20;
				yList[1] = ty; 
				yList[2] = ty-20;
			}
	        g2.fillPolygon(new Polygon(xList, yList, 3)); 
		}
		
	}
}

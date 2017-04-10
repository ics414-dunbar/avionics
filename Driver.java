import java.util.Random;

public class Driver{
    
	public static void main(String[] args){
		
		//image is 400x400, so window size is the same
		final int WORLDH = 400;
		final int WORLDW = 400;
		GUI gui = new GUI(WORLDH, WORLDW);

		//get a random radial that the plane is approaching from
		Random rand = new Random();
		int n = rand.nextInt(360);
		
		double x1, y1, x2, y2;
		final int XCENTER = 200;
		final int YCENTER = 200;
		
		//radius of the inner circle, used for needle calculation
		final int RADIUS = 107;
		double radAng;
		
		while(true){		
		
			//set the needle to point directly up
			radAng = ( 270 * Math.PI ) / 180;
			x1 = XCENTER + RADIUS * Math.cos ( radAng );
			y1 = YCENTER + RADIUS * Math.sin ( radAng );
			
			//set the other end of the needle to be pointing towards the direction the plane
			//is coming from
			radAng = ( ((n + GUI.degree) % 360) * Math.PI ) / 180;
			x2 = XCENTER + RADIUS * Math.cos ( radAng );
			y2 = YCENTER + RADIUS * Math.sin ( radAng );
			
			//if the needle origin ends up in the 1st or 2nd quadrant it looks bad,
			//keep the needle origin in the 3rd and 4th quadrant
			if((n + GUI.degree) % 360 > 270 || (n + GUI.degree) % 360 < 0){
				radAng = ( 360 * Math.PI ) / 180;
				x2 = XCENTER + RADIUS * Math.cos ( radAng );
				y2 = YCENTER + RADIUS * Math.sin ( radAng );
			}
			else if((n + GUI.degree) % 360 > 180){
				radAng = ( 180 * Math.PI ) / 180;
				x2 = XCENTER + RADIUS * Math.cos ( radAng );
				y2 = YCENTER + RADIUS * Math.sin ( radAng );
			}
			//set the needle on the screen
			gui.setLine((int)x1, (int)y1, (int)x2, (int)y2);
		}
    }

}

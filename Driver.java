import java.util.Random;

public class Driver{
    
	public static void main(String[] args){
		
		//image is 400x400, so window size is the same
		final int WORLDH = 400;
		final int WORLDW = 400;
		GUI gui = new GUI(WORLDH, WORLDW);

		//get a random radial that serves as the station location
		Random rand = new Random();
		int n = rand.nextInt(72) * 5;

		//variables used for the needle
		double x1, y1, x2, y2;
		
		//world center
		final int XCENTER = 200;
		final int YCENTER = 200;
		
		//radius of the inner circle, used for needle calculation
		final int RADIUS = 107;
		double radAng1, radAng2;
		
		//variables used to draw the TO and FR arrow indicator on screen
		int tx1 = 270, ty1 = 190;
		int tx2 = 270, ty2 = 230;
		
		//variable to indicate whether headed to or from the station
		boolean to = true;
		
		while(true){
			
			//if the OBS is tuned within 90 degrees of the station, we are heading to it
			//otherwise, the OBS is tuned over 90 degrees of the station, we are heading from it
			if(Math.abs(n % 360 - GUI.degree % 360) > 90 
					&& Math.abs(n % 360 - GUI.degree % 360) < 270){
				to = false;
			}
			else{
				to = true;
			}
			if(to){
				//set the needle to point in the direction the plane needs to head to 
				//get to the station
				radAng1 = Math.toRadians(n - GUI.degree);
				y1 = XCENTER - RADIUS * Math.cos(radAng1);
				x1 = YCENTER - RADIUS * Math.sin(radAng1);
				
				//set the opposite end of the needle, moves more degrees to the side than
				//the front end because that's how the simulation had it
				radAng2 = Math.toRadians(n - GUI.degree + (180 + n - GUI.degree));
				y2 = XCENTER - RADIUS * Math.cos(radAng2);
				x2 = YCENTER - RADIUS * Math.sin(radAng2);
				
				/*The needle will point in the direction the plane needs to go in only up to a 
				 * maximum of 30 degrees either way. This mess of and's and or's is to ensure that
				 * once the plane is more than 30 degrees off course, the needle will stop moving*/
				
				//first, check if the direction of the station and the OBS setting are within 30 degrees of each other
				if(Math.abs((GUI.degree % 360) - (n % 360)) > 30 && Math.abs((GUI.degree % 360) - (n % 360)) < 330){
					
					//next check which case it is, if the station is to the right of the plane or the left
					
					//if n is not 360 and degree > n, then we know the station is to the left
					//if n < 30 and 360 - degree > 30, station is left
					//if n == 360 and degree < 90, station is left (degree < 30 is within our 30 degree range of the target)
					if((n != 360 && GUI.degree > n) || (n < 30 && 360 - GUI.degree > n) 
							|| (n == 360 && GUI.degree > 30 && GUI.degree <= 90)){
						radAng1 = Math.toRadians(300);
						x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
						y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
						radAng2 = Math.toRadians(150);
						x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
						y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
					}
					else{
						radAng1 = Math.toRadians(240);
						x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
						y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
						radAng2 = Math.toRadians(30);
						x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
						y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
					}
					
				}

				//set the needle and the TO indicator
				gui.setLine((int)x1, (int)y1, (int)x2, (int)y2);
				gui.setTriangle(tx1, ty1, false);
			}
			else{
				//if we are heading from the station, then all calculations are flipped by 180
				//degrees
				radAng1 = Math.toRadians(n + GUI.degree + (180 - n + GUI.degree));
				y1 = XCENTER + RADIUS * Math.cos(radAng1);
				x1 = YCENTER + RADIUS * Math.sin(radAng1);
				radAng2 = Math.toRadians((n + 180) % 360 + GUI.degree % 360);
				y2 = XCENTER + RADIUS * Math.cos(radAng2);
				x2 = YCENTER + RADIUS * Math.sin(radAng2);
				
				/**TODO this currently doesn't work properly, gonna fix it*/
				if((n == 360 && (GUI.degree > 210 || GUI.degree < 150))
						|| (n == 180 && GUI.degree != 360 
						&& ((GUI.degree > 30 && GUI.degree < 90) || (GUI.degree < 330 && GUI.degree > 270)))
							 || (n != 180 && n != 360 && (Math.abs(GUI.degree - ((n + 180) % 360)) > 30))
						){
					if(n != 180 && (n + 180) % 360 < GUI.degree
							|| n == 180 && (GUI.degree > 30 && GUI.degree < 270)){
						radAng1 = Math.toRadians(240);
						x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
						y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
						radAng2 = Math.toRadians(30);
						x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
						y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
					}
					else{
						radAng1 = Math.toRadians(300);
						x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
						y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
						radAng2 = Math.toRadians(150);
						x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
						y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
					}
				}
				
				gui.setLine((int)x1, (int)y1, (int)x2, (int)y2);
				//set the FROM indicator
				gui.setTriangle(tx2, ty2, true);
			}
		}
    }

}

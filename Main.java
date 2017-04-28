import java.util.Random;

public class Main{ 
	
	final static int WORLDH = 400;
	final static int WORLDW = 400;
	final static int XCENTER = 200;
	final static int YCENTER = 200;
	final static int RADIUS = 107;
	
	public static void main(String[] args){
		//image is 400x400, so window size is the same
		GUI gui = new GUI(WORLDH, WORLDW);
		//get a random radial that serves as the station location
		Random rand = new Random();
		int n = 30;//rand.nextInt(72) * 5;
		//variables used for the needle
		double x1, y1, x2, y2;
		double radAng1, radAng2;		
		//variables used to draw the TO and FR arrow indicator on screen
		int tx1 = 270, ty1 = 190;
		int tx2 = 270, ty2 = 230;
		//variable to indicate whether headed to or from the station
		boolean to = true;
		
		boolean overVOR = false;
		
		//print out the degree of the station
		System.out.println("Station is at " + n + " degrees.");

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
			//check that the station and plane are not 90 degrees from each other, if they
			//aren't, then VOR behaves normally
			if(Math.abs(GUI.degree - n) != 90 && Math.abs(GUI.degree - n) != 270 && overVOR == false){
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
					
					//The needle will point in the direction the plane needs to go in only up to 
					//a maximum of 30 degrees either way.
					//first, check if the direction of the station and the OBS setting are more than 30 degrees apart
					if(Math.abs(GUI.degree - n) > 30 && Math.abs(GUI.degree - n) < 330){
						//now check the x position, and stop the needle to the left or right based on
						//which direction it was pointing in
						if(x1 < XCENTER + RADIUS * Math.cos(240)){
							radAng1 = Math.toRadians(240); //(240, 30) needle pointed to the left
							x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
							y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
							radAng2 = Math.toRadians(30);
							x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
							y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
						}
						else{
							radAng1 = Math.toRadians(300); //(300, 150) needle pointed to the right
							x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
							y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
							radAng2 = Math.toRadians(150);
							x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
							y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
						}
					}

				//set the needle and the TO indicator
				gui.setLine((int)x1, (int)y1, (int)x2, (int)y2);
				gui.setTriangle(tx1, ty1, false);
				}// end to
				
				//in this case we are headed away from the station, so the FR indicator will be set,
				//and all calculations are flipped by 180 degrees
				else{						
					radAng1 = Math.toRadians(GUI.degree - n);
					y1 = XCENTER + RADIUS * Math.cos(radAng1);
					x1 = YCENTER + RADIUS * Math.sin(radAng1);
					radAng2 = Math.toRadians(n - GUI.degree + 180 + (180 + n - GUI.degree));
					y2 = XCENTER + RADIUS * Math.cos(-radAng2);
					x2 = YCENTER + RADIUS * Math.sin(-radAng2);				
					if(Math.abs(Math.abs(GUI.degree - n) - 180) >= 30 
							&& Math.abs(Math.abs(GUI.degree - n) - 180) <= 330){
						if(x1 < XCENTER + RADIUS * Math.cos(240)){
							radAng1 = Math.toRadians(240); //(240, 30) needle pointed to the left
							x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
							y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
							radAng2 = Math.toRadians(30);
							x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
							y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
						}
						else{
							radAng1 = Math.toRadians(300); //(300, 150) needle pointed to the right
							x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
							y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
							radAng2 = Math.toRadians(150);
							x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
							y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
						}
					}			
					gui.setLine((int)x1, (int)y1, (int)x2, (int)y2);
					gui.setTriangle(tx2, ty2, true);
				} // end fr
			} //end normal VOR behavior
			
			//If the plane is directly over the VOR, or 90 degrees from the station, the reading is bad
			else{
				if(Math.abs(GUI.degree - n) == 270){
					radAng1 = Math.toRadians(240); //(240, 30) needle pointed to the left
					x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
					y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
					radAng2 = Math.toRadians(30);
					x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
					y2 = YCENTER + RADIUS * Math.sin ( radAng2 );

				}
				else{
					radAng1 = Math.toRadians(300); //(300, 150) needle pointed to the right
					x1 = XCENTER + RADIUS * Math.cos ( radAng1 );
					y1 = YCENTER + RADIUS * Math.sin ( radAng1 );
					radAng2 = Math.toRadians(150);
					x2 = XCENTER + RADIUS * Math.cos ( radAng2 );
					y2 = YCENTER + RADIUS * Math.sin ( radAng2 );
				}
				gui.setLine((int)x1, (int)y1, (int)x2, (int)y2);
				gui.setTriangle(-20, -20, false);
			}//end erratic VOR behavior
		}//end while true
    }//end main method
}//end class
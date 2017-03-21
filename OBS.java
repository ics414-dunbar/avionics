/*
*This object represents the OBS.
*It is used to represent and set the omni bearing selector. 
*It can be used to set or retrieve the OBS setting.
*
*/
class OBS{{
   private int radial;
   public OBS{(int radial){
	   if((radial <= 359) && (radial >=0)){
		setOBS(radial);   
	   }
	   else{
		   setOBS(0);
	   }
   }
   public void setOBS(int radial){
      this.radial = radial;
   }
   public int getOBS(){
      return this.radial;
   }
}

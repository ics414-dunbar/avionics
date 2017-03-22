import java.util.Scanner;
import java.lang.Math;

public class AvionicsProject{
   public static void main(String[] args){
     Scanner scan = new Scanner(System.in);
     int radial = 0;
     System.out.print("Enter desired radial: ");
      radial = scan.nextInt();
     OBS obs = new OBS(radial);
      VOR vor = new VOR(0 + (int)(Math.random() * 359));
      Needle needle = new Needle(vor.getVOR(), obs.getOBS());
      System.out.println(needle.toFrom() + " " + needle.deflection());
   }
}
class OBS{
   private int radial;
   public OBS(int radial){
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

class VOR{
   private int radial;
   public VOR(int input){
      radial = input;
   }
   public void setVOR(int radial){
      this.radial = radial;
   }
   public int getVOR(){
      return this.radial;
   }
}

class Needle{
   private int vorRadial;
   private int planeRadial;
   public Needle(int input1, int input2){
      vorRadial = input1;
      planeRadial = input2;
   }
   public String toFrom(){
      String output = "";
      if(vorRadial == (planeRadial + 90) || vorRadial == (planeRadial -90 )){
         output = "BAD";
      }else{
         if(vorRadial < 270 || vorRadial > 90){
            output = "TO";
         }else{
            output =  "FROM";
         }
      }
      return output;
   }
   public String deflection(){
      String output = "";
      int deflection = 0;
      if(vorRadial != (planeRadial + 90) && vorRadial != (planeRadial - 90 )){
         if(vorRadial < 180){
            deflection = vorRadial - planeRadial;
            if(deflection == 2 || deflection == 1){
               output = "LEFT 2";
            }
            if(deflection == 4 || deflection == 3){
               output = "LEFT 4";
            }
            if(deflection == 6 || deflection == 5){
               output = "LEFT 6";
            }
            if(deflection == 8 || deflection == 7){
               output = "LEFT 8";
            }
         }else{
            output = "FULL LEFT";
         }
         if(vorRadial > 180){
           deflection = planeRadial - vorRadial;
           if(deflection == 2 || deflection == 1){
              output = "RIGHT 2";
           }
           if(deflection == 4 || deflection == 3){
              output = "RIGHT 4";
           }
           if(deflection == 6 || deflection == 5){
              output = "RIGHT 6";
           }
           if(deflection == 8 || deflection == 7){
              output = "RIGHT 8";
           }
         }else{
            output = "FULL RIGHT";
         }
         if(planeRadial == vorRadial){
            output = "CENTERED";
         }
      }
      return output;
   }
}


public class Pixel {
  public Pixel(int r,int g,int b){
    this.red=r;
    this.green=g;
    this.blue=b;
  }
  //public Pixel(){}
  private int red;
  private int green;
  private int blue;
  public int getRed(){return red;}
  public int getGreen(){return green;}
  public int getBlue(){return blue;}
  public void setRed(int r){red=r;}
  public void setGreen(int g){green=g;}
  public void setBlue(int b){blue=b;}
}

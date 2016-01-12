
public class Image {
  Image(int w,int h){
    this.width = w;
    this.height = h;
    this.pixels = new Pixel[w][h];
    //System.out.println("Setting up pixels");
    for(int i=0;i<w;i++){
      for(int j=0;j<h;j++){
        this.pixels[i][j] = new Pixel(0,0,0);
      }
    }
   // System.out.println("Done setting up pixels");
  }
  private int width;
  private int height;
  public int getWidth(){return width;}
  public int getHeight(){return height;}
  public Pixel[][] pixels;
  public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append("P3\n");
	sb.append(width + "\n");
	sb.append(height + "\n");
	sb.append("255" + "\n");
		for(int j=0;j<height;j++){
			for(int i=0;i<width;i++){
			sb.append(pixels[i][j].getRed());
			sb.append("\n");
			sb.append(pixels[i][j].getGreen());
			sb.append("\n");
			sb.append(pixels[i][j].getBlue());
			sb.append("\n");
		}
	}
	return sb.toString();
  }
}

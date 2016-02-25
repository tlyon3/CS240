package edu.byu.cs.superasteroids.model.gamedefinition;

/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @versoin 1.0*/

import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/**Class for object that have "image" member*/
public class ImageObject {

    public ImageObject(){
        height=0;
        width=0;
        imagePath="";
    }

    public ImageObject(int h,int w,String ip){
        height=h;
        width=w;
        imagePath=ip;
    }
    /** Height of image in pixels */
    protected int height;
    /** Width of image in pixels */
    protected int width;
    /** Path to image file */
    protected String imagePath;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    protected Point makePoint(String pointString){
        String[] parts = pointString.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Point result = new Point(x,y);
        return result;
    }
}

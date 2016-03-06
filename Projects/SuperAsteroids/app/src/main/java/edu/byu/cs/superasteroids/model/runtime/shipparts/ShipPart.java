package edu.byu.cs.superasteroids.model.runtime.shipparts;

/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @version 1.0*/

import edu.byu.cs.superasteroids.model.gamedefinition.ImageObject;

/** Super-class for all ShipPart objects*/
public class ShipPart extends ImageObject {
    public ShipPart(){
        attachPoint = null;
    }

    public ShipPart(int imageHeight, int imageWidth, String imagePath, long id, Point attachPoint) {
        super(imageHeight, imageWidth, imagePath);
        this.id = id;
        this.attachPoint = attachPoint;
    }

    public ShipPart(int attachX,int attachY,long id){
        attachPoint=new Point(attachX,attachY);
        this.id=id;
    }
    private long id;
    /** Attach point for the ship part*/
    protected Point attachPoint;


    public Point getAttachPoint() {
        return attachPoint;
    }

    public void setAttachPoint(Point attachPoint) {
        this.attachPoint = attachPoint;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
//        if(o.getClass()!=this.getClass())
//            return false;
        ShipPart shipPart = (ShipPart)o;
        if(shipPart.getAttachPoint()!=this.attachPoint)
            return false;
        else if(shipPart.getId()!=this.id)
            return false;
        else if(shipPart.getHeight()!=this.height)
            return false;
        else if(shipPart.getImagePath()!=this.imagePath)
            return false;
        else if(shipPart.getWidth()!=this.width)
            return false;
        else return true;
    }

}

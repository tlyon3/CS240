package edu.byu.cs.superasteroids.ship_builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/20/16.
 */
public class ShipBuildingController implements IShipBuildingController {
    private Set<Cannon> cannons;
    private Set<Engine> engines;
    private Set<MainBody> mainBodies;
    private Set<ExtraPart> extraParts;
    private Set<PowerCore> powerCores;

    private List<Cannon> cannonList;
    private List<Engine> engineList;
    private List<MainBody> mainBodyList;
    private List<ExtraPart> extraPartList;
    private List<PowerCore> powerCoreList;

    private AsteroidsData modelData;
    private Ship ship;
    private ContentManager content;
    private IShipBuildingView.PartSelectionView currentSelectionView;

    //indexes of images. Given from content.loadImages()
    private Set<Integer> allImages;
    private List<Integer> mainBodyImages;
    private List<Integer> cannonImages;
    private List<Integer> extraPartImages;
    private List<Integer> engineImages;
    private List<Integer> powerCoreImages;
    //
//    private ShipBuilderState state;
    //keeps track of selected parts
    private int mainBodyIndex;
    private int engineIndex;
    private int cannonIndex;
    private int extraPartIndex;
    private int powerCoreIndex;

    ShipBuildingActivity shipBuildingActivity;
    /* Constructors -----------------------------------------------------------------------------*/

    public ShipBuildingController(IShipBuildingView view){
        this.shipBuildingActivity = (ShipBuildingActivity)view;
        modelData = AsteroidsData.getInstance();
        ship = modelData.getShip();
        content = null;

        currentSelectionView = null;

        this.cannonIndex = -1;
        this.extraPartIndex = -1;
        this.mainBodyIndex = -1;
        this.powerCoreIndex = -1;
        this.engineIndex = -1;

        this.cannons = null;
        this.engines = null;
        this.mainBodies = null;
        this.extraParts = null;
        this.powerCores = null;

        this.allImages = new HashSet<>();
        this.mainBodyImages = new ArrayList<>();
        this.cannonImages = new ArrayList<>();
        this.extraPartImages = new ArrayList<>();
        this.engineImages = new ArrayList<>();
        this.powerCoreImages = new ArrayList<>();

        this.cannonList = new ArrayList<>();
        this.engineList = new ArrayList<>();
        this.powerCoreList = new ArrayList<>();
        this.mainBodyList = new ArrayList<>();
        this.extraPartList = new ArrayList<>();
    }
    /*-------------------------------------------------------------------------------------------*/

    /* Get list of images -----------------------------------------------------------------------*/
    public List<String> getCannonImages(){
        List<String> images = new ArrayList<>();
        for(Cannon cannon:cannons){
            String image = cannon.getImagePath();
            images.add(image);
        }
        return images;
    }

    public List<String> getEngineImages(){
        List<String> images = new ArrayList<>();
        for(Engine engine:engines){
            images.add(engine.getImagePath());
        }
        return images;
    }

    public List<String> getMainBodyImages(){
        List<String> images = new ArrayList<>();
        for(MainBody mainBody:mainBodies){
            images.add(mainBody.getImagePath());
        }
        return images;
    }

    public List<String > getExtraPartsImages(){
        List<String> images = new ArrayList<>();
        for(ExtraPart extraPart:extraParts){
            images.add(extraPart.getImagePath());
        }
        return images;
    }

    public List<String> getPowerCoreImages(){
        List<String> images = new ArrayList<>();
        for(PowerCore powerCore:powerCores){
            images.add(powerCore.getImagePath());
        }
        return images;
    }
    //-----------
    private List<Cannon> convertCannonsToList(Set<Cannon> cannonSet){
        List<Cannon> result = new ArrayList<>();
        for(Cannon cannon:cannonSet){
            result.add(cannon);
        }
        return result;
    }

    private List<MainBody> convertMainBodiesToList(Set<MainBody> mainBodySet){
        List<MainBody> result = new ArrayList<>();
        for(MainBody mainBody:mainBodySet){
            result.add(mainBody);
        }
        return result;
    }

    private List<Engine> convertEnginesToList(Set<Engine> engineSet){
        List<Engine> result = new ArrayList<>();
        for(Engine engine:engineSet){
            result.add(engine);
        }
        return result;
    }

    private List<PowerCore> convertPowerCoresToList(Set<PowerCore> powerCoreSet){
        List<PowerCore> result = new ArrayList<>();
        for(PowerCore powerCore:powerCoreSet){
            result.add(powerCore);
        }
        return result;
    }

    private List<ExtraPart> convertExtraPartsToList(Set<ExtraPart> extraPartSet){
        List<ExtraPart> result = new ArrayList<>();
        for(ExtraPart extraPart:extraPartSet){
            result.add(extraPart);
        }
        return result;
    }
    /*-------------------------------------------------------------------------------------------*/
    /* Override Methods--------------------------------------------------------------------------*/

    private void setupStartGameButton(){
        shipBuildingActivity.setStartGameButton(ship.isComplete());
    }


    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        this.currentSelectionView = partView;
    }

    @Override
    public void loadContent(ContentManager content) {
        if(this.content == null){
            this.content = content;
//            loadContent(this.mainBodies,mainBodyImages,MAINBODY);
//            loadContent(this.engines,engineImages,ENGINE);
        }
        this.cannons = AsteroidsData.getInstance().getCannons();
        this.engines = AsteroidsData.getInstance().getEngines();
        this.mainBodies = AsteroidsData.getInstance().getMainBodies();
        this.extraParts = AsteroidsData.getInstance().getExtraParts();
        this.powerCores = AsteroidsData.getInstance().getPowerCores();

        this.cannonList = convertCannonsToList(AsteroidsData.getInstance().getCannons());
        this.engineList = convertEnginesToList(AsteroidsData.getInstance().getEngines());
        this.mainBodyList = convertMainBodiesToList(AsteroidsData.getInstance().getMainBodies());
        this.powerCoreList = convertPowerCoresToList(AsteroidsData.getInstance().getPowerCores());
        this.extraPartList = convertExtraPartsToList(AsteroidsData.getInstance().getExtraParts());

        //returns a list of indexes???
        cannonImages = content.loadImages(getCannonImages());
        engineImages = content.loadImages(getEngineImages());
        extraPartImages = content.loadImages(getExtraPartsImages());
        mainBodyImages = content.loadImages(getMainBodyImages());
        powerCoreImages = content.loadImages(getPowerCoreImages());
        //set images
        shipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY,mainBodyImages);
        shipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE,engineImages);
        shipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE,powerCoreImages);
        shipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON,cannonImages);
        shipBuildingActivity.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART,extraPartImages);
    }

    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        switch (currentSelectionView){
            case MAIN_BODY:
                switch (direction){
                    case RIGHT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.POWER_CORE;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.ENGINE;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case ENGINE:
                switch (direction){
                    case RIGHT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case EXTRA_PART:
                switch (direction){
                    case RIGHT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.ENGINE;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.ENGINE, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.CANNON;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case CANNON:
                switch (direction){
                    case RIGHT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.EXTRA_PART, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.POWER_CORE;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.POWER_CORE, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            case POWER_CORE:
                switch (direction){
                    case RIGHT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.CANNON;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.CANNON, IShipBuildingView.ViewDirection.LEFT);
                        break;
                    case LEFT:
                        currentSelectionView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        shipBuildingActivity.animateToView(IShipBuildingView.PartSelectionView.MAIN_BODY, IShipBuildingView.ViewDirection.RIGHT);
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onPartSelected(int index) {

        switch (this.currentSelectionView){
            case MAIN_BODY:
                this.mainBodyIndex = index;
                ship.setMainBody(mainBodyList.get(index));
                break;
            case CANNON:
                this.cannonIndex = index;
                ship.setCannon(cannonList.get(index));
                break;
            case EXTRA_PART:
                this.extraPartIndex = index;
                ship.setExtraPart(extraPartList.get(index));
                break;
            case ENGINE:
                this.engineIndex = index;
                ship.setEngine(engineList.get(index));
                break;
            case POWER_CORE:
                this.powerCoreIndex = index;
                ship.setPowerCore(powerCoreList.get(index));
                break;
        }
    }

    @Override
    public void onStartGamePressed() {
        //pass AsteroidsData ship?
        shipBuildingActivity.startGame();
    }

    @Override
    public void onResume() {

    }

    @Override
    public IView getView() {
        return this.shipBuildingActivity;
    }

    @Override
    public void setView(IView view) {
        shipBuildingActivity = (ShipBuildingActivity)view;
    }

    @Override
    public void update(double elapsedTime) {

    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    // TODO: 2/23/16 Drawing isn't working. Not drawing in correct place
    @Override
    public void draw() {
        Point gameViewCenter = new Point(DrawingHelper.getGameViewWidth()/2,DrawingHelper.getGameViewHeight()/2);
        float scale = 0.33f;
        if(mainBodyIndex>=0){
            MainBody body = ship.getMainBody();
            int bodyImageId = mainBodyImages.get(mainBodyIndex);
            DrawingHelper.drawImage(bodyImageId,gameViewCenter.getX(),gameViewCenter.getY(),0,scale,scale,255);
            int bodyCenterX = body.getWidth()/2;
            int bodyCenterY = body.getHeight()/2;

            if(engineIndex>=0){
                //partOffset = (bodyAttach-bodyCenter) + (partCenter - partAttach)
                //bodyAttach = point from part
                //partAttach = point from mainBody
                Engine engine = ship.getEngine();
                int engineImageId = engineImages.get(engineIndex);

                int engineCenterX = engine.getWidth()/2;
                int engineCenterY = engine.getHeight()/2;
                int attachFromPartToBodyX = engine.getAttachPoint().getX();
                int attachFromPartToBodyY = engine.getAttachPoint().getY();
                int attachFromBodyToPartX = body.getEngineAttach().getX();
                int attachFromBodyToPartY = body.getEngineAttach().getY();

                float offsetX = (attachFromBodyToPartX - bodyCenterX) + (engineCenterX - attachFromPartToBodyX);
                float offsetY = (attachFromBodyToPartY - bodyCenterY) + (engineCenterY - attachFromPartToBodyY);

                offsetX = offsetX * scale;
                offsetY = offsetY * scale;

                int drawPointX = (int)offsetX + gameViewCenter.getX();
                int drawPointY = (int)offsetY + gameViewCenter.getY();

                DrawingHelper.drawImage(engineImageId,drawPointX,drawPointY,0,scale,scale,255);
            }
            if(cannonIndex>=0){
                Cannon cannon = ship.getCannon();
                int cannonImageId = cannonImages.get(cannonIndex);

                int cannonCenterX = cannon.getWidth()/2;
                int cannonCenterY = cannon.getHeight()/2;
                int attachFromPartToBodyX = cannon.getAttachPoint().getX();
                int attachFromPartToBodyY = cannon.getAttachPoint().getY();
                int attachFromBodyToPartX = body.getCannonAttach().getX();
                int attachFromBodyToPartY = body.getCannonAttach().getY();

                float offsetX = (attachFromBodyToPartX - bodyCenterX) + (cannonCenterX - attachFromPartToBodyX);
                float offsetY = (attachFromBodyToPartY - bodyCenterY) + (cannonCenterY - attachFromPartToBodyY);

                offsetX = offsetX * scale;
                offsetY = offsetY * scale;

                int drawPointX = (int)offsetX + gameViewCenter.getX();
                int drawPointY = (int)offsetY + gameViewCenter.getY();

                DrawingHelper.drawImage(cannonImageId,drawPointX,drawPointY,0,scale,scale,255);

            }
            if(extraPartIndex>=0){
                ExtraPart extraPart = ship.getExtraPart();
                int extraPartImageId = extraPartImages.get(extraPartIndex);

                int extraCenterX = extraPart.getWidth()/2;
                int extraCenterY = extraPart.getHeight()/2;
                int attachFromPartToBodyX = extraPart.getAttachPoint().getX();
                int attachFromPartToBodyY = extraPart.getAttachPoint().getY();
                int attachFromBodyToPartX = body.getExtraAttach().getX();
                int attachFromBodyToPartY = body.getExtraAttach().getY();

                float offsetX = (attachFromBodyToPartX - bodyCenterX) + (extraCenterX - attachFromPartToBodyX);
                float offsetY = (attachFromBodyToPartY - bodyCenterY) + (extraCenterY - attachFromPartToBodyY);

                offsetX = offsetX * scale;
                offsetY = offsetY * scale;

                int drawPointX = (int)offsetX + gameViewCenter.getX();
                int drawPointY = (int)offsetY + gameViewCenter.getY();

                DrawingHelper.drawImage(extraPartImageId,drawPointX,drawPointY,0,scale,scale,255);


            }
        }
        setupStartGameButton();
    }
    /*-------------------------------------------------------------------------------------------*/

//    private abstract class ShipBuilderState{
//        public void onViewLoaded(IShipBuildingView.PartSelectionView partView){}
//        public void onSlideView(IShipBuildingView.ViewDirection direction){}
//        public void onPartSelected(int index){}
//    }
//
//    private class MainBodyState extends ShipBuilderState {
//        @Override
//        public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
//
//        }
//
//        @Override
//        public void onSlideView(IShipBuildingView.ViewDirection direction) {
//        }
//
//        @Override
//        public void onPartSelected(int index) {
//
//        }
//    }
}

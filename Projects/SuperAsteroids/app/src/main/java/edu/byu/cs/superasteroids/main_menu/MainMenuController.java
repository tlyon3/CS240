package edu.byu.cs.superasteroids.main_menu;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/22/16.
 */
public class MainMenuController implements IMainMenuController {

    private Context mainContext;
    private IView currentView;
    List<Cannon> cannonList;
    List<MainBody> mainBodyList;
    List<ExtraPart> extraPartList;
    List<Engine> engineList;
    List<PowerCore> powerCoreList;

    public MainMenuController(Context context) {
        this.mainContext=context;
    }

    @Override
    public void onQuickPlayPressed() {
        //generate random ship
        Set<Cannon> cannons = AsteroidsData.getInstance().getCannons();
        Set<MainBody> mainBodies = AsteroidsData.getInstance().getMainBodies();
        Set<ExtraPart> extraParts = AsteroidsData.getInstance().getExtraParts();
        Set<Engine> engines = AsteroidsData.getInstance().getEngines();
        Set<PowerCore> powerCores = AsteroidsData.getInstance().getPowerCores();

        this.cannonList = convertCannonsToList(cannons);
        this.mainBodyList = convertMainBodiesToList(mainBodies);
        this.extraPartList = convertExtraPartsToList(extraParts);
        this.engineList = convertEnginesToList(engines);
        this.powerCoreList = convertPowerCoresToList(powerCores);

        Random random = new Random();

        Cannon cannon = cannonList.get(random.nextInt()>0.5 ? 0:1);
        MainBody mainBody = mainBodyList.get(random.nextInt() > 0.5 ? 0:1);
        ExtraPart extraPart = extraPartList.get(random.nextInt() > 0.5 ? 0:1);
        Engine engine = engineList.get(random.nextInt() > 0.5 ? 0:1);
        PowerCore powerCore = powerCoreList.get(random.nextInt() > 0.5 ? 0:1);

        Ship ship = new Ship(engine,cannon,powerCore,mainBody,extraPart);


        //call startGame()
    }

    @Override
    public IView getView() {
        return this.currentView;
    }

    @Override
    public void setView(IView view) {
        this.currentView = view;
    }

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
}

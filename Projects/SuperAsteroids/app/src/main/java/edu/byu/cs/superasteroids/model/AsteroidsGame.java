package edu.byu.cs.superasteroids.model;

import java.util.Set;

import edu.byu.cs.superasteroids.data.AsteroidsDAO;
import edu.byu.cs.superasteroids.data.LevelDAO;
import edu.byu.cs.superasteroids.data.ShipPartsDAO;
import edu.byu.cs.superasteroids.model.runtime.Level;
import edu.byu.cs.superasteroids.model.runtime.LevelHandler;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/19/16.
 */
/** Object that will hold all the information for the game*/
public class AsteroidsGame {
    public AsteroidsGame() {
        cannons = null;
        engines = null;
        extraParts = null;
        mainBodies = null;
        powerCores = null;
        levels = null;
    }

    public AsteroidsGame(LevelDAO levelDAO, AsteroidsDAO asteroidsDAO, ShipPartsDAO shipPartsDAO) {
        this.levelDAO = levelDAO;
        this.asteroidsDAO = asteroidsDAO;
        this.shipPartsDAO = shipPartsDAO;
        levelHandler = new LevelHandler();
    }

    //ship parts
    private Set<Cannon> cannons;
    private Set<Engine> engines;
    private Set<ExtraPart> extraParts;
    private Set<MainBody> mainBodies;
    private Set<PowerCore> powerCores;
    //levels
    private Set<Level> levels;
    //ship
    private Ship ship;

    private LevelDAO levelDAO;
    private AsteroidsDAO asteroidsDAO;
    private ShipPartsDAO shipPartsDAO;

    private LevelHandler levelHandler;

    /** Loads data from database and populates sets*/
    public void loadData() {
        cannons = shipPartsDAO.getCannonSet();
        engines = shipPartsDAO.getEngineSet();
        extraParts = shipPartsDAO.getExtraPartsSet();
        mainBodies = shipPartsDAO.getMainBodySet();
        powerCores = shipPartsDAO.getPowerCoreSet();

        levels = levelDAO.getLevels();
    }

    /** Starts the level with the given number
     * @param level Number of level to start
     * @return Returns true if level can be started, false if not*/
    public boolean startLevel(int level){
        Level levelToStart = getLevelWithId(level);
        if(levelToStart == null)
            return false;
        levelHandler.setCurrentLevel(levelToStart);
        levelHandler.initialize();
        return true;
    }

    /** Gets the level with the given id
     * @param id ID of level to get
     * @return Returns level with the given ID*/
    private Level getLevelWithId(int id){
        for(Level level:levels){
            if(level.getNumber() == id)
                return level;
        }
        return null;
    }

    /*--------------------------------------------*/

    public Set<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(Set<Cannon> cannons) {
        this.cannons = cannons;
    }

    public Set<Engine> getEngines() {
        return engines;
    }

    public void setEngines(Set<Engine> engines) {
        this.engines = engines;
    }

    public Set<ExtraPart> getExtraParts() {
        return extraParts;
    }

    public void setExtraParts(Set<ExtraPart> extraParts) {
        this.extraParts = extraParts;
    }

    public Set<MainBody> getMainBodies() {
        return mainBodies;
    }

    public void setMainBodies(Set<MainBody> mainBodies) {
        this.mainBodies = mainBodies;
    }

    public Set<PowerCore> getPowerCores() {
        return powerCores;
    }

    public void setPowerCores(Set<PowerCore> powerCores) {
        this.powerCores = powerCores;
    }

    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public LevelDAO getLevelDAO() {
        return levelDAO;
    }

    public void setLevelDAO(LevelDAO levelDAO) {
        this.levelDAO = levelDAO;
    }

    public AsteroidsDAO getAsteroidsDAO() {
        return asteroidsDAO;
    }

    public void setAsteroidsDAO(AsteroidsDAO asteroidsDAO) {
        this.asteroidsDAO = asteroidsDAO;
    }

    public ShipPartsDAO getShipPartsDAO() {
        return shipPartsDAO;
    }

    public void setShipPartsDAO(ShipPartsDAO shipPartsDAO) {
        this.shipPartsDAO = shipPartsDAO;
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public void setLevelHandler(LevelHandler levelHandler) {
        this.levelHandler = levelHandler;
    }

}


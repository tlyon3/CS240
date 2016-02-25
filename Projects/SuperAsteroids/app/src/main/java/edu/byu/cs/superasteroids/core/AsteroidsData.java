package edu.byu.cs.superasteroids.core;

import java.util.Set;

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.Level;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/19/16.
 */
public class AsteroidsData {

    private static AsteroidsData asteroidsData;
    private Set<Cannon> cannons;
    private Set<MainBody> mainBodies;
    private Set<Engine> engines;
    private Set<ExtraPart> extraParts;
    private Set<PowerCore> powerCores;
    private Set<BGObjectType> backgroundObjects;
    private Set<AsteroidType> asteroidTypes;
    private Set<Level> levels;
    private Ship ship;

    private AsteroidsData(){}

    public static AsteroidsData getInstance(){
        if(asteroidsData==null){
            asteroidsData = new AsteroidsData();
        }
        return asteroidsData;
    }

    public static AsteroidsData getAsteroidsData() {
        return asteroidsData;
    }

    public static void setAsteroidsData(AsteroidsData asteroidsData) {
        AsteroidsData.asteroidsData = asteroidsData;
    }

    public Set<Cannon> getCannons() {
        return cannons;
    }

    public void setCannons(Set<Cannon> cannons) {
        this.cannons = cannons;
    }

    public Set<MainBody> getMainBodies() {
        return mainBodies;
    }

    public void setMainBodies(Set<MainBody> mainBodies) {
        this.mainBodies = mainBodies;
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

    public Set<PowerCore> getPowerCores() {
        return powerCores;
    }

    public void setPowerCores(Set<PowerCore> powerCores) {
        this.powerCores = powerCores;
    }

    public Set<BGObjectType> getBackgroundObjects() {
        return backgroundObjects;
    }

    public void setBackgroundObjects(Set<BGObjectType> backgroundObjects) {
        this.backgroundObjects = backgroundObjects;
    }

    public Set<AsteroidType> getAsteroidTypes() {
        return asteroidTypes;
    }

    public void setAsteroidTypes(Set<AsteroidType> asteroidTypes) {
        this.asteroidTypes = asteroidTypes;
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
}

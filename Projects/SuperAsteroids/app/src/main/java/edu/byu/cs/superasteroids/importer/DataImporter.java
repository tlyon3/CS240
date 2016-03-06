package edu.byu.cs.superasteroids.importer;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.data.AsteroidsDAO;
import edu.byu.cs.superasteroids.data.DbOpenHelper;
import edu.byu.cs.superasteroids.data.LevelDAO;
import edu.byu.cs.superasteroids.data.ShipPartsDAO;
import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.BGObject;
import edu.byu.cs.superasteroids.model.runtime.Level;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/11/16.
 */
public class DataImporter implements IGameDataImporter {
    private SQLiteDatabase db;
    private DbOpenHelper openHelper;
    private Context baseContext;
    private AsteroidsDAO asteroidsDAO;
    private LevelDAO levelDAO;
    private ShipPartsDAO shipPartsDAO;

    public DataImporter(Context c){
        this.baseContext = c;
        this.openHelper = new DbOpenHelper(c);
        this.db = openHelper.getReadableDatabase();
        this.asteroidsDAO = new AsteroidsDAO(db);
        this.levelDAO = new LevelDAO(db);
        this.shipPartsDAO = new ShipPartsDAO(db);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public DbOpenHelper getOpenHelper() {
        return openHelper;
    }

    public void setOpenHelper(DbOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    public Context getBaseContext() {
        return baseContext;
    }

    public void setBaseContext(Context baseContext) {
        this.baseContext = baseContext;
    }

    public AsteroidsDAO getAsteroidsDAO() {
        return asteroidsDAO;
    }

    public void setAsteroidsDAO(AsteroidsDAO asteroidsDAO) {
        this.asteroidsDAO = asteroidsDAO;
    }

    public LevelDAO getLevelDAO() {
        return levelDAO;
    }

    public void setLevelDAO(LevelDAO levelDAO) {
        this.levelDAO = levelDAO;
    }

    public ShipPartsDAO getShipPartsDAO() {
        return shipPartsDAO;
    }

    public void setShipPartsDAO(ShipPartsDAO shipPartsDAO) {
        this.shipPartsDAO = shipPartsDAO;
    }

    private static String makeString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[512];
        int n=0;
        while((n = reader.read(buffer) ) > 0){
            sb.append(buffer,0,n);
        }
        return sb.toString();
    }

    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        asteroidsDAO.dropTable();
        levelDAO.dropAllTables();
        shipPartsDAO.dropAllTables();
        openHelper.createAllTables(db);
        AssetManager manager = baseContext.getAssets();
        JSONArray objectsArray = null;
        JSONArray asteroidsArr = null;
        JSONArray levelsArray = null;
        JSONArray mainBodiesArray = null;
        JSONArray engineArray = null;
        JSONArray cannonArray = null;
        JSONArray extraPartArray = null;
        JSONArray powerCoreArray = null;

        try{
            JSONObject rootObject = new JSONObject(makeString(dataInputReader));
            JSONObject asteroidsGame = rootObject.getJSONObject("asteroidsGame");

            //import backgroundobjects
            objectsArray = asteroidsGame.getJSONArray("objects");
            importBackgroundObjects(objectsArray);
            Set<BGObjectType> objectTypes = levelDAO.getAllBgObjects();

            AsteroidsData.getInstance().setBackgroundObjects(objectTypes);

            //import asteroids
            asteroidsArr = asteroidsGame.getJSONArray("asteroids");
            importAsteroids(asteroidsArr);
            AsteroidsData.getInstance().setAsteroidTypes(asteroidsDAO.getAsteroids());

            //import level
            levelsArray = asteroidsGame.getJSONArray("levels");
            importLevels(levelsArray);
            AsteroidsData.getInstance().setLevels(levelDAO.getLevels());

            //import mainBodies
            mainBodiesArray = asteroidsGame.getJSONArray("mainBodies");
            importMainBodies(mainBodiesArray);
            AsteroidsData.getInstance().setMainBodies(shipPartsDAO.getMainBodySet());

            //import cannons
            cannonArray = asteroidsGame.getJSONArray("cannons");
            importCannons(cannonArray);
            AsteroidsData.getInstance().setCannons(shipPartsDAO.getCannonSet());

            //import extraParts
            extraPartArray = asteroidsGame.getJSONArray("extraParts");
            importExtraParts(extraPartArray);
            AsteroidsData.getInstance().setExtraParts(shipPartsDAO.getExtraPartsSet());

            //import engines
            engineArray = asteroidsGame.getJSONArray("engines");
            importEngines(engineArray);
            AsteroidsData.getInstance().setEngines(shipPartsDAO.getEngineSet());

            //import powerCores
            powerCoreArray = asteroidsGame.getJSONArray("powerCores");
            importPowerCores(powerCoreArray);
            AsteroidsData.getInstance().setPowerCores(shipPartsDAO.getPowerCoreSet());
            AsteroidsData.getInstance().setShip(new Ship());
        }
        catch (JSONException e){
            e.printStackTrace();
            return false;
        }
        catch (IOException ex){
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /** Imports backgroundObjects.
     * @param bgObjectsArray JSONArray of all background objects. Given from importData()*/
    private void importBackgroundObjects(JSONArray bgObjectsArray) throws JSONException{
        for(int i=0;i<bgObjectsArray.length();i++){
            String image = bgObjectsArray.getString(i);
            BGObjectType bgObjectType = new BGObjectType(image);
            levelDAO.addBGObject(bgObjectType);
        }
    }

    /** Imports asteroids
     * @param asteroidsArray JSONArray of all asteroids. Given from importData()*/
    private void importAsteroids(JSONArray asteroidsArray) throws JSONException{
        for(int i=0;i<asteroidsArray.length();i++){
            JSONObject asteroid = asteroidsArray.getJSONObject(i);
            AsteroidType asteroidType = new AsteroidType(asteroid);
            asteroidsDAO.addAsteroid(asteroidType);
        }
    }

    /** Imports mainBodies
     * @param mainBodiesArray JSONArray of all MainBodies. Given from importData()*/
    private void importMainBodies(JSONArray mainBodiesArray) throws JSONException{
        for(int i=0;i<mainBodiesArray.length();i++){
            JSONObject mainBodyObject = mainBodiesArray.getJSONObject(i);
            MainBody mainBody = new MainBody(mainBodyObject);
            shipPartsDAO.addShipPart(mainBody);
        }
    }

    /** Imports cannons
     * @param cannonsArray JSONArray of all cannons. Given from importData()*/
    private void importCannons(JSONArray cannonsArray) throws JSONException{
        for(int i=0;i<cannonsArray.length();i++){
            JSONObject cannonObject = cannonsArray.getJSONObject(i);
            Cannon cannon = new Cannon(cannonObject);
            shipPartsDAO.addShipPart(cannon);
        }
    }

    /** Imports extraParts
     * @param extraPartsArray JSONArray of all ExtraParts. Given from importData()*/
    private void importExtraParts(JSONArray extraPartsArray) throws JSONException{
        for(int i=0;i<extraPartsArray.length();i++){
            JSONObject extraPartObject = extraPartsArray.getJSONObject(i);
            ExtraPart extraPart = new ExtraPart(extraPartObject);
            shipPartsDAO.addShipPart(extraPart);
        }
    }

    /** Imports all engines.
     * @param enginesArray JSONArray of all Engines. Given from importData()*/
    private void importEngines(JSONArray enginesArray) throws JSONException{
        for(int i=0;i<enginesArray.length();i++){
            JSONObject engineObject = enginesArray.getJSONObject(i);
            Engine engine = new Engine(engineObject);
            shipPartsDAO.addShipPart(engine);
        }
    }

    /** Imports all powerCores
     * @param powerCoresArray JSONArray of all powerCores. Given from importData()*/
    private void importPowerCores(JSONArray powerCoresArray) throws JSONException{
        for(int i=0;i<powerCoresArray.length();i++){
            JSONObject powerCoreObject = powerCoresArray.getJSONObject(i);
            PowerCore powerCore = new PowerCore(powerCoreObject);
            shipPartsDAO.addShipPart(powerCore);
        }
    }

    /** Imports all levels
     * @param levelsArray JSONArray of all levels. Given from importData()*/
    private void importLevels(JSONArray levelsArray)throws JSONException{
        for(int i=0;i<levelsArray.length();i++){
            JSONObject levelO = levelsArray.getJSONObject(i);
            Level level = new Level(levelO);
            int levelNumber = levelO.getInt("number");

            //import levelObjects
            JSONArray levelObjectsArray = levelO.getJSONArray("levelObjects");
            importLevelObjects(levelObjectsArray,levelNumber);

            //import levelAsteroids
            JSONArray levelAsteroids = levelO.getJSONArray("levelAsteroids");
            importLevelAsteroids(levelAsteroids,levelNumber);

            //set maps and add level to database
            Map<BGObject,Integer> bgObjects = levelDAO.getBGObjectsForLevel(levelNumber);
            Map<AsteroidType,Integer> asteroidObjects = levelDAO.getLevelAsteroids(levelNumber);
            level.setLevelAsteroidsMap(asteroidObjects);
            level.setLevelBackgroundObjectsMap(bgObjects);
            levelDAO.addLevel(level);
        }
    }

    /** Imports all levelObjects for a given level
     * @param levelObjectsArray JSONArray of levelObjects for specific level. Given from importLevels()
     * @param levelNumber number of level to add objects to*/
    private void importLevelObjects(JSONArray levelObjectsArray, int levelNumber) throws JSONException{
        for(int j=0;j<levelObjectsArray.length();j++){
            JSONObject object = levelObjectsArray.getJSONObject(j);
            int objectId = object.getInt("objectId");
            BGObjectType objectType = levelDAO.getObjectTypeForId(objectId);
            BGObject bgObject = new BGObject(object, objectType);
            levelDAO.addLevelObject(bgObject, levelNumber);
        }
    }

    /** Imports all levelAsteroids for a given level
     * @param levelAsteroidsArray JSONArray of all asteroids for a specific level. Given from importLevels()
     * @param levelNumber number of level to add asteroids to.*/
    private void importLevelAsteroids(JSONArray levelAsteroidsArray, int levelNumber) throws JSONException{
        for(int j=0;j<levelAsteroidsArray.length();j++){
            JSONObject object = levelAsteroidsArray.getJSONObject(j);
            int number = object.getInt("number");
            int asteroidId = object.getInt("asteroidId");
            AsteroidType asteroidType = levelDAO.getAsteroidTypeForId(asteroidId);
            levelDAO.addLevelAsteroid(asteroidType, levelNumber, number);
        }
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

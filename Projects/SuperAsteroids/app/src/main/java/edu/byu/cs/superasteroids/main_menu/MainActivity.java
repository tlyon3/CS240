package edu.byu.cs.superasteroids.main_menu;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.byu.cs.superasteroids.R;
import edu.byu.cs.superasteroids.base.ActionBarActivityView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.data.AsteroidsDAO;
import edu.byu.cs.superasteroids.data.DbOpenHelper;
import edu.byu.cs.superasteroids.data.LevelDAO;
import edu.byu.cs.superasteroids.data.ShipPartsDAO;
import edu.byu.cs.superasteroids.game.GameActivity;
import edu.byu.cs.superasteroids.importer.DataImporter;
import edu.byu.cs.superasteroids.importer.ImportActivity;
import edu.byu.cs.superasteroids.model.runtime.Asteroid;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.Viewport;
import edu.byu.cs.superasteroids.ship_builder.ShipBuildingActivity;

public class MainActivity extends ActionBarActivityView implements IMainMenuView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        IMainMenuController controller = new MainMenuController(this);
        controller.setView(this);
        setController(controller);



        ContentManager.getInstance().setResources(getResources());

        ContentManager.getInstance().setAssets(getAssets());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void startGame(View v) {
        //load data
        DbOpenHelper openHelper = new DbOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        LevelDAO levelDAO = new LevelDAO(db);
        AsteroidsDAO asteroidsDAO = new AsteroidsDAO(db);
        ShipPartsDAO shipPartsDAO = new ShipPartsDAO(db);
        AsteroidsData.getInstance().setBackgroundObjects(levelDAO.getAllBgObjects());
        AsteroidsData.getInstance().setAsteroidTypes(asteroidsDAO.getAsteroids());
        AsteroidsData.getInstance().setLevels(levelDAO.getLevels());
        AsteroidsData.getInstance().setMainBodies(shipPartsDAO.getMainBodySet());
        AsteroidsData.getInstance().setCannons(shipPartsDAO.getCannonSet());
        AsteroidsData.getInstance().setExtraParts(shipPartsDAO.getExtraPartsSet());
        AsteroidsData.getInstance().setEngines(shipPartsDAO.getEngineSet());
        AsteroidsData.getInstance().setPowerCores(shipPartsDAO.getPowerCoreSet());
        AsteroidsData.getInstance().setShip(new Ship());
        Viewport.getInstance().setCurrentLevel(AsteroidsData.getInstance().getLevelWithId(1));
        Intent intent = new Intent(this, ShipBuildingActivity.class);
        startActivity(intent);
    }

    public void quickPlay(View v) {
        //load data
        DbOpenHelper openHelper = new DbOpenHelper(this);
        SQLiteDatabase db = openHelper.getWritableDatabase();
        LevelDAO levelDAO = new LevelDAO(db);
        AsteroidsDAO asteroidsDAO = new AsteroidsDAO(db);
        ShipPartsDAO shipPartsDAO = new ShipPartsDAO(db);

        AsteroidsData.getInstance().setBackgroundObjects(levelDAO.getAllBgObjects());
        AsteroidsData.getInstance().setAsteroidTypes(asteroidsDAO.getAsteroids());
        AsteroidsData.getInstance().setLevels(levelDAO.getLevels());
        AsteroidsData.getInstance().setMainBodies(shipPartsDAO.getMainBodySet());
        AsteroidsData.getInstance().setCannons(shipPartsDAO.getCannonSet());
        AsteroidsData.getInstance().setExtraParts(shipPartsDAO.getExtraPartsSet());
        AsteroidsData.getInstance().setEngines(shipPartsDAO.getEngineSet());
        AsteroidsData.getInstance().setPowerCores(shipPartsDAO.getPowerCoreSet());
        AsteroidsData.getInstance().setShip(new Ship());
        if (getController() != null) {
            ((IMainMenuController) getController()).onQuickPlayPressed();
        }
    }

    public void startGame() {

        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
        this.startActivity(intent);
    }

    public void importData(View v) {
        Intent intent = new Intent(this, ImportActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

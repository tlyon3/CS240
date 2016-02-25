package edu.byu.cs.woodfiel.catalogdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.byu.cs.woodfiel.catalogdemo.database.Database;

/**
 * Created by Scott Woodfield on 2/2/2016.
 */
public class Catalog {

    private List<CD> items;

    public Catalog(Database database) {
        items = new ArrayList<>();
        Set<CD> cds = database.getCD_DAO().getCDs();

        for(CD cd : cds) {
            items.add(cd);
        }
    }

    public List<CD> getItems() {
        return items;
    }
}

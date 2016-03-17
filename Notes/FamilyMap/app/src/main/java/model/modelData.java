package model;

/**
 * Created by tlyon on 3/17/16.
 */
public class modelData {
    private static modelData ourInstance = new modelData();

    public static modelData getInstance() {
        return ourInstance;
    }

    private modelData() {
    }
}

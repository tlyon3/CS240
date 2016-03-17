package model;

/**
 * Created by tlyon on 3/17/16.
 */
public class ModelData {
    private static ModelData ourInstance = new ModelData();

    public static ModelData getInstance() {
        return ourInstance;
    }

    private ModelData() {
    }

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

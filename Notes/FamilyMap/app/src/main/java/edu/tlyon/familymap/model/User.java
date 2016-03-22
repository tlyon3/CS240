package edu.tlyon.familymap.model;

/**
 * Created by tlyon on 3/17/16.
 */
public class User {
    private String authorizationKey;
    private Person ownPerson;

    public User(String authorizationKey) {
        this.authorizationKey = authorizationKey;

    }

    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    public Person getOwnPerson() {
        return ownPerson;
    }

    public void setOwnPerson(Person ownPerson) {
        this.ownPerson = ownPerson;
    }
}

package com.github.hhyeg2016.mon.data_manager;

import java.util.ArrayList;
import java.util.UUID;

public abstract class StoringManager<A> {

    public Boolean existsLocally(UUID id) {
        A object = getById(id);
        return object != null;
    }

    public void sync(A object, UUID id) {
        if (existsLocally(id)) {
            update(object);
        } else {
            insert(object);
        }
    }

    public abstract void insert(A object);

    public abstract ArrayList<A> retrieveAll();

    public abstract void update(A newObject);

    public abstract A getById(UUID id);

    public void remove(UUID objId) {
    }
}
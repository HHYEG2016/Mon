package com.github.hhyeg2016.mon.data_manager;

import java.util.ArrayList;
import java.util.UUID;

public abstract class StoringManager<A> {

    public abstract void insert(A object);

    public abstract ArrayList<A> retrieveAll();

    public abstract A getById(UUID id);

    public void remove(UUID objId) {
    }
}
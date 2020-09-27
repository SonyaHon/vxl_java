package me.sonyahon.engine.resource;

import java.util.HashMap;

public abstract class AbstractManager<T, L> {
    protected String basePath = System.getProperty("user.dir") + "/src/resources/";
    protected HashMap<String, T> items = new HashMap<>();
    public abstract void load(L data, String name);
    public T get(String name) {
        return items.get(name);
    }
    public abstract void drop();
    public abstract void unbindCurrent();
}

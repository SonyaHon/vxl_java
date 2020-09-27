package me.sonyahon.engine.terrain;

import me.sonyahon.engine.components.Transform;
import me.sonyahon.engine.entity.Entity;

public class Terrain extends Entity {
    public Terrain() {
        super("Terrain");
        addComponent(new Transform());
        generateChunks();
    }

    private void generateChunks() {
        
    }

}

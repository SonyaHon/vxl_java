package me.sonyahon.engine.world;

import me.sonyahon.engine.entity.Entity;

public class World {
    public static final World instance = new World();

    private Entity mainCamera;
    private Entity player;

    public void setMainCamera(Entity mainCamera) {
        this.mainCamera = mainCamera;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Entity getMainCamera() {
        return mainCamera;
    }

    public Entity getPlayer() {
        return player;
    }
}

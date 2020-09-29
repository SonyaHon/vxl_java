package me.sonyahon.game;

import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.graphics.Material;

public class Player extends Entity {
    public Player(Transform transform, StaticMeshData meshData, Material material) {
        super(transform, meshData, material);
        addChild(MainCamera.instance);
    }
}

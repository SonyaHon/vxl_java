package me.sonyahon.engine.entity;

import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.graphics.Material;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private Transform transform;
    private StaticMeshData meshData;
    private Material material;
    private String tag = "";

    private List<Entity> children = new ArrayList<>();

    public Entity(Transform transform, StaticMeshData meshData, Material material) {
        this.transform = transform;
        this.meshData = meshData;
        this.material = material;
    }

    public Entity(Transform transform, StaticMeshData meshData, Material material, String tag) {
        this.transform = transform;
        this.meshData = meshData;
        this.material = material;
        this.tag = tag;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void setMeshData(StaticMeshData meshData) {
        this.meshData = meshData;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Transform getTransform() {
        return transform;
    }

    public StaticMeshData getMeshData() {
        return meshData;
    }

    public Material getMaterial() {
        return material;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public void addChild(Entity e) {
        this.children.add(e);
    }
}

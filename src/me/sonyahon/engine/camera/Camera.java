package me.sonyahon.engine.camera;

import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.entity.Entity;
import org.joml.Matrix4f;

public class Camera extends Entity{
    private float FOVy;
    private float aspect;
    private float nearPlane;
    private float farPlane;


    public Camera(float FOVy, float aspect, float nearPlane, float farPlane) {
        super(new Transform(), null, null);
        this.FOVy = FOVy;
        this.aspect = aspect;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }


    public Matrix4f getProjectionMatrix() {
        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.perspective((float) Math.toRadians(FOVy), aspect, nearPlane, farPlane);
        return projectionMatrix;
    }
}

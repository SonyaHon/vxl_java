package me.sonyahon.engine.components;

import org.joml.Matrix4f;

public class Camera {
    private float FOVy;
    private float aspect;
    private float nearPlane;
    private float farPlane;


    public Camera(float FOVy, float aspect, float nearPlane, float farPlane) {
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

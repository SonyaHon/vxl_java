package me.sonyahon.game;

import me.sonyahon.Reference;
import me.sonyahon.engine.camera.Camera;

public class MainCamera extends Camera {
    public static MainCamera instance = new MainCamera(Reference.FOVY, Reference.ASPECT, Reference.NEAR_PLANE, Reference.FAR_PLANE);
    public MainCamera(float FOVy, float aspect, float nearPlane, float farPlane) {
        super(FOVy, aspect, nearPlane, farPlane);
    }
}

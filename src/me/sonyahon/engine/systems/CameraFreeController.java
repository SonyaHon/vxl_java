package me.sonyahon.engine.systems;

import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.input.InputManager;
import me.sonyahon.engine.input.KeyCode;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CameraFreeController extends VXLSystem {

    private Vector2f mouseLastPosition = new Vector2f();
    private Vector2f mouseCurrentPosition = new Vector2f();

    @Override
    public void update() {
//        Entity mainCamera = World.instance.getMainCamera();
//        Transform camTransform = mainCamera.getComponent(Transform.class);
//
//        float cameraRotateSpeedCoef = 0.002f;
//
//        Vector2f mousePos = InputManager.instance.getMouseCoordinates();
//        mouseLastPosition = mouseCurrentPosition;
//        mouseCurrentPosition = mousePos;
//
//        float mouseDx = mouseCurrentPosition.x - mouseLastPosition.x;
//        float mouseDy = mouseCurrentPosition.y - mouseLastPosition.y;
//
//        camTransform.rotateY(mouseDx * cameraRotateSpeedCoef);
//        camTransform.rotateX(mouseDy * cameraRotateSpeedCoef);
//
//
//        float camSpeed = 0.1f;
//        Vector3f initialDirection = new Vector3f(0, 0, -1);
//        Vector3f velocity = new Vector3f();
//
//        initialDirection.rotateX((float) camTransform.getRotation().x * -1);
//        initialDirection.rotateY((float) camTransform.getRotation().y * -1);
//
//
//        if (InputManager.instance.isKeyPressed(KeyCode.W)) {
//            velocity.add(initialDirection.get(new Vector3f()).mul(1));
//        } else if (InputManager.instance.isKeyPressed(KeyCode.S)) {
//            velocity.add(initialDirection.get(new Vector3f()).mul(-1));
//        }
//
//        Vector3f projection = new Vector3f(initialDirection.x, 0, initialDirection.z);
//        Vector3f perpendicular = projection.get(new Vector3f()).cross(new Vector3f(0, 1, 0));
//
//        if (InputManager.instance.isKeyPressed(KeyCode.A)) {
//            velocity.add(perpendicular.get(new Vector3f()).mul(-1));
//        } else if (InputManager.instance.isKeyPressed(KeyCode.D)) {
//            velocity.add(perpendicular.get(new Vector3f()).mul(1));
//        }
//
//
//        camTransform.translateVector(velocity.mul(camSpeed));
    }
}

package me.sonyahon.game;

import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.graphics.Material;
import me.sonyahon.engine.input.InputManager;
import me.sonyahon.engine.input.KeyCode;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Player extends Entity {
    private Vector2f mouseLastPosition = new Vector2f();
    private Vector2f mouseCurrentPosition = new Vector2f();

    public Player(Transform transform, StaticMeshData meshData, Material material) {
        super(transform, meshData, material);
    }

    public void update() {
        float cameraRotateSpeedCoef = 0.002f;

        Vector2f mousePos = InputManager.instance.getMouseCoordinates();
        mouseLastPosition = mouseCurrentPosition;
        mouseCurrentPosition = mousePos;

        float mouseDx = mouseCurrentPosition.x - mouseLastPosition.x;
        float mouseDy = mouseCurrentPosition.y - mouseLastPosition.y;

        MainCamera.instance.getTransform().rotateY(mouseDx * cameraRotateSpeedCoef);
        MainCamera.instance.getTransform().rotateX(mouseDy * cameraRotateSpeedCoef);


        float camSpeed = 0.1f;
        Vector3f velocity = new Vector3f();

        Vector3f direction = MainCamera.instance.getTransform().getDirection();

       if (InputManager.instance.isKeyPressed(KeyCode.W)) {
            velocity.add(MainCamera.instance.getTransform().getDirection().get(new Vector3f()).mul(-1));
        } else if (InputManager.instance.isKeyPressed(KeyCode.S)) {
            velocity.add(MainCamera.instance.getTransform().getDirection().get(new Vector3f()).mul(1));
        }

        Vector3f projection = new Vector3f(MainCamera.instance.getTransform().getDirection().x, 0, MainCamera.instance.getTransform().getDirection().z);
        Vector3f perpendicular = projection.get(new Vector3f()).cross(new Vector3f(0, 1, 0));

        if (InputManager.instance.isKeyPressed(KeyCode.A)) {
            velocity.add(perpendicular.get(new Vector3f()).mul(1));
        } else if (InputManager.instance.isKeyPressed(KeyCode.D)) {
            velocity.add(perpendicular.get(new Vector3f()).mul(-1));
        }


        MainCamera.instance.getTransform().translate(velocity.mul(camSpeed));
    }
}

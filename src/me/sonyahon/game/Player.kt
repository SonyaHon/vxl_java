package me.sonyahon.game

import me.sonyahon.engine.d3.StaticMeshData
import me.sonyahon.engine.d3.Transform
import me.sonyahon.engine.entity.Entity
import me.sonyahon.engine.graphics.Material
import me.sonyahon.engine.input.InputManager
import me.sonyahon.engine.input.KeyCode
import org.joml.Vector2f
import org.joml.Vector3f

class Player(transform: Transform, material: Material?, meshData: StaticMeshData?) : Entity(transform, meshData, material) {
    private var mouseLastPosition = Vector2f()
    private var mouseCurrentPosition = Vector2f()


    fun update() {
        val cameraRotateSpeedCoef = 0.002f

        val mousePos = InputManager.instance.mouseCoordinates
        mouseLastPosition = mouseCurrentPosition
        mouseCurrentPosition = mousePos

        val mouseDx = mouseCurrentPosition.x - mouseLastPosition.x
        val mouseDy = mouseCurrentPosition.y - mouseLastPosition.y

        MainCamera.INSTANCE.transform.rotateY(mouseDx * cameraRotateSpeedCoef)
        MainCamera.INSTANCE.transform.rotateX(mouseDy * cameraRotateSpeedCoef)

        val camSpeed = 0.1f
        val velocity = Vector3f()

        val direction = MainCamera.INSTANCE.transform.direction

        if (InputManager.instance.isKeyPressed(KeyCode.W)) {
            velocity.add(direction.mul(-1f))
        } else if (InputManager.instance.isKeyPressed(KeyCode.S)) {
            velocity.add(direction.mul(1f))
        }

        val projection = Vector3f(MainCamera.INSTANCE.transform.direction.x, 0f, MainCamera.INSTANCE.transform.direction.z)
        val perpendicular = projection[Vector3f()].cross(Vector3f(0f, 1f, 0f))

        if (InputManager.instance.isKeyPressed(KeyCode.A)) {
            velocity.add(perpendicular[Vector3f()].mul(1f))
        } else if (InputManager.instance.isKeyPressed(KeyCode.D)) {
            velocity.add(perpendicular[Vector3f()].mul(-1f))
        }

        MainCamera.INSTANCE.transform.translate(velocity.mul(camSpeed))
    }
}
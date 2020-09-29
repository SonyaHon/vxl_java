package me.sonyahon.game

import me.sonyahon.Reference
import me.sonyahon.engine.camera.Camera

class MainCamera(FOVy: Float, aspect: Float, nearPlane: Float, farPlane: Float) : Camera(FOVy, aspect, nearPlane, farPlane) {
    companion object {
        @JvmField
        val INSTANCE: MainCamera = MainCamera(Reference.FOVY, Reference.ASPECT, Reference.NEAR_PLANE, Reference.FAR_PLANE)
    }
}
package me.sonyahon.game

import org.joml.Vector3f

object Game {
    val ambientStrength = 0.2f
    val ambientColor = Vector3f(1f, 1f, 1f)
    val sunPosition = (Vector3f(0f, 0f, -80f)).rotateX(Math.toRadians(45.0).toFloat())

    public fun update() {
        val anglePerCycle = 0.2f;
        sunPosition.rotateX(Math.toRadians(anglePerCycle.toDouble()).toFloat());
    }
}
package me.sonyahon.engine.utils;

public class GameMath {
    public static float Map(float value, float inputMin, float inputMax, float outputMin, float outputMax) {
        return (value - inputMin) * (outputMax - outputMin) / (inputMax - inputMin) + outputMin;
    }
}

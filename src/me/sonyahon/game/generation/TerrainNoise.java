package me.sonyahon.game.generation;

import me.sonyahon.engine.utils.GameMath;
import me.sonyahon.engine.utils.OpenSimplexNoise;

public class TerrainNoise {
    public static final float BASE_NOISE_OFFSET = 100f;
    public static final float BASE_FREQUENCY = 0.01f;
    public static final float BASE_PERSISTENCE = 0.8f;
    public static final float PERSISTENCE_MULTIPLIER = 0.8f;
    public static final float FREQUENCY_MULTIPLAYER = 1.2F;
    public static final float POW = 1f;
    public static final float MIN_FLOOR_ELEVATION = 0f;
    public static final float MAX_FLOOR_ELEVATION = 10f;

    public static final int OCTAVES = 4;

    private final OpenSimplexNoise noise = new OpenSimplexNoise();

    public float getElevation(float x, float y, float xOffset, float yOffset) {
//        float elevation = (float) noise.eval(x, y); return elevation;

        float baseOffset = BASE_NOISE_OFFSET;
        float baseFrequency = BASE_FREQUENCY;
        float basePersistence = BASE_PERSISTENCE;
        float elevation = 0;

        for (int i = 0; i < OCTAVES; i++) {
            float xSample = baseOffset + x + xOffset;
            float ySample = baseOffset + y + yOffset;

            elevation += basePersistence * noise.eval(xSample * baseFrequency, ySample * baseFrequency);

            baseFrequency *= FREQUENCY_MULTIPLAYER;
            basePersistence *= PERSISTENCE_MULTIPLIER;
        }

        elevation = (float) Math.pow(elevation, POW);
        elevation = GameMath.Map(elevation, 0f, 1f, MIN_FLOOR_ELEVATION, MAX_FLOOR_ELEVATION);
        elevation = Math.round(elevation);

        return elevation;
    }
}

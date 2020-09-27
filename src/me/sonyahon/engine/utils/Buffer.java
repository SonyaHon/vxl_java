package me.sonyahon.engine.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffer {
    public static FloatBuffer bufferFromVector3FArray(Vector3f[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length * 3);
        float[] bufferData = new float[data.length * 3];

        int j = 0;
        for (Vector3f vector : data) {
            bufferData[j++] = vector.x;
            bufferData[j++] = vector.y;
            bufferData[j++] = vector.z;
        }

        buffer.put(bufferData);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer bufferFromIntArray(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer bufferFromFloatArray(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer bufferFromVector2fArray(Vector2f[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length * 2);
        float[] bufferData = new float[data.length * 2];

        int j = 0;
        for (Vector2f vector : data) {
            bufferData[j++] = vector.x;
            bufferData[j++] = vector.y;
        }

        buffer.put(bufferData);
        buffer.flip();
        return buffer;
    }
}

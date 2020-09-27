package me.sonyahon.engine.terrain;

import me.sonyahon.engine.d3.Mesh;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    public static int CHUNK_SIZE = 4;

    private Mesh mesh;

    public Chunk() {
        this.mesh = new Mesh();
    }

    public void generateMesh() {
        for (int y = 0; y < CHUNK_SIZE; y++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
            }
        }
    }
}

package me.sonyahon.engine.d3;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private List<Vector3f> vertices = new ArrayList<>();
    private List<Vector3f> colors = new ArrayList<>();
    private List<Vector3f> normals = new ArrayList<>();

    private int vaoId;

    public Mesh() {
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public void setColors(List<Vector3f> colors) {
        this.colors = colors;
    }

    public void setNormals(List<Vector3f> normals) {
        this.normals = normals;
    }

}

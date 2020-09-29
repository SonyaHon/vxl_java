package me.sonyahon.engine.d3;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private List<Vector3f> vertices = new ArrayList<>();
    private List<Vector3f> colors = new ArrayList<>();
    private List<Vector3f> normals = new ArrayList<>();
    private List<Integer> indices = new ArrayList<>();

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
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

    public StaticMeshData commit() {

        float[] vertices = new float[this.vertices.size() * 3];
        int i = 0;
        for (Vector3f vertex : this.vertices) {
            vertices[i++] = vertex.x;
            vertices[i++] = vertex.y;
            vertices[i++] = vertex.z;
        }

        float[] colors = new float[this.colors.size() * 3];
        i = 0;
        for (Vector3f color : this.colors) {
            colors[i++] = color.x;
            colors[i++] = color.y;
            colors[i++] = color.z;
        }


        float[] normals = new float[this.normals.size() * 3];
        i = 0;
        for (Vector3f normal : this.normals) {
            normals[i++] = normal.x;
            normals[i++] = normal.y;
            normals[i++] = normal.z;
        }

        int[] indices = new int[this.indices.size()];
        i = 0;
        for (int index : this.indices) {
            indices[i++] = index;
        }

        return MeshDataFactory.fromDataColors(vertices, colors, normals, indices);
    }
}

package me.sonyahon.engine.d3;

import me.sonyahon.engine.utils.Buffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class MeshDataFactory {

    private static final List<Integer> vaoIDs = new ArrayList<>();
    private static final List<Integer> vboIDs = new ArrayList<>();

    public static StaticMeshData fromDataUVs(float[] vertices, float[] uvs, float[] normals, int[] indices) {
        int vaoId = createVAO();
        bindVAO(vaoId);
        createIndicesVBO(indices);
        createVBO(Buffer.bufferFromFloatArray(vertices), 0, 3);
        createVBO(Buffer.bufferFromFloatArray(uvs), 1, 2);
        createVBO(Buffer.bufferFromFloatArray(normals), 2, 3);
        unbindVAO();

        return new StaticMeshData(vaoId, indices.length, List.of(0, 1, 2));
    }

    public static StaticMeshData fromDataColors(float[] vertices, float[] colors, float[] normals, int[] indices) {
        int vaoId = createVAO();
        bindVAO(vaoId);
        createIndicesVBO(indices);
        createVBO(Buffer.bufferFromFloatArray(vertices), 0, 3);
        createVBO(Buffer.bufferFromFloatArray(colors), 1, 3);
        createVBO(Buffer.bufferFromFloatArray(normals), 2, 3);
        unbindVAO();

        return new StaticMeshData(vaoId, indices.length, List.of(0, 1, 2));
    }

    public static void drop() {
        for (int vaoId : vaoIDs) {
            GL30.glDeleteVertexArrays(vaoId);
        }

        for (int vboId : vboIDs) {
            GL30.glDeleteBuffers(vboId);
        }
    }

    private static int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaoIDs.add(vaoID);
        return vaoID;
    }

    private static void bindVAO(int vaoID) {
        GL30.glBindVertexArray(vaoID);
    }

    private static void unbindVAO() {
        bindVAO(0);
    }

    private static void createIndicesVBO(int[] indices) {
        int indicesVBO = GL30.glGenBuffers();
        vboIDs.add(indicesVBO);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Buffer.bufferFromIntArray(indices), GL15.GL_STATIC_DRAW);
    }

    private static void createVBO(FloatBuffer buffer, int arrayPointer, int vectorSize) {
        int verticesVBO = GL30.glGenBuffers();
        vboIDs.add(verticesVBO);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesVBO);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(arrayPointer, vectorSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}

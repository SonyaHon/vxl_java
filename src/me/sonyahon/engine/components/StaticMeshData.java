package me.sonyahon.engine.components;

import java.util.List;

public class StaticMeshData {
    private final int vaoID;
    private final int vertexCount;
    private final List<Integer> usedAttribArrays;

    public StaticMeshData(int vaoID, int vertexCount, List<Integer> usedAttribArrays) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.usedAttribArrays = usedAttribArrays;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public List<Integer> getUsedAttribArrays() {
        return usedAttribArrays;
    }
}

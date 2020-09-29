package me.sonyahon.engine.resource.obj;

import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.MeshDataFactory;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {
    private static final String basePath = System.getProperty("user.dir") + "/src/resources/obj/";

    public static StaticMeshData load(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(new File(basePath + filePath + ".obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        BufferedReader reader = new BufferedReader(fileReader);

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> uvs = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] uvArray = null;
        int[] indicesArray = null;

        try {
            String line = null;
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])
                    );
                    vertices.add(vertex);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])
                    );
                    normals.add(normal);
                } else if (line.startsWith("vt ")) {
                    Vector2f uv = new Vector2f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2])
                    );
                    uvs.add(uv);
                } else if (line.startsWith("f ")) {
                    uvArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }

            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }

                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, uvs, normals, uvArray, normalsArray);
                processVertex(vertex2, indices, uvs, normals, uvArray, normalsArray);
                processVertex(vertex3, indices, uvs, normals, uvArray, normalsArray);

                line = reader.readLine();
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for(Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for(int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return MeshDataFactory.fromDataUVs(verticesArray, uvArray, normalsArray, indicesArray);
    }

    private static void processVertex(
            String[] vertexData,
            List<Integer> indices,
            List<Vector2f> uvs,
            List<Vector3f> normals,
            float[] uvsArray,
            float[] normalsArray
    ) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f uv = uvs.get(Integer.parseInt(vertexData[1]) - 1);
        uvsArray[currentVertexPointer * 2] = uv.x;
        uvsArray[currentVertexPointer * 2 + 1] = uv.y;
        Vector3f normal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = normal.x;
        normalsArray[currentVertexPointer * 3 + 1] = normal.y;
        normalsArray[currentVertexPointer * 3 + 2] = normal.z;
    }
}

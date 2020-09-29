package me.sonyahon.game.environment;

import me.sonyahon.engine.d3.Mesh;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.graphics.Material;
import org.joml.Vector3f;

import java.util.List;

public class DemoFloor extends Entity {
    public DemoFloor(Transform transform, Material material) {
        super(transform, null, material);

        Mesh mesh = new Mesh();

        List<Vector3f> verts = List.of(
                new Vector3f(-1, 0, -1),
                new Vector3f(1, 0, -1),
                new Vector3f(-1, 0, 1),
                new Vector3f(1, 0, 1)
        );
        List<Vector3f> normals = List.of(
                new Vector3f(0, 1, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(0, 1, 0)
        );
        List<Vector3f> colors = List.of(
                new Vector3f(1, 1, 1),
                new Vector3f(1, 1, 1),
                new Vector3f(1, 1, 1),
                new Vector3f(1, 1, 1)
        );

        List<Integer> indices = List.of( 0, 1, 2, 2, 1, 3 );

        mesh.setVertices(verts);
        mesh.setNormals(normals);
        mesh.setColors(colors);
        mesh.setIndices(indices);

        setMeshData(mesh.commit());
    }
}

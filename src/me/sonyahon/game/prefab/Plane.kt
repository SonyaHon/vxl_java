package me.sonyahon.game.prefab

import me.sonyahon.engine.d3.Mesh
import me.sonyahon.engine.d3.Transform
import me.sonyahon.engine.entity.Entity
import me.sonyahon.engine.graphics.Material
import me.sonyahon.engine.resource.shader.ShaderManager
import org.joml.Vector3f

class Plane(transform: Transform, color: Vector3f) : Entity(transform, null, Material(ShaderManager.instance.get("color"), null)) {
    init {
        val mesh = Mesh();

        val verts = listOf(
                Vector3f(-1f, 0f, -1f),
                Vector3f(1f, 0f, -1f),
                Vector3f(-1f, 0f, 1f),
                Vector3f(1f, 0f, 1f)
        )
        val normals = listOf(
                Vector3f(0f, 1f, 0f),
                Vector3f(0f, 1f, 0f),
                Vector3f(0f, 1f, 0f),
                Vector3f(0f, 1f, 0f)
        )
        val colors = listOf(
                color.get(Vector3f()),
                color.get(Vector3f()),
                color.get(Vector3f()),
                color.get(Vector3f())
        )

        val indices = listOf(0, 1, 2, 2, 1, 3)

        mesh.setVertices(verts)
        mesh.setNormals(normals)
        mesh.setColors(colors)
        mesh.setIndices(indices)

        meshData = mesh.commit()
    }
}
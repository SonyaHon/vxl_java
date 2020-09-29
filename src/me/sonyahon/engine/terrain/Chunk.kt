package me.sonyahon.engine.terrain

import me.sonyahon.engine.d3.Mesh
import me.sonyahon.engine.d3.StaticMeshData
import me.sonyahon.engine.d3.Transform
import me.sonyahon.engine.entity.Entity
import me.sonyahon.engine.graphics.Material
import me.sonyahon.engine.resource.shader.ShaderManager
import org.joml.Vector2f
import org.joml.Vector3f

class Chunk(coordinates: Vector2f) : Entity(null, null, Material(ShaderManager.instance.get("color"), null)) {

    val CHUNK_SIZE: Float = 16f

    init {
        val transform = Transform()
        transform.translate(Vector3f(coordinates.x * CHUNK_SIZE, 0f, coordinates.y * CHUNK_SIZE))
        this.transform = (transform)

        this.meshData = generateMesh()
    }

    private fun generateMesh(): StaticMeshData {
        val mesh = Mesh()

        val heightMap = generateHeightMap()

        val vertices = MutableList<Vector3f>(0) { Vector3f() }
        val normals = MutableList<Vector3f>(0) { Vector3f() }
        val indices = MutableList(0) { 0 }
        val colors = MutableList<Vector3f>(0) { Vector3f() }

        var currentIndex = 0
        for (y in IntRange(0, CHUNK_SIZE.toInt() - 1)) {
            for (x in IntRange(0, CHUNK_SIZE.toInt() - 1)) {
                vertices.add(Vector3f(x.toFloat(), heightMap[y][x], y.toFloat()))
                normals.add(Vector3f(0f, 1f, 0f))
                colors.add(Vector3f(.5f, .9f, 0f))

                if (y != CHUNK_SIZE.toInt() - 1 && x != CHUNK_SIZE.toInt() - 1) {

                    indices.add(currentIndex);
                    indices.add(currentIndex + CHUNK_SIZE.toInt() + 1);
                    indices.add(currentIndex + CHUNK_SIZE.toInt());

                    indices.add(currentIndex);
                    indices.add(currentIndex + 1);
                    indices.add(currentIndex + CHUNK_SIZE.toInt() + 1);

                }

                currentIndex++;
            }
        }



        mesh.setNormals(normals)
        mesh.setVertices(vertices)
        mesh.setIndices(indices)
        mesh.setColors(colors)
        return mesh.commit()
    }

    private fun generateHeightMap(): List<List<Float>> {
        return List(CHUNK_SIZE.toInt()) { List(CHUNK_SIZE.toInt()) { Math.random().toFloat() } }
    }
}
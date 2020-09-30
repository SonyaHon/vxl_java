package me.sonyahon.game.terrain

import me.sonyahon.engine.d3.Mesh
import me.sonyahon.engine.d3.StaticMeshData
import me.sonyahon.engine.d3.Transform
import me.sonyahon.engine.entity.Entity
import me.sonyahon.engine.graphics.Material
import me.sonyahon.engine.resource.shader.ShaderManager
import me.sonyahon.game.generation.TerrainNoise
import org.joml.Vector2f
import org.joml.Vector3f

class Chunk(private var coordinates: Vector2f) : Entity(null, null, Material(ShaderManager.instance.get("color"), null)) {

    val CHUNK_SIZE: Float = 64f

    init {
        val transform = Transform()
        transform.translate(Vector3f(coordinates.x * CHUNK_SIZE, 0f, coordinates.y * CHUNK_SIZE))
        this.transform = (transform)

        this.meshData = generateMesh()
    }

    private fun generateMesh(): StaticMeshData {

        val heightMap = generateHeightMap()

        val vertices = MutableList<Vector3f>(0) { Vector3f() }
        val normals = MutableList<Vector3f>(0) { Vector3f() }
        val indices = MutableList(0) { 0 }

        var lastQuadStartingPoint: Int = 0;

        for (y in IntRange(0, CHUNK_SIZE.toInt() - 1)) {
            for (x in IntRange(0, CHUNK_SIZE.toInt() - 1)) {

                val height = heightMap[y][x]
                val centerPoint = Vector3f(x.toFloat(), height, y.toFloat())

                vertices.add(centerPoint.get(Vector3f()).add(Vector3f(-0.5f, 0f, 0.5f)))
                vertices.add(centerPoint.get(Vector3f()).add(Vector3f(0.5f, 0f, 0.5f)))
                vertices.add(centerPoint.get(Vector3f()).add(Vector3f(-0.5f, 0f, -0.5f)))
                vertices.add(centerPoint.get(Vector3f()).add(Vector3f(0.5f, 0f, -0.5f)))

                normals.add(Vector3f(0f, 1f, 0f))
                normals.add(Vector3f(0f, 1f, 0f))
                normals.add(Vector3f(0f, 1f, 0f))
                normals.add(Vector3f(0f, 1f, 0f))

                indices.add(lastQuadStartingPoint)
                indices.add(lastQuadStartingPoint + 1)
                indices.add(lastQuadStartingPoint + 2)

                indices.add(lastQuadStartingPoint + 1)
                indices.add(lastQuadStartingPoint + 3)
                indices.add(lastQuadStartingPoint + 2)

                lastQuadStartingPoint += 4
            }
        }

        var vertexOffset = vertices.size

        for (y in IntRange(0, CHUNK_SIZE.toInt() - 1)) {
            for (x in IntRange(0, CHUNK_SIZE.toInt() - 1)) {
                var currentQuadHeight = heightMap[y][x]
                var currentQuadFirstPoint = (x + y * CHUNK_SIZE) * 4

                var currentQuad = Array<Vector3f>(4) { i -> vertices[(currentQuadFirstPoint + i).toInt()] }

                if (y != CHUNK_SIZE.toInt() - 1 && x != CHUNK_SIZE.toInt() - 1) {
                    var rightQuadHeight = heightMap[y][x + 1]
                    var rightQuadFirstPoint = (x + 1 + y * CHUNK_SIZE) * 4
                    var rightQuad = Array<Vector3f>(4) { i -> vertices[(rightQuadFirstPoint + i).toInt()] }

                    if (rightQuadHeight != currentQuadHeight) {
                        vertices.add(currentQuad[1])
                        vertices.add(currentQuad[3])
                        vertices.add(rightQuad[0])
                        vertices.add(rightQuad[2])

                        normals.add(Vector3f(-1f, 0f, 0f))
                        normals.add(Vector3f(-1f, 0f, 0f))
                        normals.add(Vector3f(-1f, 0f, 0f))
                        normals.add(Vector3f(-1f, 0f, 0f))

                        indices.add(vertexOffset)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 1)

                        indices.add(vertexOffset + 1)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 3)

                        vertexOffset += 4;
                    }

                    var bottomQuadHeight = heightMap[y + 1][x]
                    var bottomQuadFirstPoint = (x + (y + 1) * CHUNK_SIZE) * 4;

                    var bottomQuad = Array(4) { i -> vertices[(bottomQuadFirstPoint + i).toInt()] }

                    if (bottomQuadHeight != currentQuadHeight) {
                        vertices.add(currentQuad[0])
                        vertices.add(currentQuad[1])
                        vertices.add(bottomQuad[2])
                        vertices.add(bottomQuad[3])

                        normals.add(Vector3f(0f, 0f, 1f))
                        normals.add(Vector3f(0f, 0f, 1f))
                        normals.add(Vector3f(0f, 0f, 1f))
                        normals.add(Vector3f(0f, 0f, 1f))

                        indices.add(vertexOffset)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 1)

                        indices.add(vertexOffset + 1)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 3)

                        vertexOffset += 4
                    }
                } else if (y == CHUNK_SIZE.toInt() - 1 && x != CHUNK_SIZE.toInt() - 1) {
                    var rightQuadHeight = heightMap[y][x + 1]
                    var rightQuadFirstPoint = (x + 1 + y * CHUNK_SIZE) * 4;

                    var rightQuad = Array(4) { i -> vertices[(rightQuadFirstPoint + i).toInt()] }


                    if (rightQuadHeight != currentQuadHeight) {
                        vertices.add(currentQuad[1])
                        vertices.add(currentQuad[3])
                        vertices.add(rightQuad[0])
                        vertices.add(rightQuad[2])

                        normals.add(Vector3f(-1f, 0f, 0f))
                        normals.add(Vector3f(-1f, 0f, 0f))
                        normals.add(Vector3f(-1f, 0f, 0f))
                        normals.add(Vector3f(-1f, 0f, 0f))

                        indices.add(vertexOffset)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 1)

                        indices.add(vertexOffset + 1)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 3)

                        vertexOffset += 4
                    }
                } else if (y != CHUNK_SIZE.toInt() - 1 && x == CHUNK_SIZE.toInt() - 1) {
                    var bottomQuadHeight = heightMap[y + 1][x]
                    var bottomQuadFirstPoint = (x + (y + 1) * CHUNK_SIZE) * 4;

                    var bottomQuad = Array(4) { i -> vertices[(bottomQuadFirstPoint + i).toInt()] }

                    if (bottomQuadHeight != currentQuadHeight) {
                        vertices.add(currentQuad[0])
                        vertices.add(currentQuad[1])
                        vertices.add(bottomQuad[2])
                        vertices.add(bottomQuad[3])

                        normals.add(Vector3f(0f, 0f, 1f))
                        normals.add(Vector3f(0f, 0f, 1f))
                        normals.add(Vector3f(0f, 0f, 1f))
                        normals.add(Vector3f(0f, 0f, 1f))

                        indices.add(vertexOffset)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 1)

                        indices.add(vertexOffset + 1)
                        indices.add(vertexOffset + 2)
                        indices.add(vertexOffset + 3)

                        vertexOffset += 4
                    }
                }
            }
        }

        val colors = List(vertices.size) { Vector3f(0.5f, 0.9f, 0f) }


        val mesh = Mesh()
        mesh.setNormals(normals)
        mesh.setVertices(vertices)
        mesh.setIndices(indices)
        mesh.setColors(colors)
        return mesh.commit()
    }

    private fun generateHeightMap(): List<List<Float>> {
        val xOffset: Float = coordinates.x * CHUNK_SIZE;
        val yOffset: Float = coordinates.y * CHUNK_SIZE;

        val noise = TerrainNoise();

        return List(CHUNK_SIZE.toInt()) { y -> List(CHUNK_SIZE.toInt()) { x -> noise.getElevation(x.toFloat(), y.toFloat(), xOffset, yOffset) } }
    }
}
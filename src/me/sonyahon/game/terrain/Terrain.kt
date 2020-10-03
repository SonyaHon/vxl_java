package me.sonyahon.game.terrain

import me.sonyahon.engine.d3.Transform
import me.sonyahon.engine.entity.Entity
import me.sonyahon.game.generation.TerrainNoise
import org.joml.Vector2f

class Terrain : Entity(Transform(), null, null) {
    var loadedChunks: MutableList<Chunk> = mutableListOf()

    init {
        initialGenerateChunks()
        initializeChildren()
    }

    private fun initializeChildren() {
        for (chunk in loadedChunks) {
            addChild(chunk)
        }
    }

    private fun initialGenerateChunks() {
        for (x in IntRange(-1, 1)) {
            for (y in IntRange(-1, 1)) {
                loadedChunks.add(Chunk(Vector2f(x.toFloat(), y.toFloat()), noise = TerrainNoise()))
            }
        }
    }
}
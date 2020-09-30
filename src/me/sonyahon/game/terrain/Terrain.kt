package me.sonyahon.game.terrain

import me.sonyahon.engine.d3.Transform
import me.sonyahon.engine.entity.Entity
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
        loadedChunks.add(Chunk(Vector2f(0f, 0f)))
    }
}
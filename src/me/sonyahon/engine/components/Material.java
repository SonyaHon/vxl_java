package me.sonyahon.engine.components;

import me.sonyahon.engine.resource.shader.ShaderProgram;
import me.sonyahon.engine.resource.texture.Texture;

public class Material {
    private ShaderProgram shaderProgram;
    private Texture texture = null;

    public Material(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public Material(ShaderProgram shaderProgram, Texture texture) {
        this.shaderProgram = shaderProgram;
        this.texture = texture;
    }

    public boolean hasTexture() {
        return texture != null;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public Texture getTexture() {
        return texture;
    }
}

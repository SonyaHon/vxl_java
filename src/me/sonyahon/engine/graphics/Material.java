package me.sonyahon.engine.graphics;

import me.sonyahon.engine.resource.shader.ShaderProgram;
import me.sonyahon.engine.resource.texture.Texture;

public class Material {
    private ShaderProgram shaderProgram;
    private Texture texture = null;
    private boolean isLightBlocking = true;

    public Material(ShaderProgram shaderProgram, Texture texture, boolean isLightBlocking) {
        this.shaderProgram = shaderProgram;
        this.texture = texture;
        this.isLightBlocking = isLightBlocking;
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

    public boolean isLightBlocking() {
        return isLightBlocking;
    }

    public void setLightBlocking(boolean lightBlocking) {
        isLightBlocking = lightBlocking;
    }
}

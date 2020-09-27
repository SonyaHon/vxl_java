package me.sonyahon.engine.resource.shader;

import me.sonyahon.engine.resource.AbstractManager;
import me.sonyahon.engine.utils.ShaderTouple;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class ShaderManager extends AbstractManager<ShaderProgram, List<ShaderTouple>> {

    public static final ShaderManager instance = new ShaderManager();

    public ShaderManager() {
        basePath += "shaders/";
    }
    @Override
    public void load(List<ShaderTouple> shaders, String shaderName) {
        ShaderProgram program = new ShaderProgram(basePath, shaders);
        items.put(shaderName, program);
    }

    @Override
    public void drop() {
        for(ShaderProgram program : items.values()) {
            program.drop();
        }
    }

    @Override
    public void unbindCurrent() {
        GL20.glUseProgram(0);
    }
}

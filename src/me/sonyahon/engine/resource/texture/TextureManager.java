package me.sonyahon.engine.resource.texture;

import me.sonyahon.engine.resource.AbstractManager;
import org.lwjgl.opengl.GL15;

public class TextureManager extends AbstractManager<Texture, String> {
    public static final TextureManager instance = new TextureManager();

    public TextureManager() {
        basePath += "img/";
    }

    @Override
    public void load(String filePath, String name) {
        Texture texture = new Texture(basePath, filePath);
        items.put(name, texture);
    }

    @Override
    public void drop() {
        for (Texture texture : items.values()) {
            texture.drop();
        }
    }

    @Override
    public void unbindCurrent() {
        GL15.glBindTexture(GL15.GL_TEXTURE_2D, 0);
    }

    public Texture getMGVoxel() {
        return get("mgvoxel");
    }
}

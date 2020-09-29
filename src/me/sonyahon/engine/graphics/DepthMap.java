package me.sonyahon.engine.graphics;

import me.sonyahon.game.Game;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class DepthMap {
    public static DepthMap INSTANCE = null;


    private int frameBuffer;
    private int texSize;
    private int texId;
    private Matrix4f projectionMatrix;

    public DepthMap() {
        frameBuffer = GL30.glGenFramebuffers();
        texSize = 1024;
        texId = GL20.glGenTextures();
        projectionMatrix = new Matrix4f().ortho(-100f, 100f, -100f, 100f, 0.1f, 100f);

        GL20.glBindTexture(GL20.GL_TEXTURE_2D, texId);
        GL20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, GL20.GL_DEPTH_COMPONENT, texSize, texSize, 0, GL20.GL_DEPTH_COMPONENT, GL20.GL_FLOAT, 0);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
        GL20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL20.GL_TEXTURE_2D, texId, 0);
        GL30.glDrawBuffer(GL30.GL_NONE);
        GL30.glReadBuffer(GL30.GL_NONE);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public static DepthMap getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new DepthMap();
        }

        return INSTANCE;
    }

    public void setViewport() {
        GL20.glViewport(0, 0, texSize, texSize);
    }

    public Matrix4f getLightSpaceMatrix() {
        Matrix4f viewMatrix = new Matrix4f().lookAt(Game.INSTANCE.getSunPosition(),
                new Vector3f(),
                new Vector3f(0f, 1f, 0f));

        return projectionMatrix.get(new Matrix4f()).mul(viewMatrix);
    }


    public int getFrameBuffer() {
        return frameBuffer;
    }

    public int getTextureId() {
       return texId;
    }
}

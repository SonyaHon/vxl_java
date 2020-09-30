package me.sonyahon.engine.render;

import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.display.DisplayManager;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.graphics.DepthMap;
import me.sonyahon.engine.graphics.Material;
import me.sonyahon.engine.resource.shader.ShaderManager;
import me.sonyahon.engine.resource.shader.ShaderProgram;
import me.sonyahon.game.Game;
import me.sonyahon.game.MainCamera;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class Renderer {

    public static void render(Entity entity) {
        if (entity.getMaterial() != null && entity.getTransform() != null && entity.getMeshData() != null) {
            render(entity.getMeshData(), entity.getMaterial(), entity.getTransform());
        }

        for (Entity child : entity.getChildren()) {
            render(child, entity.getTransform());
        }
    }

    public static void render(Entity entity, Transform parentTransform) {
        if (entity.getMaterial() != null && entity.getTransform() != null && entity.getMeshData() != null) {
            render(entity.getMeshData(), entity.getMaterial(), Transform.withParent(entity.getTransform(), parentTransform));
        }

        for (Entity child : entity.getChildren()) {
            render(child, entity.getTransform());
        }
    }

    private static void render(StaticMeshData meshData, Material material, Transform transform) {
        // render to depth map
        if (material.isLightBlocking()) {
            DepthMap.getInstance().setViewport();
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, DepthMap.getInstance().getFrameBuffer());
            GL20.glClear(GL11.GL_DEPTH_BUFFER_BIT);

            // render
            ShaderProgram depthProgram = ShaderManager.instance.get("depth");
            depthProgram.bind();
            depthProgram.addMatrix4fUniform("lightSpaceMatrix", DepthMap.getInstance().getLightSpaceMatrix());
            depthProgram.addMatrix4fUniform("transformMatrix", transform.getTransformMatrix());

            GL30.glBindVertexArray(meshData.getVaoID());
            enableVertexAttribArrays(List.of(0));
            GL11.glDrawElements(GL11.GL_TRIANGLES, meshData.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            disableVertexAttribArrays(List.of(0));
            GL30.glBindVertexArray(0);

            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
            DisplayManager.setDefaultViewport();
        }


        ShaderProgram program = material.getShaderProgram();
        program.bind();
        program.addMatrix4fUniform("transformMatrix", transform.getTransformMatrix());
        program.addMatrix4fUniform("projectionMatrix", MainCamera.INSTANCE.getProjectionMatrix());
        program.addMatrix4fUniform("viewMatrix", MainCamera.INSTANCE.getTransform().getViewMatrix());

        program.addFloatUniform("ambientStrength", Game.INSTANCE.getAmbientStrength());
        program.addVector3fUniform("lightColor", Game.INSTANCE.getLightColor());
        program.addVector3fUniform("lightPos", Game.INSTANCE.getSunPosition());
        program.addVector3fUniform("viewPos", MainCamera.INSTANCE.getTransform().getPosition());

        program.addFloatUniform("specStrength", material.getSpecStrength());
        program.addFloatUniform("specSharpness", material.getSpecSharpness());

        GL30.glBindVertexArray(meshData.getVaoID());
        enableVertexAttribArrays(meshData.getUsedAttribArrays());


        if (material.hasTexture()) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            material.getTexture().bind();

            GL13.glActiveTexture(GL13.GL_TEXTURE0 + 1);
            GL20.glBindTexture(GL20.GL_TEXTURE_2D, DepthMap.getInstance().getTextureId());

        } else {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL20.glBindTexture(GL20.GL_TEXTURE_2D, DepthMap.getInstance().getTextureId());
        }

        GL11.glDrawElements(GL11.GL_TRIANGLES, meshData.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);


        if (material.hasTexture()) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            material.getTexture().unbind();
        }

        disableVertexAttribArrays(meshData.getUsedAttribArrays());
        GL30.glBindVertexArray(0);

        program.unbind();

    }

    private static void enableVertexAttribArrays(List<Integer> arrays) {
        for (int array : arrays) {
            GL20.glEnableVertexAttribArray(array);
        }
    }

    private static void disableVertexAttribArrays(List<Integer> arrays) {
        for (int array : arrays) {
            GL20.glDisableVertexAttribArray(array);
        }
    }
}

package me.sonyahon.engine.render;

import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.graphics.Material;
import me.sonyahon.engine.resource.shader.ShaderProgram;
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
        ShaderProgram program = material.getShaderProgram();
        program.bind();
        program.addMatrix4fUniform("tmat", transform.getTransformMatrix());
        program.addMatrix4fUniform("pmat", MainCamera.INSTANCE.getProjectionMatrix());
        program.addMatrix4fUniform("vmat", MainCamera.INSTANCE.getTransform().getViewMatrix());

        GL30.glBindVertexArray(meshData.getVaoID());
        enableVertexAttribArrays(meshData.getUsedAttribArrays());


        if (material.hasTexture()) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            material.getTexture().bind();
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
        for(int array : arrays) {
            GL20.glEnableVertexAttribArray(array);
        }
    }

    private static void disableVertexAttribArrays(List<Integer> arrays) {
        for(int array : arrays) {
            GL20.glDisableVertexAttribArray(array);
        }
    }
}

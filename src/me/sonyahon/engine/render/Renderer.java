package me.sonyahon.engine.render;

import me.sonyahon.engine.components.Camera;
import me.sonyahon.engine.components.StaticMeshData;
import me.sonyahon.engine.components.Transform;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.errors.DoesNotHaveRequiredComponents;
import me.sonyahon.engine.resource.shader.ShaderProgram;
import me.sonyahon.engine.components.Material;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

public class Renderer {
//    public void render(StaticMeshData meshData) {
//        GL30.glBindVertexArray(meshData.getVaoID());
//        enableVertexAttribArrays(meshData.getUsedAttribArrays());
//
//        GL11.glDrawElements(GL11.GL_TRIANGLES, meshData.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//
//        disableVertexAttribArrays(meshData.getUsedAttribArrays());
//        GL30.glBindVertexArray(0);
//    }
//

    private Entity camera;

    public Renderer(Entity camera) throws DoesNotHaveRequiredComponents {
        if(!camera.hasComponents(List.of(Transform.class, Camera.class))) {
            throw new DoesNotHaveRequiredComponents();
        }
        this.camera = camera;
    }


    public void render(StaticMeshData meshData, Material material, Transform transform) {
        ShaderProgram program = material.getShaderProgram();
        program.bind();
        program.addMatrix4fUniform("tmat", transform.getTransformMatrix());
        program.addMatrix4fUniform("pmat", camera.<Camera>getComponent(Camera.class).getProjectionMatrix());
        program.addMatrix4fUniform("vmat", camera.<Transform>getComponent(Transform.class).getViewMatrix());

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

    public void render(Entity entity) {
        if (! entity.hasComponents(List.of(StaticMeshData.class, Transform.class, Material.class))) {
            return;
        }
        Material material = entity.getComponent(Material.class);
        StaticMeshData meshData = entity.getComponent(StaticMeshData.class);
        Transform transform = entity.getComponent(Transform.class);

        render(meshData, material, transform);

        for(Entity child : entity.getChildren()) {
            render(child);
        }
    }

    private void enableVertexAttribArrays(List<Integer> arrays) {
        for(int array : arrays) {
            GL20.glEnableVertexAttribArray(array);
        }
    }

    private void disableVertexAttribArrays(List<Integer> arrays) {
        for(int array : arrays) {
            GL20.glDisableVertexAttribArray(array);
        }
    }
}

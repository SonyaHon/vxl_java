package me.sonyahon;

import me.sonyahon.engine.d3.MeshDataFactory;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.display.DisplayManager;
import me.sonyahon.engine.render.Renderer;
import me.sonyahon.engine.resource.shader.ShaderManager;
import me.sonyahon.engine.resource.texture.TextureManager;
import me.sonyahon.engine.terrain.Terrain;
import me.sonyahon.engine.utils.ShaderTouple;
import me.sonyahon.game.Game;
import me.sonyahon.game.MainCamera;
import me.sonyahon.game.Player;
import me.sonyahon.game.prefab.Plane;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class Main {
    private void loop() {
        ShaderManager.instance.load(List.of(
                new ShaderTouple(GL20.GL_VERTEX_SHADER, "plain-white/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "plain-white/fragment")
        ), "plain-white");

        ShaderManager.instance.load(List.of(
                new ShaderTouple(GL20.GL_VERTEX_SHADER, "textured/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "textured/fragment")
        ), "textured");

        ShaderManager.instance.load(List.of(
                new ShaderTouple(GL20.GL_VERTEX_SHADER, "color/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "color/fragment")
        ), "color");

        ShaderManager.instance.load(List.of(
                new ShaderTouple(GL20.GL_VERTEX_SHADER, "depth/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "depth/fragment")
        ), "depth");

        TextureManager.instance.load("test", "test");
        TextureManager.instance.load("mgvoxel", "mgvoxel");

        Player player = new Player(new Transform(), null, null);

        MainCamera.INSTANCE.getTransform().translateY(5f);

        Terrain terrain = new Terrain();


        while (!DisplayManager.shouldMainWindowClose()) {
            DisplayManager.clearDisplay();

            // Logic
            Game.INSTANCE.update();

            player.update();

            // Depth map baking

            // Rendering
            Renderer.render(terrain);

            DisplayManager.updateDisplay();
            int glError = GL20.glGetError();
            if (glError != 0) {
                System.out.println("GL Error: " + glError);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        DisplayManager.createDisplay();
        main.loop();
        MeshDataFactory.drop();
        DisplayManager.clearDisplay();
    }

}
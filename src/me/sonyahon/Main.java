package me.sonyahon;

import me.sonyahon.engine.d3.MeshDataFactory;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.display.DisplayManager;
import me.sonyahon.engine.errors.DoesNotHaveRequiredComponents;
import me.sonyahon.engine.graphics.Material;
import me.sonyahon.engine.render.Renderer;
import me.sonyahon.engine.resource.shader.ShaderManager;
import me.sonyahon.engine.resource.texture.TextureManager;
import me.sonyahon.engine.utils.ShaderTouple;
import me.sonyahon.game.Player;
import me.sonyahon.game.environment.DemoFloor;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.CallbackI;

import java.sql.Ref;
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


        TextureManager.instance.load("test", "test");
        TextureManager.instance.load("mgvoxel", "mgvoxel");

        Transform playerTransform = new Transform();
        playerTransform.translate(0, 0, 5);
//        playerTransform.rotate(-90, 0, 0);
        Player player = new Player(playerTransform, null, null);

        Transform floorTransform = new Transform();
        floorTransform.translateY(-1);
        DemoFloor floor = new DemoFloor(floorTransform, new Material(ShaderManager.instance.get("color"), null));

        while (!DisplayManager.shouldMainWindowClose()) {
            DisplayManager.clearDisplay();

            Renderer.render(floor);

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
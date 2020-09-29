package me.sonyahon;

import me.sonyahon.engine.d3.MeshDataFactory;
import me.sonyahon.engine.d3.StaticMeshData;
import me.sonyahon.engine.d3.Transform;
import me.sonyahon.engine.display.DisplayManager;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.graphics.Material;
import me.sonyahon.engine.render.Renderer;
import me.sonyahon.engine.resource.obj.OBJLoader;
import me.sonyahon.engine.resource.shader.ShaderManager;
import me.sonyahon.engine.resource.texture.TextureManager;
import me.sonyahon.engine.utils.ShaderTouple;
import me.sonyahon.game.Player;
import org.lwjgl.opengl.GL20;

import java.util.List;

public class Main {
    private void loop() {
        ShaderManager.instance.load(List.of(new ShaderTouple(GL20.GL_VERTEX_SHADER, "plain-white/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "plain-white/fragment")), "plain-white");
        ShaderManager.instance.load(List.of(new ShaderTouple(GL20.GL_VERTEX_SHADER, "textured/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "textured/fragment")), "textured");
        ShaderManager.instance.load(List.of(new ShaderTouple(GL20.GL_VERTEX_SHADER, "color/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "color/fragment")), "color");

        TextureManager.instance.load("test", "test");
        TextureManager.instance.load("mgvoxel", "mgvoxel");

        Transform playerTransform = new Transform();
//        playerTransform.translate(0, 0, 5);
        Player player = new Player(playerTransform, null, null);

//        Transform floorTransform = new Transform();
//        floorTransform.setScale(new Vector3f(100, 1, 100));
//        floorTransform.translate(80, -2, -50);
//        DemoFloor floor = new DemoFloor(floorTransform, new Material(ShaderManager.instance.get("color"), null));

        StaticMeshData meshData = OBJLoader.load("tester");
        Transform transform = new Transform();
        transform.setScaleUniform(.2f);
//        transform.translateZ(-10);
        Entity tester = new Entity(transform, meshData, new Material(ShaderManager.instance.get("textured"), TextureManager.instance.getMGVoxel()));

//        MainCamera.instance.getTransform().rotateX(45);

        while (!DisplayManager.shouldMainWindowClose()) {
            DisplayManager.clearDisplay();

            player.update();

            Renderer.render(tester);

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
package me.sonyahon;

import me.sonyahon.engine.components.*;
import me.sonyahon.engine.d3.MeshDataFactory;
import me.sonyahon.engine.display.DisplayManager;
import me.sonyahon.engine.entity.Entity;
import me.sonyahon.engine.errors.DoesNotHaveRequiredComponents;
import me.sonyahon.engine.input.InputManager;
import me.sonyahon.engine.input.KeyCode;
import me.sonyahon.engine.resource.obj.OBJLoader;
import me.sonyahon.engine.resource.shader.ShaderManager;
import me.sonyahon.engine.resource.texture.Texture;
import me.sonyahon.engine.resource.texture.TextureManager;
import me.sonyahon.engine.render.Renderer;
import me.sonyahon.engine.systems.CameraFreeController;
import me.sonyahon.engine.terrain.Terrain;
import me.sonyahon.engine.utils.ShaderTouple;
import me.sonyahon.engine.world.World;
import org.lwjgl.opengl.GL20;

import java.sql.Ref;
import java.util.List;

public class Main {
    private void loop() throws DoesNotHaveRequiredComponents {

        ShaderManager.instance.load(List.of(
                new ShaderTouple(GL20.GL_VERTEX_SHADER, "plain-white/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "plain-white/fragment")
        ), "plain-white");
        ShaderManager.instance.load(List.of(
                new ShaderTouple(GL20.GL_VERTEX_SHADER, "textured/vertex"),
                new ShaderTouple(GL20.GL_FRAGMENT_SHADER, "textured/fragment")
        ), "textured");


        TextureManager.instance.load("test", "test");
        TextureManager.instance.load("mgvoxel", "mgvoxel");


        Entity mainCamera = new Entity();
        mainCamera.addComponent(new Camera(
                45,
                (float) Reference.DISPLAY_WIDTH / (float) Reference.DISPLAY_HEIGHT,
                0.001f,
                1000.0f
        ));
        mainCamera.addComponent(new Transform());

        Entity test = new Entity();

        Material mat = new Material(ShaderManager.instance.get("textured"), TextureManager.instance.getMGVoxel());
        Transform trams = new Transform();
        trams.translateZ(-1);
        trams.setScaleUniform(2f);
        StaticMeshData meshData = OBJLoader.load("tester");
        test.addComponent(trams);
        test.addComponent(mat);
        test.addComponent(meshData);


        Terrain terrain = new Terrain();

        Renderer mainRenderer = new Renderer(mainCamera);

        World.instance.setMainCamera(mainCamera);

        CameraFreeController cameraFreeController = new CameraFreeController();



        while (!DisplayManager.shouldMainWindowClose()) {
            DisplayManager.clearDisplay();

            cameraFreeController.update();

            mainRenderer.render(terrain);

            DisplayManager.updateDisplay();
            int glError = GL20.glGetError();
            if (glError != 0) {
                System.out.println("GL Error: " + glError);
            }
        }
    }

    public static void main(String[] args) throws DoesNotHaveRequiredComponents {
        Main main = new Main();
        DisplayManager.createDisplay();
        main.loop();
        MeshDataFactory.drop();
        DisplayManager.clearDisplay();
    }

}
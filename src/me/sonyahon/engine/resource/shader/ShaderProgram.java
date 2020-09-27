package me.sonyahon.engine.resource.shader;

import me.sonyahon.engine.utils.ShaderTouple;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ShaderProgram {
    private final int programID;
    private final List<Integer> shaders = new ArrayList<>();
    private static FloatBuffer mat4fBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String basePath, List<ShaderTouple> shaderTouples) {
        programID = GL20.glCreateProgram();

        for (ShaderTouple shaderTouple : shaderTouples) {
            int shaderId = loadShader(basePath + shaderTouple.filePath + ".glsl", shaderTouple.shaderType);
            shaders.add(shaderId);

            GL20.glAttachShader(programID, shaderId);
        }

        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        for (int shaderId : shaders) {
            GL20.glDetachShader(programID, shaderId);
        }
    }

    private static int loadShader(String filePath, int shaderType) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }

    public void bind()  {
        GL20.glUseProgram(programID);
    }

    public void unbind()  {
        GL20.glUseProgram(0);
    }
    public void drop() {
        for (int shader : shaders) {
            GL20.glDeleteShader(shader);
        }
        GL20.glDeleteProgram(programID);
    }

    private int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void addFloatUniform(String uniformName, float value) {
        int location = getUniformLocation(uniformName);
        GL20.glUniform1f(location, value);
    }

    public void addBoolUniform(String uniformName, boolean value) {
        int location = getUniformLocation(uniformName);
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    public void addVector2fUniform(String uniformName, Vector2f value) {
        int location = getUniformLocation(uniformName);
        GL20.glUniform2f(location, value.x, value.y);
    }

    public void addVector3fUniform(String uniformName, Vector3f value) {
        int location = getUniformLocation(uniformName);
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    public void addMatrix4fUniform(String uniformName, Matrix4f value) {
        int location = getUniformLocation(uniformName);
        GL20.glUniformMatrix4fv(location, false, value.get(mat4fBuffer));
    }
}

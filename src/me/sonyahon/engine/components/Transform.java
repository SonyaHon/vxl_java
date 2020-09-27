package me.sonyahon.engine.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
    protected Vector3f position;
    protected Vector3f rotation;
    protected Vector3f scaleVector;
    protected Vector3f direction;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale, Vector3f direction) {
        this.position = position;
        this.rotation = rotation;
        this.scaleVector = scale;
        this.direction = direction;
    }

    public Transform() {
        this.position = new Vector3f(0f, 0f, 0f);
        this.rotation = new Vector3f(0f, 0f, 0f);
        this.scaleVector = new Vector3f(1f, 1f, 1f);
        this.direction = new Vector3f(0, 0, -1);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scaleVector;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3f scale) {
        this.scaleVector = scale;
    }

    public void setScaleUniform(float scale) {
        this.setScale(new Vector3f(scale, scale, scale));
    }

    public void translateVector(Vector3f vector) {
        this.position.add(vector);
    }

    public void translate(float x, float y, float z) {
        this.position.add(new Vector3f(x, y, z));
    }

    public void translateX(float x) {
        this.translate(x, 0f, 0f);
    }

    public void translateY(float y) {
        this.translate(0, y, 0);
    }

    public void translateZ(float z) {
        this.translate(0, 0, z);
    }

    public void rotateVector(Vector3f vector) {
        this.rotation.add(vector);
    }

    public void rotate(float x, float y, float z) {
        this.rotation.add(new Vector3f(x, y, z));
    }

    public void rotateX(float x) {
        this.rotate(x, 0f, 0f);
    }

    public void rotateY(float y) {
        this.rotate(0, y, 0);
    }

    public void rotateZ(float z) {
        this.rotate(0, 0, z);
    }

    public void scaleVector(Vector3f vector) {
        this.scaleVector.mul(vector);
    }

    public void scale(float x, float y, float z) {
        this.scaleVector.mul(new Vector3f(x, y, z));
    }

    public void scaleX(float x) {
        this.scale(x, 1f, 1f);
    }

    public void scaleY(float y) {
        this.scale(1, y, 1);
    }

    public void scaleZ(float z) {
        this.scale(1, 1, z);
    }

    public Matrix4f getTransformMatrix() {
        Matrix4f transformMat = new Matrix4f();
        transformMat.translate(position);
        transformMat.rotate((float) Math.toRadians(rotation.x), new Vector3f(1f, 0f, 0f));
        transformMat.rotate((float) Math.toRadians(rotation.y), new Vector3f(0f, 1f, 0f));
        transformMat.rotate((float) Math.toRadians(rotation.z), new Vector3f(0f, 0f, 1f));
        transformMat.scale(scaleVector);
        return transformMat;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotate( (float) rotation.x, new Vector3f(1f, 0f, 0f));
        viewMatrix.rotate( (float) rotation.y, new Vector3f(0f, 1f, 0f));
        viewMatrix.rotate( (float) rotation.z, new Vector3f(0f, 0f, 1f));
        viewMatrix.translate(position.get(new Vector3f()).mul(-1));
        return viewMatrix;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
}

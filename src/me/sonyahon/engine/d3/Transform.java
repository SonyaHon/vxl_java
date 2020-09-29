package me.sonyahon.engine.d3;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scaleVector;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scaleVector = scale;

    }

    public Transform() {
        this.position = new Vector3f(0f, 0f, 0f);
        this.rotation = new Vector3f(0f, 0f, 0f);
        this.scaleVector = new Vector3f(1f, 1f, 1f);
    }

    public static Transform withParent(Transform transform, Transform parentTransform) {
        Transform t = new Transform(transform.position, transform.rotation, transform.scaleVector);

        t.setPosition(t.getPosition().get(new Vector3f()).add(parentTransform.getPosition()));
        t.setRotation(t.getRotation().get(new Vector3f()).add(parentTransform.getRotation()));
        t.setScale(t.getScale().get(new Vector3f()).mul(parentTransform.getScale()));

        return t;
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

    public Vector3f getDirection() {
        Vector3f baseDirection = new Vector3f(0, 0, -1);
        baseDirection.rotateX((float) Math.toRadians(this.rotation.x));
        baseDirection.rotateY((float) Math.toRadians(this.rotation.y));
        baseDirection.rotateZ((float) Math.toRadians(this.rotation.z));
        return baseDirection;
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


    public void translate(Vector3f vector) {
        position.add(vector);
    }

    public void translate(float x, float y, float z) {
        translate(new Vector3f(x, y, z));
    }

    public void translateY(float value) {
        translate(0, value, 0);
    }

    public void translateZ(float value) {
        translate(0, 0, value);
    }

    public void rotate(Vector3f vector) {
        rotation.add(vector);
    }

    public void rotate(float x, float y, float z) {
        rotate(new Vector3f(x, y, z));
    }

    public void rotateX(float value) {
        rotate(value, 0, 0);
    }

    public void rotateY(float value) {
        rotate(0, value, 0);
    }
}

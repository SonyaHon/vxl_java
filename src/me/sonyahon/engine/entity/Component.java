package me.sonyahon.engine.entity;

import java.lang.reflect.Type;

public class Component {
    private Object realComponent;
    private Type realType;

    public Component(Object realComponent, Type realType) {
        this.realComponent = realComponent;
        this.realType = realType;
    }

    public Object getRealComponent() {
        return realComponent;
    }

    public Type getRealType() {
        return realType;
    }
}

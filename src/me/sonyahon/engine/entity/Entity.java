package me.sonyahon.engine.entity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private final List<Component> components = new ArrayList<>();
    private final List<Entity> children = new ArrayList<>();
    private String tag = "";

    public Entity() {
    }

    public Entity(String tag) {
        this.tag = tag;
    }

    public <T> void addComponent(T component) {
        components.add(new Component(component, component.getClass()));
    }

    public void removeComponent(Type componentType) {
        for (int i = 0, componentsSize = components.size(); i < componentsSize; i++) {
            Component component = components.get(i);
            if (component.getRealType() == componentType) {
                components.remove(i);
                break;
            }
        }
    }

    public <T> T getComponent(Type componentType) {
        for (Component component : components) {
            if (component.getRealType() == componentType) {
                return (T) component.getRealComponent();
            }
        }
        return null;
    }

    public boolean hasComponents(List<Type> types) {
        for (Type type : types) {
            if (!hasComponent(type)) {
                return false;
            }
        }
        return true;
    }

    public <T> boolean hasComponent(Type componentType) {
        for (Component component : components) {
            if (component.getRealType() == componentType) {
                return true;
            }
        }
        return false;
    }

    public void addChild(Entity e) {
        children.add(e);
    }

    public void removeChild(int index) {
        children.remove(index) ;
    }

    public void removeChild(String tag) {
        for (int i = 0; i < children.size(); i++) {
            Entity e = children.get(i);
            if(e.tag.equals(tag)) {
                children.remove(i);
                break;
            }
        }
    }

    public void clearChildren() {
        children.clear();
    }

    public Entity getChild(int index) {
        return children.get(index);
    }

    public Entity getChild(String tag) {
        for (Entity child : children) {
            if (child.tag.equals(tag)) {
                return child;
            }
        }
        return null;
    }

    public List<Entity> getChildren() {
        return children;
    }


}

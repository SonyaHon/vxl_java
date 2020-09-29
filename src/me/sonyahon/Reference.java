package me.sonyahon;

import org.joml.Vector3f;

public class Reference {
    public static CharSequence WINDOW_TITLE = "VXL";
    public static int DISPLAY_WIDTH = 1280;
    public static int DISPLAY_HEIGHT = 720;
    public static boolean VSYNC = true;
    public static Vector3f CLEAR_COLOR = new Vector3f(.2f, .2f, .2f);


    public static final float FOVY = (float) Math.toRadians(45f);
    public static final float ASPECT = (float)DISPLAY_WIDTH / (float)DISPLAY_HEIGHT ;
    public static final float NEAR_PLANE = 0.01f;
    public static final float FAR_PLANE = 1000.0f;
}

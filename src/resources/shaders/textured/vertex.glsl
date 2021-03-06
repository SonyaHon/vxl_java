#version 330 core

layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec2 uvPos;

uniform mat4 tmat;
uniform mat4 pmat;
uniform mat4 vmat;

out vec2 uv;

void main() {
    gl_Position = pmat * vmat * tmat * vec4(vertexPos, 1.0);
    uv = uvPos;
}

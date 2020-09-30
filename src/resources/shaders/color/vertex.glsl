#version 330 core

layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec3 colors;
layout (location = 2) in vec3 normals;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec3 fragPos;
out vec3 fragNormal;
out vec3 fragColor;


void main() {
    fragPos = vec3(transformMatrix * vec4(vertexPos, 1.0));
    fragNormal = transpose(inverse(mat3(transformMatrix))) * normals;
    fragColor = colors;

    gl_Position = projectionMatrix * viewMatrix * vec4(fragPos, 1.0);
}

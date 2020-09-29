#version 330 core

layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec3 colors;
layout (location = 2) in vec3 normals;

uniform mat4 transformMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 lightSpaceMatrix;


out VS_OUT {
    vec3 fragPos;
    vec3 normal;
    vec3 color;
    vec4 fragPosLightSpace;
} vs_out;

void main() {

    vs_out.fragPos = vec3(transformMatrix * vec4(vertexPos, 1.0));
    vs_out.normal = transpose(inverse(mat3(transformMatrix))) * normals;
    vs_out.color = colors;
    vs_out.fragPosLightSpace = lightSpaceMatrix * vec4(vs_out.fragPos, 1.0);

    gl_Position = projectionMatrix * viewMatrix * vec4(vs_out.fragPos, 1.0);
}

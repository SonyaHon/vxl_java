#version 330 core

layout (location = 0) in vec3 vertexPos;
layout (location = 1) in vec3 colors;
layout (location = 2) in vec3 normals;

uniform mat4 transform_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

out vec3 color;
out vec3 normal;
out vec3 frag_pos;

void main() {

    vec4 world_pos = transform_matrix * vec4(vertexPos, 1.0);

    gl_Position = projection_matrix * view_matrix * world_pos;

    color = colors;
    normal = normals;
    frag_pos = world_pos.xyz;
}

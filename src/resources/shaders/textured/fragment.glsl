#version 330 core

in vec2 uv;

uniform sampler2D textureSampler;

out vec4 color_out;

void main() {
    color_out = texture(textureSampler, uv);
}

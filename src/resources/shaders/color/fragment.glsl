#version 330 core

in vec3 color;
in vec3 normal;
in vec3 frag_pos;

uniform float ambient_strength;
uniform vec3 ambient_color;
uniform vec3 sun_position;

out vec4 color_out;

void main() {

    vec3 ambient = ambient_color * ambient_strength;

    vec3 normal_normalized = normalize(normal);
    vec3 light_direction = normalize(sun_position - frag_pos);
    float diff = max(dot(normal_normalized, light_direction), 0.0);
    vec3 diffuse = diff * ambient_color;

    vec3 result = color * (ambient + diffuse);
    color_out = vec4(result, 1.0);
}

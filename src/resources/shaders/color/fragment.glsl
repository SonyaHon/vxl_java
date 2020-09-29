#version 330 core

in VS_OUT {
    vec3 fragPos;
    vec3 normal;
    vec3 color;
    vec4 fragPosLightSpace;
} fs_in;

uniform sampler2D shadowMap;

uniform float ambientStrength;
uniform vec3 ambientColor;

uniform vec3 lightPos;

out vec4 fragColor;

float ShadowCalculation(vec4 fragPosLightSpace, vec3 normal, vec3 lightDir) {
    vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    projCoords = projCoords * 0.5 + 0.5;
    float closestDepth = texture(shadowMap, projCoords.xy).r;
    float currentDepth = projCoords.z;
    float bias = max(0.009 * (1.0 - dot(normal, lightDir)), 0.005);
    float shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;
    return shadow;
}

void main() {
    vec3 color = fs_in.color;
    vec3 normal = normalize(fs_in.normal);
    vec3 lightColor = ambientColor;

    // ambient part
    vec3 ambient = ambientStrength * ambientColor;

    // diffuse part
    vec3 lightDir = normalize(lightPos - fs_in.fragPos);
    float diff = max(dot(lightDir, normal), 0.0);
    vec3 diffuse = diff * lightColor;

    // shadow
    float shadow = ShadowCalculation(fs_in.fragPosLightSpace, normal, lightDir);

    fragColor = vec4(
    (ambient + (1.0 - shadow) * diffuse) * color
    , 1.0);
}

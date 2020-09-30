#version 330 core

in vec3 fragColor;
in vec3 fragPos;
in vec3 fragNormal;
in float zPosition;

uniform float ambientStrength;
uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 viewPos;
uniform float specStrength;
uniform float specSharpness;
uniform float sunlightStrength;

out vec4 outColor;

void main() {

    //ambient
    vec3 ambient = ambientStrength * lightColor;

    //diffuse
    vec3 norm = normalize(fragNormal);
    vec3 lightDir = normalize(lightPos - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor * sunlightStrength;

    //specular
    vec3 viewDir = normalize(viewPos - fragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), specSharpness);
    vec3 specular = specStrength * spec * lightColor;

    vec3 result = (ambient + diffuse + specular) * fragColor;
    outColor = vec4(result, 1.0);
}

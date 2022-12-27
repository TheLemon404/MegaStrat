#version 400 core

layout(location = 0) in vec3 a_position;
layout(location = 1) in vec2 a_uv;

out vec2 uv;

void main() {
    gl_Position = vec4(a_position, 1);
    uv = a_uv;
}

#split

#version 400 core

in vec2 uv;

uniform vec3 u_lightDirection;
uniform sampler2D u_position;
uniform sampler2D u_normal;
uniform sampler2D u_color;

layout(location = 0) out vec4 o_color;

void main() {
    vec3 position = texture(u_position, uv).rgb;
    vec3 normal = texture(u_normal, uv).rgb;
    vec3 color = texture(u_color, uv).rgb;

    float diffuse = max(dot(normal, -u_lightDirection), 0.3);

    o_color = vec4(color, 1) * diffuse;
}
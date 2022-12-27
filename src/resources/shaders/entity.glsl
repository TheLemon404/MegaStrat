#version 400 core

layout(location = 0) in vec3 a_position;
layout(location = 1) in vec2 a_uv;
layout(location = 2) in vec3 a_normal;

out vec3 normal;
out vec4 position;
out vec3 color;

uniform vec3 u_color;
uniform mat4 u_transform;
uniform mat4 u_view;
uniform mat4 u_projection;

void main() {
    vec4 world = u_transform * vec4(a_position, 1);
    gl_Position = u_projection * u_view * world;
    position = world;
    normal = (u_transform * vec4(a_normal, 0)).xyz;
    color = u_color;
}

#split

#version 400 core

in vec3 normal;
in vec4 position;
in vec3 color;

layout(location = 0) out vec4 o_color;
layout(location = 1) out vec4 o_normal;
layout(location = 2) out vec4 o_position;

void main() {
    o_color = vec4(color,1);
    o_normal = vec4(normal, 1);
    o_position = position;
}
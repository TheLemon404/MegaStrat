#version 400 core

layout(location = 0) in vec3 a_position;
layout(location = 1) in vec2 a_uv;
layout(location = 2) in vec3 a_normal;
layout(location = 3) in vec3 a_color;

uniform mat4 u_projection;
uniform mat4 u_rect;

out vec2 uv;

void main() {
    vec4 world = u_rect * vec4(a_position, 1);
    gl_Position = u_projection * world;
}

#split

#version 400 core

in vec2 uv;

uniform vec3 u_color;
uniform sampler2D tex;

layout(location = 0) out vec4 o_color;

void main() {
    o_color = vec4(u_color, 1) * texture(tex, uv);
}
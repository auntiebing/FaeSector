#version 110

uniform sampler2D sprite;
uniform sampler2D alphaMask;

vec2 coord1 = gl_TexCoord[0].xy;

void main() {

    vec4 col1 = texture2D(sprite, coord1);
    vec4 col2 = texture2D(alphaMask, coord1);

    gl_FragColor = vec4(col1.r, col1.g, col1.b, col1.a);
}
uniform float width;
uniform float height;
uniform float t;
uniform sampler2D texture;

#include "../lygia/generative/cnoise.glsl"

void main() {
    vec2 u_resolution = vec2(width, height);
    
    // Normalized pixel coordinates (from 0 to 1)
    vec4 color = vec4(0., 0., 0., 1.);
    vec2 uv = gl_FragCoord.xy / u_resolution;
    
    float noise3d = cnoise(vec3(uv * 8., t)) * 0.5 + 0.5;
    
    color.rgb = texture2D(texture, uv).rgb;
    color.a -= noise3d * 4.;
    
    gl_FragColor = color;
}
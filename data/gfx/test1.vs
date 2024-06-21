#ifdef GL_ES
precision mediump float;
#endif

#include "lygia/generative/voronoi.glsl"

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec4 color = vec4(0, 0, 0, 1);
    vec2 uv = fragCoord.xy/iResolution.xy;

    vec3 noise3d = voronoi(vec3(uv * 5., iTime));
    
    color.rb += noise3d.r;
    
    fragColor = color;
}


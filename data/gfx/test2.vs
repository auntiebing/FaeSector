#ifdef GL_ES
precision mediump float;
#endif

#include "lygia/generative/voronoi.glsl"
#include "lygia/distort/barrel.glsl"
#include "lygia/draw/rect.glsl"

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec4 color = vec4(0, 0, 0, 1);
    vec2 uv = fragCoord.xy/iResolution.xy;

    uv = barrel(uv, 8.);

    uv *= 4.;

    uv = fract(uv);

    color.rgb = vec3(uv, 1).rgb;

    fragColor = color;
}


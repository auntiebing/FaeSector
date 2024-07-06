uniform float width;
uniform float height;
uniform float t;

void main() {
    vec2 u_resolution = vec2(width, height);
    
    // Normalized pixel coordinates (from 0 to 1), adjusted for -1 to 1 range
    vec2 uv = (gl_FragCoord.xy - 0.5 * u_resolution.xy) / u_resolution.y;
    
    // Simulate refraction for a glass ball effect
    float radius = 13.0; // Radius of the glass ball
    vec2 center = vec2(0.0, 0.0); // Center of the glass ball
    float dist = distance(uv, center); // Distance from the center
    
    // Refraction effect
    if (dist < radius) {
        float refractionIndex = 1.02; // Refraction index; adjust for stronger/weaker refraction
        vec2 refraction = normalize(uv - center) * (1.0 - refractionIndex) * (radius - dist);
        uv += refraction;
    }
    
    float time = t * 0.5;
    
    mat4 rotationXY = mat4(cos(time), -sin(time), 0.0, 0.0,
        sin(time), cos(time), 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0);
    
    mat4 rotationZW = mat4(1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, cos(time), -sin(time),
        0.0, 0.0, sin(time), cos(time));
    
    gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
    
    // Mirror reflection effect near the sides
    float mirrorWidth = 0.35; // Width of the mirror effect from the edges
    float edgeDistX = min(uv.x + 1.0, 1.0 - uv.x); // Distance from the nearest horizontal edge
    if (edgeDistX < mirrorWidth) {
        // Reflect the uv coordinates horizontally based on proximity to the edge
        uv.x = mix(-uv.x, uv.x, edgeDistX / mirrorWidth);
    }
    
    for (int i = 0; i < 16; i++) {
        for (int j = i + 1; j < 16; j++) {
            // Simplified vertex calculation for demonstration
            vec4 point1 = vec4(float(i % 4 - 2), float((i / 4) % 4 - 2), float((i / 16) % 4 - 2), float(i / 64 - 1));
            vec4 point2 = vec4(float(j % 4 - 2), float((j / 4) % 4 - 2), float((j / 16) % 4 - 2), float(j / 64 - 1));
            
            point1 = rotationXY * point1;
            point1 = rotationZW * point1;
            point2 = rotationXY * point2;
            point2 = rotationZW * point2;
            
            vec3 p3D1 = point1.xyz / (13.0 - point1.w);
            vec3 p3D2 = point2.xyz / (13.0 - point2.w);
            
            vec2 p2D1 = p3D1.xy / (1.0 - p3D1.z);
            vec2 p2D2 = p3D2.xy / (1.0 - p3D2.z);
            
            // Line drawing logic (simplified)
            vec2 line = p2D2 - p2D1;
            vec2 perpLine = vec2(-line.y, line.x);
            float dist = abs(dot(perpLine, uv - p2D1)) / length(perpLine);
            if(dist < 0.0005) {
                gl_FragColor = vec4(uv, 1.5 + 1.5 * sin(time), 1.0);
            }
        }
    }
}
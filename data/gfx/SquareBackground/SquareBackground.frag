uniform float width;
uniform float height;
uniform float t;

struct Ray {
    vec3 origin;
    vec3 direction;
};

struct BoundingBox {
    vec3 center;
    vec3 bounds;
    float edge;
};

float distToBoundingBox(Ray ray, BoundingBox bb) {
    vec3 p = abs(ray.origin - bb.center) - bb.bounds;
    vec3 q = abs(p + bb.edge) - bb.edge;
    return min(min(
            length(max(vec3(p.x,q.y,q.z),0.0))+min(max(p.x,max(q.y,q.z)),0.0),
            length(max(vec3(q.x,p.y,q.z),0.0))+min(max(q.x,max(p.y,q.z)),0.0)),
        length(max(vec3(q.x,q.y,p.z),0.0))+min(max(q.x,max(q.y,p.z)),0.0));
}

float distToBoundingBoxScene(Ray r) {
    BoundingBox bb = BoundingBox(vec3(1.0), vec3(1.0), 0.05);
    Ray repeatRay = r;
    repeatRay.origin = mod(r.origin, 2.0);
    return distToBoundingBox(repeatRay, bb);
}

void main() {
    vec2 u_resolution = vec2(width, height);
    
    vec2 uv = (gl_FragCoord - 0.5 * u_resolution.xy) / u_resolution.y;
    vec3 camPos = vec3(1000.0 + (sin(t) / 2.0) + 1.0, 1000.0 + (cos(t) / 2.0) + 1.0, t);
    Ray ray = Ray(camPos, normalize(vec3(uv, 1.0)));
    vec3 col = vec3(0.0);
    for (float i=0.0; i<100.0; i++) {
        float dist = distToBoundingBoxScene(ray);
        if (dist < 0.01) {
            col = vec3(1.0);
            break;
        }
        ray.origin += ray.direction * dist;
    }
    vec3 posRelativeToCamera = ray.origin - camPos;
    gl_FragColor = vec4(1.0) - vec4(col * abs(posRelativeToCamera / 5.0), 0.0);
}
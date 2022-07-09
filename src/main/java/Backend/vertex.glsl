#version 430 core +

layout (location = 0) in vec2 pos;

out vec2 texCords;

uniform vec4 camera;
uniform int objectId;
flat out float depth;

struct Obs{
    vec2 pos;
    vec2 scaling;
    float depth;
    float someValue;
    vec2 padding;
};

layout(binding = 1) buffer objectBuffer{
    Obs objects[];
} ObjectBuffer;

void main() {
    texCords = vec2(max(0, pos.x), max(0, pos.y));
    gl_Position = vec4(((pos.x * ObjectBuffer.objects[objectId].scaling.x - camera.x + ObjectBuffer.objects[objectId].pos.x) * camera.z) / camera.w, ((pos.y * ObjectBuffer.objects[objectId].scaling.y- camera.y + ObjectBuffer.objects[objectId].pos.y)) / camera.w, 0.0f, 1.0f);
    depth = ObjectBuffer.objects[objectId].depth;
}



#version 430 core

in vec2 texCords;

flat in float depth;
uniform sampler2D TEX_SAMPLER;
uniform image2D delphi;
out vec4 color;

void main(){
    vec4 c = texture(TEX_SAMPLER, texCords);
    if(gl_FragCoord.z < imageLoad(delphi, gl_FragCoord.xy)){
        discard;
    }
    color = c;
    imageStore(delphi, glFragCoord.xy, imageStore(delphi, gl_FragCoord.xy, gl_FragCoord.z));
}



#version 430 core

layout (location = 0) in vec2 pos;

out vec2 texCords;

uniform vec4 camera;

flat out float depth;

flat out float textureIndex;

struct Obs{
    vec2 pos;
    vec2 scaling;
    float depth;
    float textureIndex;
    vec2 padding;
};

layout(binding = 1) buffer objectBuffer{
    Obs objects[];
} ObjectBuffer;

void main() {
    texCords = vec2(max(0, pos.x), max(0, pos.y));
    gl_Position = vec4(((pos.x * ObjectBuffer.objects[gl_InstanceID].scaling.x - camera.x + ObjectBuffer.objects[gl_InstanceID].pos.x) * camera.z) / camera.w, ((pos.y * ObjectBuffer.objects[gl_InstanceID].scaling.y- camera.y + ObjectBuffer.objects[gl_InstanceID].pos.y)) / camera.w, 0.0f, 1.0f);
    depth = ObjectBuffer.objects[gl_InstanceID].depth;
    textureIndex = ObjectBuffer.objects[gl_InstanceID].textureIndex;
}



#version 430 core

in vec2 texCords;

flat in float depth;

flat in float textureIndex;

out vec4 color;

void main(){
    color = vec4(1.0f, 0.0f, 0.0f, 1.0f);
}



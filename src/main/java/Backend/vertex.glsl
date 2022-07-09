#version 430 core

layout (location = 0) in vec2 pos;

out vec2 texCords;

uniform vec4 camera;
uniform int objectId;
flat out float depth;

struct Obs{
    vec2 pos;
    vec2 scaling;
    float depth;
    float rotation;
    vec2 padding;
};

layout(binding = 1) buffer objectBuffer{
    Obs objects[];
} ObjectBuffer;

void main() {
    texCords = pos;
    //gl_Position = vec4(((pos.x * ObjectBuffer.objects[objectId].scaling.x - camera.x + ObjectBuffer.objects[objectId].pos.x) * camera.z) / camera.w, ((pos.y * ObjectBuffer.objects[objectId].scaling.y- camera.y + ObjectBuffer.objects[objectId].pos.y)) / camera.w, ObjectBuffer.objects[objectId].depth, 1.0f);
    vec2 mpos = pos;

    //Rotate
    mpos.x = (mpos.x * cos(ObjectBuffer.objects[objectId].rotation) - mpos.y * sin(ObjectBuffer.objects[objectId].rotation));
    mpos.y = (mpos.y * cos(ObjectBuffer.objects[objectId].rotation) + mpos.x * sin(ObjectBuffer.objects[objectId].rotation));

    //Scale
    mpos = mpos * ObjectBuffer.objects[objectId].scaling;

    //Transform with worldspace coordinates and camera coordinates
    mpos = mpos + ObjectBuffer.objects[objectId].pos - camera.xy;

    //Scale with width/height ratio
    mpos.x = mpos.x * camera.z;

    //Transform by camera distance
    mpos = mpos / camera.w;

    gl_Position = vec4(mpos, depth, 1.0f);
    texCords = vec2(max(0, texCords.x), max(0, texCords.y));
    depth = ObjectBuffer.objects[objectId].depth;
}



#version 430 core

in vec2 texCords;

flat in float depth;
uniform sampler2D TEX_SAMPLER;

out vec4 color;

void main(){
    vec4 c = texture(TEX_SAMPLER, texCords);
    color = c;
}



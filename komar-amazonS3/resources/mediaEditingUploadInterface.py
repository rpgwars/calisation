import os
import sys
import moviepyInterface
import botoInterface

def getVideoClip(filePath, start, end, withAudio, bucketName, profileName):
    videoClipFile = ""
    try:
        videoClipFile = moviepyInterface.makeVideoClip(filePath, start, end, withAudio)
        key = filePath.split('/')[-1:]
        clipLink = botoInterface.putClip(profileName, bucketName, videoClipFile, key)
        print clipLink
        return clipLink
    finally:
        os.remove(filePath)
        os.remove(videoClipFile)
    

def getAudioClip(filePath, start, end, bucketName, profileName):
    audioClipFile = ""
    try:
        audioClipFile = moviepyInterface.makeAudioClip(filePath, start, end)
        key = filePath.split('/')[-1:]
        clipLink = botoInterface.putClip(profileName, bucketName, audioClipFile, key)
        print clipLink
        return clipLink
    finally:
        os.remove(filePath)
        os.remove(audioClipFile)

if len(sys.argv) == 6:
    getAudioClip(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5])
else:
    getVideoClip(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4] in ['true'], sys.argv[5], sys.argv[6])

#getVideoClip("MySample", 30, 40, True, "komar-sandbox")
#getAudioClip("MyAudioSample", 30, 40, "komar-sandbox")
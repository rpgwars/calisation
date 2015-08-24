from moviepy.editor import *
import ntpath

def makeVideoClip(filePath, start, end, withAudio):
    originalVideo = VideoFileClip(filePath)
    if(originalVideo.duration < float(end) or float(start) < 0):
        raise Exception('Wrong clip interval')
    if(float(start) > float(end)):
        raise Exception('Wrong clip interval')
    video = originalVideo.subclip(float(start), float(end))
    clipFile = ntpath.dirname(filePath) + "/clip_" + ntpath.basename(filePath)
    video.write_videofile(clipFile, audio=withAudio) # Many options...
    return clipFile

def makeAudioClip(filePath, start, end):
    originalAudio = AudioFileClip(filePath)
    if(originalAudio.duration < float(end) or float(start) < 0):
        raise Exception('Wrong clip interval')
    if(float(start) > float(end)):
        raise Exception('Wrong clip interval')
    audio = originalAudio.subclip(float(start), float(end))
    clipFile = ntpath.dirname(filePath) + "/clip_" + ntpath.basename(filePath)
    audio.write_audiofile(clipFile)
    return clipFile

def concatenateClips(audioFiles, videoFiles):
   
    clips = []
    videoIndex = 0;
    for audioFile in audioFiles:
        if(audioFile == ""):
            clips.append(VideoFileClip(videoFiles[videoIndex]))
        else:
            clips.append(VideoFileClip(videoFiles[videoIndex]).set_audio(AudioFileClip(audioFile)))
        videoIndex = videoIndex + 1
        
    video = concatenate_videoclips(clips, method="compose")
    video.write_videofile("full.mp4", fps=24)
    
#makeVideoClip("xxx",30,40,False)
#makeAudioClip("/home/damian/Videos/wilderness.mp3",30,40)
#concatenateClips(["a.mp3",""],["v.mp4","small.ogv"])


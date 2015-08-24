package com.komar.domain.resource.transfer;

public class Edit {

    private Float start;
    private Float end;
    private boolean withAudio;

    public Float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public Float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public boolean isWithAudio() {
        return withAudio;
    }

    public void setWithAudio(boolean withAudio) {
        this.withAudio = withAudio;
    }
}

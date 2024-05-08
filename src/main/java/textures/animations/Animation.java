package textures.animations;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    private int frameCount;
    private final int frameDelay;
    private int currentFrame;
    private final int animationDirection;
    private final int totalFrames;
    private boolean looping;
    private boolean stopped;
    private final List<Frame> frames = new ArrayList<>();

    public Animation(BufferedImage[] frames, int animationSpeed, boolean looping) {
        this.frameDelay = animationSpeed;
        this.looping = looping;
        this.stopped = true;

        for (BufferedImage frame : frames) {
            addFrame(frame, frameDelay);
        }

        this.frameCount = 0;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();
    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.isEmpty()) {
            return;
        }

        stopped = false;
    }

    public void stop() {
        if (frames.isEmpty()) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.isEmpty()) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    public void update() {
        if (!stopped) {
            frameCount++;

            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (looping && currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                } else if (looping && currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                } else if (!looping && currentFrame > totalFrames - 1) {
                    currentFrame = totalFrames -1;
                    stop();
                } else if (!looping && currentFrame < 0) {
                    currentFrame = 0;
                    stop();
                }
            }
        }
    }

    public boolean isAnimationFinished() {
        return currentFrame >= totalFrames - 1;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}

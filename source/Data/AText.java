package Data;

import timer.stopWatchX;

public class AText {
    // Fields
    private String srcStr;
    private String destStr;
    private stopWatchX timer;
    private boolean isFinished;
    private int cursor;

    public AText(String srcStr, int delay) {
        this.srcStr = srcStr;
        timer = new stopWatchX(delay);
        destStr = "";
        isFinished = false;
        cursor = 0;
    }

    public String getCurrentStr() {
        if(isFinished) {
            return destStr;
        }
        if(timer.isTimeUp()) {
            if(cursor < srcStr.length()) {
                destStr += srcStr.charAt(cursor++);
            }
            if(cursor >= srcStr.length()) {
                isFinished = true;
            }
            timer.resetWatch();
        }
        return destStr;
    }

    public boolean isAnimationDone() {
        return isFinished;
    }

    public void resetAnimation() {
        isFinished = false;
        destStr = "";
        cursor = 0;
        timer.resetWatch();
    }

    public void setSrcString(String input) {
        srcStr = input;
    }
}

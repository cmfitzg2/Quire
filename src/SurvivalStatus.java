package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.*;
import java.io.File;

public class SurvivalStatus {

    public static int maxDarknessLevel = Assets.tunnelVision.length - 1;
    public static int darknessLevel = 0;
    public static int currentTemperature = 5;
    public static int currentTime = 3;
    public static float currentFear = 0;
    public static long heartbeatThreshold = 1500000000L;
    public static long heartbeatTimer;
    private long maxTimerReduction = (long) (heartbeatThreshold / 1.8);
    private int maxVolume = 0;
    private int minVolume = -20;
    private float volume = minVolume;
    private float maxFear = 100;
    private Clip heartbeat1, heartbeat2, heartbeat3, heartbeat4;
    private FloatControl control1, control2, control3, control4;
    private Handler handler;
    private KeyManager keyManager;
    private Player player;
    private int switcher = 0;

    public SurvivalStatus(Handler handler) throws Exception {
        this.handler = handler;
        heartbeatInit();
    }

    public void tick() {
        keyManager = handler.getKeyManager();
        if (keyManager.one) {
            if (!keyManager.isStillHolding1()) {
                keyManager.setStillHolding1(true);
                currentTime += 1;
                if (currentTime >= 7) {
                    currentTime = 0;
                }
            }
        }

        if (keyManager.two) {
            if (!keyManager.isStillHolding2()) {
                keyManager.setStillHolding2(true);
                currentTemperature += 1;
                if (currentTemperature >= 12) {
                    currentTemperature = 0;
                }
            }
        }
        if (handler.getFlags().isVisionLimited()) {
            calculateFear();
            playHeartbeat();
        }
    }

    public void render(Graphics graphics) {
        //graphics.drawImage(Assets.currentTime[currentTime], handler.getWidth() / 2 - 48, 0, 96, 96, null);
    }

    private void calculateFear() {
        if (darknessLevel >= 10) {
            //System.out.println("Adding " + (float) darknessLevel / 1000);
            currentFear += (float) darknessLevel / 2000;
            if (currentFear >= 100) {
                currentFear = 100;
            }
        } else if (currentFear > 0) {
            //System.out.println("Subtracting " + (float) (10 - darknessLevel) / 100);
            currentFear -= (float) (10 - darknessLevel) / 200;
            if (currentFear < 0) {
                currentFear = 0;
            }
        }
    }

    private void playHeartbeat() {
        scaleVolume();
        if (System.nanoTime() - heartbeatTimer + calculateHeartbeatScale() >= heartbeatThreshold) {
            if (switcher == 0) {
                heartbeat1.setFramePosition(0);
                heartbeat1.start();
            } else if (switcher == 1) {
                heartbeat2.setFramePosition(0);
                heartbeat2.start();
            } else if (switcher == 2) {
                heartbeat3.setFramePosition(0);
                heartbeat3.start();
            } else {
                heartbeat4.setFramePosition(0);
                heartbeat4.start();
            }
            switcher = switcherNextState(switcher);
            heartbeatTimer = System.nanoTime();
        }
    }

    private float calculateHeartbeatScale() {
        if (currentFear == 0) {
            return 0;
        } else if (currentFear >= maxFear) {
            return maxTimerReduction;
        }
        //https://www.wolframalpha.com/input/?i=y+%3D+0.5+%2F+%281+%2B+e%5E%28-.09+*+%28x+-+50%29%29%29+from+x+%3D+0+to+x+%3D+100%2C+y+%3D+0+to+y+%3D+0.6
        float result = (0.5f / (1 + (float) Math.exp(-0.09f * (currentFear - 50)))) * (maxTimerReduction / .5f);
        return maxTimerReduction > result ? result : maxTimerReduction;
    }

    private void scaleVolume() {
        if (currentFear == maxFear) {
            volume = maxVolume;
        } else if (currentFear == 0) {
            volume = minVolume;
        } else {
            //https://www.wolframalpha.com/input/?i=y+%3D+20+%2F+%281+%2B+e%5E%28-.10*+%28x+-+45%29%29%29+-+20+from+x+%3D+0+to+x+%3D+100%2C+y+%3D+-25+to+y+%3D+0
            volume = (20 / (1 + (float) Math.exp(-0.10f* (currentFear - 45))) - 20);
        }
        control1.setValue(volume);
        control2.setValue(volume);
        control3.setValue(volume);
        control4.setValue(volume);
    }

    private int switcherNextState(int switcher) {
        if (switcher == 0) {
            return 1;
        } else if (switcher == 1) {
            return 2;
        } else if (switcher == 2) {
            return 3;
        }
        return 0;
    }

    private void heartbeatInit() throws Exception {
        //multiple clips necessary for seamless play?
        //TODO: find a less stupid way to do this

        float initialVolume = -20f;
        heartbeat1 = AudioSystem.getClip();
        AudioInputStream ais1 = AudioSystem.getAudioInputStream(new File("res/sounds/heartbeat.au"));
        heartbeat1.open(ais1);
        control1 = (FloatControl) heartbeat1.getControl(FloatControl.Type.MASTER_GAIN);
        control1.setValue(initialVolume);

        heartbeat2 = AudioSystem.getClip();
        AudioInputStream ais2 = AudioSystem.getAudioInputStream(new File("res/sounds/heartbeat.au"));
        heartbeat2.open(ais2);
        control2 = (FloatControl) heartbeat2.getControl(FloatControl.Type.MASTER_GAIN);
        control2.setValue(initialVolume);

        heartbeat3 = AudioSystem.getClip();
        AudioInputStream ais3 = AudioSystem.getAudioInputStream(new File("res/sounds/heartbeat.au"));
        heartbeat3.open(ais3);
        control3 = (FloatControl) heartbeat3.getControl(FloatControl.Type.MASTER_GAIN);
        control3.setValue(initialVolume);

        heartbeat4 = AudioSystem.getClip();
        AudioInputStream ais4 = AudioSystem.getAudioInputStream(new File("res/sounds/heartbeat.au"));
        heartbeat4.open(ais4);
        control4 = (FloatControl) heartbeat4.getControl(FloatControl.Type.MASTER_GAIN);
        control4.setValue(initialVolume);
    }

    public float getCurrentFear() {
        return currentFear;
    }

    public float getVolume() {
        return volume;
    }
}

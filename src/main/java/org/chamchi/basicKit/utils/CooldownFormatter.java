package org.chamchi.basicKit.utils;

import org.chamchi.basicKit.modules.Cooldown;

public class CooldownFormatter {
    private Cooldown cooldown;
    private String message;

    public CooldownFormatter(Cooldown cooldown) {
        this.cooldown = cooldown;
    }

    public String format() {
        return message.replace("%cooldown%", longToTime(cooldown.getRemain()));
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String longToTime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d분 %d초", minutes, seconds);
    }
}

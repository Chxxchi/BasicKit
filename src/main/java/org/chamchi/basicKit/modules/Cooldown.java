package org.chamchi.basicKit.modules;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.AnimalTamer;


import java.util.Calendar;

public class Cooldown {
    private int coolTime;
    private OfflinePlayer owner;
    private Calendar startTime, expirTime;

    public Cooldown(OfflinePlayer owner, int coolTime) {
        this.owner = owner;
        this.coolTime = coolTime;

        startTime = Calendar.getInstance();
        expirTime = Calendar.getInstance();
        expirTime.add(Calendar.MINUTE, 30);
    }

    public boolean isExpired() {
        Calendar current = Calendar.getInstance();
        return (expirTime.before(current));
    }

    public long getRemain() {
        if (isExpired()) return 0;
        Calendar current = Calendar.getInstance();
        return expirTime.getTimeInMillis() - current.getTimeInMillis();
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getExpirTime() {
        return expirTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public void setExpirTime(Calendar expirTime) {
        this.expirTime = expirTime;
    }
}

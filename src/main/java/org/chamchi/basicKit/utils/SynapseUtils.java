package org.chamchi.basicKit.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SynapseUtils {

    private static final String PREFIX = "&6&l[알림] &f";

    public static String color(String content) {
        return content.replace("&", "§");
    }

    public static void send(Audience player, String content) {
        player.sendMessage(Component.text(color(content)));
    }

    public static void info(Audience player, String content) {
        send(player, PREFIX + content);
    }

    public static String comma(int value) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(value);
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (player == null || sound == null) return; // 안전 검사
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static boolean containsAllItems(Inventory inventory, ArrayList<ItemStack> itemList) {
        ItemStack[] contents = inventory.getContents().clone();

        for (ItemStack requiredItem : itemList) {
            if (requiredItem == null) continue;
            int amount = requiredItem.getAmount(); // 요구수량 ex 10개

            for (int i = 0; i<contents.length; i++) { // 인벤토리 루프
                ItemStack currentItem = contents[i];
                if (currentItem == null) continue;

                if (currentItem.isSimilar(requiredItem)) {
                    if (currentItem.getAmount() >= amount) { // 현재 아이템 수량이 더 많으면
                        amount = 0;
                    } else {
                        amount = amount - currentItem.getAmount();
                        contents[i] = null;
                    }
                }
            }
            if (amount >= 1) return false;
        }
        return true;
    }

    public static void removeItems(Inventory inventory, ItemStack item, int amount) {
        if (amount <= 0)
            return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is != null &&
                    item.isSimilar(is)) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                }
                inventory.clear(slot);
                amount = -newAmount;
                if (amount == 0)
                    break;
            }
        }
    }

    public static boolean hasEnoughSpace(Inventory inventory, ItemStack itemStack) {
        if (itemStack == null) return true;
        List<ItemStack> contents = Arrays.stream(inventory.getStorageContents()).toList();

        for(int i = 0; i<contents.size(); i++) {
            ItemStack slot = contents.get(i);
            if (slot == null || slot.getType() == Material.AIR) return true;

            if (slot.isSimilar(itemStack)) {
                if (slot.getAmount() < slot.getMaxStackSize()) return true;
            }
        }
        return false;
    }

    public static Calendar stringToCalendar(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul")); // KST가 인식되도록 설정

        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(string);  // 문자열 → Date 변환
            calendar.setTime(date);         // Date → Calendar 변환
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }
}

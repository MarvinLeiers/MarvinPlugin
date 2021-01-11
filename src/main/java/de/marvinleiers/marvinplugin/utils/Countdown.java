package de.marvinleiers.marvinplugin.utils;

import de.marvinleiers.marvinplugin.MarvinPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Countdown
{
    private final List<Player> players;
    private final String message;
    private final int startValue;
    private final int[] specialTimes;
    private int taskId;

    public Countdown(List<Player> players, String message, int startValue, int... specialTimes)
    {
        if (!message.contains("%s"))
            throw new IllegalArgumentException("String message must contain %s placeholder");

        this.players = players;
        this.message = message;
        this.startValue = startValue;
        this.specialTimes = specialTimes;
    }

    public Countdown(Collection<? extends Player> players, String message, int startValue, int... specialTimes)
    {
        if (!message.contains("%s"))
            throw new IllegalArgumentException("String message must contain %s placeholder");

        this.players = new ArrayList<>(players);
        this.message = message;
        this.startValue = startValue;
        this.specialTimes = specialTimes;
    }

    public void startCountdown()
    {
        AtomicInteger currentSeconds = new AtomicInteger(startValue);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(MarvinPlugin.getInstance(), () -> {

            if (isSpecial(currentSeconds.get()))
            {
                for (Player player : players)
                {
                    player.sendMessage(message.replace("%s", currentSeconds.get() + ""));
                }
            }

            if (currentSeconds.get() <= 0)
            {
                Bukkit.getScheduler().cancelTask(taskId);
                return;
            }

            currentSeconds.addAndGet(-1);
        }, 0, 20);
    }

    private boolean isSpecial(int current)
    {
        for (int i : specialTimes)
        {
            if (i == current)
                return true;
        }

        return false;
    }
}
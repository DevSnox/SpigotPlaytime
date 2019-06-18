package me.devsnox.playtime.commands;

import me.devsnox.playtime.playtime.TimeManager;
import me.devsnox.playtime.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

/**
 * Copyright by DevSnox
 * E-Mail: me.devsnox@gmail.com
 * Skype: DevSnox
 */
public class PlayTimeCommand implements CommandExecutor {

    private TimeManager timeManager;

    public PlayTimeCommand(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Messages.ERROR_ONLY_PLAYERS.asString());
                return true;
            }

            if (sender.hasPermission("spigotplaytime.command.playtime")) {
                long millis = this.timeManager.getPlayedTime(((Player) sender).getUniqueId()).getTime();
                sender.sendMessage(this.replaceValues(Messages.PLAYTIME.asString(), millis));
            }

            return true;
        } else if (args.length == 1) {
            if (sender.hasPermission("varoxtime.playtime.other") ||
                    sender.hasPermission("spigotplaytime.command.playtime.other")) {

                if(Bukkit.getOfflinePlayer(args[0]) != null) {
                    if(this.timeManager.exists(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
                        long millis = this.timeManager.getPlayedTime(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getTime();

                        sender.sendMessage(this.replaceValues(Messages.PLAYTIME_OTHER.asString().replaceAll("%target%", args[0]), millis));
                    } else {
                        sender.sendMessage(Messages.ERROR_UNKNOWN_PLAYER.asString());
                    }
                }
            }
        } else {
            sender.sendMessage(Messages.ERROR_COMMAND_FORMAT_INVALID.asString());
        }

        return false;
    }


    private String replaceValues(String input, long milliseconds) {

        TimeUnit[] timeUnits = {TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES, TimeUnit.SECONDS};

        for(TimeUnit timeUnit : timeUnits) {
            String formatedName = timeUnit.toString().toLowerCase();
            if(input.contains(formatedName)) {
                long value = timeUnit.convert(milliseconds, TimeUnit.MILLISECONDS);

                milliseconds = milliseconds - TimeUnit.MILLISECONDS.convert(value, timeUnit);

                input = input.replaceFirst("%" + formatedName + "%", String.valueOf(value));
            }
        }

        return input;
    }
}

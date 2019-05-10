package me.devsnox.playtime.commands;

import me.devsnox.playtime.playtime.TimeManager;
import me.devsnox.playtime.playtime.TimePlayer;
import me.devsnox.playtime.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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
        /*
            @Deprecated will be removed in the next version (v.0.4)
         */
        if (sender instanceof ConsoleCommandSender) {
            if (args.length == 3 && args[0].equalsIgnoreCase("convert")) {
                if(Long.valueOf(args[2]) > 0) {
                    TimePlayer timePlayer = this.timeManager.getPlayedTime(Bukkit.getPlayer(args[1]).getUniqueId());
                    timePlayer.setTime(Long.valueOf(args[2]) * 60 * 60 * 1000);

                    this.timeManager.getPlayers().replace(timePlayer.getUuid(), timePlayer);

                    return true;
                }
            }
        }

        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Messages.ERROR_ONLY_PLAYERS.asString());
                return true;
            }

            long millis = this.timeManager.getPlayedTime(((Player) sender).getUniqueId()).getTime();

            sender.sendMessage(this.replaceValues(Messages.PLAYTIME.asString(), millis));

            return true;
        } else if (args.length == 1) {
            if(sender.hasPermission("varoxtime.playtime.other")) {
                if(Bukkit.getOfflinePlayer(args[0]) != null) {
                    if(this.timeManager.exists(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
                        long millis = this.timeManager.getPlayedTime(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getTime();

                        sender.sendMessage(this.replaceValues(Messages.PLAYTIME_OTHER.asString(), millis));
                    } else {
                        sender.sendMessage(Messages.ERROR_UNKOWN_PLAYER.asString());
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

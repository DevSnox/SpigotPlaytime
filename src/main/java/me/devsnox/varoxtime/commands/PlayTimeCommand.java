package me.devsnox.varoxtime.commands;

import me.devsnox.varoxtime.varoxtime.TimeManager;
import me.devsnox.varoxtime.varoxtime.TimePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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

        if (!(sender instanceof Player)) return false;

        if(args.length == 0) {
            long ms = this.timeManager.getPlayedTime(((Player) sender).getUniqueId()).getTime();

            int SECOND = 1000;
            int MINUTE = 60 * SECOND;
            int HOUR = 60 * MINUTE;

            StringBuffer text = new StringBuffer("§6§lDu hast eine Spielzeit von §d§l");
            if (ms > HOUR) {
                text.append(ms / HOUR).append(" §6§lStunden und §d§l");
                ms %= HOUR;
            }
            if (ms > MINUTE) {
                text.append(ms / MINUTE).append(" §6§lMinuten.");
            }

            sender.sendMessage(text.toString());

            return true;
        }

        if(sender.hasPermission("varoxtime.playtime.other"))

        if(Bukkit.getOfflinePlayer(args[0]) != null) {
            if(this.timeManager.exists(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
                long ms = this.timeManager.getPlayedTime(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).getTime();

                int SECOND = 1000;
                int MINUTE = 60 * SECOND;
                int HOUR = 60 * MINUTE;

                StringBuffer text = new StringBuffer("§6§l" + args[0] + " hat eine Spielzeit von §d§l");
                if (ms > HOUR) {
                    text.append(ms / HOUR).append(" §6§lStunden und §d§l");
                    ms %= HOUR;
                } else {
                    text.append(ms / HOUR).append(" §6§lStunden und §d§l");
                }
                if (ms > MINUTE) {
                    text.append(ms / MINUTE).append(" §6§lMinuten.");
                } else {
                    text.append(0).append(" §6§lMinuten.");
                }
                sender.sendMessage(text.toString());
            } else {
                sender.sendMessage("§cDieser Spieler war noch nie auf dem Server!");
            }
        }
        return false;
    }
}

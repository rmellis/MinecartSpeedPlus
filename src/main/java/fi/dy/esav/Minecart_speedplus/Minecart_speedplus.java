package fi.dy.esav.Minecart_speedplus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Minecart_speedplus extends JavaPlugin {
    static double speedmultiplier = 1.25D;
    private final Minecart_speedplusVehicleListener VehicleListener = new Minecart_speedplusVehicleListener(this);
    private final Minecart_speedplusSignListener SignListener = new Minecart_speedplusSignListener(this);
    Logger log = Logger.getLogger("Minecraft");
    boolean result;
    double multiplier;

    public static double getSpeedMultiplier() {
        return speedmultiplier;
    }

    public boolean setSpeedMultiplier(double multiplier) {
        if ((((0.0D < multiplier) ? 1 : 0) & ((multiplier <= 4.0D) ? 1 : 0)) != 0) {
            speedmultiplier = multiplier;
            return true;
        }
        return false;
    }

    public void onEnable() {
        this.log.info(getDescription().getName() + " version " + getDescription().getVersion() + " started.");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.VehicleListener, this);
        pm.registerEvents(this.SignListener, this);
    }

    public void onDisable() {
        this.log.info(getDescription().getName() + " version " + getDescription().getVersion() + " stopped.");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("msp")) {
            return false;
        }

        if (sender instanceof Player player) {
            if (!player.hasPermission("msp.cmd")) {
                player.sendMessage("You don't have permission to do that");
                return true;
            }
        }

        try {
            this.multiplier = Double.parseDouble(args[0]);
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "speed must be between 0.0 and 4.0");
            return false;
        }

        this.result = setSpeedMultiplier(this.multiplier);
        if (this.result) {
            sender.sendMessage(ChatColor.YELLOW + "multiplier for new mine carts set to: " + this.multiplier);
            return true;
        }
        sender.sendMessage(ChatColor.YELLOW + "speed must be between 0.0 and 4.0");
        return true;
    }
}

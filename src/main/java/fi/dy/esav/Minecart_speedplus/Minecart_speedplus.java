package fi.dy.esav.Minecart_speedplus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class Minecart_speedplus extends JavaPlugin {
    static double speedMultiplier = 1.25D;
    private final Minecart_speedplusVehicleListener VehicleListener = new Minecart_speedplusVehicleListener(this);
    private final Minecart_speedplusSignListener SignListener = new Minecart_speedplusSignListener(this);
    final Logger log = Logger.getLogger("Minecraft");

    public static double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public boolean setSpeedMultiplier(final double multiplier) {
        if ((((0.0D < multiplier) ? 1 : 0) & ((multiplier <= 4.0D) ? 1 : 0)) != 0) {
            speedMultiplier = multiplier;
            return true;
        }
        return false;
    }

    public void onEnable() {
        this.log.info("MinecartSpeedPlus started.");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.VehicleListener, this);
        pm.registerEvents(this.SignListener, this);
    }

    public void onDisable() {
        this.log.info("MinecartSpeedPlus stopped.");
    }

    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("msp")) {
            return false;
        }

        if ((sender instanceof final Player player) && !player.hasPermission("msp.cmd")) {
            player.sendMessage("You don't have permission to do that");
            return true;
        }

        var multiplier = 0D;
        try {
            multiplier = Double.parseDouble(args[0]);
        } catch (final Exception e) {
            sender.sendMessage(ChatColor.RED + "speed must be between 0.0 and 4.0");
            return false;
        }

        var success = setSpeedMultiplier(multiplier);
        if (success) {
            sender.sendMessage(ChatColor.YELLOW + "multiplier for new mine carts set to: " + multiplier);
            return true;
        }
        sender.sendMessage(ChatColor.YELLOW + "speed must be between 0.0 and 4.0");
        return true;
    }
}

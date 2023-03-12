package fi.dy.esav.Minecart_speedplus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import java.util.EnumSet;
import java.util.Set;

public class Minecart_speedplusVehicleListener implements Listener {
    public static Minecart_speedplus plugin;
    private final Set<Material> signs = EnumSet.of(Material.OAK_SIGN, Material.SPRUCE_SIGN, Material.BIRCH_SIGN, Material.JUNGLE_SIGN, Material.ACACIA_SIGN, Material.DARK_OAK_SIGN, Material.MANGROVE_SIGN, Material.BAMBOO_SIGN, Material.CRIMSON_SIGN, Material.WARPED_SIGN);
    int[] xmodifier = {-1, 0, 1};
    int[] ymodifier = {-2, -1, 0, 1, 2};
    int[] zmodifier = {-1, 0, 1};
    Vector flyingmod = new Vector(10, 0.01, 10);
    Vector noflyingmod = new Vector(1, 1, 1);

    public Minecart_speedplusVehicleListener(Minecart_speedplus instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleCreate(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart cart) {
            cart.setMaxSpeed(0.4 * Minecart_speedplus.getSpeedMultiplier());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleMove(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart cart)) { return; }

        for (int xmod : xmodifier) {
            for (int ymod : ymodifier) {
                for (int zmod : zmodifier) {
                    final var cartx = cart.getLocation().getBlockX();
                    final var carty = cart.getLocation().getBlockY();
                    final var cartz = cart.getLocation().getBlockZ();
                    final var blockx = cartx + xmod;
                    final var blocky = carty + ymod;
                    final var blockz = cartz + zmod;
                    final var block = cart.getWorld().getBlockAt(blockx, blocky, blockz);
                    final var isSign = signs.contains(block.getType());
                    if (!isSign) { continue; }

                    Sign sign = (Sign) block.getState();
                    String[] text = sign.lines().stream()
                            .filter(c -> c instanceof TextComponent)
                            .map(t -> ((TextComponent) t).content())
                            .toArray(String[]::new);

                    if (!text[0].equalsIgnoreCase("[msp]")) { continue; }

                    if (text[1].equalsIgnoreCase("fly")) {
                        cart.setFlyingVelocityMod(flyingmod);
                        return;
                    }

                    if (text[1].equalsIgnoreCase("nofly")) {
                        cart.setFlyingVelocityMod(noflyingmod);
                        return;
                    }

                    var speed = 0D;
                    try {
                        speed = Double.parseDouble(text[1]);
                    } catch (Exception e) {
                        sign.line(2, Component.text("  ERROR"));
                        sign.line(3, Component.text("WRONG VALUE"));
                        sign.update();
                        continue;
                    }

                    if (0 < speed && speed <= 50) {
                        cart.setMaxSpeed(0.4D * speed);
                    } else {
                        sign.line(2, Component.text("  ERROR"));
                        sign.line(3, Component.text("WRONG VALUE"));
                        sign.update();
                    }
                }
            }
        }
    }
}

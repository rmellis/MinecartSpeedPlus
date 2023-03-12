package fi.dy.esav.Minecart_speedplus;

import org.bukkit.Material;
import org.bukkit.block.Block;
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
import java.util.logging.Logger;

public class Minecart_speedplusVehicleListener implements Listener {
    public static Minecart_speedplus plugin;
    int[] xmodifier = {-1, 0, 1};
    int[] ymodifier = {-2, -1, 0, 1, 2};
    int[] zmodifier = {-1, 0, 1};

    int cartx, carty, cartz;
    int blockx, blocky, blockz;

    Block block;
    int blockid;

    double line1;
    Logger log = Logger.getLogger("Minecraft");
    boolean error;
    Vector flyingmod = new Vector(10, 0.01, 10);
    Vector noflyingmod = new Vector(1, 1, 1);
    private final Set<Material> signs = EnumSet.of(Material.OAK_SIGN,
            Material.SPRUCE_SIGN,
            Material.BIRCH_SIGN,
            Material.JUNGLE_SIGN,
            Material.ACACIA_SIGN,
            Material.DARK_OAK_SIGN,
            Material.MANGROVE_SIGN,
            Material.BAMBOO_SIGN,
            Material.CRIMSON_SIGN,
            Material.WARPED_SIGN);

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

        if (event.getVehicle() instanceof Minecart cart) {

            for (int xmod : xmodifier) {
                for (int ymod : ymodifier) {
                    for (int zmod : zmodifier) {

                        cartx = cart.getLocation().getBlockX();
                        carty = cart.getLocation().getBlockY();
                        cartz = cart.getLocation().getBlockZ();
                        blockx = cartx + xmod;
                        blocky = carty + ymod;
                        blockz = cartz + zmod;
                        block = cart.getWorld().getBlockAt(blockx, blocky,
                                blockz);

                        var isSign = signs.contains(cart.getWorld().getBlockAt(blockx, blocky, blockz).getType());
                        if (isSign) {
                            Sign sign = (Sign) block.getState();
                            String[] text = sign.getLines();

                            if (text[0].equalsIgnoreCase("[msp]")) {

                                if (text[1].equalsIgnoreCase("fly")) {
                                    cart.setFlyingVelocityMod(flyingmod);

                                } else if (text[1].equalsIgnoreCase("nofly")) {

                                    cart.setFlyingVelocityMod(noflyingmod);

                                } else {

                                    error = false;
                                    try {

                                        line1 = Double.parseDouble(text[1]);

                                    } catch (Exception e) {

                                        sign.setLine(2, "  ERROR");
                                        sign.setLine(3, "WRONG VALUE");
                                        sign.update();
                                        error = true;

                                    }
                                    if (!error) {

                                        if (0 < line1 & line1 <= 50) {

                                            cart.setMaxSpeed(0.4D * Double.parseDouble(text[1]));

                                        } else {

                                            sign.setLine(2, "  ERROR");
                                            sign.setLine(3, "WRONG VALUE");
                                            sign.update();
                                        }
                                    }
                                }
                            }

                        }

                    }
                }
            }

        }
    }

}

package fi.dy.esav.Minecart_speedplus;

import java.util.logging.Logger;

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

public class Minecart_speedplusVehicleListener implements Listener {

	int[] xmodifier = { -1, 0, 1 };
	int[] ymodifier = { -2, -1, 0, 1, 2 };
	int[] zmodifier = { -1, 0, 1 };

	int cartx, carty, cartz;
	int blockx, blocky, blockz;

	Block block;
	int blockid;

	double line1;

	public static Minecart_speedplus plugin;
	Logger log = Logger.getLogger("Minecraft");

	boolean error;
	
	Vector flyingmod = new Vector(10 , 0.01 , 10);
	Vector noflyingmod = new Vector(1, 1, 1);

	public Minecart_speedplusVehicleListener(Minecart_speedplus instance) {
		plugin = instance;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onVehicleCreate(VehicleCreateEvent event) {
		if (event.getVehicle() instanceof Minecart) {

			Minecart cart = (Minecart) event.getVehicle();
			cart.setMaxSpeed(0.4 * Minecart_speedplus.getSpeedMultiplier());

		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onVehicleMove(VehicleMoveEvent event) {

		if (event.getVehicle() instanceof Minecart) {

			Minecart cart = (Minecart) event.getVehicle();
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
						blockid = cart.getWorld().getBlockTypeIdAt(blockx,
								blocky, blockz);

						if (blockid == Material.WALL_SIGN.getId()
						    || blockid == Material.SIGN_POST.getId()) {
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

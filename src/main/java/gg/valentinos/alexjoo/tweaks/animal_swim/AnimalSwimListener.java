package gg.valentinos.alexjoo.tweaks.animal_swim;

import gg.valentinos.alexjoo.api.AbstractTweakListener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class AnimalSwimListener extends AbstractTweakListener {
    private final AnimalSwimTweak tweak;

    public AnimalSwimListener(AnimalSwimTweak tweak) {
        super();
        this.tweak = tweak;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      Entity vehicle = player.getVehicle();

      if (vehicle == null || !tweak.isEntityEligible(vehicle)) return;
      if (!(vehicle.isInWater() && player.isInWater())) return;
     
      Vector newVelocity = vehicle.getVelocity();
      newVelocity.setY(tweak.getSwimAssistVelocity());

      vehicle.setVelocity(newVelocity);
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
      Entity vehicle = event.getDismounted();
      
      if (!tweak.isEntityEligible(vehicle)) return;
      if (!(event.getEntity() instanceof Player player)) return;

      // to stop player from being dismounted when they didn't choose to.
      if (!player.isSneaking() && vehicle.isInWater())
        event.setCancelled(true);
    }
}

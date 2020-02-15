package com.happyzleaf.pixelgenocide.util;

import com.happyzleaf.pixelgenocide.PGConfig;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import net.minecraft.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.world.chunk.UnloadChunkEvent;

public class FixSpawningIssueEvents {
    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onUnloadChunkEvent(UnloadChunkEvent event) {

        for (Object s : event.getTargetChunk().getEntities()) {
            if (s instanceof Entity) {
                Entity entity = (Entity) s;
                if (entity instanceof EntityPixelmon) {
                    EntityPixelmon pixelmon = (EntityPixelmon) entity;
                    if (!(!pixelmon.canDespawn || pixelmon.hasOwner() || pixelmon.battleController != null || pixelmon.isInRanchBlock || PGConfig.shouldKeepPokemon(pixelmon))) {
                        pixelmon.unloadEntity();
                    }
                }
            }
        }
    }

    @Listener(order = Order.FIRST, beforeModifications = true)
    public void onEntityDestruct(DestructEntityEvent event) {
        if (event.getTargetEntity() instanceof EntityPixelmon) {
            EntityPixelmon pixelmon = (EntityPixelmon) event.getTargetEntity();
            if (!(!pixelmon.canDespawn || pixelmon.hasOwner() || pixelmon.battleController != null || pixelmon.isInRanchBlock || PGConfig.shouldKeepPokemon(pixelmon))) {
                pixelmon.unloadEntity();
            }
        }
    }
}



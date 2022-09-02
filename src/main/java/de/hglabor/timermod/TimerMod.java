package de.hglabor.timermod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TimerMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("timermod");
    private static final String dateFormat = "Day %s";

    @Override
    public void onInitializeClient() {
        ServerTickEvents.START_SERVER_TICK.register(server -> sendTime(MinecraftClient.getInstance().player));
    }

    private void sendTime(@Nullable ClientPlayerEntity player) {
        Audience client = FabricClientAudiences.of().audience();
        if (player != null) {
            long minecraftDay = player.getWorld().getTime() / 24000;
            TextComponent text = Component.text(
                    String.format(dateFormat, (minecraftDay + 1)),
                    Style.style().color(TextColor.fromHexString("#117FE6")).decorate(TextDecoration.BOLD).build()
            );
            client.sendActionBar(text);
        }
    }
}

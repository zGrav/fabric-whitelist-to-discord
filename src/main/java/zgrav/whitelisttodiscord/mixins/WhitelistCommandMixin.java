package zgrav.whitelisttodiscord.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.command.ServerCommandSource;

import net.minecraft.server.dedicated.command.WhitelistCommand;

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.stream.Collectors;

import static zgrav.whitelisttodiscord.Utils.sendToDiscord;

@Mixin(WhitelistCommand.class)
public class WhitelistCommandMixin {
    @Inject(method = "executeAdd", at = @At("RETURN"))
    private static void postAddCommand(ServerCommandSource source, Collection<GameProfile> targets, CallbackInfoReturnable<Integer> cir) {
        String targetsAsString = String.join(",", targets.stream().map(o -> o.getName() + " - " + o.getId()).collect(Collectors.toList()));

        sendToDiscord(source, targetsAsString, "added");

        source.getServer().getPlayerManager().reloadWhitelist();
        source.sendFeedback(Text.translatable("commands.whitelist.reloaded"), true);
        source.getServer().kickNonWhitelistedPlayers(source);
    }

    @Inject(method = "executeRemove", at = @At("RETURN"))
    private static void postRemoveCommand(ServerCommandSource source, Collection<GameProfile> targets, CallbackInfoReturnable<Integer> cir) {
        String targetsAsString = String.join(",", targets.stream().map(o -> o.getName() + " - " + o.getId()).collect(Collectors.toList()));

        sendToDiscord(source, targetsAsString, "removed");

        source.getServer().getPlayerManager().reloadWhitelist();
        source.sendFeedback(Text.translatable("commands.whitelist.reloaded"), true);
        source.getServer().kickNonWhitelistedPlayers(source);
    }
}
package cqb13.NumbyHack.mixins;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.blaze3d.vertex.PoseStack;

import cqb13.NumbyHack.modules.general.TntFuseEsp;
import meteordevelopment.meteorclient.systems.modules.Modules;

@Mixin(TntRenderer.class)
public class TntRendererMixin {
    @Redirect(
        method = "submit(Lnet/minecraft/client/renderer/entity/state/TntRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/TntMinecartRenderer;submitWhiteSolidBlock(Lnet/minecraft/client/renderer/block/BlockModelRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;IZI)V"
        )
    )
    private void numbyhack$disableTntFlash(
        BlockModelRenderState blockModel,
        PoseStack poseStack,
        SubmitNodeCollector collector,
        int lightCoords,
        boolean flashing,
        int outlineColor
    ) {
        TntFuseEsp tntFuseEsp = Modules.get().get(TntFuseEsp.class);
        if (tntFuseEsp != null && tntFuseEsp.shouldHideFlashing()) {
            flashing = false;
        }
        TntMinecartRenderer.submitWhiteSolidBlock(blockModel, poseStack, collector, lightCoords, flashing, outlineColor);
    }
}

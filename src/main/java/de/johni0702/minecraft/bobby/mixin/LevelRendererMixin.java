package de.johni0702.minecraft.bobby.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import de.johni0702.minecraft.bobby.ext.GameRendererExt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;addSkyPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"))
    private GpuBufferSlice clampMaxValue(GpuBufferSlice fogBuffer) {
        if (Minecraft.getInstance().options.getEffectiveRenderDistance() >= 32) {
            fogBuffer = ((GameRendererExt) Minecraft.getInstance().gameRenderer).bobby_getSkyFogRenderer().getBuffer(FogRenderer.FogMode.WORLD);
        }
        return fogBuffer;
    }
}

package com.firemerald.additionalplacements.mixin.fabric;

import com.firemerald.additionalplacements.client.fabric.BlockModelDefinitionExtensions;
import com.firemerald.additionalplacements.client.models.DynamicModelsDefinition;
import com.firemerald.additionalplacements.client.models.IAPCustomBlockModelDefinition;
import com.firemerald.additionalplacements.client.models.fabric.DynamicModelsDefinitionImpl;
import com.google.common.collect.Streams;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Mixin(BlockModelDefinition.class)
public class MixinBlockModelDefinition implements BlockModelDefinitionExtensions {
    @Mutable
    @Final
    @Shadow
    public static Codec<BlockModelDefinition> CODEC;

    @Unique
    public IAPCustomBlockModelDefinition additionalplacements$customModelDefinition = null;

    @Override
    @Nullable
    public IAPCustomBlockModelDefinition additionalplacements$getCustomModelDefinition() {
        return additionalplacements$customModelDefinition;
    }

    @Override
    public void additionalplacements$setCustomModelDefinition(@Nullable IAPCustomBlockModelDefinition customDefinition) {
        additionalplacements$customModelDefinition = customDefinition;
    }

    @Inject(method = "instantiate(Lnet/minecraft/world/level/block/state/StateDefinition;Ljava/util/function/Supplier;)Ljava/util/Map;", at = @At("HEAD"), cancellable = true)
    public void instantiate(StateDefinition<Block, BlockState> stateDefinition, Supplier<String> name, CallbackInfoReturnable<Map<BlockState, BlockStateModel.UnbakedRoot>> cir) {
        if (additionalplacements$customModelDefinition != null) cir.setReturnValue(additionalplacements$customModelDefinition.instantiate(stateDefinition, name));
    }

    @ModifyReturnValue(method = "equals(Ljava/lang/Object;)Z", at = @At("RETURN"))
    public boolean modifyEquals(boolean original, @Local(argsOnly = true) Object other) {
        if (original) {
            return Objects.equals(
                    additionalplacements$customModelDefinition,
                    ((BlockModelDefinitionExtensions) other).additionalplacements$getCustomModelDefinition());
        } else return false;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyCodec(CallbackInfo ci) {
        final Codec<BlockModelDefinition> vanillaCodec = CODEC;
        final MapCodec<ResourceLocation> keyCodec = ResourceLocation.CODEC.fieldOf("neoforge:definition_type");
        final Codec<IAPCustomBlockModelDefinition> moddedCodec = new MapCodec<IAPCustomBlockModelDefinition>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return Streams.concat(
                        keyCodec.keys(ops),
                        DynamicModelsDefinitionImpl.CODEC.keys(ops)
                ).distinct();
            }

            @Override
            public <T> DataResult<IAPCustomBlockModelDefinition> decode(DynamicOps<T> ops, MapLike<T> input) {
                return keyCodec.decode(ops, input).flatMap(key -> {
                    if (key.equals(DynamicModelsDefinition.ID)) {
                        return DynamicModelsDefinitionImpl.CODEC.decode(ops, input).map(Function.identity());
                    } else return DataResult.error(() -> "Unknown definition " + key);
                });
            }

            @Override
            public <T> RecordBuilder<T> encode(IAPCustomBlockModelDefinition input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
                prefix = keyCodec.encode(input.id(), ops, prefix);
                //noinspection unchecked
                prefix = ((MapCodec<IAPCustomBlockModelDefinition>) input.codec()).encode(input, ops, prefix);
                return prefix;
            }
        }.codec();
        CODEC = new Codec<>() {
            @Override
            public <T> DataResult<Pair<BlockModelDefinition, T>> decode(DynamicOps<T> ops, T input) {
                DataResult<Pair<IAPCustomBlockModelDefinition, T>> modded = moddedCodec.decode(ops, input);
                if (modded.isSuccess()) return modded.map(pair -> {
                    BlockModelDefinition definition = new BlockModelDefinition(Optional.empty(), Optional.empty());
                    ((BlockModelDefinitionExtensions) (Object) definition).additionalplacements$setCustomModelDefinition(pair.getFirst());
                    return Pair.of(definition, pair.getSecond());
                });
                else return vanillaCodec.decode(ops, input);
            }

            @Override
            public <T> DataResult<T> encode(BlockModelDefinition input, DynamicOps<T> ops, T prefix) {
                IAPCustomBlockModelDefinition customDefinition = ((BlockModelDefinitionExtensions) (Object) input).additionalplacements$getCustomModelDefinition();
                if (customDefinition != null) return moddedCodec.encode(customDefinition, ops, prefix);
                else return vanillaCodec.encode(input, ops, prefix);
            }
        };
    }
}

package com.firemerald.additionalplacements.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.BiConsumer;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.block.*;
import com.firemerald.additionalplacements.client.models.retextured.RetexturedModelData;
import com.firemerald.additionalplacements.client.models.rotated.RotatedModelData;
import com.firemerald.additionalplacements.generation.CreatedBlockEntry;
import com.firemerald.additionalplacements.generation.GenerationType;
import com.firemerald.additionalplacements.generation.Registration;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class APDynamicResources implements PackResources {
    public static final Pack PACK = Pack.create(
            "Additional Placements dynamic resources",
            Component.literal("title"),
            true,
            unknown -> new APDynamicResources(),
            getPackInfo(Component.literal("description"), 15, FeatureFlagSet.of()),
            PackType.CLIENT_RESOURCES,
            Pack.Position.BOTTOM,
            true,
            PackSource.BUILT_IN
    );

    @ExpectPlatform
    public static Pack.Info getPackInfo(Component description, int format, FeatureFlagSet featureFlagSet) {
        throw new AssertionError();
    }

    private IJsonInputSupplier getBlockModelSupplier(AdditionalPlacementBlock<?> block, BlockState state) {
        if (block.rotatesModel(state)) {
            return new RotatedModelData(block.getModelState(state), block.getRotation(state), block.rotatesTexture(state));
        } else {
            return new RetexturedModelData(block.getModelDefinition(state).location(block.getBaseModelPrefix()), block.getModelState(state));
        }
    }

    @Override
    public IoSupplier<InputStream> getRootResource(String @NotNull ... strings) {
        return null;
    }

    @Override
    public IoSupplier<InputStream> getResource(@NotNull PackType packType, @NotNull ResourceLocation resourceLocation)
    {
        if (packType != PackType.CLIENT_RESOURCES) return null;
        else if (!resourceLocation.getNamespace().equals(AdditionalPlacementsMod.MOD_ID)) return null;
        else if (!resourceLocation.getPath().endsWith(".json")) return null;
        else if (resourceLocation.getPath().startsWith("blockstates/")) { //blockstate json
            String blockName = resourceLocation.getPath().substring(12, resourceLocation.getPath().length() - 5);
            Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(AdditionalPlacementsMod.MOD_ID, blockName));
            if (block instanceof AdditionalPlacementBlock<?> placement) return new BlockStateJsonSupplier(placement, blockName);
            else return null;
        }
        else if (resourceLocation.getPath().startsWith("models/block/") && resourceLocation.getPath().endsWith("/model.json")) {
            String key = resourceLocation.getPath().substring(13, resourceLocation.getPath().length() - 11);
            Optional<?> match = Registration.types().flatMap(GenerationType::created).filter(entry -> key.startsWith(entry.newId().getPath())).findFirst();
            if (match.isPresent()) {
                CreatedBlockEntry<?, ?> entry = (CreatedBlockEntry<?, ?>) match.get();
                String[] stateVals = key.substring(entry.newId().getPath().length() + 1).split("/");
                BlockState state = entry.newBlock().defaultBlockState();
                Collection<Property<?>> props = state.getProperties();
                if (stateVals.length != props.size()) return null;
                else {
                    Iterator<Property<?>> it = props.iterator();
                    for (String stateVal : stateVals) {
                        Property<?> prop = it.next();
                        @SuppressWarnings("unchecked")
                        Optional<Comparable<?>> opt = (Optional<Comparable<?>>) prop.getValue(stateVal);
                        if (opt.isEmpty()) return null;
                        else state = set(state, prop, opt.get());
                    }
                    return getBlockModelSupplier(entry.newBlock(), state);
                }
            }
            else return null;
        }
        else return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> BlockState set(BlockState state, Property<?> prop, Comparable<?> val) {
        return state.setValue((Property<T>) prop, (T) val);
    }

    @Override
    public void listResources(@NotNull PackType packType, @NotNull String domain, @NotNull String path, @NotNull ResourceOutput resourceOutput) {
        if (packType == PackType.CLIENT_RESOURCES && AdditionalPlacementsMod.MOD_ID.equals(domain)) {
            if ("blockstates".equals(path)) {
                Registration.forEach(type -> {
                    type.forEachCreated(entry -> {
                        ResourceLocation id = entry.newId();
                        resourceOutput.accept(
                                new ResourceLocation(AdditionalPlacementsMod.MOD_ID, "blockstates/" + id.getPath() + ".json"),
                                new BlockStateJsonSupplier(entry.newBlock(), id.getPath()));
                    });
                });
            } else if ("models".equals(path)) {
                Registration.types().flatMap(GenerationType::created).forEach(entry -> {
                    AdditionalPlacementBlock<?> block = entry.newBlock();
                    BlockState state = block.defaultBlockState();
                    parseBlockstates(state, new ArrayList<>(state.getProperties()), 0, "models/block/" + entry.newId().getPath() + "/", (modelPath, newState) -> {
                        resourceOutput.accept(
                                new ResourceLocation(AdditionalPlacementsMod.MOD_ID, modelPath + ".json"),
                                getBlockModelSupplier(entry.newBlock(), newState));
                    });
                });
            }
        }
    }

    private <T extends Comparable<T>> void parseBlockstates(BlockState state, List<Property<?>> props, int index, String currentStateDir, BiConsumer<String, BlockState> action) {
        if (index >= props.size())
            action.accept(currentStateDir + "model", state);
        else {
            @SuppressWarnings("unchecked")
            Property<T> prop = (Property<T>) props.get(index);
            prop.getAllValues().forEach(val -> parseBlockstates(set(state, prop, val.value()), props, index + 1, currentStateDir + prop.getName(val.value()) + "/", action));
        }
    }

    @Override
    public @NotNull Set<String> getNamespaces(@NotNull PackType packType) {
        return Collections.singleton(AdditionalPlacementsMod.MOD_ID);
    }

    @Override
    public <T> T getMetadataSection(@NotNull MetadataSectionSerializer<T> metadataSectionSerializer) throws IOException {
        return null;
    }

    @Override
    public @NotNull String packId() {
        return "Additional Placements Dynamic Resources";
    }

    @Override
    public void close() {}

    @Override
    public boolean isBuiltin() {
        return true;
    }
}

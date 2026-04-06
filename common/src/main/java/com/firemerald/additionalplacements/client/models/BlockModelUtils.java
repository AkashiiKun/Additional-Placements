package com.firemerald.additionalplacements.client.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firemerald.additionalplacements.util.PlatformUtils;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.data.AtlasIds;
import org.apache.commons.lang3.tuple.Pair;

import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.util.BlockRotation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class BlockModelUtils {
	static {
		PlatformUtils.checkIsClient();
	}

	public static BlockState getModeledState(BlockState state) {
		if (state != null && state.getBlock() instanceof AdditionalPlacementBlock<?> block) return block.getModelState(state);
		else return state;
	}

	public static BakedQuad retexture(BakedQuad jsonBakedQuad, TextureAtlasSprite newSprite, int newTintIndex) {
		return transformed(
				jsonBakedQuad,
				(oldPos, oldTex, oldNorms, oldColors, newPos, newTex, newNorms, newColors) -> {
					System.arraycopy(oldPos, 0, newPos, 0, oldPos.length);
					updateUVs(oldTex, newTex, jsonBakedQuad.sprite(), newSprite);
					if (oldNorms != null) System.arraycopy(oldNorms, 0, newNorms, 0, oldNorms.length);
					if (oldColors != null) System.arraycopy(oldColors, 0, newColors, 0, oldColors.length);
				},
				newTintIndex,
				jsonBakedQuad.direction(),
				newSprite
		);
	}

	@FunctionalInterface
	public interface QuadTransform {
		void transform(Vector3fc[] sourcePos, long[] sourceTex, Vector3fc @Nullable [] sourceNorms, int @Nullable [] sourceCols, Vector3fc[] desPos, long[] desTex, Vector3fc @Nullable [] desNorms, int @Nullable [] desCols);
	}

	public static BakedQuad transformed(BakedQuad originalQuad, QuadTransform quadTransform, int tintIndex, Direction direction, TextureAtlasSprite sprite) {
		Vector3fc[] oldPos = getVertices(originalQuad);
		long[] oldTex = getUVs(originalQuad);
		Vector3fc @Nullable [] oldNorms = getNorms(originalQuad);
		int @Nullable [] oldColors = getColors(originalQuad);
		Vector3fc[] newPos = oldPos.clone();
		long[] newTex = oldTex.clone();
		Vector3fc[] newNorms = oldNorms == null ? null : oldNorms.clone();
		int[] newColors = oldColors == null ? null : oldColors.clone();
		quadTransform.transform(oldPos, oldTex, oldNorms, oldColors, newPos, newTex, newNorms, newColors);
		return transformed(originalQuad, newPos, newTex, newNorms, newColors, tintIndex, direction, sprite);
	}

	@ExpectPlatform
	public static BakedQuad transformed(BakedQuad originalQuad, Vector3fc[] newPos, long[] newTex, Vector3fc @Nullable [] newNorm, int @Nullable [] newColor, int tintIndex, Direction direction, TextureAtlasSprite sprite) {
		throw new AssertionError();
	}

	public static final Vector3fc ZERO_POINT = new Vector3f();

	public static float getFaceSize(Vector3fc[] vertices) {
		Vector3f first = newVertex(vertices, 0);
		Vector3f prev = new Vector3f();
		Vector3f cur = newVertex(vertices, 1, first);
		float size = 0;
		for (int vertexIndex = 2; vertexIndex < vertices.length; vertexIndex++) {
			Vector3f tmp = prev;
			prev = cur;
			cur = getVertex(vertices, vertexIndex, first, tmp);
			size += getArea(prev, cur);
		}
		return size;
	}

	public static Vector3f newVertex(Vector3fc[] vertices, int vertexIndex) {
		return newVertex(vertices, vertexIndex, ZERO_POINT);
	}

	public static Vector3f newVertex(Vector3fc[] vertices, int vertexIndex, Vector3fc origin) {
		return new Vector3f(vertices[vertexIndex]).sub(origin);
	}

	public static Vector3f getVertex(Vector3fc[] vertices, int vertexIndex, Vector3f des) {
		return getVertex(vertices, vertexIndex, ZERO_POINT, des);
	}

	public static Vector3f getVertex(Vector3fc[] vertices, int vertexIndex, Vector3fc origin, Vector3f des) {
		return vertices[vertexIndex].get(des).sub(origin);
	}

	public static float getArea(Vector3f ab, Vector3f ac) {
		return .5f * Mth.sqrt(
				Mth.square(ab.x * ac.y - ab.y * ac.x) +
				Mth.square(ab.y * ac.z - ab.z * ac.y) +
				Mth.square(ab.z * ac.x - ab.x * ac.z)
				);
	}

	public static void updateUVs(long[] source, long[] des, TextureAtlasSprite oldSprite, TextureAtlasSprite newSprite) {
		for (int uvIndex = 0; uvIndex < source.length; uvIndex++) {
			des[uvIndex] = changeUVSprite(oldSprite, newSprite, source[uvIndex]);
	    }
	}

	private static long changeUVSprite(TextureAtlasSprite oldSprite, TextureAtlasSprite newSprite, long packedUV) {
		float u = getU(packedUV);
		float v = getV(packedUV);
		return packUV(
				newSprite.getU(getUOffset(oldSprite, getU(packedUV))),
				newSprite.getV(getVOffset(oldSprite, getV(packedUV)))
		);
	}

	private static int changeUVertexElementSprite(TextureAtlasSprite oldSprite, TextureAtlasSprite newSprite, int vertex) {
		return Float.floatToRawIntBits(newSprite.getU(BlockModelUtils.getUOffset(oldSprite, Float.intBitsToFloat(vertex))));
	}

	private static int changeVVertexElementSprite(TextureAtlasSprite oldSprite, TextureAtlasSprite newSprite, int vertex) {
		return Float.floatToRawIntBits(newSprite.getV(BlockModelUtils.getVOffset(oldSprite, Float.intBitsToFloat(vertex))));
	}

	@SuppressWarnings("SuspiciousSystemArraycopy")
    public static <T> void shiftData(T source, T des, int size, int shiftLeft) {
		//shiftLeft %= originalData.length;
		if (shiftLeft == 0) {
			System.arraycopy(source, 0, des, 0, size); //direct copy
		} else {
			int lengthRight = size - shiftLeft;
			System.arraycopy(source, shiftLeft, des, 0, lengthRight); //copy [middle to end] to [start to middle]
			System.arraycopy(source, 0, des, lengthRight, shiftLeft); //copy [start to middle] to [middle to end]
		}
	}

	public static Pair<TextureAtlasSprite, Integer> getSidedTexture(List<BlockModelPart> fromModel, Direction fromSide) {
		Map<Pair<TextureAtlasSprite, Integer>, Double> weights = new HashMap<>();
		List<BakedQuad> referenceQuads = fromModel.stream().flatMap(part -> part.getQuads(fromSide).stream()).toList();
		if (fromSide != null && (referenceQuads.isEmpty() || referenceQuads.stream().noneMatch(quad -> quad.direction() == fromSide))) //no valid culled sides
			referenceQuads = fromModel.stream().flatMap(part -> part.getQuads(null).stream()).toList();
		if (!referenceQuads.isEmpty()) {
			referenceQuads.forEach(referredBakedQuad -> {
				if (fromSide == null || referredBakedQuad.direction() == fromSide) { //only for quads facing the correct side
					Pair<TextureAtlasSprite, Integer> tex = Pair.of(referredBakedQuad.sprite(), referredBakedQuad.tintIndex());
					weights.merge(tex, (double) BlockModelUtils.getFaceSize(getVertices(referredBakedQuad)), Double::sum);
				}
			});
			return weights.entrySet().stream().max((e1, e2) -> (int) Math.signum(e2.getValue() - e1.getValue())).map(Map.Entry::getKey).orElse(
					Pair.of(Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS).missingSprite(), -1)
			);
		}
		else return Pair.of(Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS).missingSprite(), -1);
	}

	public static List<BakedQuad> retexturedQuads(List<BlockModelPart> originalModel, BlockModelPart ourModel, Direction side) {
		@SuppressWarnings("unchecked")
		Pair<TextureAtlasSprite, Integer>[] textures = new Pair[6];
		List<BakedQuad> originalQuads = ourModel.getQuads(side);
		List<BakedQuad> bakedQuads = new ArrayList<>(originalQuads.size());
		for (BakedQuad originalQuad : originalQuads) {
			Direction modelSide = originalQuad.direction();
			int dirIndex = modelSide.get3DDataValue();
			Pair<TextureAtlasSprite, Integer> texture = textures[dirIndex];
			if (texture == null) texture = textures[dirIndex] = getSidedTexture(originalModel, modelSide);
    		bakedQuads.add(retexture(originalQuad, texture.getLeft(), texture.getRight()));
		}
		return bakedQuads;
	}

	public static List<BakedQuad> rotatedQuads(BlockModelPart model, BlockRotation rotation, boolean rotateTex, Direction side) {
		List<BakedQuad> originalQuads = model.getQuads(rotation.unapply(side));
		List<BakedQuad> bakedQuads = new ArrayList<>(originalQuads.size());
		for (BakedQuad originalQuad : originalQuads) {
    		bakedQuads.add(transformed(
					originalQuad,
					(oldPos, oldTex, oldNorms, oldColors, newPos, newTex, newNorms, newColors) ->
							rotation.rotateVertices(originalQuad.direction(), oldPos, oldTex, oldNorms, oldColors, newPos, newTex, newNorms, newColors, rotateTex, originalQuad.sprite()),
					originalQuad.tintIndex(),
					rotation.apply(originalQuad.direction()),
    				originalQuad.sprite()));
		}
		return bakedQuads;
	}

	public static float getUOffset(TextureAtlasSprite sprite, float offset) {
		float f = sprite.getU1() - sprite.getU0();
		return (offset - sprite.getU0()) / f;
	}

	public static float getVOffset(TextureAtlasSprite sprite, float offset) {
		float f = sprite.getV1() - sprite.getV0();
		return (offset - sprite.getV0()) / f;
	}

	public static Vector3fc[] getVertices(BakedQuad quad) {
		return new Vector3fc[] {
				quad.position0(),
				quad.position1(),
				quad.position2(),
				quad.position3()
		};
	}

	public static long[] getUVs(BakedQuad quad) {
		return new long[] {
				quad.packedUV0(),
				quad.packedUV1(),
				quad.packedUV2(),
				quad.packedUV3()
		};
	}

	@ExpectPlatform
	@Nullable
	public static Vector3fc[] getNorms(BakedQuad quad) {
		throw new AssertionError();
	}

	@ExpectPlatform
    public static int @Nullable [] getColors(BakedQuad quad) {
		throw new AssertionError();
	}

	public static float getU(long packedUV) {
		return Float.intBitsToFloat((int) ((packedUV & 0xFFFFFFFF00000000L) >> 32));
	}

	public static float getV(long packedUV) {
		return Float.intBitsToFloat((int) (packedUV & 0x00000000FFFFFFFFL));
	}

	public static long packUV(float u, float v) {
		return ((long) Float.floatToRawIntBits(v)) | (((long) Float.floatToRawIntBits(u)) << 32);
	}
}
package dev.rbn.chroma.submods.plasma.particle.registration;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;

import java.util.List;

public class PlasmaSpriteSet {
    private final List<TextureAtlasSprite> sprites;

    public PlasmaSpriteSet(List<TextureAtlasSprite> sprites) {
        this.sprites = sprites;
    }

    public TextureAtlasSprite get(int age, int lifetime) {
        if (sprites.size() == 1)
            return sprites.getFirst();

        int index = Math.min(
                sprites.size() - 1,
                age * sprites.size() / lifetime
        );

        return sprites.get(index);
    }

    public TextureAtlasSprite random(RandomSource random) {
        return sprites.get(random.nextInt(sprites.size()));
    }
}

package dev.rbn.chroma.submods.plasma.particle.render;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.vehicle.minecart.Minecart;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpriteGetter {
    private final Identifier identifier;

    public SpriteGetter(Identifier identifier) {
        this.identifier = identifier;
    }

    public List<TextureAtlasSprite> getSprites() throws FileNotFoundException {
        List<TextureAtlasSprite> sprites = new ArrayList<>();
        Identifier jsonId = Identifier.fromNamespaceAndPath(identifier.getNamespace(), "plasma/" + identifier.getPath() + ".json");
        Minecraft minecraft = Minecraft.getInstance();
        Optional<Resource> resource = minecraft.getResourceManager().getResource(jsonId);
        if (resource.isEmpty()){
            throw new FileNotFoundException("Missing plasma definition: " + jsonId);
        }
        try (Reader reader = resource.get().openAsReader()) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray textures = GsonHelper.getAsJsonArray(json, "textures");

            for (JsonElement element : textures){
                Identifier textureId = Identifier.parse(element.getAsString());
                TextureAtlasSprite sprite = getSprite(textureId);
                sprites.add(sprite);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sprites;
    }

    private TextureAtlasSprite getSprite(Identifier textureId) {
        @SuppressWarnings("deprecation") TextureAtlas atlas = Minecraft.getInstance()
                .getAtlasManager().getAtlasOrThrow(TextureAtlas.LOCATION_PARTICLES);
        return atlas.getSprite(textureId);
    }
}
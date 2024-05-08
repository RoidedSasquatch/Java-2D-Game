package textures;

import utils.UtilityMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.*;

public class TextureAtlas {

    private BufferedImage textureAtlas;
    private Map<String, TextureRegion> atlasMap;
    private String regionName;
    private int regionWidth, regionHeight, atlasSize;


    public TextureAtlas(String fileName) {
        atlasMap = new HashMap<>();
        textureAtlas = null;

        try {
            textureAtlas = ImageIO.read(getClass().getClassLoader().getResourceAsStream("textures/" + fileName + ".png"));
        } catch(IOException e) {
            System.out.println("Could not load sprite sheet. Check file path. Error:" + e);
        }
    }


    //TODO: Implement parser and call parseatlastexture to get corresponding texture
    //TODO: Then save into textureregion and store textureregion in map
    private void parseXMLData() {

    }

    private BufferedImage parseAtlasTexture(int xGrid, int yGrid, int width, int height, int scale) {
        BufferedImage textureRegion;

        textureRegion = textureAtlas.getSubimage(xGrid * width, yGrid * height, width, height);
        textureRegion = UtilityMethods.scaleImage(textureRegion, width * scale, height * scale);
        return textureRegion;
    }

    public BufferedImage getRegionFromAtlas(String regionName) {
          return atlasMap.get(regionName).getTexture();
    }

    public List<BufferedImage> getTexturesFromRegion() {
        List<BufferedImage> regions = new ArrayList<>();
        return regions;
    }
}

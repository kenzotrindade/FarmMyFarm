package services;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpriteService {
    public static final Image SPRITE_SHEET = new Image(SpriteService.class.getResourceAsStream("/sprite.png"));
    
    public static final int TILE_SIZE = 16; 

    public static ImageView getSprite(int row, int col) {
        ImageView view = new ImageView(SPRITE_SHEET);
        
        Rectangle2D viewport = new Rectangle2D(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        view.setViewport(viewport);
        view.setSmooth(false);
        view.setPreserveRatio(true);
        view.setFitWidth(45);
        view.setFitHeight(45);
        
        return view;
    }
}
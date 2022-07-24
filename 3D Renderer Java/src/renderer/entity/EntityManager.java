package renderer.entity;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.input.ClickType;
import renderer.input.Keyboard;
import renderer.input.Mouse;
import renderer.input.UserInput;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.world.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {
    
    private final List<IEntity> entities;
    private int initialX, initialY;
    
    private final MyVector lightVector = MyVector.normalize(new MyVector(1, 1, 1));
    
    private Mouse mouse;
    private Keyboard keyboard;
    private final Camera camera;
    private final double moveSpeed = 10;
    
    public EntityManager() {
        this.entities = new ArrayList<>();
        this.camera = new Camera();
    }
    
    public void init(UserInput userInput) {
        this.mouse = userInput.mouse;
        this.keyboard = userInput.keyboard;
        this.entities.add(BasicEntityBuilder.createDiamond(new Color(50, 100, 150), 200, 0, 0, 0));
//        this.entities.add(ComplexEntityBuilder.createRubiksCube(100, 0, 0, 0));
        this.setLighting();
    }
    
    public void update() {
    
        int x = this.mouse.getX();
        int y = this.mouse.getY();
        double mouseSensitivity = 2.5;
        if (this.mouse.getButton() == ClickType.LeftClick) {
            int xDif = x - initialX;
            int yDif = y - initialY;
        
            this.rotate(0, -yDif / mouseSensitivity, -xDif / mouseSensitivity);
        } else if (this.mouse.getButton() == ClickType.RightClick) {
            int xDif = x - initialX;
        
            this.rotate(-xDif / mouseSensitivity, 0, 0);
        }
    
        if (this.mouse.isScrollingUp()) {
            PointConverter.zoomOut();
        } else if (this.mouse.isScrollingDown()) {
            PointConverter.zoomIn();
        }
        
        if (this.keyboard.isLeft()) {
            this.camera.translate();
            this.entities.forEach(entity -> entity.translate(0, moveSpeed, 0));
        }
        if (this.keyboard.isRight()) {
            this.camera.translate();
            this.entities.forEach(entity -> entity.translate(0, -moveSpeed, 0));
        }
        if (this.keyboard.isUp()) {
            this.camera.translate();
            this.entities.forEach(entity -> entity.translate(0, 0, -moveSpeed));
        }
        if (this.keyboard.isDown()) {
            this.camera.translate();
            this.entities.forEach(entity -> entity.translate(0, 0, moveSpeed));
        }
        if (this.keyboard.isForward()) {
            this.camera.translate();
            this.entities.forEach(entity -> entity.translate(moveSpeed, 0, 0));
        }
        if (this.keyboard.isBackward()) {
            this.camera.translate();
            this.entities.forEach(entity -> entity.translate(-moveSpeed, 0, 0));
        }
    
        this.mouse.resetScroll();
        this.keyboard.update();
    
        initialX = x;
        initialY = y;
    }
    
    public void render(Graphics g) {
        this.entities.forEach(entity -> entity.render(g));
    }
    
    private void rotate(double xAngle, double yAngle, double zAngle) {
        this.entities.forEach(entity -> entity.rotate(true, xAngle, yAngle, zAngle, this.lightVector));
    }
    
    private void setLighting() {
        this.entities.forEach(entity -> entity.setLighting(this.lightVector));
    }
}

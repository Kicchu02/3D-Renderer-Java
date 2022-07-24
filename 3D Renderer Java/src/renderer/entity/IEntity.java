package renderer.entity;

import renderer.point.MyVector;

import java.awt.*;

public interface IEntity {
    
    void render(Graphics g);
    
    void translate(double x, double y, double z);
    
    void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector);
    
    void setLighting(MyVector lightVector);
}

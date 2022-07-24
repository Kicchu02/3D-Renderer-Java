package renderer.shapes;

import renderer.point.MyVector;

import java.awt.*;
import java.util.Arrays;

public class Polyhedron {
    private final MyPolygon[] polygons;
    private Color color;
    
    public Polyhedron(Color color, boolean decayColor, MyPolygon... polygons) {
        this.color = color;
        this.polygons = polygons;
        if (decayColor) {
            this.setDecayingPolygonColor();
        } else {
            this.setPolygonColor();
        }
        this.sortPolygons();
    }
    
    public Polyhedron(MyPolygon... polygons) {
        this.color = Color.WHITE;
        this.polygons = polygons;
        this.sortPolygons();
    }
    
    public void translate(double x, double y, double z) {
        Arrays.stream(this.polygons).forEach(p -> p.translate(x, y, z));
    }
    
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector) {
        Arrays.stream(this.polygons).forEach(p -> p.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector));
        this.sortPolygons();
    }
    
    public MyPolygon[] getPolygons() {
        return this.polygons;
    }
    
    private void sortPolygons() {
        MyPolygon.sortPolygons(this.polygons);
    }
    
    private void setPolygonColor() {
        for (MyPolygon poly: this.polygons) {
            poly.setBaseColor(this.color);
        }
    }
    
    private void setDecayingPolygonColor() {
        double decayingFactor = 0.95;
        for (MyPolygon poly : this.polygons) {
            poly.setBaseColor(this.color);
            int r = (int) (this.color.getRed() * decayingFactor);
            int g = (int) (this.color.getGreen() * decayingFactor);
            int b = (int) (this.color.getBlue() * decayingFactor);
            this.color = new Color(r, g, b);
        }
    }
    
    public void setLighting(MyVector lightVector) {
        for (MyPolygon p :
                this.polygons) {
            p.setLighting(lightVector);
        }
    }
}

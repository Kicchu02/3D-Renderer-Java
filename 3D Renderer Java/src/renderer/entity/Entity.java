package renderer.entity;

import renderer.point.MyVector;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polyhedron;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Entity implements IEntity {
    
    private final List<Polyhedron> polyhedrons;
    private MyPolygon[] polygons;
    
    public Entity(List<Polyhedron> polyhedrons) {
        this.polyhedrons = polyhedrons;
        List<MyPolygon> tempList = new ArrayList<>();
        this.polyhedrons.forEach(tetra -> Collections.addAll(tempList, tetra.getPolygons()));
        this.polygons = new MyPolygon[tempList.size()];
        this.polygons = tempList.toArray(this.polygons);
        this.sortPolygons();
    }
    
    private void sortPolygons() {
        MyPolygon.sortPolygons(this.polygons);
    }
    
    @Override
    public void render(Graphics g) {
        Arrays.stream(this.polygons).forEach(poly -> poly.render(g));
    }
    
    @Override
    public void translate(double x, double y, double z) {
        this.polyhedrons.forEach(poly -> poly.translate(x, y, z));
    }
    
    @Override
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector) {
        polyhedrons.forEach(tetra -> tetra.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector));
        this.sortPolygons();
    }
    
    @Override
    public void setLighting(MyVector lightVector) {
        this.polyhedrons.forEach(tetra -> tetra.setLighting(lightVector));
    }
}

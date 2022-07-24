package renderer.entity.builder;

import renderer.entity.Entity;
import renderer.entity.IEntity;
import renderer.point.MyPoint;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polyhedron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BasicEntityBuilder {
    
    public static IEntity createDiamond(Color color, double size, double centerX, double centerY, double centerZ) {
        List<Polyhedron> tetras = new ArrayList<>();
        
        int edges = 8;
        double inFactor = 0.8;
        MyPoint bottom = new MyPoint(centerX, centerY, centerZ - size / 2);
        MyPoint[] outerPoints = new MyPoint[edges];
        MyPoint[] innerPoints = new MyPoint[edges];
        for (int i = 0; i < edges; i++) {
            double theta = 2 * Math.PI / edges * i;
            double xPos = -Math.sin(theta) * size / 2;
            double yPos = Math.cos(theta) * size / 2;
            double zPos = size / 2;
            outerPoints[i] = new MyPoint(centerX + xPos, centerY + yPos, centerZ + zPos * inFactor);
            innerPoints[i] = new MyPoint(centerX + xPos * inFactor, centerY + yPos * inFactor, centerZ + zPos);
        }
        
        MyPolygon[] polygons = new MyPolygon[2 * edges + 1];
        for (int i = 0; i < edges; i++) {
            polygons[i] = new MyPolygon(outerPoints[i], bottom, outerPoints[(i + 1) % edges]);
        }
        for (int i = 0; i < edges; i++) {
            polygons[i + edges] = new MyPolygon(outerPoints[i], outerPoints[(i + 1) % edges], innerPoints[(i + 1) % edges], innerPoints[i]);
        }
        polygons[2 * edges] = new MyPolygon(innerPoints);
        
        Polyhedron tetra = new Polyhedron(color, false, polygons);
        tetras.add(tetra);
        
        return new Entity(tetras);
    }
}

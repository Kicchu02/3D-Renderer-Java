package renderer.shapes;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyPolygon {
    
    private static final double AMBIENT_LIGHTING = 0.05;
    
    private final MyPoint[] points;
    private Color baseColor, lightingColor;
    
    public MyPolygon(Color color, MyPoint... points) {
        this.baseColor = this.lightingColor = color;
        this.points = new MyPoint[points.length];
        for (int i = 0; i < points.length; i++) {
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }
    
    public MyPolygon(MyPoint... points) {
        this.baseColor = this.lightingColor = Color.WHITE;
        this.points = new MyPoint[points.length];
        for (int i = 0; i < points.length; i++) {
            MyPoint p = points[i];
            this.points[i] = new MyPoint(p.x, p.y, p.z);
        }
    }
    
    public void render(Graphics g) {
        Polygon poly = new Polygon();
        for (MyPoint point : this.points) {
            Point p = PointConverter.convertPoint(point);
            poly.addPoint(p.x, p.y);
        }
        
        g.setColor(this.lightingColor);
        g.fillPolygon(poly);
    }
    
    public void translate(double x, double y, double z) {
        Arrays.stream(points).forEach(p -> {
            p.xOffset += x;
            p.yOffset += y;
            p.zOffset += z;
        });
    }
    
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector) {
        Arrays.stream(points).forEach(p -> {
            PointConverter.rotateAxisX(p, CW, xDegrees);
            PointConverter.rotateAxisY(p, CW, yDegrees);
            PointConverter.rotateAxisZ(p, CW, zDegrees);
        });
        
        this.setLighting(lightVector);
    }
    
    public static void sortPolygons(MyPolygon[] polygons) {
        List<MyPolygon> polygonsList = new ArrayList<>(polygons.length);
        
        Collections.addAll(polygonsList, polygons);
        
        polygonsList.sort((p1, p2) -> {
            double diff = p1.getAverageX() - p2.getAverageX();
            if (diff == 0) return 0;
            return diff < 0 ? -1 : 1;
        });
        
        for (int i = 0; i < polygons.length; i++) {
            polygons[i] = polygonsList.get(i);
        }
    
    }
    
    public double getAverageX() {
        double sum = Arrays.stream(this.points).mapToDouble(p -> p.x + p.xOffset).sum();
    
        return sum / this.points.length;
    }
    
    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }
    
    public void setLighting(MyVector lightVector) {
        if (this.points.length < 3) {
            return;
        }
        
        MyVector v1 = new MyVector(this.points[0], this.points[1]);
        MyVector v2 = new MyVector(this.points[1], this.points[2]);
        MyVector normal = MyVector.normalize(MyVector.cross(v2, v1));
        double dot = MyVector.dot(normal, lightVector);
        double sign = dot < 0 ? -1 : 1;
        dot *= sign * dot;
        dot = (dot + 1) / 2 * (1 - AMBIENT_LIGHTING);
        
        double lightRatio = Math.min(1, Math.max(0, AMBIENT_LIGHTING + dot));
        this.updateLightingColor(lightRatio);
    }
    
    private void updateLightingColor(double lightRatio) {
        int red = (int) (this.baseColor.getRed() * lightRatio);
        int green = (int) (this.baseColor.getGreen() * lightRatio);
        int blue = (int) (this.baseColor.getBlue() * lightRatio);
        this.lightingColor = new Color(red, green, blue);
    }
    
}

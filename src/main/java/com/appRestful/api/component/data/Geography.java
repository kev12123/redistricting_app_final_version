package com.appRestful.api.component.data;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

public class Geography {
    private String county;
    private Geometry polygon;
    private static final GeometryFactory factory = new GeometryFactory();
    private int externalEdges;
    private int internalEdges;

    public Geography(String county, Coordinate[] boundary) {
        this.county = county;
        this.polygon = factory.createPolygon(boundary);
    }

    private Geography(String county, Coordinate[] boundary,int externalEdges, int internalEdges){
        this(county,boundary);
        this.externalEdges = externalEdges;
        this.internalEdges = internalEdges;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public double getArea(){
       return polygon.getArea();
    }


    public double getPerimeter(){
        return polygon.getLength();
    }

    public double getLongestAxis() {
        return polygon.getEnvelopeInternal().getWidth();
    }

    public double getMaxWidthPerpendicular() {
        return polygon.getEnvelopeInternal().getHeight();
    }

    public double getConvexHullArea(){
        return polygon.convexHull().getArea();
    }

    public int getInternalEdgeCount(){
        return internalEdges;
    }

    public int getExternalEdgeCount(){
        return externalEdges;
    }

    public void updateInternalEdgeCount(int internalEdgeChange){
        internalEdges += internalEdgeChange;
    }

    public void updateExternalEdgeCount(int externalEdgeChange){
        externalEdges += externalEdgeChange;
    }
    
    public Geometry getGeometry() {
    		return polygon;
    }
    
    public void setGeometry(Geometry geometry) {
    		this.polygon = geometry;
    }

    public Object clone(){
    		Coordinate[] coordinates = polygon.getCoordinates(); 
    		Coordinate[] coordinatesClone = new Coordinate[coordinates.length];
    		for(int i = 0; i < coordinates.length;i++) {
    			coordinatesClone[i] = new Coordinate(coordinates[i].x,coordinates[i].y);
    		}
        return new Geography(county,coordinatesClone,externalEdges,internalEdges);
    }

    @Override
    public String toString() {
        return "Geography{" +
                "county='" + county + '\'' +
                '}';
    }
}


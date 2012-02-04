package com.eatthepath.jeospatial;

import static org.junit.Assert.*;

import org.junit.Test;

import com.eatthepath.jeospatial.util.SimpleGeospatialPoint;

/**
 * Abstract test suite for classes implementing the GeospatialPoint interface.
 * 
 * @author <a href="mailto:jon.chambers@gmail.com">Jon Chambers</a>
 */
public abstract class GeospatialPointTest {
    public abstract GeospatialPoint getPoint(double latitude, double longitude);
    
    @Test
    public void testGetSetLatitude() {
        GeospatialPoint p = this.getPoint(10, 20);
        assertEquals(10, p.getLatitude(), 0);
        
        p.setLatitude(30);
        assertEquals(30, p.getLatitude(), 0);
    }
    
    @Test
    public void testSetLatitudeEdgeOfRange() {
        GeospatialPoint p = this.getPoint(10, 20);
        
        // As long as we don't throw an exception here, everything's fine.
        p.setLatitude(90);
        p.setLatitude(-90);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetLatitudeOutOfRange() {
        GeospatialPoint p = this.getPoint(10, 20);
        p.setLatitude(120);
    }
    
    @Test
    public void testGetSetLongitude() {
        GeospatialPoint p = this.getPoint(10, 20);
        assertEquals(20, p.getLongitude(), 0);
        
        p.setLongitude(30);
        assertEquals(30, p.getLongitude(), 0);
    }
    
    @Test
    public void testSetLongitudeNormalization() {
        GeospatialPoint p = this.getPoint(0, 0);
        
        p.setLongitude(-180);
        assertEquals(-180, p.getLongitude(), 0);
        
        p.setLongitude(180);
        assertEquals(-180, p.getLongitude(), 0);
        
        p.setLongitude(240);
        assertEquals(-120, p.getLongitude(), 0);
        
        p.setLongitude(360);
        assertEquals(0, p.getLongitude(), 0);
    }
    
    @Test
    public void testGetDistanceTo() {
        SimpleGeospatialPoint BOS = new SimpleGeospatialPoint(42.3631, -71.0064);
        SimpleGeospatialPoint LAX = new SimpleGeospatialPoint(33.9425, -118.4072);
        
        assertEquals("Distance from point to self must be zero.",
                0, BOS.getDistanceTo(BOS), 0);
        
        assertEquals("Distance from A to B must be equal to distance from B to A.",
                BOS.getDistanceTo(LAX), LAX.getDistanceTo(BOS), 0);
        
        assertEquals("Distance between BOS and LAX should be within 50km of 4,200km.",
                4200000, BOS.getDistanceTo(LAX), 50000);
        
        SimpleGeospatialPoint a = new SimpleGeospatialPoint(0, 0);
        SimpleGeospatialPoint b = new SimpleGeospatialPoint(0, 180);
        
        assertEquals("Distance between diametrically opposed points should be within 1m of 20015086m.",
                20015086, a.getDistanceTo(b), 1d);
    }
}

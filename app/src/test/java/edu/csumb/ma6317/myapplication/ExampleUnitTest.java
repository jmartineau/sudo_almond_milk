package edu.csumb.ma6317.myapplication;

import android.location.Location;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void my_addition_isCorrect() throws Exception {
        int answer;
        home myHome = new home();

        answer = myHome.add(1,3);
        assertEquals(-4, answer);
    }

//    @Test
//    public void calcLocation_works() throws Exception {
//        double answer;
//        MapsActivity myMap = new MapsActivity();
//        Location pointA = new Location("");
//        Location pointB = new Location("");
//
//        //Latitude and Longitudes should be double values
//        pointA.setLatitude(100);
//        pointA.setLongitude(100);
//        pointB.setLatitude(100);
//        pointB.setLongitude(100);
//        answer = myMap.calculateDistance(pointA,pointB);
//        assertEquals(0, answer);
//    }

    @Test
    public void calcLocation_works() throws Exception {
        double answer;
        MapsActivity myMap = new MapsActivity();
        // mock creation
        Location mockedPointA = mock(Location.class);
        Location mockedPointB = mock(Location.class);

        // stubbing appears before the actual execution
        when(mockedPointA.getLatitude()).thenReturn(40.73);
        when(mockedPointA.getLongitude()).thenReturn(40.73);

        when(mockedPointB.getLatitude()).thenReturn(40.73);
        when(mockedPointB.getLongitude()).thenReturn(40.73);

        answer = myMap.calculateDistance(mockedPointA,mockedPointB);
        assertEquals(0, answer, 0.1);
    }







}
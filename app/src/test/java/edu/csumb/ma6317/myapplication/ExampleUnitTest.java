package edu.csumb.ma6317.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.Assert.*;

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

    //@Test
    //Tests to make sure FirebaseDatabase getInstance() and getReference() build the correct https link
//    public void mDatabaseRef_holds_a_string() throws Exception {
//        MapsActivity test_MapActivity = new MapsActivity();
//        DatabaseReference test_mDatabaseRef;
//        test_mDatabaseRef = FirebaseDatabase.getInstance().getReference("path/geofire");
//        test_MapActivity.setmDatabaseRef(test_mDatabaseRef);
//        assertEquals("https://sudo-almond-milk.firebaseio.com/path/geofire", test_MapActivity.getmDatabaseRef());
//    }

//    @Test
//    public void dist_between_two_markers() throws Exception {
//
//        assertEquals(31.08493441084737, test_MapActivity.getmDatabaseRef());
//
//    }
}
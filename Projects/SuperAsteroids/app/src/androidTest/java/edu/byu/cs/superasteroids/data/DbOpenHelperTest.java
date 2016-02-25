package edu.byu.cs.superasteroids.data;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

/**
 * Created by tlyon on 2/18/16.
 */
public class DbOpenHelperTest extends AndroidTestCase {

    public void testOnCreate() throws Exception {

        DbOpenHelper openHelper = new DbOpenHelper(this.getContext());
    }

    public void testOnUpgrade() throws Exception {

    }
}
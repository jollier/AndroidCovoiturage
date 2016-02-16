package com.fousduvolant.androidcovoiturage;

        import android.app.Application;
        import android.test.ApplicationTestCase;
        import android.test.suitebuilder.TestSuiteBuilder;

        import com.fousduvolant.androidcovoiturage.FousDuVolant;

        import junit.framework.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */


@RunWith(AndroidJUnit4.class)
public class ApplicationTest extends JunitTestCase<FousDuVolant> {
    public static Test suite() {
        return new TestSuiteBuilder(ApplicationTest.class).includeAllPackagesUnderHere().build();
    }
    public ApplicationTest() {
        super(FousDuVolant.class);
    }
}
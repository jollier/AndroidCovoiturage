package com.fousduvolant.androidcovoiturage;

        import android.app.Application;
        import android.test.ApplicationTestCase;
        import android.test.suitebuilder.TestSuiteBuilder;

        import com.fousduvolant.androidcovoiturage.FousDuVolant;

        import junit.framework.Test;

        import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */



public class ApplicationTest extends ApplicationTestCase<Application> {
    public static Test suite() {
        return new TestSuiteBuilder(ApplicationTest.class).includeAllPackagesUnderHere().build();
    }
    public ApplicationTest() {
        super(Application.class);
    }
}
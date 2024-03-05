package eu.pinteam.foodorderingsystem.tests.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrderManagmentJunitTest.class, RestaurantJunitTest.class })
public class AllTests {

}

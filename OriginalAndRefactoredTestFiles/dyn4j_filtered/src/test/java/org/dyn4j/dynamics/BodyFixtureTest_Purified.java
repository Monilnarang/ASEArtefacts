package org.dyn4j.dynamics;

import org.dyn4j.collision.Fixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class BodyFixtureTest_Purified {

    private BodyFixture fixture;

    @Before
    public void setup() {
        fixture = new BodyFixture(Geometry.createUnitCirclePolygon(5, 0.5));
    }

    @Test
    public void getSetDensity_1() {
        TestCase.assertEquals(BodyFixture.DEFAULT_DENSITY, fixture.getDensity());
    }

    @Test
    public void getSetDensity_2_testMerged_2() {
        fixture.setDensity(1.0);
        TestCase.assertEquals(1.0, fixture.getDensity());
        fixture.setDensity(0.001);
        TestCase.assertEquals(0.001, fixture.getDensity());
        fixture.setDensity(1000);
        TestCase.assertEquals(1000.0, fixture.getDensity());
    }

    @Test
    public void getSetFriction_1() {
        TestCase.assertEquals(BodyFixture.DEFAULT_FRICTION, fixture.getFriction());
    }

    @Test
    public void getSetFriction_2_testMerged_2() {
        fixture.setFriction(1.0);
        TestCase.assertEquals(1.0, fixture.getFriction());
        fixture.setFriction(0.001);
        TestCase.assertEquals(0.001, fixture.getFriction());
        fixture.setFriction(0.0);
        TestCase.assertEquals(0.0, fixture.getFriction());
        fixture.setFriction(5);
        TestCase.assertEquals(5.0, fixture.getFriction());
    }

    @Test
    public void getSetRestitution_1() {
        TestCase.assertEquals(BodyFixture.DEFAULT_RESTITUTION, fixture.getRestitution());
    }

    @Test
    public void getSetRestitution_2_testMerged_2() {
        fixture.setRestitution(1.0);
        TestCase.assertEquals(1.0, fixture.getRestitution());
        fixture.setRestitution(0.001);
        TestCase.assertEquals(0.001, fixture.getRestitution());
        fixture.setRestitution(0.0);
        TestCase.assertEquals(0.0, fixture.getRestitution());
        fixture.setRestitution(5);
        TestCase.assertEquals(5.0, fixture.getRestitution());
    }

    @Test
    public void getSetRestitutionVelocity_1() {
        TestCase.assertEquals(BodyFixture.DEFAULT_RESTITUTION_VELOCITY, fixture.getRestitutionVelocity());
    }

    @Test
    public void getSetRestitutionVelocity_2_testMerged_2() {
        fixture.setRestitutionVelocity(0.0001);
        TestCase.assertEquals(0.0001, fixture.getRestitutionVelocity());
        fixture.setRestitutionVelocity(5.0);
        TestCase.assertEquals(5.0, fixture.getRestitutionVelocity());
        fixture.setRestitutionVelocity(0.0);
        TestCase.assertEquals(0.0, fixture.getRestitutionVelocity());
        fixture.setRestitutionVelocity(-1.0);
        TestCase.assertEquals(-1.0, fixture.getRestitutionVelocity());
    }
}

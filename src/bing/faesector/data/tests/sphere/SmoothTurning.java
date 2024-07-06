package bing.faesector.data.tests.sphere;

import org.lazywizard.lazylib.CollectionUtils;
import org.lazywizard.lazylib.LazyLib;
import org.lazywizard.lazylib.MathUtils;
import org.lazywizard.lazylib.VectorUtils;

public class SmoothTurning {

    private float targetAngle = 0;

    public SmoothTurning setTargetAngle(float targetAngle) {
        this.targetAngle = targetAngle;
        return this;
    }

    public float currentAngle = 0;
    public SmoothTurning SetChangeAmount(float changeAmount) {
        this.changeAmount = changeAmount;
        return this;
    }
    private float changeAmount = 10f;
    public SmoothTurning SetAcceleration(float acceleration) {
        this.acceleration = acceleration;
        return this;
    }
    private float acceleration = 0.1f;



    public SmoothTurning advance() {

        if (targetAngle == currentAngle) return this;

        float angleDelta = MathUtils.getShortestRotation(currentAngle, targetAngle) * acceleration;

        float mult = angleDelta * acceleration;

        currentAngle += changeAmount * mult;
        return this;
    }


}

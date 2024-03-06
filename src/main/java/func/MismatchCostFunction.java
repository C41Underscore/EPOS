package func;

import afu.org.checkerframework.checker.oigj.qual.O;
import config.Configuration;
import data.Vector;

import java.lang.Math;

public class MismatchCostFunction implements DifferentiableCostFunction<Vector>, HasGoal {

    private Vector costVector;


    public void setCostVector(Vector costVector) {
        this.costVector = costVector;
    }

    @Override
    public double calcCost(Vector vector) {
        Vector currentCost = this.costVector.cloneThis();
        currentCost.subtract(vector);
        return Math.log10(currentCost.normSqr());
    }

    @Override
    public Vector calcGradient(Vector vector) {
        Vector v = vector.cloneThis();
        for(int i = 0; i < v.getArray().length; i++)
        {
            double x = v.getValue(i) - this.costVector.getValue(i);
            double grad = 2./(Math.log(10.)*(x));
            v.setValue(i, grad);
        }
        return v;
    }

    @Override
    public void populateGoalSignal() {
        this.costVector = Configuration.goalSignalSupplier.get();
    }

    @Override
    public String toString() {
        return "Sensing Mission Mismatch";
    }

    @Override
    public String getLabel() {
        return "MIS";
    }
}

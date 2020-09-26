package Vehicles;

public abstract class AbstractVehicle implements Vehicle{
    protected int fuel;
    protected Color color;

    @Override
    public void fill(int fuelAmount){
        fuel += fuelAmount;
    }

    @Override
    public void paint(Color color){
        this.color = color;
    }
}

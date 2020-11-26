package Vehicles;

public class Truck extends AbstractVehicle implements FourWheelVehicle{
    private int storage = 0;

    public void store(int amount){
        storage += amount;
    }

    public void unstore(int amount){
        storage -= amount;
    }

    @Override
    public void drift() {
        if (fuel < 20){
            return;
        }
        fuel -= 20;
        // DRIFT!!11
    }
}

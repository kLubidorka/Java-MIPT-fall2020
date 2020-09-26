package Vehicles;

public class Car extends AbstractVehicle implements FourWheelVehicle {
    @Override
    public void drift() {
        if (fuel < 10){
            return;
        }
        fuel -= 10;
        // DRIFT!!11
    }
}

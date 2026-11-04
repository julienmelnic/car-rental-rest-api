package carrental;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CarController {

    private final List<Car> cars = new ArrayList<>();

    public CarController() {
        cars.add(new Car("AA11BB", "Ferrari", 100));
        cars.add(new Car("CC22DD", "BMW", 80));
        cars.add(new Car("EE33FF", "Renault", 50));
    }

    // Afficher toutes les voitures
    @GetMapping("/cars")
    public List<Car> listOfCars() {
        List<Car> availableCars = new ArrayList<>();

        for (Car car : cars) {
            if (!car.isRented()) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }

    // Afficher une voiture par son immatriculation
    @GetMapping("/cars/{plateNumber}")
    public Car getCar(
            @PathVariable("plateNumber") String plateNumber) {

        for (Car car : cars) {
            if (car.getPlateNumber().equals(plateNumber)) {
                return car;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Car not found");
    }

    // Louer ou rendre une voiture
    @PutMapping("/cars/{plateNumber}")
    public String rentOrReturnCar(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam("rent") boolean rent,
            @RequestBody(required = false) Dates dates) {

        Car car = getCar(plateNumber);

        if (rent) {

            if (car.isRented()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Car already rented");
            }

            if (dates == null || dates.getBegin() == null
                    || dates.getEnd() == null) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Rental dates are required");
            }

            car.setRented(true);
            return "Car rented successfully";
        }

        if (!car.isRented()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Car is not rented");
        }

        car.setRented(false);
        return "Car returned successfully";
    }
}

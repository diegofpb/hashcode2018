import model.CityModel;
import model.GameModel;
import model.TripModel;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public GameModel parseGame(List<String> lines){
        GameModel gameModel = new GameModel();

        String[] city = lines.get(0).split(" ");

        CityModel cityModel = new CityModel();
        cityModel.setRows( Integer.parseInt(city[0]) );
        cityModel.setColumns( Integer.parseInt(city[1]) );
        cityModel.setnVehicles( Integer.parseInt(city[2]) );
        cityModel.setnTrips( Integer.parseInt(city[3]) );
        cityModel.setBonus( Integer.parseInt(city[4]) );
        cityModel.setSteps( Integer.parseInt(city[5]) );

        gameModel.setCity(cityModel);
        gameModel.setTrips(new ArrayList<>());

        for (int i=1; i<lines.size(); i++) {
            String[] trip = lines.get(i).split(" ");

            TripModel tripModel = new TripModel();
            tripModel.setId(i-1);
            tripModel.setOriginX( Integer.parseInt(trip[0]) );
            tripModel.setOriginY( Integer.parseInt(trip[1]) );
            tripModel.setDestX( Integer.parseInt(trip[2]) );
            tripModel.setDestY( Integer.parseInt(trip[3]) );
            tripModel.setStepStart( Integer.parseInt(trip[4]) );
            tripModel.setStepFinish( Integer.parseInt(trip[5]) );

            gameModel.getTrips().add(tripModel);
        }

        return gameModel;
    }

}

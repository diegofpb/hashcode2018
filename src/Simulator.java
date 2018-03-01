import model.GameModel;
import model.TripModel;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator {

    private final static Double DISTANCE_WEIGHT = 0.5D;

    public static void main(String[] args) throws IOException {

        File dir = new File(args[0]);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {

                Path path = child.toPath();
                List<String> lines = Files.readAllLines(path, Charset.forName("UTF8"));
                GameModel gameModel = new Parser().parseGame(lines);

                PrintWriter writer = new PrintWriter("output/" + path.getFileName(), "UTF-8");
                new Simulator(gameModel, writer).simulate();
                writer.flush();

            }
        }

    }

    private GameModel gameModel;
    private Writer writer;

    Simulator(GameModel gameModel, Writer writer) {
        this.gameModel = gameModel;
        this.writer = writer;
    }

    private void simulate() {

        for(int car=0; car<gameModel.getCity().getnVehicles(); car++){
            simulateCar();
        }

    }

    private void simulateCar() {
        Point position = new Point(0,0);
        Integer cycle = 0;
        List<TripModel> carTrips = new ArrayList<>(gameModel.getTrips());
        List<TripModel> madeTrips = new ArrayList<>();

        for(int i=0; !carTrips.isEmpty() && i <= carTrips.size(); i++){
            Map<Double, TripModel> scoredTrips = getScoredTrips(position, carTrips);
            for (Map.Entry<Double, TripModel> tripEntry : scoredTrips.entrySet()) {
                TripModel trip = tripEntry.getValue();
                Integer distance = tripEntry.getKey().intValue();
                Integer startCycle = Math.max(distance+cycle, trip.getStepStart());

                if(startCycle + trip.getDistance() <= gameModel.getCity().getSteps()){
                    cycle = startCycle + trip.getDistance();
                    position = new Point(trip.getDestX(), trip.getDestY());
                    madeTrips.add(trip);
                    gameModel.getTrips().remove(trip);
                    carTrips.remove(trip);
                    break;
                } else {
                    carTrips.remove(trip);
                }
            }
        }

        try {
            writer.write(String.format("%d %s\n", madeTrips.size(),
                    madeTrips.stream().map(tripModel -> Integer.toString(tripModel.getId())).collect(Collectors.joining(" "))));
        } catch (IOException e) {}
    }

    private Map<Double, TripModel> getScoredTrips(Point position, List<TripModel> trips){
        SortedMap<Double, TripModel> result = new TreeMap<>(Comparator.reverseOrder());

        trips.forEach(tripModel -> {
            Integer distance = Math.abs(position.x - tripModel.getOriginX()) + Math.abs(position.y - tripModel.getOriginY());
            double normDistance = new Double(distance) / (gameModel.getCity().getRows() + gameModel.getCity().getColumns());
            Double tmpScore = tripModel.getScore() * (1-DISTANCE_WEIGHT) + (1/normDistance) * DISTANCE_WEIGHT;
            result.put(tmpScore, tripModel);
        });

        return result;
    }

}

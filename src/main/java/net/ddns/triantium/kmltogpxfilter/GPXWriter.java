package net.ddns.triantium.kmltogpxfilter;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import io.jenetics.jpx.GPX;
import io.jenetics.jpx.WayPoint;
import java.util.ArrayList;
import java.util.List;

public class GPXWriter {

    GPX convertFrom(List<Placemark> marks) {
        List<WayPoint> points = createWaypoints(marks);
        GPX.Builder builder = GPX.builder();
        points.forEach(p -> builder.addWayPoint(p));
        return builder.build();
    }

    private List<WayPoint> createWaypoints(List<Placemark> marks) {
        List<WayPoint> points = new ArrayList<>();
        for (Placemark mark : marks) {
            String name = mark.getName();
            Point point = (Point) mark.getGeometry();
            List<Coordinate> coordinates = point.getCoordinates();
            for (Coordinate coordinate : coordinates) {
                WayPoint waypoint = WayPoint.builder()
                        .name(name)
                        .cmt(mark.getDescription())
                        .build(coordinate.getLatitude(), coordinate.getLongitude());

                points.add(waypoint);
            }
        }
        return points;
    }

}

package net.ddns.triantium.kmltogpxfilter;

import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import java.util.ArrayList;
import java.util.List;

public class Mocker {

    static List<Placemark> getPlaceMarkList() {
        List<Placemark> marks = new ArrayList<Placemark>();
        int upper = (int) Math.round(Math.random() * 1000);
        for (int i = 0; i < upper; i++) {
            Placemark mark = KmlFactory.createPlacemark();
            marks.add(mark);
            mark.setName("mark_" + i);
            mark.createAndSetPoint()
                    .addToCoordinates(randomCoord(), randomCoord());
        }
        return marks;
    }

    private static double randomCoord() {
        //Precede South latitudes and West longitudes with a minus sign.
        //Latitudes range from -90 to 90.
        //Longitudes range from -180 to 180.
        return Math.random() * 90;
    }

}

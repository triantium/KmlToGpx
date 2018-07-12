package net.ddns.triantium.kmltogpxfilter;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KMLFilter {

    Kml kml;

    KMLFilter() {
    }

    public KMLFilter(File file) {
        this.readFile(file);
    }

    void readFile(File file) {
        kml = Kml.unmarshal(file);
        /*
        Object object = (Document) kml.getFeature();

        if (Document.class.isAssignableFrom(object.getClass())) {
            Document doc = (Document) object;
            List<Feature> features = doc.getFeature();
            for (Feature feature : features) {
                if (Folder.class.isAssignableFrom(feature.getClass())) {
                    Folder fold = (Folder) feature;
                    for (Feature feat : fold.getFeature()) {
                        if (Placemark.class.isAssignableFrom(feat.getClass())) {
                            Placemark placemark = (Placemark) feat;
                            System.out.println(placemark.getName());
                            Point point = (Point) placemark.getGeometry();
                            List<Coordinate> coordinates = point.getCoordinates();
                            for (Coordinate coordinate : coordinates) {
                                System.out.println(coordinate.getLatitude());
                                System.out.println(coordinate.getLongitude());
                                System.out.println(coordinate.getAltitude());
                            }
                        }
                    }
                }
            }
        }
         */
    }

    public List<Placemark> filterByPlaceMarkName(String regex) {
        List<Placemark> marks = searchPlaceMarks(kml.getFeature());

        return marks.stream()
                .parallel()
                .filter(mark -> mark.getName().matches(regex))
                .collect(Collectors.toList());
    }

    private List<Placemark> searchPlaceMarks(final Feature feature) {
        List<Placemark> marks = new ArrayList<>();
        if (Placemark.class.isAssignableFrom(feature.getClass())) {
            Placemark mark = (Placemark) feature;
            marks.add(mark);
        } else if (Folder.class.isAssignableFrom(feature.getClass())) {
            Folder doc = (Folder) feature;
            for (Feature feat : doc.getFeature()) {
                marks.addAll(searchPlaceMarks(feat));
            }
        } else if (Document.class.isAssignableFrom(feature.getClass())) {
            Document doc = (Document) feature;
            for (Feature feat : doc.getFeature()) {
                marks.addAll(searchPlaceMarks(feat));
            }
        }
        return marks;
    }
}

package net.ddns.triantium.kmltogpxfilter;

import de.micromata.opengis.kml.v_2_2_0.Placemark;
import io.jenetics.jpx.GPX;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author manuel müller <manuel.mueller@geekinbusiness.de>
 */
public class GPXWriterTest {

    private final String bigFilePath = "src/test/resources/kml/big.kml";

    public GPXWriterTest() {
    }

    @Test
    public void testSomeMethod() {
        GPXWriter gpxwriter = new GPXWriter();
        List<Placemark> marks = Mocker.getPlaceMarkList();
        GPX gpx = gpxwriter.convertFrom(marks);
        Assertions.assertThat(gpx).isNotNull();
        Assertions.assertThat(gpx.getWayPoints().size()).isEqualTo(marks.size());

    }

    @Test
    public void testWriteFile() throws IOException {
        KMLFilter filter = new KMLFilter();
        File file = new File(bigFilePath);
        Path path = Paths.get("nd.gpx");
        filter.readFile(file);
        String regex = "ND-[1-2][0-9][0-9][0-9]";
        List<Placemark> marks = filter.filterByPlaceMarkName(regex);

        GPXWriter gpxwriter = new GPXWriter();
        GPX gpx = gpxwriter.convertFrom(marks);
        Assertions.assertThat(gpx).isNotNull();
        Assertions.assertThat(gpx.getWayPoints().size()).isEqualTo(marks.size());
        GPX.write(gpx, path);

    }

}

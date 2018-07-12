package net.ddns.triantium.kmltogpxfilter;

import de.micromata.opengis.kml.v_2_2_0.Placemark;
import java.io.File;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author manuel müller <manuel.mueller@geekinbusiness.de>
 */
public class KMLFilterTest {

    private final String testFilePath = "src/test/resources/kml/test.kml";
    private final String bigFilePath = "src/test/resources/kml/big.kml";

    public KMLFilterTest() {
    }

    @Test
    public void ensureTestFileIsReadable() {
        File file = new File(testFilePath);
        Assertions.assertThat(file.canRead()).isTrue();
    }

    @Test
    public void testReadFile() {
        KMLFilter filter = new KMLFilter();
        File file = new File(testFilePath);
        filter.readFile(file);
        Assertions.assertThat(filter.kml).isNotNull();
    }

    @Test
    public void testFilter() {
        KMLFilter filter = new KMLFilter();
        File file = new File(testFilePath);
        filter.readFile(file);
        String regex = "ND-[1-2][0-9][0-9][0-9]";
        List<Placemark> marks = filter.filterByPlaceMarkName(regex);
        Assertions.assertThat(marks).isNotEmpty();
        for (Placemark mark : marks) {
            String name = mark.getName();
            Assertions.assertThat(name.matches(regex)).as("%s should match regex: \"%s\"", name, regex).isTrue();
        }

    }

    @Test
    public void performanceTest() {
        long maxTime = 10000;
        File bigFile = new File(bigFilePath);
        String regex = "ND-[1-2][0-9][0-9][0-9]";
        long startTime = System.currentTimeMillis();
        KMLFilter filter = new KMLFilter(bigFile);
        long readTime = System.currentTimeMillis();
        List<Placemark> marks = filter.filterByPlaceMarkName(regex);
        long endtime = System.currentTimeMillis();
        long filterTime = endtime - readTime;
        long totalTime = endtime - startTime;
        System.out.println("needed " + totalTime + "ms to filter");
        Assertions.assertThat(marks.size()).isEqualTo(127);
        Assertions.assertThat(totalTime).isLessThan(maxTime);

    }
}

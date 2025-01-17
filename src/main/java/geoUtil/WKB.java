package geoUtil;

import domain.HillShadeGrid;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.io.WKBWriter;

public class WKB {

    private final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
    private final WKBWriter wkbWriter = new WKBWriter();
    private final SRID srid = new SRID();

    public byte[] convertPolygonWKB(HillShadeGrid hillShadeGrid) {
        double x1 = hillShadeGrid.getX1();
        double y1 = hillShadeGrid.getY1();
        double x2 = hillShadeGrid.getX2();
        double y2 = hillShadeGrid.getY2();

        Coordinate coord1 = srid.convertPoint(x1, y1);
        Coordinate coord2 = srid.convertPoint(x1, y2);
        Coordinate coord3 = srid.convertPoint(x2, y2);
        Coordinate coord4 = srid.convertPoint(x2, y1);

        Coordinate[] coords =
            new Coordinate[]{coord1, coord2, coord3, coord4, coord1};
        LinearRing ring = geometryFactory.createLinearRing(coords);
        LinearRing holes[] = null;

        return wkbWriter.write(geometryFactory.createPolygon(ring, holes));
    }
}

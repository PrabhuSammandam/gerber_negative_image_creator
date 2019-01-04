/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.GerberNegativeCreator;

import java.awt.BasicStroke;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_ROUND;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.camfilelibrary.GeometryList;
import org.jinjuamla.gerber.common.Region;
import org.jinjuamla.gerber.geometries.CircleGeometry;
import org.jinjuamla.gerber.geometries.DrillGeometry;
import org.jinjuamla.gerber.geometries.LineGeometry;
import org.jinjuamla.gerber.geometries.ObroundGeometry;
import org.jinjuamla.gerber.geometries.RectGeometry;
import org.jinjuamla.gerber.geometries.RegionGeometry;

/**
 *
 * @author psammand
 */
public class GerberRenderer {

    public static void RenderGeometries( Graphics2D g, Color color, GeometryList geometryList ) {

        Paint oldPaint = g.getPaint();
        g.setPaint( color );

        geometryList.forEach( (geo) -> {
            drawGeometries( g, geo );
        } );
        g.setPaint( oldPaint );
    }

    private static void drawGeometries( Graphics2D g2d, Geometry geometry ) {
        if ( geometry instanceof RectGeometry ) {
            renderRectangle( g2d, ( RectGeometry ) geometry );
        } else if ( geometry instanceof CircleGeometry ) {
            renderCircle( g2d, ( CircleGeometry ) geometry );
        } else if ( geometry instanceof ObroundGeometry ) {
            renderObround( g2d, ( ObroundGeometry ) geometry );
        } else if ( geometry instanceof LineGeometry ) {
            renderLine( g2d, ( LineGeometry ) geometry );
        } else if ( geometry instanceof RegionGeometry ) {
            renderRegion( g2d, ( RegionGeometry ) geometry );
        } else if ( geometry instanceof DrillGeometry ) {
            renderDrill( g2d, ( DrillGeometry ) geometry );
        }
    }

    protected static void renderRectangle( Graphics2D g2d, RectGeometry primitive ) {
        Coordinate ul = primitive.lowerLeft();

        g2d.fill( new Rectangle2D.Double( ul.getX(), ul.getY(), primitive.getWidth(), primitive.getHeight() ) );
    }

    private static void renderCircle( Graphics2D g2d, CircleGeometry circleGeometry ) {
        Coordinate position = circleGeometry.getPosition();
        double radius = circleGeometry.getDiameter() / 2;

        double x = position.getX() - radius;
        double y = position.getY() - radius;
        double width = circleGeometry.getDiameter();

        if ( circleGeometry.getHoleDiameter() > 0 ) {
            Stroke oldStroke = g2d.getStroke();

            g2d.setStroke( new BasicStroke(  ( float ) (circleGeometry.getDiameter() - circleGeometry.getHoleDiameter())) );

            g2d.draw( new Ellipse2D.Double( x, y, width, width ) );
            
            g2d.setStroke( oldStroke );
        } else {
            g2d.fill( new Ellipse2D.Double( x, y, width, width ) );
        }
//        if ( circleGeometry.getHoleDiameter() > 0 ) {
//            Composite oldComposite = g2d.getComposite();
//
//            g2d.setComposite( AlphaComposite.getInstance( SRC_OUT, 0.0f ) );
//            double holeDiameter = circleGeometry.getHoleDiameter();
//            g2d.fill( new Ellipse2D.Double( position.getX() - holeDiameter / 2, position.getY() - holeDiameter / 2, holeDiameter, holeDiameter ) );
//
//            g2d.setComposite( oldComposite );
//        }
    }

    private static void renderObround( Graphics2D g2d, ObroundGeometry obroundGeometry ) {
        renderCircle( g2d, obroundGeometry.getCircle1() );
        renderCircle( g2d, obroundGeometry.getCircle2() );
        renderRectangle( g2d, obroundGeometry.getRectangle() );
    }

    private static void renderLine( Graphics2D g2d, LineGeometry primitive ) {
        if ( primitive.getStrokeGObj() instanceof CircleGeometry ) {
            CircleGeometry circlePrimitive = ( CircleGeometry ) primitive.getStrokeGObj();

            float lineWidth = ( float ) circlePrimitive.getDiameter();
            double x0 = primitive.getStartPt().getX();
            double y0 = primitive.getStartPt().getY();
            double x1 = primitive.getEndPt().getX();
            double y1 = primitive.getEndPt().getY();

            Stroke oldStroke = g2d.getStroke();

            g2d.setStroke( new BasicStroke( lineWidth, CAP_ROUND, JOIN_ROUND ) );
            g2d.draw( new Line2D.Double( x0, y0, x1, y1 ) );
            g2d.setStroke( oldStroke );
        }
    }

    private static void renderRegion( Graphics2D g2d, RegionGeometry primitive ) {
        Region region = primitive.getRegion();

        GeneralPath path = new GeneralPath();

        Coordinate startPt = region.getPoints().get( 0 );

        path.moveTo( startPt.getX(), startPt.getY() );

        for ( int i = 1; i < region.getPoints().size(); i++ ) {
            Coordinate currentPt = region.getPoints().get( i );
            path.lineTo( currentPt.getX(), currentPt.getY() );
        }
        path.closePath();

        g2d.fill( path );
    }

    private static void renderDrill( Graphics2D g2d, DrillGeometry drillGeometry ) {
        Coordinate position = drillGeometry.getPosition();
        double radius = drillGeometry.radius();

        double x = position.getX() - radius;
        double y = position.getY() - radius;
        double width = drillGeometry.radius() * 2;

        g2d.fill( new Ellipse2D.Double( x, y, width, width ) );
    }
}

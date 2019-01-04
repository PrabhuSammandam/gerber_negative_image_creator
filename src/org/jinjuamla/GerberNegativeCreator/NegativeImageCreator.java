/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.GerberNegativeCreator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Consts;
import org.jinjuamla.camfilelibrary.FileHelper;
import static org.jinjuamla.camfilelibrary.FileHelper.FindFileType;
import org.jinjuamla.camfilelibrary.GeometryList;
import org.jinjuamla.camfilelibrary.GerberImageWriter;
import org.jinjuamla.camfilelibrary.Overlay;
import org.jinjuamla.camfilelibrary.OverlayCollection;
import org.jinjuamla.camfilelibrary.enums.PcbBoardSideEnum;
import org.jinjuamla.camfilelibrary.enums.PcbFileTypeEnum;
import org.jinjuamla.camfilelibrary.enums.PcbLayerTypeEnum;
import org.jinjuamla.excellon.ExcellonParser;
import org.jinjuamla.gerber.GerberParser;

/**
 *
 * @author psammand
 */
public class NegativeImageCreator {

    private static final Logger s_LOG = Logger.getLogger( NegativeImageCreator.class.getName() );

    public static Overlay getOverlay( String filePath ) {
        Overlay overlay = null;
        PcbFileTypeEnum fileType = FindFileType( filePath );

        switch ( fileType ) {
            case Gerber: {
                GerberParser gerberParser = new GerberParser();
                gerberParser.Decode( filePath );
                GeometryList geometryList = gerberParser.getGeometryList();

                if ( geometryList != null ) {
                    overlay = new Overlay();
                    overlay.setGeometryList( geometryList );
                    overlay.setUnit( gerberParser.getGerberState().getUnitMode() );
                    overlay.setFilePath( filePath );
                    overlay.setLayerType( FileHelper.FindLayerType( filePath ) );
                    overlay.setBoardSide( FileHelper.FindBoardSide( filePath ) );
                    overlay.setColor( Color.WHITE );
                }
            }
            break;

            case Excellon: {
                ExcellonParser excellonParser = new ExcellonParser();
                excellonParser.Parse( filePath );
                GeometryList geometryList = excellonParser.getGeometryList();
                if ( geometryList != null && geometryList.size() > 0 ) {
                    overlay = new Overlay();
                    overlay.setGeometryList( geometryList );
                    overlay.setUnit( excellonParser.getDecoderState().getUnits() );
                    overlay.setFilePath( filePath );
                    overlay.setLayerType( FileHelper.FindLayerType( filePath ) );
                    overlay.setBoardSide( FileHelper.FindBoardSide( filePath ) );
                    overlay.setColor( Color.BLUE );
                }
            }
            break;

            default:
                throw new AssertionError();
        }

        return overlay;
    }

    public NegativeImageCreator() {
        s_LOG.setLevel( Level.ALL );
    }

    void createImage( CommandOptions commandOptions, StepAndRepeatValues stepAndRepeatValues ) {
        s_LOG.entering( this.getClass().getName(), "createImage" );
        int xCopy = stepAndRepeatValues.getxCopies();
        int yCopy = stepAndRepeatValues.getyCopies();

        System.out.println( "Creating BOTTOM Layer Image" );
        BufferedImage bottomLayerImage = createBottomLayerImage( commandOptions );

        if ( bottomLayerImage == null ) {
            s_LOG.severe( "Failed to create BOTTOM Layer Image" );
            return;
        }
        s_LOG.info( "Created BOTTOM Layer Image" );

        s_LOG.info( "Creating Negative Background Image" );
        BufferedImage negativeImage = convertToNegativeImage( bottomLayerImage, commandOptions );
        if ( negativeImage == null ) {
            s_LOG.severe( "Failed to create Negative Background Image" );
            return;
        }

        s_LOG.info( "Created Negative Background Image" );
        BufferedImage finalImage = null;

        if ( xCopy * yCopy > 2 ) {
            s_LOG.info( "Creating TILED Image" );
            BufferedImage titledImage = titleImage( negativeImage, commandOptions, stepAndRepeatValues );

            if ( titledImage != null ) {
                s_LOG.info( "Created TILED Image" );

                s_LOG.info( "Creating LAYER ALIGN Image" );
                finalImage = addLayerAlignment( titledImage, commandOptions );

                if ( finalImage == null ) {
                    s_LOG.severe( "Failed to create LAYER ALIGN Image" );
                    return;
                }

                s_LOG.info( "Created LAYER ALIGN Image" );
            } else {
                s_LOG.severe( "Failed to create TILED Image" );
                return;
            }
        } else {
            s_LOG.info( "Creating LAYER ALIGN Image" );
            BufferedImage layerAlign = addLayerAlignment( negativeImage, commandOptions );

            if ( layerAlign == null ) {
                s_LOG.severe( "Failed to create LAYER ALIGN Image" );
                return;
            }
            s_LOG.info( "Created LAYER ALIGN Image" );

            s_LOG.info( "Creating TILED Image" );
            finalImage = titleImage( layerAlign, commandOptions, stepAndRepeatValues );
            if ( finalImage == null ) {
                s_LOG.severe( "Failed to create TILED Image" );
                return;
            }
            s_LOG.info( "Created TILED Image" );
        }

        GerberImageWriter gerberImageCreator = new GerberImageWriter();
        gerberImageCreator.saveImageAsPng( finalImage, commandOptions.getOutFilePath(), commandOptions.getDpi() );
        //gerberImageCreator.saveImageAsBmp( finalImage, commandOptions.getOutFilePath(), commandOptions.getDpi() );
        s_LOG.info( "Created FINAL IMAGE" );
    }

    BufferedImage titleImage( BufferedImage image, CommandOptions commandOptions, StepAndRepeatValues stepAndRepeatValues ) {
        int xCopies = stepAndRepeatValues.getxCopies();
        int yCopies = stepAndRepeatValues.getyCopies();
        double xGap = stepAndRepeatValues.getxGap();
        double yGap = stepAndRepeatValues.getyGap();
        double a4_width = 210;
        double a4_height = 297;
        double scale = commandOptions.getDpi() / 25.4;

        double bitmapWidth = image.getWidth();
        double bitmapHeight = image.getHeight();

        System.out.println( String.format( "Bitmap Width[MM] %f, Bitmap Height[MM] %f", bitmapWidth/scale, bitmapHeight/scale ) );

        if ( ((bitmapWidth / scale * xCopies) + (xGap * (xCopies - 1)) + (2 * commandOptions.getPaperXOffset())) > a4_width ) {
            return null;
        }

        if ( ((bitmapHeight / scale * yCopies) + (yGap * (yCopies - 1)) + (2 * commandOptions.getPaperYOffset())) > a4_height ) {
            return null;
        }

        double width = bitmapWidth * xCopies + 2 * commandOptions.getPaperXOffset() * scale;

        if ( xCopies > 1 ) {
            width += xGap * scale * (xCopies - 1);
        }

        double height = bitmapHeight * yCopies + 2 * commandOptions.getPaperYOffset() * scale;

        if ( yCopies > 1 ) {
            height += yGap * scale * (yCopies - 1);
        }

        BufferedImage newImage = new BufferedImage( ( int ) width, ( int ) height, TYPE_INT_RGB );
        Graphics2D g2d = newImage.createGraphics();
        setRenderingHints( g2d );

        g2d.setPaint( Color.WHITE );
        g2d.fillRect( 0, 0, ( int ) width, ( int ) height );

        g2d.setPaint( Color.BLUE );
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke( new BasicStroke( 2 ) );
        g2d.drawRect( 0, 0, (( int ) width) - 1, (( int ) height) - 1 );

        AffineTransform oldTextTransform = g2d.getTransform();
        g2d.scale( 2, 2 );
        char text[] = { 'T', 'O', 'P' };
        g2d.drawChars( text, 0, 3, 150, 150 );
        g2d.setTransform( oldTextTransform );
        g2d.setStroke( oldStroke );

        double xOffset = 0;
        double yOffset = 0;

        for ( int i = 0; i < xCopies; i++ ) {
            for ( int j = 0; j < yCopies; j++ ) {
                xOffset = bitmapWidth * i + xGap * scale * i;
                yOffset = bitmapHeight * j + yGap * scale * j;

                System.out.println( String.format( "xOff[MM] %f, yOff[MM] %f", xOffset / scale, yOffset / scale ) );

                AffineTransform oldTransform = g2d.getTransform();

                g2d.translate( xOffset + commandOptions.getPaperXOffset() * scale, yOffset + commandOptions.getPaperYOffset() * scale );
                g2d.drawImage( image, 0, 0, null );

                g2d.setTransform( oldTransform );
            }
        }

        g2d.dispose();

        return newImage;
    }

    BufferedImage convertToNegativeImage( BufferedImage image, CommandOptions options ) {
        double scale = options.getDpi() / 25.4;

        // size in mm for the surrounding box
        double surroundX = 1;
        double surroundY = 1;

        double bitmapWidth = image.getWidth() + 2 * surroundX * scale;
        double bitmapHeight = image.getHeight() + 2 * surroundY * scale;

        BufferedImage newImage = new BufferedImage( ( int ) bitmapWidth, ( int ) bitmapHeight, TYPE_INT_RGB );
        Graphics2D g2d = newImage.createGraphics();
        setRenderingHints( g2d );
        g2d.setPaint( Color.BLUE );
        g2d.fillRect( 0, 0, ( int ) bitmapWidth, ( int ) bitmapHeight );

        g2d.translate( surroundX * scale, surroundY * scale );
        g2d.drawImage( image, 0, 0, null );

        g2d.dispose();

        return newImage;
    }

    BufferedImage addLayerAlignment( BufferedImage image, CommandOptions options ) {
        double scale = options.getDpi() / 25.4;

        double circleDiameter = 5;
        double lineLength = 10;
        double penWidth = .2;

        double bitmapWidth = image.getWidth();
        double bitmapHeight = image.getHeight() + 2 * (lineLength) * scale;

        double scaledOriginX = 0;
        double scaledOriginY = lineLength * scale;

        BufferedImage newImage = new BufferedImage( ( int ) bitmapWidth, ( int ) bitmapHeight, TYPE_INT_RGB );
        Graphics2D g2d = newImage.createGraphics();
        setRenderingHints( g2d );

        g2d.setPaint( Color.WHITE );
        g2d.fillRect( 0, 0, ( int ) bitmapWidth, ( int ) bitmapHeight );

        g2d.setPaint( Color.BLUE );

        drawCircle( g2d, bitmapWidth, bitmapHeight, scale, lineLength, circleDiameter, penWidth );

        g2d.translate( scaledOriginX, scaledOriginY );
        g2d.drawImage( image, 0, 0, null );

        g2d.dispose();

        return newImage;

    }

    void drawCircle( Graphics2D g2d, double width, double height, double scale, double lineLength, double circleDiameter, double penWidth ) {
        AffineTransform oldtransform = g2d.getTransform();
        Stroke oldStroke = g2d.getStroke();

        g2d.setStroke( new BasicStroke( ( float ) penWidth ) );
        g2d.scale( scale, scale );

        double widthInMM = width / scale;
        double heightInMM = height / scale;
        double lineCenter = lineLength / 2;
        double radius = circleDiameter / 2;

        // top left
        g2d.draw( new Ellipse2D.Double( lineCenter - radius, lineCenter - radius, circleDiameter, circleDiameter ) );
        g2d.draw( new Line2D.Double( 0, lineCenter, lineLength, lineCenter ) );
        g2d.draw( new Line2D.Double( widthInMM - lineLength, lineCenter, widthInMM, lineCenter ) );

        // top right
        g2d.draw( new Ellipse2D.Double( widthInMM - lineCenter - radius, lineCenter - radius, circleDiameter, circleDiameter ) );
        g2d.draw( new Line2D.Double( lineCenter, 0, lineCenter, lineLength ) );
        g2d.draw( new Line2D.Double( widthInMM - lineCenter, 0, widthInMM - lineCenter, lineLength ) );

        //bottom left
        g2d.draw( new Ellipse2D.Double( lineCenter - radius, heightInMM - lineCenter - radius, circleDiameter, circleDiameter ) );
        g2d.draw( new Line2D.Double( 0, heightInMM - lineCenter, lineLength, heightInMM - lineCenter ) );
        g2d.draw( new Line2D.Double( widthInMM - lineLength, heightInMM - lineCenter, widthInMM, heightInMM - lineCenter ) );

        //bottom right
        g2d.draw( new Ellipse2D.Double( widthInMM - lineCenter - radius, heightInMM - lineCenter - radius, circleDiameter, circleDiameter ) );
        g2d.draw( new Line2D.Double( lineCenter, heightInMM - lineLength, lineCenter, heightInMM ) );
        g2d.draw( new Line2D.Double( widthInMM - lineCenter, heightInMM - lineLength, widthInMM - lineCenter, heightInMM ) );

        g2d.setTransform( oldtransform );
        g2d.setStroke( oldStroke );
    }

    private void setRenderingHints( Graphics2D pG2D ) {
    // Turn on antialiasing
    pG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    pG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    // Pick text quality instead of speed
    pG2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    // Turn dithering on. Dithering approximates color values by drawing groups of pixels of similar colors
    pG2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    // Alpha composites for Quality
    pG2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    // Turn the computation of fractional character dimensions on.
    //   Fractional character dimensions lead to better placement of characters
    pG2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    // Select quality or speed for color rendering.
    pG2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    // Interpolating pixels when scaling or rotating images.
    pG2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    // Combining strokes
    pG2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        
//        g2d.setRenderingHint( KEY_ANTIALIASING, VALUE_ANTIALIAS_ON );
//        g2d.setRenderingHint( KEY_RENDERING, VALUE_RENDER_QUALITY );
//        g2d.setRenderingHint( KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY );
    }

    private BufferedImage createBottomLayerImage( CommandOptions commandOptions ) {
        ArrayList<String> bottomImageFiles = new ArrayList<>();

        // get the bottom and drill files
        boolean bottomImageFilesCreated = getBottomImageFiles( bottomImageFiles, commandOptions.getGerberFiles() );

        if ( !bottomImageFilesCreated || bottomImageFiles.size() <= 0 ) {
            return null;
        }

        // convert the file path to overlay object
        OverlayCollection overlayCollection = getOverlayCollection( bottomImageFiles );

        if ( overlayCollection == null || overlayCollection.size() <= 0 ) {
            return null;
        }
        int unitMode = overlayCollection.get( 0 ).getUnit(); // get the unit from the overlay object
        BoundingBox bounds = overlayCollection.getBounds(); // get the overall bounds of the overlay collection object

        double scale = (unitMode == Consts.UNIT_MODE_INCH) ? commandOptions.getDpi() : commandOptions.getDpi() / 25.4;

        double widthInMM = (unitMode == Consts.UNIT_MODE_INCH) ? bounds.getWidth() * 25.4 : bounds.getWidth();
        double heightInMM = (unitMode == Consts.UNIT_MODE_INCH) ? bounds.getHeight() * 25.4 : bounds.getHeight();

        double originX = bounds.getMinX();
        double originY = bounds.getMinY();

        double widthInPx = widthInMM * scale;
        double heightInPx = heightInMM * scale;
        double scaledOriginX = originX * scale;
        double scaledOriginY = originY * scale;
        AffineTransform affineTransform = new AffineTransform( scale, 0, 0, -scale, -1 * scaledOriginX, heightInPx + scaledOriginY );

        BufferedImage image = new BufferedImage( ( int ) widthInPx, ( int ) heightInPx, TYPE_INT_RGB );
        Graphics2D g2d = image.createGraphics();
        setRenderingHints( g2d );

        g2d.setPaint( Color.WHITE );
        g2d.fillRect( 0, 0, ( int ) widthInPx, ( int ) heightInPx );

        g2d.setTransform( affineTransform );

        g2d.setPaint( Color.BLUE );
        g2d.fill( new Rectangle2D.Double( originX, originY, widthInMM, heightInMM ) );

        for ( Overlay overlay : overlayCollection ) {
            GerberRenderer.RenderGeometries( g2d, overlay.getColor(), overlay.getGeometryList() );
        }

        g2d.dispose();

        return image;
    }

    private boolean getBottomImageFiles( ArrayList<String> bottomImageFiles, ArrayList<String> srcFilesList ) {
        String tempFilePath = FileHelper.getLayerForType( PcbBoardSideEnum.Bottom, PcbLayerTypeEnum.Copper, srcFilesList );
        if ( tempFilePath == null || tempFilePath.isEmpty() ) {
            return false;
        }
        bottomImageFiles.add( tempFilePath );
        tempFilePath = FileHelper.getLayerForType( PcbBoardSideEnum.Unknown, PcbLayerTypeEnum.Drill, srcFilesList );
        if ( tempFilePath != null && !tempFilePath.isEmpty() ) {
            bottomImageFiles.add( tempFilePath );
        }

        return true;
    }

    private OverlayCollection getOverlayCollection( ArrayList<String> overlayFiles ) {
        OverlayCollection overlayCollection = new OverlayCollection();

        for ( String overlayFile : overlayFiles ) {
            Overlay overlay = getOverlay( overlayFile );

            if ( overlay != null && overlay.getGeometryList() != null && overlay.getGeometryList().size() > 0 ) {
                overlayCollection.add( overlay );
            }
        }

        return overlayCollection;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.camfilelibrary;

//import com.sun.imageio.plugins.bmp.BMPMetadata;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import org.w3c.dom.Element;

/**
 *
 * @author psammand
 */
public class GerberImageWriter {

    public void saveImageToFile( BufferedImage image, String filePath, int dpi, String imageType ) {
        if ( "jpg".equals( imageType ) ) {
            saveImageAsJpeg( image, filePath, dpi );
        } else if ( "png".equals( imageType ) ) {
            saveImageAsJpeg( image, filePath, dpi );
        } else if ( "bmp".equals( imageType ) ) {
            saveImageAsBmp( image, filePath, dpi );
        }
    }

    public void saveImageAsJpeg( BufferedImage image, String filePath, int dpi ) {
        File destinationFile = new File( filePath );

        ImageWriter imageWriter = ImageIO.getImageWritersBySuffix( "jpeg" ).next();
        try ( ImageOutputStream ios = ImageIO.createImageOutputStream( destinationFile ) ) {
            imageWriter.setOutput( ios );
            ImageWriteParam jpegParams = imageWriter.getDefaultWriteParam();

            IIOMetadata data = imageWriter.getDefaultImageMetadata( new ImageTypeSpecifier( image ), jpegParams );
            Element tree = ( Element ) data.getAsTree( "javax_imageio_jpeg_image_1.0" );
            Element jfif = ( Element ) tree.getElementsByTagName( "app0JFIF" ).item( 0 );
            jfif.setAttribute( "Xdensity", Integer.toString( dpi ) );
            jfif.setAttribute( "Ydensity", Integer.toString( dpi ) );
            jfif.setAttribute( "resUnits", "1" ); // density is dots per inch
            data.mergeTree( "javax_imageio_jpeg_image_1.0", tree ); // Write and clean up

            imageWriter.write( data, new IIOImage( image, null, data ), jpegParams );
        } catch ( IOException ex ) {
            Logger.getLogger( GerberImageWriter.class.getName() ).log( Level.SEVERE, null, ex );
        }
        imageWriter.dispose();
    }

    public void saveImageAsPng( BufferedImage image, String file, int dpi ) {

        File filePath = new File( file );

        filePath.delete();

        final String formatName = "png";

        for ( Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName( formatName ); iw.hasNext(); ) {
            ImageWriter writer = iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType( BufferedImage.TYPE_INT_RGB );
            IIOMetadata metadata = writer.getDefaultImageMetadata( typeSpecifier, writeParam );
            if ( metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported() ) {
                continue;
            }

            Element tree = ( Element ) metadata.getAsTree( "javax_imageio_png_1.0" );
            IIOMetadataNode physNode = new IIOMetadataNode( "pHYs" );
            physNode.setAttribute( "pixelsPerUnitXAxis", Integer.toString( ( int ) (dpi * 39.3701) ) );
            physNode.setAttribute( "pixelsPerUnitYAxis", Integer.toString( ( int ) (dpi * 39.3701) ) );
            physNode.setAttribute( "unitSpecifier", "meter" );
            tree.appendChild( physNode );
            try {
                metadata.mergeTree( "javax_imageio_png_1.0", tree ); // Write and clean up
            } catch ( IIOInvalidTreeException ex ) {
                Logger.getLogger( GerberImageWriter.class.getName() ).log( Level.SEVERE, null, ex );
            }

            try {
                try ( ImageOutputStream stream = ImageIO.createImageOutputStream( filePath ) ) {
                    writer.setOutput( stream );
                    writer.write( metadata, new IIOImage( image, null, metadata ), writeParam );
                }
            } catch ( IOException ex ) {
                Logger.getLogger( GerberImageWriter.class.getName() ).log( Level.SEVERE, null, ex );
            } finally {

            }
            break;
        }
    }

    public void saveImageAsBmp( BufferedImage image, String filePath, int dpi ) {
        File destinationFile = new File( filePath );

        ImageWriter imageWriter = ImageIO.getImageWritersBySuffix( "bmp" ).next();
        try ( ImageOutputStream ios = ImageIO.createImageOutputStream( destinationFile ) ) {
            imageWriter.setOutput( ios );
            ImageWriteParam jpegParams = imageWriter.getDefaultWriteParam();

//            BMPMetadata data = (BMPMetadata)imageWriter.getDefaultImageMetadata( new ImageTypeSpecifier( image ), jpegParams );
//            data.xPixelsPerMeter = ( int ) (dpi * 39.3701);
//            data.yPixelsPerMeter = ( int ) (dpi * 39.3701);
            // old - comment - start
//            Element tree = ( Element ) data.getAsTree( "javax_imageio_bmp_1.0" );
//            Element jfif = ( Element ) tree.getElementsByTagName( "PixelsPerMeter" ).item( 0 );
//            Element x = ( Element ) jfif.getElementsByTagName( "X" ).item( 0 );
//            x.setNodeValue( Integer.toString( ( int ) (dpi * 39.3701) ) );
//            Element y = ( Element ) jfif.getElementsByTagName( "Y" ).item( 0 );
//            y.setNodeValue( Integer.toString( ( int ) (dpi * 39.3701) ) );
//            data.mergeTree( "javax_imageio_bmp_1.0", tree ); // Write and clean up
            // old - comment - end
//            imageWriter.write( data, new IIOImage( image, null, data ), jpegParams );
        } catch ( IOException ex ) {
            Logger.getLogger( GerberImageWriter.class.getName() ).log( Level.SEVERE, null, ex );
        }
        imageWriter.dispose();
    }

}

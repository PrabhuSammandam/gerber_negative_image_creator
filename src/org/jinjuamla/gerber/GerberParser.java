/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber;

import java.io.File;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import org.jinjuamla.camfilelibrary.FileHelper;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.camfilelibrary.GeometryList;
import org.jinjuamla.gerber.cmds.GCmd;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createADStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createAMStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createCommentStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createDStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createFSStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createGStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createLPStatement;
import static org.jinjuamla.gerber.cmds.GCmdFactory.createMOStatement;

/**
 *
 * @author psammand
 */
public class GerberParser {

    static Pattern PATTERN_COMMENT = compile( "^G0?4(?<comment>[^*]*)(\\*)?" );
    static Pattern PATTERN_G = compile( "^G(?<code>[0-9]{1,10})\\*$" );
    static Pattern PATTERN_D = compile( "^(X(?<x>[\\+-]?\\d+))?(Y(?<y>[\\+-]?\\d+))?(I(?<i>[\\+-]?\\d+))?(J(?<j>[\\+-]?\\d+))?D(?<opcode>[0-9]{1,10})\\*$" );
    static Pattern PATTERN_FS = compile( "^%FS(?<zero>(L|T))(?<notation>(A|I))X(?<xn>[0-6])(?<xm>[0-6])Y(?<yn>[0-6])(?<ym>[0-6])\\*%$" );
    static Pattern PATTERN_MO = compile( "^%MO(?<unit>(MM|IN))\\*%$" );
    static Pattern PATTERN_LP = compile( "^%LP(?<polarity>(C|D))\\*%$" );
    static Pattern PATTERN_AD = compile( "^%ADD(?<dcode>\\d+)(?<name>[a-zA-Z_$\\.][a-zA-Z_$\\.0-9+\\-]*)[,]?(?<modifiers>[^,%]*)?\\*%$" );
    static Pattern PATTERN_AM = compile( "^%AM(?<name>[a-zA-Z_$\\.][a-zA-Z_$\\.0-9+\\-]+)\\*(?<macro>[^%]*)\\*%$" );
    //static Pattern PATTERN_ATTRIBUTE = Pattern.compile("^%(?<name>T[F|A|D])\\.(?<attr_name>[a-zA-Z_$\\.][a-zA-Z_$\\.0-9+\\-]+)[,]?(?<attr_value>.*)?\\*%$");

    private final GeometryList _geometryList = new GeometryList();
    private final List<GCmd> _cmdList = new ArrayList<>();
    private final GraphicsState gerberState = new GraphicsState();

    public GerberParser() {
    }

    public GraphicsState getGerberState() {
        return gerberState;
    }

    public List<GCmd> getCommandList() {
        return _cmdList;
    }

    public GeometryList getGeometryList() {
        return _geometryList;
    }

    public void Decode( String filePath ) {
        File fileObj = new File( filePath );

        ParseInternal( fileObj );

        for ( GCmd stmt : _cmdList ) {
            if ( stmt != null ) {
                Geometry[] geoArray = stmt.decode( gerberState );

                if ( geoArray != null ) {
                    for ( Geometry geo : geoArray ) {
                        if ( geo != null ) {
                            _geometryList.add( geo );
                        }
                    }
                }
            }
        }
    }

    public void ParseInternal( File file ) {
        List<String> lineList = FileHelper.readFileAsStringList( file );
        int lineCount = 0;
        String multiLine = "";
        String currentLine;
        Matcher currentMatch;
        boolean isInMultiLine = false;

        for ( String line : lineList ) {
            //System.out.println( "Parsing " + line );

            lineCount++;
            currentLine = line.trim();

            // skip empty lines
            if ( currentLine.length() <= 0 ) {
                continue;
            }

            if ( isInMultiLine ) {
                multiLine += currentLine;

                if ( currentLine.endsWith( "%" ) ) {
                    currentLine = multiLine;
                    multiLine = "";
                    isInMultiLine = false;
                } else {
                    continue;
                }
            }

            // deal with multi-line parameters
            if ( currentLine.startsWith( "%" ) && !currentLine.endsWith( "%" ) && !currentLine.substring( 1 ).contains( "%" ) ) {
                multiLine = currentLine;
                isInMultiLine = true;
                continue;
            }

            currentMatch = PATTERN_COMMENT.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createCommentStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_FS.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createFSStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_G.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createGStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_D.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createDStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_MO.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createMOStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_LP.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createLPStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_AD.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createADStatement( currentMatch ) );
                continue;
            }

            currentMatch = PATTERN_AM.matcher( currentLine );
            if ( currentMatch.matches() ) {
                _cmdList.add( createAMStatement( currentMatch ) );
                continue;
            }

            out.println( "Unprocessed Line at : " + lineCount );
        }
    }


}

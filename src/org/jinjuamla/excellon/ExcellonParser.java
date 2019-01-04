/*
 * Copyright (C) 2016 psammand
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jinjuamla.excellon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import org.jinjuamla.camfilelibrary.FileHelper;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.camfilelibrary.GeometryList;
import org.jinjuamla.excellon.cmds.ECmd;
import org.jinjuamla.excellon.cmds.ECmdAbsoulteMode;
import org.jinjuamla.excellon.cmds.ECmdComment;
import org.jinjuamla.excellon.cmds.ECmdCoordinate;
import org.jinjuamla.excellon.cmds.ECmdDrillMode;
import org.jinjuamla.excellon.cmds.ECmdEndOfProgram;
import org.jinjuamla.excellon.cmds.ECmdFactory;
import org.jinjuamla.excellon.cmds.ECmdHdrBegin;
import org.jinjuamla.excellon.cmds.ECmdHdrEnd;
import org.jinjuamla.excellon.cmds.ECmdIncrmentalMode;
import org.jinjuamla.excellon.cmds.ECmdMeasurementMode;
import org.jinjuamla.excellon.cmds.ECmdRepeatHole;
import org.jinjuamla.excellon.cmds.ECmdRewindStop;
import org.jinjuamla.excellon.cmds.ECmdRoutCmd;
import org.jinjuamla.excellon.cmds.ECmdUnitMode;

/**
 *
 * @author psammand
 */
public class ExcellonParser {

    static Pattern PATTERN_COMMENT = compile( "^R(?<count>[0-9]{1,4})(X(?<xoff>[+\\-]?\\d*\\.?\\d*))?(Y(?<yoff>[+\\-]?\\d*\\.?\\d*))?" );
    static Pattern PATTERN_COORDINATE = compile( "^(X(?<xoff>[+\\-]?\\d*\\.?\\d*))?(Y(?<yoff>[+\\-]?\\d*\\.?\\d*))?.*" );

    public static void main( String[] args ) {
        //String fileName = "C:\\drive\\MyProject\\electronics\\Projects\\Kicad_Projects\\Projects\\Dc_Connector\\gerber\\Dc_Connector-PTH.drl";
        String fileName = "C:\\drive\\MyProject\\electronics\\Projects\\EagleProjects\\12v7A-BatteryCharger_v10\\12v7A-LedArray_v10.drd";

        ExcellonParser exParser = new ExcellonParser();

        exParser.Parse( fileName );
    }

    private final List<ECmd> _cmdList = new ArrayList<>();
    private final GeometryList _geometryList = new GeometryList();
    ExcellonDecoderState _decoderState = new ExcellonDecoderState();

    public ExcellonParser() {
    }

    public ExcellonDecoderState getDecoderState() {
        return _decoderState;
    }
    
    public GeometryList getGeometryList() {
        return _geometryList;
    }

    public void Parse( String filePath ) {
        File fileObj = new File( filePath );

        ParseInternal( fileObj );

        for ( ECmd eCmd : _cmdList ) {
            if ( eCmd != null ) {
                Geometry[] drawObjs = eCmd.evaluate(_decoderState );

                if ( drawObjs != null ) {
                    _geometryList.addAll( Arrays.asList( drawObjs ) );
                }
            }
        }
    }

    private void ParseInternal( File file ) {
        List<String> lineList = FileHelper.readFileAsStringList( file );
        int lineCount = 0;
        String line;

        for ( String loopLine : lineList ) {
            line = loopLine;
            lineCount++;
            if ( line.isEmpty() ) {
                continue;
            }

            line = line.trim();

            if ( line.charAt( 0 ) == ';' ) {
                ECmdComment cmd = ECmdFactory.createCommentCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.charAt( 0 ) == '%' ) {
                ECmdRewindStop cmd = ECmdFactory.createRewindStopCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "M48" ) ) {
                ECmdHdrBegin cmd = ECmdFactory.createHdrBeginCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "M95" ) ) {
                ECmdHdrEnd cmd = ECmdFactory.createHdrEndCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "M30" ) ) {
                ECmdEndOfProgram cmd = ECmdFactory.createEndOfProgramCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "G00" ) || line.startsWith( "G01" ) ) {
                ECmdRoutCmd cmd = ECmdFactory.createECmdRoutCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "G05" ) ) {
                ECmdDrillMode cmd = ECmdFactory.createDrillModeCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "INCH" ) || line.startsWith( "METRIC" ) ) {
                ECmdUnitMode cmd = ECmdFactory.createUnitModeCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "M71" ) || line.startsWith( "M72" ) ) {
                ECmdMeasurementMode cmd = ECmdFactory.createMeasurementModeCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "ICI" ) ) {
                ECmdIncrmentalMode cmd = ECmdFactory.createIncrmentalModeCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "G90" ) ) {
                ECmdAbsoulteMode cmd = ECmdFactory.createAbsoulteModeCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "T" ) ) {
                ECmd cmd = ECmdFactory.createToolCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "R" ) ) {
                ECmdRepeatHole cmd = ECmdFactory.createRepeatHoleCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else if ( line.startsWith( "X" ) || line.startsWith( "Y" ) ) {
                ECmdCoordinate cmd = ECmdFactory.createCoordinateCmd(_decoderState, line );
                _cmdList.add( cmd );
            } else {
                System.out.println( "Unprocessed line " + lineCount + ":" + line );
            }
        }
    }

}

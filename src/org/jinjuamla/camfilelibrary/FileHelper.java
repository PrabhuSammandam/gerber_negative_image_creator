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
package org.jinjuamla.camfilelibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jinjuamla.camfilelibrary.enums.PcbBoardSideEnum;
import org.jinjuamla.camfilelibrary.enums.PcbFileTypeEnum;
import org.jinjuamla.camfilelibrary.enums.PcbLayerTypeEnum;

/**
 *
 * @author psammand
 */
public class FileHelper {

    private static final LinkedList<String> ToIgnoreExtList = new LinkedList<>();

    static {
        ToIgnoreExtList.add( "config" );
        ToIgnoreExtList.add( "exe" );
        ToIgnoreExtList.add( "dll" );
        ToIgnoreExtList.add( "png" );
        ToIgnoreExtList.add( "zip" );
        ToIgnoreExtList.add( "gif" );
        ToIgnoreExtList.add( "jpeg" );
        ToIgnoreExtList.add( "doc" );
        ToIgnoreExtList.add( "docx" );
        ToIgnoreExtList.add( "jpg" );
        ToIgnoreExtList.add( "bmp" );
    }

    public static PcbFileTypeEnum FindFileType( String fp ) {
        String ext = getFileExtension( new File( fp ) ).toLowerCase();

        if ( !ToIgnoreExtList.stream().noneMatch( (s) -> (ext.equals( s )) ) ) {
            return PcbFileTypeEnum.Unknown;
        }

        PcbFileTypeEnum fileType = PcbFileTypeEnum.Unknown;

        try ( BufferedReader br = new BufferedReader( new FileReader( fp ) ) ) {
            String line = br.readLine();

            while ( line != null ) {
                if ( line.contains( "%FS" ) ) {
                    fileType = PcbFileTypeEnum.Gerber;
                    break;
                }
                if ( line.contains( "M48" ) ) {
                    fileType = PcbFileTypeEnum.Excellon;
                    break;
                }
                line = br.readLine();
            }
        } catch ( FileNotFoundException e ) {
            Logger.getLogger( FileHelper.class.getName() ).log( Level.SEVERE, null, e );
        } catch ( IOException ex ) {
            Logger.getLogger( FileHelper.class.getName() ).log( Level.SEVERE, null, ex );
        }

        return fileType;
    }

    public static List<String> readFileAsStringList( File file ) {
        List<String> list = new ArrayList<>();

        try ( BufferedReader br = new BufferedReader( new FileReader( file ) ) ) {
            String line = br.readLine();
            while ( line != null ) {
                list.add( line );
                line = br.readLine();
            }
        } catch ( FileNotFoundException e ) {
            out.println( e );
        } catch ( IOException e ) {
            out.println( e );
        }
        return list;
    }

    public static String getFileExtension( File file ) {
        String fileName = file.getName();
        if ( fileName.lastIndexOf( "." ) != -1 && fileName.lastIndexOf( "." ) != 0 ) {
            return fileName.substring( fileName.lastIndexOf( "." ) + 1 );
        } else {
            return "";
        }
    }

    public static PcbLayerTypeEnum FindLayerType( String filePath ) {
        BoardInfo boardInfo = FindBoardAndLayerType( filePath );

        return boardInfo.getLayerTypeEnum();
    }

    public static PcbBoardSideEnum FindBoardSide( String filePath ) {
        BoardInfo boardInfo = FindBoardAndLayerType( filePath );

        return boardInfo.getBoardSideEnum();
    }

    static class BoardInfo {

        PcbLayerTypeEnum _layerTypeEnum = PcbLayerTypeEnum.Unknown;
        PcbBoardSideEnum _boardSideEnum = PcbBoardSideEnum.Unknown;

        public BoardInfo( PcbLayerTypeEnum layerTypeEnum, PcbBoardSideEnum boardSideEnum ) {
            _layerTypeEnum = layerTypeEnum;
            _boardSideEnum = boardSideEnum;
        }

        public PcbLayerTypeEnum getLayerTypeEnum() {
            return _layerTypeEnum;
        }

        public PcbBoardSideEnum getBoardSideEnum() {
            return _boardSideEnum;
        }
    }

    private static BoardInfo FindBoardAndLayerType( String filePath ) {
        PcbLayerTypeEnum layerTypeEnum = PcbLayerTypeEnum.Unknown;
        PcbBoardSideEnum boardSideEnum = PcbBoardSideEnum.Unknown;
        File fileObj = new File( filePath );
        String file_ext = getFileExtension( fileObj );

        if ( null != file_ext ) {
            switch ( file_ext ) {
                case "gbr": {
                    String file_name = fileObj.getName();

                    if ( file_name.contains( "-B.Cu." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Bottom;
                        layerTypeEnum = PcbLayerTypeEnum.Copper;
                    } else if ( file_name.contains( "-B.Mask." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Bottom;
                        layerTypeEnum = PcbLayerTypeEnum.SolderMask;
                    } else if ( file_name.contains( "-B.Paste." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Bottom;
                        layerTypeEnum = PcbLayerTypeEnum.Paste;
                    } else if ( file_name.contains( "-B.SilkS." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Bottom;
                        layerTypeEnum = PcbLayerTypeEnum.Silk;
                    } else if ( file_name.contains( "-T.Cu." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Top;
                        layerTypeEnum = PcbLayerTypeEnum.Copper;
                    } else if ( file_name.contains( "-T.Mask." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Top;
                        layerTypeEnum = PcbLayerTypeEnum.SolderMask;
                    } else if ( file_name.contains( "-T.Paste." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Top;
                        layerTypeEnum = PcbLayerTypeEnum.Paste;
                    } else if ( file_name.contains( "-T.SilkS." ) ) {
                        boardSideEnum = PcbBoardSideEnum.Top;
                        layerTypeEnum = PcbLayerTypeEnum.Silk;
                    }
                }
                break;
                //Solder
                case "gbl":
                    boardSideEnum = PcbBoardSideEnum.Bottom;
                    layerTypeEnum = PcbLayerTypeEnum.Copper;
                    break;
                case "gtl":
                    boardSideEnum = PcbBoardSideEnum.Top;
                    layerTypeEnum = PcbLayerTypeEnum.Copper;
                    break;
                // Solder Mask
                case "gbs":
                    boardSideEnum = PcbBoardSideEnum.Bottom;
                    layerTypeEnum = PcbLayerTypeEnum.SolderMask;
                    break;
                case "gts":
                    boardSideEnum = PcbBoardSideEnum.Top;
                    layerTypeEnum = PcbLayerTypeEnum.SolderMask;
                    break;
                // Solder Paste
                case "gbp":
                    boardSideEnum = PcbBoardSideEnum.Bottom;
                    layerTypeEnum = PcbLayerTypeEnum.Paste;
                    break;
                case "gtp":
                    boardSideEnum = PcbBoardSideEnum.Top;
                    layerTypeEnum = PcbLayerTypeEnum.Paste;
                    break;
                // Silk screen
                case "gbo":
                    boardSideEnum = PcbBoardSideEnum.Bottom;
                    layerTypeEnum = PcbLayerTypeEnum.Silk;
                    break;
                case "gto":
                    boardSideEnum = PcbBoardSideEnum.Top;
                    layerTypeEnum = PcbLayerTypeEnum.Silk;
                    break;
                case "drl":
                    boardSideEnum = PcbBoardSideEnum.Both;
                    layerTypeEnum = PcbLayerTypeEnum.Drill;
                    break;

                default:
                    break;
            }
        }

        BoardInfo boardInfo = new BoardInfo( layerTypeEnum, boardSideEnum );

        return boardInfo;
    }

    public static String getLayerForType( PcbBoardSideEnum boardSide, PcbLayerTypeEnum layerType, ArrayList<String> filePathList ) {
        for ( String filePath : filePathList ) {
            BoardInfo boardInfo = FindBoardAndLayerType( filePath );

            if ( boardSide == PcbBoardSideEnum.Unknown ) {
                if ( layerType == boardInfo.getLayerTypeEnum() ) {
                    return filePath;
                }
            } else if ( layerType == boardInfo.getLayerTypeEnum() && boardSide == boardInfo.getBoardSideEnum() ) {
                return filePath;
            }
        }

        return null;
    }

}

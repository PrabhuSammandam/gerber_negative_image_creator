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

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.jinjuamla.camfilelibrary.enums.PcbBoardSideEnum;
import org.jinjuamla.camfilelibrary.enums.PcbLayerTypeEnum;

/**
 *
 * @author psammand
 */
public class Overlay {

    private GeometryList _geometryList;
    private int _unit;
    private Color _color;
    private double[] _position; // lower-left corner
    private BoundingBox _bounds;
    private int _compositeRule;
    private PcbLayerTypeEnum _layerType;
    private PcbBoardSideEnum _boardSide;
    private String _filePath;
    private BufferedImage _image;

    public Overlay() {
        this._position = new double[ 2 ];
        _image = null;
    }

    public BufferedImage getImage() {
        return _image;
    }

    public void setImage( BufferedImage image ) {
        this._image = image;
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath( String filePath ) {
        this._filePath = filePath;
    }

    public PcbBoardSideEnum getBoardSide() {
        return _boardSide;
    }

    public void setBoardSide( PcbBoardSideEnum boardSide ) {
        this._boardSide = boardSide;
    }

    public PcbLayerTypeEnum getLayerType() {
        return _layerType;
    }

    public void setLayerType( PcbLayerTypeEnum layerType ) {
        this._layerType = layerType;
    }

    public GeometryList getGeometryList() {
        return _geometryList;
    }

    public void setGeometryList( GeometryList geometryList ) {
        this._geometryList = geometryList;

        if ( _geometryList != null ) {
            _bounds = _geometryList.getBounds();
            _position[ 0 ] = _bounds.getMinX();
            _position[ 1 ] = _bounds.getMinY();
        }
    }

    public int getUnit() {
        return _unit;
    }

    public void setUnit( int unit ) {
        this._unit = unit;
    }

    public Color getColor() {
        return _color;
    }

    public void setColor( Color color ) {
        this._color = color;
    }

    public double[] getPosition() {
        return _position;
    }

    public void setPosition( double[] position ) {
        this._position = position;
    }

    public int getCompositeRule() {
        return _compositeRule;
    }

    public void setCompositeRule( int compositeRule ) {
        this._compositeRule = compositeRule;
    }

    BoundingBox getBounds() {
        if ( _unit == Consts.UNIT_MODE_INCH) {
            
        }
        return _bounds;
    }
}

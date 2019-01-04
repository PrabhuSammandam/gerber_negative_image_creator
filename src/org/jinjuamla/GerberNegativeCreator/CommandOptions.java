/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.GerberNegativeCreator;

import java.util.ArrayList;

/**
 *
 * @author psammand
 */
class CommandOptions {

    private int _dpi;
    private boolean _bmaskIncluded;
    private double _paperXOffset;
    private double _paperYOffset;
    private String _imageType;
    private String _outFilePath;
    private ArrayList<String> _gerberFiles;

    CommandOptions( int dpi, boolean bmaskIncluded, double paperXOffset, double paperYOffset, String imageType, String outFilePath, ArrayList<String> gerberFiles ) {
        this._dpi = dpi;
        this._bmaskIncluded = bmaskIncluded;
        this._paperXOffset = paperXOffset;
        this._paperYOffset = paperYOffset;
        this._imageType = imageType;
        this._outFilePath = outFilePath;
        this._gerberFiles = gerberFiles;
    }

    public ArrayList<String> getGerberFiles() {
        return _gerberFiles;
    }

    public void setGerberFiles( ArrayList<String> gerberFiles ) {
        this._gerberFiles = gerberFiles;
    }

    public String getOutFilePath() {
        return _outFilePath;
    }

    public void setOutFilePath( String outFilePath ) {
        this._outFilePath = outFilePath;
    }

    public int getDpi() {
        return _dpi;
    }

    public void setDpi( int dpi ) {
        this._dpi = dpi;
    }

    public boolean isBmaskIncluded() {
        return _bmaskIncluded;
    }

    public void setBmaskIncluded( boolean bmaskIncluded ) {
        this._bmaskIncluded = bmaskIncluded;
    }

    public double getPaperXOffset() {
        return _paperXOffset;
    }

    public void setPaperXOffset( double paperXOffset ) {
        this._paperXOffset = paperXOffset;
    }

    public double getPaperYOffset() {
        return _paperYOffset;
    }

    public void setPaperYOffset( double paperYOffset ) {
        this._paperYOffset = paperYOffset;
    }

    public String getImageType() {
        return _imageType;
    }

    public void setImageType( String imageType ) {
        this._imageType = imageType;
    }

}

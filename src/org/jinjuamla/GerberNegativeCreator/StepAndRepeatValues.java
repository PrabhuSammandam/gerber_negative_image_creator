/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.GerberNegativeCreator;

/**
 *
 * @author psammand
 */
public class StepAndRepeatValues {

    private int _xCopies = 1;
    private int _yCopies = 1;
    private double _xGap = 0;
    private double _yGap = 0;

    public StepAndRepeatValues( int xCopies, int yCopies, double xGap, double yGap ) {
        this._xCopies = xCopies;
        _yCopies = yCopies;
        _xGap = xGap;
        _yGap = yGap;
    }

    public double getxGap() {
        return _xGap;
    }

    public void setxGap( double xGap ) {
        this._xGap = xGap;
    }

    public double getyGap() {
        return _yGap;
    }

    public void setyGap( double yGap ) {
        this._yGap = yGap;
    }

    public int getyCopies() {
        return _yCopies;
    }

    public void setyCopies( int yCopies ) {
        this._yCopies = yCopies;
    }

    public int getxCopies() {
        return _xCopies;
    }

    public void setxCopies( int xCopies ) {
        this._xCopies = xCopies;
    }

}

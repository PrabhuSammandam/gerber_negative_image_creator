/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.camfilelibrary;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author psammand
 */
public class StreamCapturer extends OutputStream {

    private final StringBuilder buffer;
    private final String prefix;
    private final Consumer consumer;
    private final PrintStream old;

    public StreamCapturer( String prefix, Consumer consumer, PrintStream old ) {
        this.prefix = prefix;
        buffer = new StringBuilder( 128 );
        buffer.append( "[" ).append( prefix ).append( "] " );
        this.old = old;
        this.consumer = consumer;
    }

    @Override
    public void write( int b ) throws IOException {
        char c = ( char ) b;
        String value = Character.toString( c );
        buffer.append( value );
        if ( value.equals( "\n" ) ) {
            consumer.appendText( buffer.toString() );
            buffer.delete( 0, buffer.length() );
            buffer.append( "[" ).append( prefix ).append( "] " );
        }
        //old.print( c );
    }
}

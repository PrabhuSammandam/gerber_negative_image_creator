# Gerber Negative Image Creator

Java Swing Applicaion to create the PNG image for the gerber files.

## Why this application?

I am electronic hobbyist and draw many circuits. Also I will create
the PCB myself. For that it needs special processing. The default
export options (image) in the electronic CAD packages will not be so
much usefull for the individual PCB making.

## Requirements

Java SDK 8 or newer. To import the existing project you would require Java release
with module support.

# Building

## Using Eclipse

You could have eclipse "Open Projects from file-system" and point it to
the directory `ide/eclipse`.

## Ant build

    ant build

Would get you `jar/gnicreator.jar` which you can run as a Java application.

# Usage

Launch the application jar file or use `ant run` to launch the tool.

Drag and drop bunch of gerber files from your favorite EDA software
(like [KICAD](http://kicad-pcb.org/),
[EAGLE](https://www.autodesk.com/products/eagle/overview)) and hit
`Render`. A PNG image would be renderd and the file would be placed in
the same directory.

The button `clear` clears the dropped list of files.

> Note: Currently only PNG image will be generated for the gerber files.

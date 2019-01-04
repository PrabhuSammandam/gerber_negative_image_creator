/**
 * 
 */
/**
 * @author prabhu
 *
 */
module gerber_negative_image_creator {
	exports org.jinjuamla.gerber.cmds;
	exports org.jinjuamla.camfilelibrary;
	exports org.jinjuamla.camfilelibrary.enums;
	exports org.jinjuamla.GerberNegativeCreator;
	exports org.jinjuamla.excellon;
	exports org.jinjuamla.gerber;
	exports org.jinjuamla.gerber.geometries;
	exports org.jinjuamla.excellon.cmds;
	exports org.jinjuamla.gerber.common;

	requires java.datatransfer;
	requires java.desktop;
	requires java.logging;
	requires java.xml;
}
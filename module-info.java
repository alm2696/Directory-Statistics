/**
 * This module for the CMSC230 project specifies the dependencies and
 * the packages that are accessible to other modules. Requires JavaFX 
 * controls and graphics for building and displaying the GUI. Opens the
 * package to JavaFX to allow reflection for dynamic GUI operations.
 * 
 * @author angel
 */
module edu.commonwealthu.alm2696.CMSC230 {
	requires javafx.controls;    // JavaFX Controls library, used for UI controls
	requires javafx.graphics;    // JavaFX Graphics library, required for scene graph and rendering

	opens mod07_OYO to javafx.graphics;  // Opens package for JavaFX reflection
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.util;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Improvement to the default joystick class Provides toggle buttons
 */

public class NemesisJoystick extends Joystick {

  // deadbands
  private double deadBandX;
  private double deadBandY;

  // rising and falling edge calculations
  private boolean lastTopUpVal = false;
  private boolean lastTopDownVal = false;
  private boolean[] lastRisingVal = new boolean[13];
  private boolean[] lastFallingVal = new boolean[13];

  /**
   * makes a new Joystick
   * 
   * @param port      port the joystick is on
   * @param xDeadBand the deadband for the x axis
   * @param yDeadBand the deadband for the y axis
   */
  public NemesisJoystick(int port, double xDeadBand, double yDeadBand) {
    super(port);
    deadBandX = xDeadBand;
    deadBandY = yDeadBand;
  }

  /**
   * Makes a new Joystick with a x and y deadband of 0
   * 
   * @param port : port the joystick is on
   */
  public NemesisJoystick(int port) {
    this(port, 0.0, 0.0);
  }

  /**
   * Gets the banded value of the joysticks x axis
   * 
   * @return banded val of the joysticks x axis
   */
  public double getXBanded() {
    if (Math.abs(this.getX()) < deadBandX) {
      return 0.0;
    }
    return Math.signum(this.getX()) * (Math.abs(this.getX() - deadBandX) / (1 - deadBandX));
  }

  /**
   * Gets the banded value of the joysticks y axis
   * 
   * @return banded val of the joysticks y axis
   */
  public double getYBanded() {
    if (Math.abs(this.getY()) < deadBandY) {

      return 0.0;
    }
    return Math.signum(this.getY()) * (Math.abs(this.getY() - deadBandY) / (1 - deadBandY));
  }

  /**
   * button being released
   * 
   * @param button the button clicked
   * @return if it is being released
   */
  public boolean getRisingEdge(int button) {
    boolean cur = this.getRawButton(button);
    boolean ret = !lastRisingVal[button] && cur;
    lastRisingVal[button] = cur;
    return ret;
  }

  /**
   * button being pressed
   * 
   * @param button the button being pressed
   * @return if it is being clicked
   */
  public boolean getFallingEdge(int button) {
    boolean cur = this.getRawButton(button);
    boolean ret = lastFallingVal[button] && !cur;
    lastFallingVal[button] = cur;
    return ret;
  }

  public boolean getTopToggle() {
    boolean cur = this.getPOV() == 0;
    boolean ret = lastTopUpVal && !cur;
    lastTopUpVal = cur;
    return ret;
  }

  public boolean getDownToggle() {
    boolean cur = this.getPOV() == 180;
    boolean ret = lastTopDownVal && !cur;
    lastTopDownVal = cur;
    return ret;
  }
}
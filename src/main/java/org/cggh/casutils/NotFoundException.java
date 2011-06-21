package org.cggh.casutils;


/**
 * Thrown when a 404 is received. 
 * 
 * @author timp
 * @since 21 Jun 2011 10:42:18
 *
 */
public class NotFoundException extends Exception  {

  /**
   * @param message
   */
  public NotFoundException(String message) {
    super(message);
  }

}

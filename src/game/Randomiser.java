package game;

import java.util.Random;

/**
 * A randomiser singleton class to generate random numbers.
 */
public class Randomiser {

  private final int[] randomNumbers;
  private int current;
  private static Randomiser instance;

  private Randomiser() {
    this.randomNumbers = null;
  }


  private Randomiser(int... randomNumbers) {
    this.randomNumbers = randomNumbers;
    current = 0;
  }

  /**
   * Gets the DungeonGame.Randomiser instance of this  class if it exists or creates the instance.
   *
   * @return the instance
   */
  public static Randomiser getInstance() {

    if (instance == null) {
      instance = new Randomiser();
    }
    return instance;
  }

  /**
   * Gets the DungeonGame.Randomiser instance of this  class if it exists or creates the instance
   * with a given array so only the numbers in the array are returned.
   *
   * @param numbers the input numbers
   * @return the instance
   */

  public static Randomiser getInstance(int... numbers) {

    if (instance == null) {
      instance = new Randomiser(numbers);

    }
    return instance;
  }


  /**
   * Generates a random number between given bounds.
   *
   * @param min the lower bound
   * @param max the upper bound
   * @return the random number
   */
  public int getRandomNumber(int min, int max) {
    Integer randomNum = null;
    if (randomNumbers == null) {
      Random r = new Random();

      randomNum = r.nextInt((max - min) + 1) + min;

    } else {
      randomNum = randomNumbers[current];
      addToCurrent();
      for (int n : randomNumbers) {
        if (n >= min && n <= max) {
          randomNum = n;
          break;
        }
      }
      if (randomNum == null) {
        throw new IndexOutOfBoundsException();
      }

    }
    return randomNum;
  }


  /**
   * Generates a random number.
   *
   * @return the random number
   */
  public int getRandomNumber() {
    int randomNum;
    if (randomNumbers == null) {
      Random r = new Random();

      randomNum = r.nextInt();

    } else {

      randomNum = randomNumbers[current];
      addToCurrent();
    }
    return randomNum;
  }


  private void addToCurrent() {
    if (this.current < randomNumbers.length - 1) {
      this.current += 1;
    } else {
      this.current = 0;
    }
  }

  /**
   * Removes the current instance.
   */
  public static void removeInstance() {
    instance = null;
  }
}

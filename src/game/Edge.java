package game;

/**
 * DungeonGame.Edge class to represent an edge containing two indexes in a 2d array.
 */
class Edge {
  private final int x1;
  private final int y1;

  private final int x2;
  private final int y2;

  Edge(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;

    this.x2 = x2;
    this.y2 = y2;

  }


  int[] getFirstPosition() {
    return new int[]{x1, y1};
  }

  int[] getSecondPosition() {
    return new int[]{x2, y2};
  }

}

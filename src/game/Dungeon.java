package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/**
 * DungeonGame.Dungeon class that represents a dungeon. Contains multiple rooms and a player.
 * Allows a player to move through the rooms and collect treasures.
 */
public class Dungeon implements DungeonGrid, Serializable {


  private final DungeonRoom[][] grid;

  private final Person player;

  /**
   * Creates a dungeon and fills it with rooms.
   *
   * @param wrapping        If the dungeon is wrapping or not
   * @param rows            The number of rows in the dungeon
   * @param columns         The number of columns in the dungeon
   * @param connectivity    The connectivity of the dungeon
   * @param treasurePercent The percentage of rooms that should be filled with treasure
   * @param monsters        The number of monsters in the dungeon
   * @param traps           The number of traps in the dungeon
   * @param thief           If the dungeon has a thief or not
   */
  public Dungeon(boolean wrapping, int rows, int columns,
                 int connectivity, int treasurePercent, int monsters, int traps, boolean thief) {

    if (rows < 1 || columns < 1) {
      throw new IllegalArgumentException("Invalid dungeon size");
    }

    if (connectivity < 0) {
      throw new IllegalArgumentException("Invalid connectivity");
    }

    if (rows * columns <= 5) {
      throw new IllegalArgumentException("Dungeon size too small");
    }

    if (rows + columns - 1 <= 5) {
      throw new IllegalArgumentException("Dungeon size is too small");
    }

    if (wrapping && rows + columns <= 10) {
      throw new IllegalArgumentException("Dungeon too small to be a wrapping dungeon");
    }

    if (rows == 1 || columns == 1) {
      if (wrapping) {
        throw new IllegalArgumentException("Dungeon of this size cant be wrapping");
      }
    }

    if (treasurePercent < 0 || treasurePercent > 100) {
      throw new IllegalArgumentException("Invalid amount of treasure");
    }

    if (monsters < 0) {
      throw new IllegalArgumentException("Invalid number of monsters");
    }

    if (traps < 0) {
      throw new IllegalArgumentException("Invalid number of traps");
    }

    this.grid = createDungeon(wrapping, rows, columns, connectivity);
    DungeonRoom[] s = setStart(this.grid);

    DungeonRoom start = s[0];

    DungeonRoom end = s[1];
    addTreasure(this.grid, treasurePercent);
    addArrows(this.grid, treasurePercent);
    player = new Player();
    start.setStart();
    start.addPlayer(player);
    end.setEnd();
    end.setOtyugh();
    addMonsters(this.grid, monsters - 1);
    addTraps(this.grid, traps);

    if (thief) {
      addThief(this.grid);
    }

    player.updateLocation(start.getLocation());

  }

  private void addTreasure(DungeonRoom[][] g, int treasurePercent) {
    int caves = 0;
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        if (!g[i][j].isTunnel()) {
          caves++;
        }
      }
    }

    int r = (caves * treasurePercent) / 100;

    List<DungeonRoom> l = new ArrayList<>();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        l.add(g[i][j]);
      }
    }

    int c = 0;

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(l);
    }

    Iterator<DungeonRoom> it = l.iterator();

    int i = 0;
    while (c < r) {
      if (i >= l.size()) {
        break;
      }
      if (!l.get(i).isTunnel()) {
        int nt = Randomiser.getInstance().getRandomNumber(1, 3);
        while (nt > 0) {
          int t = Randomiser.getInstance().getRandomNumber(1, 3);
          if (t == 1) {
            l.get(i).addTreasure(Treasure.DIAMOND);
          } else if (t == 2) {
            l.get(i).addTreasure(Treasure.RUBY);
          } else {
            l.get(i).addTreasure(Treasure.SAPPHIRE);
          }
          nt--;
        }
        c++;
      }

      i++;
    }

  }


  private void addArrows(DungeonRoom[][] g, int arrowPercent) {


    int r = (g.length * g[0].length * arrowPercent) / 100;

    List<DungeonRoom> l = new ArrayList<>();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        l.add(g[i][j]);
      }
    }

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(l);
    }

    for (int i = 0; i < r; i++) {
      l.get(i).addArrows(Randomiser.getInstance().getRandomNumber(1, 3));
    }


  }

  private void addMonsters(DungeonRoom[][] g, int n) {

    List<DungeonRoom> l = new ArrayList<>();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        if ((!g[i][j].isTunnel()) && (!g[i][j].isEnd()) && (!g[i][j].isStart())) {
          l.add(g[i][j]);
        }
      }
    }

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(l);
    }

    for (int i = 0; i < n; i++) {
      if (i >= l.size()) {
        break;
      }
      l.get(i).setOtyugh();
    }

  }

  private void addTraps(DungeonRoom[][] g, int n) {

    List<DungeonRoom> l = new ArrayList<>();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        if ((!g[i][j].hasAliveOtyugh()) && (!g[i][j].isEnd()) && (!g[i][j].isStart())) {
          l.add(g[i][j]);
        }
      }
    }

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(l);
    }

    for (int i = 0; i < n; i++) {
      if (i >= l.size()) {
        break;
      }
      l.get(i).setTrap();
    }

  }

  private void addThief(DungeonRoom[][] g) {

    List<DungeonRoom> l = new ArrayList<>();
    for (int i = 0; i < g.length; i++) {
      for (int j = 0; j < g[0].length; j++) {
        if ((!g[i][j].hasAliveOtyugh()) && (!g[i][j].isEnd())
                && (!g[i][j].isStart()) && (!g[i][j].getTrap())) {
          l.add(g[i][j]);
        }
      }
    }

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(l);
    }

    if (l.size() > 0) {
      l.get(0).setThief();
    }

  }

  @Override
  public int[] getPlayerLocation() {
    return player.getLocation();
  }

  @Override
  public boolean getTraps() {

    int[] l = player.getLocation();


    Set<DungeonRoom> l1 = new HashSet<>();


    if (grid[l[0]][l[1]].getEast() != null) {
      l1.add(grid[l[0]][l[1]].getEast());
    }
    if (grid[l[0]][l[1]].getNorth() != null) {
      l1.add(grid[l[0]][l[1]].getNorth());
    }
    if (grid[l[0]][l[1]].getWest() != null) {
      l1.add(grid[l[0]][l[1]].getWest());
    }
    if (grid[l[0]][l[1]].getSouth() != null) {
      l1.add(grid[l[0]][l[1]].getSouth());
    }

    for (DungeonRoom d : l1) {
      if (d.getTrap()) {
        return true;
      }
    }

    return false;
  }


  private DungeonRoom[][] createDungeon(boolean wrapping, int rows, int columns, int connectivity) {

    DungeonRoom[][] tempGrid = new DungeonRoom[rows][columns];

    for (int i = 0; i < tempGrid.length; i++) {
      for (int j = 0; j < tempGrid[0].length; j++) {
        tempGrid[i][j] = new Location(i, j);
      }
    }

    List<Edge> edges = generateEdges(wrapping, rows, columns);

    List<Set<DungeonRoom>> roomSet = new ArrayList<>();
    for (int i = 0; i < tempGrid.length; i++) {
      for (int j = 0; j < tempGrid[0].length; j++) {
        Set<DungeonRoom> s = new HashSet<>();
        s.add(tempGrid[i][j]);
        roomSet.add(s);
      }
    }

    List<Edge> usedEdges = new ArrayList<>();
    List<Edge> extraEdges = new ArrayList<>();

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(edges);
    }

    for (Edge e : edges) {

      DungeonRoom d1 = tempGrid[e.getFirstPosition()[0]][e.getFirstPosition()[1]];
      DungeonRoom d2 = tempGrid[e.getSecondPosition()[0]][e.getSecondPosition()[1]];

      Set<DungeonRoom> s1 = null;
      Set<DungeonRoom> s2 = null;

      for (Set<DungeonRoom> s : roomSet) {

        if (s.contains(d1) && s.contains(d2)) {
          extraEdges.add(e);
          break;
        } else if (s.contains(d1)) {
          s1 = s;
        } else if (s.contains(d2)) {
          s2 = s;
        }
      }

      if (s1 != null && s2 != null) {
        s1.addAll(s2);
        roomSet.remove(s2);
        usedEdges.add(e);
      }

    }

    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(extraEdges);
    }

    if (connectivity > extraEdges.size()) {
      throw new IllegalArgumentException("Connectivity too large");
    }
    for (int i = 0; i < connectivity; i++) {
      usedEdges.add(extraEdges.get(i));
    }

    for (Edge e : usedEdges) {
      DungeonRoom dr1 = tempGrid[e.getFirstPosition()[0]][e.getFirstPosition()[1]];
      DungeonRoom dr2 = tempGrid[e.getSecondPosition()[0]][e.getSecondPosition()[1]];

      if (e.getFirstPosition()[0] == e.getSecondPosition()[0]) {


        if (e.getFirstPosition()[1] < e.getSecondPosition()[1]) {

          if (e.getFirstPosition()[1] == 0 && e.getSecondPosition()[1] == tempGrid[0].length - 1) {
            dr1.setLocation(Direction.WEST, dr2);
            dr2.setLocation(Direction.EAST, dr1);
          } else {
            dr1.setLocation(Direction.EAST, dr2);
            dr2.setLocation(Direction.WEST, dr1);
          }

        } else {
          if (e.getSecondPosition()[1] == 0 && e.getFirstPosition()[1] == tempGrid[0].length - 1) {
            dr1.setLocation(Direction.EAST, dr2);
            dr2.setLocation(Direction.WEST, dr1);
          } else {
            dr1.setLocation(Direction.WEST, dr2);
            dr2.setLocation(Direction.EAST, dr1);
          }
        }


      } else {
        if (e.getFirstPosition()[0] < e.getSecondPosition()[0]) {

          if (e.getFirstPosition()[0] == 0 && e.getSecondPosition()[0] == tempGrid.length - 1) {
            dr1.setLocation(Direction.NORTH, dr2);
            dr2.setLocation(Direction.SOUTH, dr1);
          } else {
            dr1.setLocation(Direction.SOUTH, dr2);
            dr2.setLocation(Direction.NORTH, dr1);
          }


        } else {

          if (e.getSecondPosition()[0] == 0 && e.getFirstPosition()[0] == tempGrid.length - 1) {
            dr1.setLocation(Direction.SOUTH, dr2);
            dr2.setLocation(Direction.NORTH, dr1);
          } else {
            dr1.setLocation(Direction.NORTH, dr2);
            dr2.setLocation(Direction.SOUTH, dr1);
          }
        }
      }
    }


    return tempGrid;
  }

  private DungeonRoom[] setStart(DungeonRoom[][] grid) {
    List<DungeonRoom> allRooms = new ArrayList<>();

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        allRooms.add(grid[i][j]);
      }
    }
    if (Randomiser.getInstance().getRandomNumber() != 2) {
      Collections.shuffle(allRooms);
    }


    Set<DungeonRoom> visited = null;
    DungeonRoom start = null;
    Iterator<DungeonRoom> roomIterator = allRooms.iterator();
    Boolean endTunnel = true;
    do {
      endTunnel = true;

      if (roomIterator.hasNext()) {
        start = roomIterator.next();
      } else {
        throw new IllegalStateException("No start position is possible");
      }

      visited = new HashSet<>();


      Queue<DungeonRoom> q = new LinkedList<>();
      q.add(start);
      q.add(null);

      int currentDepth = 0;

      while (!q.isEmpty()) {

        DungeonRoom t = q.poll();
        if (t == null) {
          currentDepth++;
          q.add(null);
          if (currentDepth > 5) {
            break;
          }
        } else {

          visited.add(t);
          if (t.getWest() != null) {
            if (!visited.contains(t.getWest())) {
              q.add(t.getWest());
              visited.add(t.getWest());
            }
          }

          if (t.getEast() != null) {
            if (!visited.contains(t.getEast())) {
              q.add(t.getEast());
              visited.add(t.getEast());
            }
          }
          if (t.getSouth() != null) {
            if (!visited.contains(t.getSouth())) {
              q.add(t.getSouth());
              visited.add(t.getSouth());
            }
          }
          if (t.getNorth() != null) {
            if (!visited.contains(t.getNorth())) {
              q.add(t.getNorth());
              visited.add(t.getNorth());
            }
          }
        }


      }


      for (DungeonRoom dr : allRooms) {
        if (!visited.contains(dr) && !dr.isTunnel()) {
          endTunnel = false;
        }
      }

    }
    while (visited.size() == allRooms.size() || start.isTunnel() || endTunnel);

    allRooms.removeAll(visited);
    DungeonRoom end = null;
    for (DungeonRoom dr : allRooms) {
      if (!dr.isTunnel()) {
        end = dr;
        break;
      }
    }


    return new DungeonRoom[]{start, end};

  }


  private List<Edge> generateEdges(boolean wrapping, int rows, int columns) {
    List<Edge> edges = new ArrayList<>();

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (i != rows - 1 && j != columns - 1) {
          edges.add(new Edge(i, j, i + 1, j));
          edges.add(new Edge(i, j, i, j + 1));
        } else if (i == rows - 1 && j != columns - 1) {
          if (wrapping) {
            edges.add(new Edge(i, j, 0, j));
          }
          edges.add(new Edge(i, j, i, j + 1));
        } else if (i != rows - 1 && j == columns - 1) {
          if (wrapping) {
            edges.add(new Edge(i, j, i, 0));
          }
          edges.add(new Edge(i, j, i + 1, j));
        } else {
          if (wrapping) {
            edges.add(new Edge(i, j, 0, j));
            edges.add(new Edge(i, j, i, 0));
          }
        }
      }
    }

    return edges;
  }

  @Override
  public int movePlayer(Direction direction) {

    if (!player.isAlive()) {
      throw new IllegalStateException("Player is not alive");
    }

    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction");
    }

    int[] l = player.getLocation();

    return grid[l[0]][l[1]].move(direction);

  }


  @Override
  public void pickUpItems() {

    if (!player.isAlive()) {
      throw new IllegalStateException("Player is not alive to pick up items");
    }

    int[] l = player.getLocation();
    grid[l[0]][l[1]].addTreasureToPlayer();
  }


  @Override
  public String getLocationDescription() {
    int[] l = player.getLocation();
    return grid[l[0]][l[1]].getRoomDescription();
  }

  @Override
  public String getPlayerDescription() {
    return player.getDescription();
  }

  @Override
  public String getPlayerInventory() {

    List<Treasure> x = player.getInventory();
    Map<Treasure, Integer> m = new LinkedHashMap<>();

    m.put(Treasure.RUBY, 0);
    m.put(Treasure.DIAMOND, 0);
    m.put(Treasure.SAPPHIRE, 0);

    for (Treasure t : x) {
      m.put(t, m.get(t) + 1);
    }

    StringBuilder s = new StringBuilder();
    s.append("Arrows:");
    s.append(player.getArrowCount());
    s.append(" Treasures: ");
    for (Treasure t1 : m.keySet()) {
      s.append(t1.name());
      s.append(":");
      s.append(m.get(t1));
      s.append("; ");
    }

    return s.toString();


  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < grid.length; i++) {
      for (DungeonRoom d : grid[i]) {
        sb.append(d.getRoomDescription());
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  @Override
  public boolean shoot(Direction direction, int length) {

    if (!player.isAlive()) {
      throw new IllegalStateException("Player is not alive to shoot.");
    }

    if (player.getArrowCount() <= 0) {
      throw new IllegalStateException("No arrows");
    }

    if (length < 0) {
      throw new IllegalArgumentException("invalid distance");
    }
    int[] l = player.getLocation();

    DungeonRoom d = grid[l[0]][l[1]];
    player.removeArrow();

    if (d.isTunnel()) {
      if (d.hasDirection(direction)) {
        return d.shoot(direction, length);
      } else {
        return false;
      }
    } else {
      return d.shoot(direction, length);
    }


  }


  @Override
  public int getSmell() {


    int[] l = player.getLocation();

    if (grid[l[0]][l[1]].hasAliveOtyugh()) {
      return 1;
    }

    Set<DungeonRoom> l1 = new HashSet<>();
    Set<DungeonRoom> l2 = new HashSet<>();

    if (grid[l[0]][l[1]].getEast() != null) {
      l1.add(grid[l[0]][l[1]].getEast());
    }
    if (grid[l[0]][l[1]].getNorth() != null) {
      l1.add(grid[l[0]][l[1]].getNorth());
    }
    if (grid[l[0]][l[1]].getWest() != null) {
      l1.add(grid[l[0]][l[1]].getWest());
    }
    if (grid[l[0]][l[1]].getSouth() != null) {
      l1.add(grid[l[0]][l[1]].getSouth());
    }

    for (DungeonRoom d : l1) {
      if (d.hasAliveOtyugh()) {
        return 1;
      }
    }

    for (DungeonRoom d : l1) {

      if (d.getEast() != null) {
        l2.add(d.getEast());
      }
      if (d.getNorth() != null) {
        l2.add(d.getNorth());
      }
      if (d.getWest() != null) {
        l2.add(d.getWest());
      }
      if (d.getSouth() != null) {
        l2.add(d.getSouth());
      }

    }

    int count = 0;
    for (DungeonRoom d : l2) {
      if (d.hasAliveOtyugh()) {
        count++;
      }
    }

    if (count >= 2) {
      return 1;
    } else if (count == 1) {
      return 2;
    }

    return 0;

  }

  @Override
  public void pickUpArrows() {

    if (!player.isAlive()) {
      throw new IllegalStateException("Player is not alive to pick up arrows.");
    }


    int[] l = player.getLocation();
    grid[l[0]][l[1]].addArrowsToPlayer();
  }

  @Override
  public boolean isTunnel() {
    int[] l = player.getLocation();
    return grid[l[0]][l[1]].isTunnel();
  }

  @Override
  public boolean isEnd() {
    int[] l = player.getLocation();
    return grid[l[0]][l[1]].isEnd();
  }

  @Override
  public int getArrowCount() {
    int[] l = player.getLocation();
    return grid[l[0]][l[1]].getArrowCount();
  }

  @Override
  public Map<Treasure, Integer> getTreasure() {
    int[] l = player.getLocation();
    List<Treasure> x = grid[l[0]][l[1]].getTreasure();
    Map<Treasure, Integer> m = new LinkedHashMap<>();

    m.put(Treasure.RUBY, 0);
    m.put(Treasure.DIAMOND, 0);
    m.put(Treasure.SAPPHIRE, 0);

    for (Treasure t : x) {
      m.put(t, m.get(t) + 1);
    }

    return m;
  }

  @Override
  public List<Direction> getDirections() {
    List<Direction> d = new ArrayList<>();

    int[] l = player.getLocation();
    DungeonRoom dr = grid[l[0]][l[1]];

    if (dr.hasDirection(Direction.NORTH)) {
      d.add(Direction.NORTH);
    }

    if (dr.hasDirection(Direction.SOUTH)) {
      d.add(Direction.SOUTH);
    }

    if (dr.hasDirection(Direction.EAST)) {
      d.add(Direction.EAST);
    }

    if (dr.hasDirection(Direction.WEST)) {
      d.add(Direction.WEST);
    }

    return d;
  }

  @Override
  public boolean hasArrows() {
    return player.getArrowCount() > 0;
  }

  @Override
  public int getPlayerArrowCount() {
    return player.getArrowCount();
  }

  @Override
  public Map<Treasure, Integer> getPlayerTreasures() {

    List<Treasure> x = player.getInventory();
    Map<Treasure, Integer> m = new LinkedHashMap<>();

    m.put(Treasure.RUBY, 0);
    m.put(Treasure.DIAMOND, 0);
    m.put(Treasure.SAPPHIRE, 0);

    for (Treasure t : x) {
      m.put(t, m.get(t) + 1);
    }

    return m;

  }

  @Override
  public boolean isPlayerAlive() {
    return player.isAlive();
  }

  @Override
  public ReadonlyDungeonRoom[][] getGrid() {
    return this.grid;
  }

}

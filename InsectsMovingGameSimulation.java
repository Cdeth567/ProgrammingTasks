/*
Brief description of the problem:

Implement an **Insects Moving Game Simulation** based on a grid (board).
There are four insect types (**Ant, Butterfly, Spider, Grasshopper**) and four colors (**Red, Green, Blue, Yellow**), each with specific movement rules and food collection behavior.
Insects move one by one in the order given in the input, choosing a direction that maximizes the total food collected, with a defined priority order for ties.

The program must validate all input constraints (board size, entity positions, duplicate insects, invalid types/colors, etc.) and throw custom exceptions for errors.
For valid input, simulate all movements and output, for each insect, its **color**, **type**, **chosen direction**, and **total food eaten** before leaving the board or being killed.
*/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;


// The type Main.
public class Main {
    private static Board gameBoard;
    private static List<String> allInsects = new ArrayList<>();
    private static List<Insect> insects = new ArrayList<>();
    private static List<String> allCoordinates = new ArrayList<>();

    // The entry point of application.
    public static void main(String[] args) {
        Scanner scanner;
        try {
            FileInputStream file = new FileInputStream("input.txt");
            System.setIn(file);
            PrintStream path = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(path);
            scanner = new Scanner(System.in);
        } catch (FileNotFoundException ignored) {
            return;
        }
        int d = 0;
        int n = 0;
        int m = 0;
        final int number4 = 4;
        final int number1000 = 1000;
        final int number16 = 16;
        final int number200 = 200;
        final int number3 = 3;
        try {
            d = scanner.nextInt();
            if (!(d >= number4) || !(d <= number1000)) {
                throw (new InvalidBoardSizeException());
            }
            gameBoard = new Board(d);
            n = scanner.nextInt();
            if (!(n >= 1) || !(n <= number16)) {
                throw (new InvalidNumberOfInsectsException());
            }
            m = scanner.nextInt();
            if (!(m >= 1) || !(m <= number200)) {
                throw (new InvalidNumberOfFoodPointsException());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        String input = scanner.nextLine();
        for (int i = 0; i < n; i++) {
            input = scanner.nextLine();
            String[] variables = input.split(" ");
            String color = variables[0];
            InsectColor insectColorEnum = null;
            try {
                insectColorEnum = InsectColor.toColor(color);
            } catch (InvalidInsectColorException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            String insectType = variables[1];
            String xCoordinate = variables[2];
            int xCoordinateOfInsect = Integer.parseInt(xCoordinate);
            String yCoordinate = variables[number3];
            int yCoordinateOfInsect = Integer.parseInt(yCoordinate);
            String coordinates = xCoordinate + " " + yCoordinate;
            if (allCoordinates.contains(coordinates)) {
                TwoEntityOnSamePositionException e = new TwoEntityOnSamePositionException();
                System.out.println(e.getMessage());
                System.exit(0);
            } else {
                allCoordinates.add(coordinates);
            }
            Insect insect = null;
            EntityPosition entityPosition = null;
            switch (insectType) {
                case "Ant":
                    entityPosition = new EntityPosition(xCoordinateOfInsect, yCoordinateOfInsect);
                    insect = new Ant(entityPosition, insectColorEnum);
                    break;
                case "Butterfly":
                    entityPosition = new EntityPosition(xCoordinateOfInsect, yCoordinateOfInsect);
                    insect = new Butterfly(entityPosition, insectColorEnum);
                    break;
                case "Grasshopper":
                    entityPosition = new EntityPosition(xCoordinateOfInsect, yCoordinateOfInsect);
                    insect = new Grasshopper(entityPosition, insectColorEnum);
                    break;
                case "Spider":
                    entityPosition = new EntityPosition(xCoordinateOfInsect, yCoordinateOfInsect);
                    insect = new Spider(entityPosition, insectColorEnum);
                    break;
                default:
                    InvalidInsectTypeException e = new InvalidInsectTypeException();
                    System.out.println(e.getMessage());
                    System.exit(0);
            }
            String insectInfo = color + " " + insectType;
            if (allInsects.contains(insectInfo)) {
                DuplicateInsectException e = new DuplicateInsectException();
                System.out.println(e.getMessage());
                System.exit(0);
            } else {
                allInsects.add(insectInfo);
            }
            if (xCoordinateOfInsect > d || xCoordinateOfInsect <= 0
                    || yCoordinateOfInsect > d || yCoordinateOfInsect <= 0) {
                InvalidEntityPositionException e = new InvalidEntityPositionException();
                System.out.println(e.getMessage());
                System.exit(0);
            }
            insects.add(insect);
            gameBoard.addEntity(insect);
        }
        for (int j = 0; j < m; j++) {
            input = scanner.nextLine();
            String[] variables1 = input.split(" ");
            String foodAmount = variables1[0];
            int intFoodAmount = Integer.parseInt(foodAmount);
            String xCoordinate = variables1[1];
            int xCoordinateOfFood = Integer.parseInt(xCoordinate);
            String yCoordinate = variables1[2];
            int yCoordinateOfFood = Integer.parseInt(yCoordinate);
            String coordinates = xCoordinate + " " + yCoordinate;
            if (xCoordinateOfFood > d || xCoordinateOfFood <= 0 || yCoordinateOfFood > d || yCoordinateOfFood <= 0) {
                InvalidEntityPositionException e = new InvalidEntityPositionException();
                System.out.println(e.getMessage());
                System.exit(0);
            }
            if (allCoordinates.contains(coordinates)) {
                TwoEntityOnSamePositionException e = new TwoEntityOnSamePositionException();
                System.out.println(e.getMessage());
                System.exit(0);
            } else {
                allCoordinates.add(coordinates);
            }
            EntityPosition entityPosition = new EntityPosition(xCoordinateOfFood, yCoordinateOfFood);
            FoodPoint food = new FoodPoint(entityPosition, intFoodAmount);
            gameBoard.addEntity(food);
        }
        for (int g = 0; g < insects.size(); g++) {
            Insect insect = insects.get(g);
            Direction direction = gameBoard.getDirection(insect);
            int eatenFood = gameBoard.getDirectionSum(insect);
            if (insect instanceof Spider) {
                System.out.println(insect.color.colorToString(insect.color.toString()) + " "
                        + "Spider" + " " + direction + " " + eatenFood);
            } else if (insect instanceof Ant) {
                System.out.println(insect.color.colorToString(insect.color.toString()) + " "
                        + "Ant" + " " + direction + " " + eatenFood);
            } else if (insect instanceof Grasshopper) {
                System.out.println(insect.color.colorToString(insect.color.toString()) + " "
                        + "Grasshopper" + " " + direction + " " + eatenFood);
            } else if (insect instanceof Butterfly) {
                System.out.println(insect.color.colorToString(insect.color.toString()) + " "
                        + "Butterfly" + " " + direction + " " + eatenFood);
            }
        }
        scanner.close();
    }
}

// The enum Direction lists all possible directions.
enum Direction {

    // North direction.
    N("North"),
    // East direction.
    E("East"),
    // South direction.
    S("South"),
    // West direction.
    W("West"),
    // North-East direction.
    NE("North-East"),
    // South-East direction.
    SE("South-East"),
    // South-West direction.
    SW("South-West"),
    // North-West direction.
    NW("North-West");

    private final String textRepresentation;

    private Direction(String text) {
        this.textRepresentation = text;
    }

    @Override
    public String toString() {
        return textRepresentation;
    }
}

// The enum Insect color lists all possible colors for insects.
enum InsectColor {
    // Red insect color.
    RED,
    // Green insect color.
    GREEN,
    // Blue insect color.
    BLUE,
    // Yellow insect color.
    YELLOW;

    /**
     * To color insect color.
     *
     * @param s the s
     * @return the insect color
     * @throws InvalidInsectColorException the invalid insect color exception
     * which prints if color of the insect is different from Red, Green, Blue, and Yellow
     */
    public static InsectColor toColor(String s) throws InvalidInsectColorException {
        switch (s) {
            case "Red":
                return RED;
            case "Green":
                return GREEN;
            case "Blue":
                return BLUE;
            case "Yellow":
                return YELLOW;
            default:
                throw new InvalidInsectColorException();
        }
    }

    // Color to string string.
    public String colorToString(String color) {
        switch (color) {
            case "RED":
                return "Red";
            case "YELLOW":
                return "Yellow";
            case "BLUE":
                return "Blue";
            case "GREEN":
                return "Green";
            default:
                return "";
        }
    }
}

// The interface Orthogonal moving.
interface OrthogonalMoving {
    /**
     * Gets orthogonal direction visible value.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the orthogonal direction visible value
     */
    default int getOrthogonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                   Map<String, BoardEntity> boardData, int boardSize) {
        int countOfFood = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();
        switch (dir) {
            case E:
                for (; y <= boardSize; y += 1) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                }
                return countOfFood;
            case N:
                for (; x > 0; x -= 1) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                }
                return countOfFood;
            case S:
                for (; x <= boardSize; x += 1) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity;
                    boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                }
                return countOfFood;
            case W:
                for (; y > 0; y -= 1) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                }
                return countOfFood;
            default:
                return countOfFood;
        }
    }

    /**
     * Travel orthogonally int.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param color          the color
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the int
     */
    default int travelOrthogonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                   Map<String, BoardEntity> boardData, int boardSize) {
        int amountOfFood = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();
        switch (dir) {
            case E:
                y++;
                while (y <= boardSize) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    y++;
                }
                break;
            case N:
                x--;
                while (x > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x--;
                }
                break;
            case S:
                x++;
                while (x <= boardSize) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x++;
                }
                break;
            case W:
                y--;
                while (y > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    y--;
                }
                break;
            default:
                return amountOfFood;
        }
        boardData.remove(entityPosition.toString());
        return amountOfFood;
    }
}

// The interface Diagonal moving.
interface DiagonalMoving {
    /**
     * Gets diagonal direction visible value.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the diagonal direction visible value
     */
    default int getDiagonalDirectionVisibleValue(Direction dir, EntityPosition entityPosition,
                                                 Map<String, BoardEntity> boardData, int boardSize) {
        int countOfFood = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();
        switch (dir) {
            case NE:
                x--;
                y++;
                while (x > 0 && y <= boardSize) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                    x--;
                    y++;
                }
                return countOfFood;
            case SE:
                x++;
                y++;
                while (x <= boardSize && y <= boardSize) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                    x++;
                    y++;
                }
                return countOfFood;
            case SW:
                x++;
                y--;
                while (x <= boardSize && y > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                    x++;
                    y--;
                }
                return countOfFood;
            case NW:
                x--;
                y--;
                while (x > 0 && y > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        countOfFood += ((FoodPoint) boardEntity).getValue();
                    }
                    x--;
                    y--;
                }
                return countOfFood;
            default:
                return countOfFood;
        }
    }

    /**
     * Travel diagonally int.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param color          the color
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the int
     */
    default int travelDiagonally(Direction dir, EntityPosition entityPosition, InsectColor color,
                                 Map<String, BoardEntity> boardData, int boardSize) {
        int amountOfFood = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();
        switch (dir) {
            case NE:
                x--;
                y++;
                while (y <= boardSize && x > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x--;
                    y++;
                }
                break;
            case SE:
                x++;
                y++;
                while (x <= boardSize && y <= boardSize) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x++;
                    y++;
                }
                break;
            case SW:
                x++;
                y--;
                while (x <= boardSize && y > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x++;
                    y--;
                }
                break;
            case NW:
                x -= 1;
                y -= 1;
                while (x > 0 && y > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x--;
                    y--;
                }
                break;
            default:
                return amountOfFood;
        }
        boardData.remove(entityPosition.toString());
        return amountOfFood;
    }
}

// The type Invalid board size exception.
class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}

// The type Invalid number of insects exception.
class InvalidNumberOfInsectsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of insects";
    }
}

// The type Invalid number of food points exception.
class InvalidNumberOfFoodPointsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of food points";
    }
}

// The type Invalid insect color exception.
class InvalidInsectColorException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid insect color";
    }
}

// The type Invalid insect type exception.
class InvalidInsectTypeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid insect type";
    }
}

// The type Invalid entity position exception.
class InvalidEntityPositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid entity position";
    }
}

// The type Duplicate insect exception.
class DuplicateInsectException extends Exception {
    @Override
    public String getMessage() {
        return "Duplicate insects";
    }
}

// The type Two entity on same position exception.
class TwoEntityOnSamePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Two entities in the same position";
    }
}

// The type Board.
class Board {
    private Map<String, BoardEntity> boardData;
    private int size;

    public Board(int boardSize) {
        size = boardSize;
        boardData = new HashMap<>();
    }

    public void addEntity(BoardEntity entity) {
        boardData.put(entity.getEntityPosition().toString(), entity);
    }

    public BoardEntity getEntity(EntityPosition position) {
        return boardData.get(position.toString());
    }

    /**
     * Gets direction.
     *
     * @param insect the insect
     * @return the direction
     */
    public Direction getDirection(Insect insect) {
        return insect.getBestDirection(boardData, size);
    }

    /**
     * Gets direction sum.
     *
     * @param insect the insect
     * @return the direction sum
     */
    public int getDirectionSum(Insect insect) {
        return insect.travelDirection(getDirection(insect), boardData, size);
    }
}

// The type Board entity.
abstract class BoardEntity {
    // The Entity position.
    protected EntityPosition entityPosition;

    public EntityPosition getEntityPosition() {
        return entityPosition;
    }
}

// The type Entity position.
class EntityPosition {
    private final int x;
    private final int y;

    public EntityPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Override toString method.
    @Override
    public String toString() {
        String strX = String.valueOf(x);
        String strY = String.valueOf(y);
        return strX + " " + strY;
    }
}

// The type Food point.
class FoodPoint extends BoardEntity {
    // The Value.
    protected int value;

    public FoodPoint(EntityPosition position, int value) {
        entityPosition = position;
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

// The type Insect.
abstract class Insect extends BoardEntity {
    // The Color.
    protected InsectColor color;

    public Insect(EntityPosition position, InsectColor color) {
        entityPosition = position;
        this.color = color;
    }

    /**
     * Gets best direction.
     *
     * @param boardData the board data
     * @param boardSize the board size
     * @return the best direction
     */
    public abstract Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize);

    /**
     * Travel direction int.
     *
     * @param dir       the dir
     * @param boardData the board data
     * @param boardSize the board size
     * @return the int
     */
    public abstract int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize);
}

// The type Butterfly.
class Butterfly extends Insect implements OrthogonalMoving {
    /**
     * Instantiates a new Butterfly.
     *
     * @param entityPosition the entity position
     * @param color          the color
     */
    public Butterfly(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        Direction direction = Direction.N;
        int maxAmountOfFood = -1;
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.N, entityPosition, boardData, boardSize)) {
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.N, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.E, entityPosition, boardData, boardSize)) {
            direction = Direction.E;
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.E, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.S, entityPosition, boardData, boardSize)) {
            direction = Direction.S;
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.S, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.W, entityPosition, boardData, boardSize)) {
            direction = Direction.W;
        }
        return direction;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, entityPosition, color, boardData, boardSize);
    }
}

// The type Ant.
class Ant extends Insect implements OrthogonalMoving, DiagonalMoving {
    public Ant(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        Direction direction = Direction.N;
        int maxAmountOfFood = -1;
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.N, entityPosition, boardData, boardSize)) {
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.N, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.E, entityPosition, boardData, boardSize)) {
            direction = Direction.E;
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.E, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.S, entityPosition, boardData, boardSize)) {
            direction = Direction.S;
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.S, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getOrthogonalDirectionVisibleValue(Direction.W, entityPosition, boardData, boardSize)) {
            direction = Direction.W;
            maxAmountOfFood = getOrthogonalDirectionVisibleValue(Direction.W, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.NE, entityPosition, boardData, boardSize)) {
            direction = Direction.NE;
            maxAmountOfFood = getDiagonalDirectionVisibleValue(Direction.NE, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.SE, entityPosition, boardData, boardSize)) {
            direction = Direction.SE;
            maxAmountOfFood = getDiagonalDirectionVisibleValue(Direction.SE, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.SW, entityPosition, boardData, boardSize)) {
            direction = Direction.SW;
            maxAmountOfFood = getDiagonalDirectionVisibleValue(Direction.SW, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.NW, entityPosition, boardData, boardSize)) {
            direction = Direction.NW;
        }
        return direction;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        if (dir == Direction.N || dir == Direction.E || dir == Direction.S || dir == Direction.W) {
            return travelOrthogonally(dir, entityPosition, color, boardData, boardSize);
        } else {
            return travelDiagonally(dir, entityPosition, color, boardData, boardSize);
        }
    }
}

// The type Spider.
class Spider extends Insect implements DiagonalMoving {
    public Spider(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        Direction direction = Direction.NE;
        int maxAmountOfFood = -1;
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.NE, entityPosition, boardData, boardSize)) {
            maxAmountOfFood = getDiagonalDirectionVisibleValue(Direction.NE, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.SE, entityPosition, boardData, boardSize)) {
            direction = Direction.SE;
            maxAmountOfFood = getDiagonalDirectionVisibleValue(Direction.SE, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.SW, entityPosition, boardData, boardSize)) {
            direction = Direction.SW;
            maxAmountOfFood = getDiagonalDirectionVisibleValue(Direction.SW, entityPosition, boardData, boardSize);
        }
        if (maxAmountOfFood < getDiagonalDirectionVisibleValue(Direction.NW, entityPosition, boardData, boardSize)) {
            direction = Direction.NW;
        }
        return direction;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelDiagonally(dir, entityPosition, color, boardData, boardSize);
    }
}

// The type Grasshopper.
class Grasshopper extends Insect {
    public Grasshopper(EntityPosition entityPosition, InsectColor color) {
        super(entityPosition, color);
    }

    @Override
    public Direction getBestDirection(Map<String, BoardEntity> boardData, int boardSize) {
        Direction direction = Direction.N;
        int countOfFood = 0;
        int maxAmountOfFood;
        int x = entityPosition.getX();
        int y = entityPosition.getY();
        for (; x > 0; x -= 2) {
            String coordinates = x + " " + y;
            BoardEntity boardEntity = boardData.get(coordinates);
            if (boardEntity instanceof FoodPoint) {
                countOfFood += ((FoodPoint) boardEntity).getValue();
            }
        }
        maxAmountOfFood = countOfFood;
        x = entityPosition.getX();
        y = entityPosition.getY();
        countOfFood = 0;
        for (; y <= boardSize; y += 2) {
            String coordinates = x + " " + y;
            BoardEntity boardEntity = boardData.get(coordinates);
            if (boardEntity instanceof FoodPoint) {
                countOfFood += ((FoodPoint) boardEntity).getValue();
            }
        }
        if (countOfFood > maxAmountOfFood) {
            direction = Direction.E;
            maxAmountOfFood = countOfFood;
        }
        x = entityPosition.getX();
        y = entityPosition.getY();
        countOfFood = 0;
        for (; x <= boardSize; x += 2) {
            String coordinates = x + " " + y;
            BoardEntity boardEntity = boardData.get(coordinates);
            if (boardEntity instanceof FoodPoint) {
                countOfFood += ((FoodPoint) boardEntity).getValue();
            }
        }
        if (countOfFood > maxAmountOfFood) {
            direction = Direction.S;
            maxAmountOfFood = countOfFood;
        }
        x = entityPosition.getX();
        y = entityPosition.getY();
        countOfFood = 0;
        for (; y > 0; y -= 2) {
            String coordinates = x + " " + y;
            BoardEntity boardEntity = boardData.get(coordinates);
            if (boardEntity instanceof FoodPoint) {
                countOfFood += ((FoodPoint) boardEntity).getValue();
            }
        }
        if (countOfFood > maxAmountOfFood) {
            direction = Direction.W;
        }
        return direction;
    }

    /**
     * Travel orthogonally int.
     *
     * @param dir            the dir
     * @param entityPosition the entity position
     * @param color          the color
     * @param boardData      the board data
     * @param boardSize      the board size
     * @return the int
     */
    public int travelOrthogonally(Direction dir, EntityPosition entityPosition,
                                  InsectColor color, Map<String, BoardEntity> boardData, int boardSize) {
        int amountOfFood = 0;
        int x = entityPosition.getX();
        int y = entityPosition.getY();
        switch (dir) {
            case E:
                y += 2;
                while (y <= boardSize) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    y += 2;
                }
                break;
            case N:
                x -= 2;
                while (x > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x -= 2;
                }
                break;
            case S:
                x += 2;
                while (x <= boardSize) {
                    String xStr = String.valueOf(x);
                    String yStr = String.valueOf(y);
                    String coordinates = xStr + " " + yStr;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    x += 2;
                }
                break;
            case W:
                y -= 2;
                while (y > 0) {
                    String coordinates = x + " " + y;
                    BoardEntity boardEntity = boardData.get(coordinates);
                    if (boardEntity instanceof FoodPoint) {
                        amountOfFood += ((FoodPoint) boardEntity).getValue();
                        boardData.remove(coordinates);
                    } else if (boardEntity instanceof Insect) {
                        if (((Insect) boardEntity).color != color) {
                            boardData.remove(entityPosition.toString());
                            return amountOfFood;
                        }
                    }
                    y -= 2;
                }
                break;
            default:
                return amountOfFood;
        }
        boardData.remove(entityPosition.toString());
        return amountOfFood;
    }

    @Override
    public int travelDirection(Direction dir, Map<String, BoardEntity> boardData, int boardSize) {
        return travelOrthogonally(dir, entityPosition, color, boardData, boardSize);
    }
}

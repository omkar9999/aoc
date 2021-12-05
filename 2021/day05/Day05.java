
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day05 {
    public static void main(String[] args) {
        firstPart();
        secondPart();
    }

    private static void firstPart() {
        Map<Coord, Integer> coords = new HashMap<>();
        Files.readAllLines(Paths.get("input-day5.txt")).stream().map(Line::read).filter(Line::isVerticalOrHorizontal).forEach(line -> addCoords(coords, line));
        System.out.println(coords.values().stream().filter(v -> v > 1).count());
    }

    private static void secondPart() {
        Map<Coord, Integer> coords = new HashMap<>();
        Files.readAllLines(Paths.get("input-day5.txt")).stream().map(Line::read).filter(Line::isVerticalOrHorizontalOrDiagonal)
                .forEach(line -> addCoords(coords, line));
        System.out.println(coords.values().stream().filter(v -> v > 1).count());
    }

    private static void addCoords(Map<Coord, Integer> coords, Line line) {
        Coord step = new Coord((int) Math.signum(line.toX - line.fromX), (int) Math.signum(line.toY - line.fromY));
        Coord current = new Coord(line.fromX, line.fromY);
        for (int i = 0; i <= line.length(); ++i) {
            saveCoord(current, coords);
            current = current.plus(step);
        }
    }

    private static void saveCoord(Coord coord, Map<Coord, Integer> coords) {
        coords.compute(coord, (k, v) -> (v == null) ? 1 : v + 1);
    }

    private static final record Coord(int x, int y) {
        Coord plus(Coord coord) {
            return new Coord(x + coord.x, y + coord.y);
        }
    }

    private static record Line(int fromX, int fromY, int toX, int toY) {

        private static final Pattern INPUT_PATTERN = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");
        boolean isVerticalOrHorizontal() {
            return fromX == toX || fromY == toY;
        }

        boolean isVerticalOrHorizontalOrDiagonal() {
            return isVerticalOrHorizontal() || isDiagonal();
        }

        boolean isDiagonal() {
            return Math.abs(fromX - toX) == Math.abs(fromY - toY);
        }

        int length() {
            int manhattanDistance = Math.abs(fromX - toX) + Math.abs(fromY - toY);
            if (isDiagonal()) {
                return manhattanDistance / 2;
            }
            return manhattanDistance;
        }

        static Line read(String line) {
            var m = INPUT_PATTERN.matcher(line);
            if (!m.matches()) {
                throw new IllegalArgumentException("bad line descriptor: " + line);
            }
            return new Line(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)),
                    Integer.parseInt(m.group(4)));
        }
    }
}

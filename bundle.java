//Bundle uploaded at 05/09/2021 22:27:17
import java.util.*;
import java.util.stream.Collectors;
class Action {
    static final String WAIT = "WAIT";
    static final String SEED = "SEED";
    static final String GROW = "GROW";
    static final String COMPLETE = "COMPELTE";
    static Action parse(String action) {
        String[] parts = action.split(" ");
        switch (parts[0]) {
            case WAIT:
                return new Action(WAIT);
            case SEED:
                return new Action(SEED, Integer.valueOf(parts[1]), Integer.valueOf(parts[2]));
            case GROW:
            case COMPLETE:
            default:
                return new Action(parts[0], Integer.valueOf(parts[1]));
        }
    }
    String type;
    Integer targetCellIdx;
    Integer sourceCellIdx;
    public Action(String type, Integer sourceCellIdx, Integer targetCellIdx) {
        this.type = type;
        this.targetCellIdx = targetCellIdx;
        this.sourceCellIdx = sourceCellIdx;
    }
    public Action(String type, Integer targetCellIdx) {
        this(type, null, targetCellIdx);
    }
    public Action(String type) {
        this(type, null, null);
    }
    @Override
    public String toString() {
        if (type == WAIT) {
            return Action.WAIT;
        }
        if (type == SEED) {
            return String.format("%s %d", type, targetCellIdx);
        }
        return String.format("%s, %d", type, targetCellIdx);
    }
}
class Actor {
    Game game;
    ActorType actorType;
    int sunPoints = 0;
    int scorePoints = 0;
    List<Tree> trees = new ArrayList<>();
    public Actor(ActorType actorType, Game game) {
        this.actorType = actorType;
        this.game = game;
    }
    public void startTurn() {
        trees.clear();
    }
    // Etsitäänkö täsä paras vai palautetaanko täsä??+
    public Action findBestAction() {
        Action bestAction = null;
        //LAMDA reverse sorting type explicit ks stackoverflow reverse sort crow best trees
        trees.sort(Comparator.comparingInt((Tree o) -> game.board.get(o.cellIndex).richness).reversed());
        if (game.day >= 22 || (sunPoints > 25 && game.day > 15)) { // tavoittele bonusta
            bestAction = findBestTreeComplete();
        }
        if (bestAction == null) {
            bestAction = findBestTreeGrow();
        }
        if (bestAction == null) {
            bestAction = findBestTreePlan();
        }
        if (bestAction == null) {
            bestAction = game.possibleActions.get(0);
        }
        return bestAction; // can this be null?=
    }
    private Action findBestTreePlan() {
        // hommaa kaikki cellit filtteröi ja valitse paras
        // richness ++ nostaa arvoa
        List<Cell> allCells = game.board.stream()
                .filter(e -> e.seedPossible)
                .sorted(Comparator.comparingInt((Cell c) -> c.richness).reversed())
                .collect(Collectors.toList());
        // ffs dormand puut ei voi siementää joten täsä on probleema
        // joten pitää antaa siementävä puu
        if (!allCells.isEmpty()) {
            return plantTree(allCells.get(0).sourceTree.index, allCells.get(0).index);
        }
        return null;
    }
    private Action findBestTreeGrow() {
        for (int i = 0; i < trees.size(); i++) {
            Tree currentTree = trees.get(i);
            if (currentTree.canGrow() && sunPoints >= game.findGrowTreeCost((currentTree))) {
                return growTree(currentTree.cellIndex);
            }
        }
        return null; // ei löytyny actionia
    }
    private Action findBestTreeComplete() {
        for (int i = 0; i < trees.size(); i++) {
            Tree currentTree = trees.get(i);
            if (currentTree.canComplete() && sunPoints >= 4) {
                return completeTree(trees.get(i).cellIndex);
            }
        }
        return null; // ei löytyny actionia
    }
    public Action plantTree(int fromCellIndex, int toCellIndex) {
        return new Action(Action.SEED, fromCellIndex, toCellIndex);
    }
    public Action growTree(int cellIndex) {
        return new Action(Action.GROW, cellIndex);
    }
    public Action completeTree(int cellIndex) {
        return new Action(Action.COMPLETE, cellIndex);
    }
}
enum ActorType {
    MYSELF,ENEMY;
}
class Cell {
    // pitääk varjo yhdistää tähän??
    // has tree ? cell?
    int index;
    int richness;
    int[] neighbours;
    int shadow;
    // tulevaisuudessa puut jotenki voi connectaa tuleviin celleihi
    boolean seedPossible = false;
    Cell sourceTree = null;
    Tree tree = null;
    public Cell(int index, int richness, int[] neighbours) {
        this.index = index;
        this.richness = richness;
        this.neighbours = neighbours;
    }
    // MITÄ HITTIA PUU KASVAA MUT VARJO EI PELIS ?
    // pclean shadows between turns
    public void startTurn() {
        clear();
    }
    public void clear() {
        sourceTree = null;
        seedPossible = false;
        shadow = 0;
        tree = null; // Voiko samalle ruudulle plänttää samall roundil millä myi?
    }
    public boolean isEmpty() {
        return tree == null;
    }
    public boolean canGrowOnTile() {
        return isEmpty() && richness != 0 && seedPossible; // barrenis ei voi kasvatel ja tulevaisuudessa myös enemy
    }
    @Override
    public String toString() {
        return "Cell{" +
                "index=" + index +
                ", shadow=" + shadow +
                ", seedPossible=" + seedPossible +
                '}';
    }
    List<Cell> getNeighbouringCells(Game game) {
        List<Cell> neighbouringCells = new ArrayList<>();
        for (int neigbour : neighbours) {
            if (neigbour != -1) {
                neighbouringCells.add(game.board.get(neigbour));
            }
        }
        return neighbouringCells;
    }
}
// Heuristiikat
// kasvata keskustaa kohden ? Onko hyvä taku ?
// kasvata puita joiden ympärillä on vähiten puita?
// Ei kannata varmaan jonoon kasvattaa (Iso puu pistää varjoon monta?)
// Iso puu voi ampua pidemmälle siemenen :S
// state -> Board (voiko boardin
// soludata täytyy jotenki laskee
// ennustaa varjo seuraavale turnille??
// Pitääkö varoa ettei myy kaikkia puita ??
// varjo näköjään kasvaa vasta vuoron lopus?? Pitääkö vuoron alussa vaan tutkii varjot?
// Entä jos puun myy? Näyttäis että varjo jää?
// Simuloi päiviä ??
// Ennusta koska peli loppuu ?+ Haluanko myydä puun vai en ?
// ALA MYYMÄÄN VASTA KU PELI ON LOPPUMAISILLAAN !!!
// TODO: planttaa enemmän
class Game {
    int day;
    boolean dayChanged = false;
    int nutrients;
    List<Cell> board;
    List<Action> possibleActions;
    List<Tree> trees;
    Actor enemy, myself;
    public static int sunDirection = 0;
    boolean opponentIsWaiting;
    public Game() {
        board = new ArrayList<>();
        possibleActions = new ArrayList<>();
        trees = new ArrayList<>();
        enemy = new Actor(ActorType.ENEMY, this);
        myself = new Actor(ActorType.MYSELF, this);
    }
    public long countTrees(int size) {
        return trees.stream().filter(e -> e.isMine() && e.size == size).count();
    }
    public long findGrowTreeCost(Tree tree) {
        int baseCost = 0;
        switch (tree.size) {
            case 0:
                baseCost = 1;
                break;
            case 1:
                baseCost = 3;
                break;
            case 2:
                baseCost = 7;
                break;
        }
        return countTrees(tree.size + 1) + baseCost;
    }
    Action getNextAction() {
        return myself.findBestAction(); // move to actor
    }
    public void readInput(Scanner in) {
        int newDay = in.nextInt();
        dayChanged = newDay != day;
        day = newDay; // the game lasts 24 days: 0-23
        sunDirection = day % 6;
        nutrients = in.nextInt(); // the base score you gain from the next COMPLETE action
        myself.sunPoints = in.nextInt(); // your sun points
        myself.scorePoints = in.nextInt(); // your current score
        enemy.sunPoints = in.nextInt(); // opponent's sun points
        enemy.scorePoints = in.nextInt(); // opponent's score
        opponentIsWaiting = in.nextInt() != 0; // whether your opponent is asleep until the next day
        trees.clear();
        int numberOfTrees = in.nextInt(); // the current amount of trees
        for (int i = 0; i < numberOfTrees; i++) {
            int cellIndex = in.nextInt(); // location of this tree
            int size = in.nextInt(); // size of this tree: 0-3
            Actor treeOwner = in.nextInt() != 0 ? myself : enemy;
            boolean isDormant = in.nextInt() != 0; // 1 if this tree is dormant
            Tree tree = new Tree(cellIndex, size, treeOwner, isDormant);
            trees.add(tree);
            board.get(tree.cellIndex).tree = tree;
            if (tree.isMine()) {
                myself.trees.add(tree);
            } else {
                enemy.trees.add(tree);
            }
        }
        possibleActions.clear();
        int numberOfPossibleActions = in.nextInt();
        in.nextLine();
        for(int i = 0; i < numberOfPossibleActions; i++){
            String possibleAction = in.nextLine();
            possibleActions.add(Action.parse(possibleAction));
        }
    }
    public void readFirstTurn(Scanner in) {
        int numberOfCells = in.nextInt(); // 37
        for (int i = 0; i < numberOfCells; i++) {
            int index = in.nextInt(); // 0 is the center cell, the next cells spiral outwards
            int richness = in.nextInt(); // 0 if the cell is unusable, 1-3 for usable cells
            int neigh0 = in.nextInt(); // the index of the neighbouring cell for each direction
            int neigh1 = in.nextInt();
            int neigh2 = in.nextInt();
            int neigh3 = in.nextInt();
            int neigh4 = in.nextInt();
            int neigh5 = in.nextInt();
            int neighs[] = new int[]{neigh0, neigh1, neigh2, neigh3, neigh4, neigh5};
            Cell cell = new Cell(index, richness, neighs);
            board.add(cell);
        }
    }
    public void resetTurnBeforeInput() {
        dayChanged = false;
        myself.startTurn();
        enemy.startTurn();
        // tee warjot
        for (Cell cell : board) {
            cell.startTurn();
        }
    }
    public void initializeTurn() {
        if (dayChanged) {
            for (Tree tree : trees) {
                tree.castShadow(board);
            }
            // ehkä tulevaisuudessa vihulle kans mut nyt vaa omille puille ?
            for (Tree tree : myself.trees) {
                tree.markSeedDistance(board);
            }
            for (Cell cell : board) {
                System.err.println(cell);
            }
        }
    }
}
/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {
    //MOIMOI
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Game game = new Game();
        game.readFirstTurn(in);
        while (true) {
            game.resetTurnBeforeInput();
            game.readInput(in);
            game.initializeTurn(); // casting shawords
            Action action = game.getNextAction();
            System.out.println(action);
        }
    }
}
class Tree {
    int cellIndex;
    int size;
    Actor actor;
    boolean isDormant;
    public Tree(int cellIndex, int size, Actor actor, boolean isDormant) {
        this.cellIndex = cellIndex;
        this.size = size;
        this.actor = actor;
        this.isDormant = isDormant;
    }
    public boolean isMine() {
        return actor.actorType == ActorType.MYSELF;
    }
    public boolean canComplete() {
        return size >= 3 && isMine();
    }
    public boolean canGrow() {
        return !this.isDormant && isMine() && size < 3;
    }
    // -1 jos ei naapuria
    public void castShadow(List<Cell> board) {
        Cell currentCell = board.get(cellIndex);
        int shadowSize = size;
        while (shadowSize > 0) {
            if (currentCell.neighbours[Game.sunDirection] == -1) return; // no neighbour so exit
            currentCell = board.get(currentCell.neighbours[Game.sunDirection]); // int
            currentCell.shadow = this.size;
            shadowSize--;
        }
    }
    // get all neighbours shadow size -> move to next
    // all napurit -> ja niistä naapurit ja lisää... tulee infinite loop
    // täytyykö kerätä solut jotka on jo tutkittu... kyl
    public void markSeedDistance(List<Cell> board) { // kato saako tän tehtyy 2 listal
        if (size == 0 || isDormant) return;
        int distance = 0;
        List<Cell> visitedCells = new ArrayList<>(board.get(cellIndex).getNeighbouringCells(actor.game));
        Queue<Cell> nextNeighbours = new ArrayDeque<>(board.get(cellIndex).getNeighbouringCells(actor.game));
        visitedCells.add(board.get(cellIndex));
        List<Cell> nextDistanceCells = new ArrayList<>();
        // jos on koko 1 niin tuhlataan aika pali prosessointiaikaa
        // puu koko 2 -> size 1 naapurit
        while (!nextNeighbours.isEmpty()) {
            Cell nextCell = nextNeighbours.remove();
            nextCell.seedPossible = true;
            nextCell.sourceTree = board.get(cellIndex);
            List<Cell> newNeighbours = nextCell.getNeighbouringCells(actor.game);
            newNeighbours.removeAll(visitedCells);
            visitedCells.addAll(newNeighbours); // hommaa
            nextDistanceCells.addAll(newNeighbours);
            if (nextNeighbours.isEmpty()) {
                distance++;
                if (distance >= size) break; // >= jotta ei tehä liikaa rangea +1
                newNeighbours.addAll(nextDistanceCells);
                nextDistanceCells.clear(); // puhistaa seuraavaa rangea varte
            }
        }
    }
}

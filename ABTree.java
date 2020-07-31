
import java.util.ArrayList;


/**
 * Diese Klasse implementiert einen (a,b)-Baum.
 */
public class ABTree {

    /**
     * Diese Variable speichert die untere Schranke des Knotengrades.
     */
    private final int a;

    /**
     * Diese Variable speichert die obere Schranke des Knotengrades.
     */
    private final int b;

    /**
     * Diese Objektvariable speichert die Wurzel des Baumes.
     */
    private ABTreeInnerNode root = null;

    public ABTree(int a, int b) {
        if (a < 2) {
            throw new RuntimeException("Invalid a");
        } else if (b < 2 * a - 1) {
            throw new RuntimeException("Invalid b");
        }
        this.a = a;
        this.b = b;
    }

    public boolean validAB() {
        return root == null || root.validAB(true);
    }

    /**
     * Diese Methode ermittelt die Höhe des Baumes.
     *
     * @return die ermittelte Höhe
     */
    public int height() {
        if (root == null) {
            return 0;
        }
        return root.height();
    }

    /**
     * Diese Methode sucht einen Schlüssel im (a,b)-Baum.
     *
     * @param key der Schlüssel, der gesucht werden soll
     * @return 'true', falls der Schlüssel gefunden wurde, 'false' sonst
     */
    public boolean find(int key) {
        return root != null && root.find(key);
    }

    /**
     * Diese Methode fügt einen neuen Schlüssel in den (a,b)-Baum ein.
     *
     * @param key der einzufügende Schlüssel
     */
    public void insert(int key) {
        if (root != null) {
            root.insert(key);
            if (root.getChildren().size() > b) {
                // Wenn die Anzahl der Kindbäume größer wird als 'b', müssen wir den Knoten aufteilen
                int middle = b / 2;

                ArrayList<Integer> keysLeft = new ArrayList<>();
                ArrayList<ABTreeNode> childrenLeft = new ArrayList<>();
                ArrayList<Integer> keysRight = new ArrayList<>();
                ArrayList<ABTreeNode> childrenRight = new ArrayList<>();

                for (int i = 0; i < root.getKeys().size(); i++) {
                    if (i < middle) {
                        keysLeft.add(root.getKeys().get(i));
                        childrenLeft.add(root.getChildren().get(i));
                    } else if (i == middle) {
                        childrenLeft.add(root.getChildren().get(i));
                    } else {
                        keysRight.add(root.getKeys().get(i));
                        childrenRight.add(root.getChildren().get(i));
                    }
                }
                childrenRight.add(root.getChildren().get(root.getChildren().size() - 1));

                ABTreeInnerNode leftNew = new ABTreeInnerNode(keysLeft, childrenLeft, a, b);
                ABTreeInnerNode rightNew = new ABTreeInnerNode(keysRight, childrenRight, a, b);

                root = new ABTreeInnerNode(root.getKeys().get(middle), leftNew, rightNew, a, b);
            }
        } else {
            root = new ABTreeInnerNode(key, a, b);
        }
    }

    /**
     * Diese Methode löscht einen Schlüssel aus dem (a,b)-Baum.
     *
     * @param key der zu löschende Schlüssel
     * @return 'true' falls der Schlüssel gefunden und gelöscht wurde, 'false'
     * sonst
     */
    public boolean remove(int key) {
        if (root != null) {
            boolean result = root.remove(key);
            if (root.getChildren().size() < 2 && root.getChildren().get(0) instanceof ABTreeInnerNode) {
                root = (ABTreeInnerNode) root.getChildren().get(0);
            } else if (root.getChildren().size() < 2) {
                root = null;
            }
            return result;
        }
        return false;
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der Baum im Graphiz-Format
     */
    String dot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        sb.append("\tnode [shape=record];\n");
        if (root != null) {
            root.dot(sb, 0);
        }
        sb.append("}");
        return sb.toString();
    }
}

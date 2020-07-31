
import java.util.ArrayList;

/**
 * Diese Klasse repräsentiert einen inneren Knoten des Baumes.
 */
public class ABTreeInnerNode extends ABTreeNode {

    private ArrayList<Integer> keys;
    private ArrayList<ABTreeNode> children;

    public ABTreeInnerNode(ArrayList<Integer> keys, ArrayList<ABTreeNode> children, int a, int b) {
        super(a, b);
        this.keys = keys;
        this.children = children;
    }

    public ABTreeInnerNode(int key, ABTreeNode left, ABTreeNode right, int a, int b) {
        super(a, b);
        keys = new ArrayList<>();
        children = new ArrayList<>();
        keys.add(key);
        children.add(left);
        children.add(right);
    }

    public ABTreeInnerNode(int key, int a, int b) {
        this(key, new ABTreeLeaf(a, b), new ABTreeLeaf(a, b), a, b);
    }
    
    public ArrayList<ABTreeNode> getChildren() {
        return children;
    }
    
    public ArrayList<Integer> getKeys() {
        return keys;
    }

    @Override
    public void insert(int key) {
        int index;
        // Wir suchen zunächst die passende Position zum einfügen...
        for (index = 0; index < keys.size(); index++) {
            // Duplike einfügen ist nicht erlaubt...
            if (keys.get(index) == key) {
                return;
            }
            if (keys.get(index) > key) {
                break;
            }
        }
        // ... und fügen dann im passenden Kindbaum ein.
        ABTreeNode child = children.get(index);
        child.insert(key);

        // jetzt müssen wir noch die Einträge anpassen, und evtl. den Kindknoten splitten.
        if (child instanceof ABTreeLeaf) {
            ABTreeLeaf newLeaf = new ABTreeLeaf(a, b);
            keys.add(index, key);
            children.add(index, newLeaf);
        } else {
            ABTreeInnerNode innerChild = (ABTreeInnerNode) child;
            if (innerChild.children.size() > b) {
                // Wenn die Anzahl der Kindbäume größer wird als 'b', müssen wir den Knoten aufteilen.
                int middle = b / 2;

                ArrayList<Integer> keysLeft = new ArrayList<>();
                ArrayList<ABTreeNode> childrenLeft = new ArrayList<>();
                ArrayList<Integer> keysRight = new ArrayList<>();
                ArrayList<ABTreeNode> childrenRight = new ArrayList<>();

                for (int i = 0; i < innerChild.keys.size(); i++) {
                    if (i < middle) {
                        keysLeft.add(innerChild.keys.get(i));
                        childrenLeft.add(innerChild.children.get(i));
                    } else if (i == middle) {
                        childrenLeft.add(innerChild.children.get(i));
                    } else {
                        keysRight.add(innerChild.keys.get(i));
                        childrenRight.add(innerChild.children.get(i));
                    }
                }
                childrenRight.add(innerChild.children.get(innerChild.children.size() - 1));

                ABTreeInnerNode leftNew = new ABTreeInnerNode(keysLeft, childrenLeft, a, b);
                ABTreeInnerNode rightNew = new ABTreeInnerNode(keysRight, childrenRight, a, b);

                keys.add(index, innerChild.keys.get(middle));
                children.set(index, leftNew);
                children.add(index + 1, rightNew);
            }
        }
    }

    @Override
    public boolean canSteal() {
        return children.size() > a;
    }

    @Override
    public boolean find(int key) {
        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i) == key) {
                return true;
            } else if (keys.get(i) > key) {
                return children.get(i).find(key);
            }
        }
        return children.get(children.size() - 1).find(key);
    }

    @Override
    public boolean remove(int key) {
        for (int index = 0; index < children.size(); index++) {
            ABTreeNode child = children.get(index);
            if (index == keys.size() || keys.get(index) > key) {
                boolean result = child.remove(key);
                if (result) {
                    balanceChildren(index);
                }
                return result;
            } else if (keys.get(index) == key) {
                if (child instanceof ABTreeLeaf) {
                    assert (index < keys.size());
                    keys.remove(index);
                    children.remove(index);
                } else {
                    keys.set(index, ((ABTreeInnerNode) child).removeRightMostChild());
                    balanceChildren(index);
                }
                return true;
            }
        }
        return false;
    }

    private void balanceChildren(int index) {
        assert (children.get(index) instanceof ABTreeInnerNode);
        ABTreeInnerNode innerChild = (ABTreeInnerNode) children.get(index);
        if (innerChild.children.size() < a) {
            // können wir einen Knoten von einem Nachbarn klauen?
            if (index - 1 >= 0 && children.get(index - 1).canSteal()) {
                // nimm größten Knoten vom linken Nachbarn
                ABTreeInnerNode leftNeighbor = (ABTreeInnerNode) children.get(index - 1);
                innerChild.children.add(0, leftNeighbor.children.get(leftNeighbor.children.size() - 1));
                leftNeighbor.children.remove(leftNeighbor.children.size() - 1);

                innerChild.keys.add(0, keys.get(index - 1));
                keys.set(index - 1, leftNeighbor.keys.get(leftNeighbor.keys.size() - 1));
                leftNeighbor.keys.remove(leftNeighbor.keys.size() - 1);
            } else if (index + 1 < children.size() && children.get(index + 1).canSteal()) {
                // nimm kleinsten Knoten vom rechten Nachbarn
                ABTreeInnerNode rightNeighbor = (ABTreeInnerNode) children.get(index + 1);
                innerChild.children.add(rightNeighbor.children.get(0));
                rightNeighbor.children.remove(0);

                innerChild.keys.add(keys.get(index));
                keys.set(index, rightNeighbor.keys.get(0));
                rightNeighbor.keys.remove(0);
            } else // verschmelze mit Nachbarknoten
            if (index + 1 < children.size()) {
                mergeChildren(index);
            } else {
                mergeChildren(index - 1);
            }
        }
    }

    private void mergeChildren(int keyIndex) {
        ABTreeInnerNode node0 = (ABTreeInnerNode) children.get(keyIndex);
        ABTreeInnerNode node1 = (ABTreeInnerNode) children.get(keyIndex + 1);

        ArrayList<Integer> keys = new ArrayList<>();
        keys.addAll(node0.keys);
        keys.add(this.keys.get(keyIndex));
        keys.addAll(node1.keys);

        ArrayList<ABTreeNode> children = new ArrayList<>();
        children.addAll(node0.children);
        children.addAll(node1.children);

        ABTreeInnerNode newNode = new ABTreeInnerNode(keys, children, a, b);

        this.children.remove(keyIndex + 1);
        this.children.set(keyIndex, newNode);
        this.keys.remove(keyIndex);
    }

    private int removeRightMostChild() {
        ABTreeNode child = children.get(children.size() - 1);
        if (child instanceof ABTreeLeaf) {
            children.remove(children.size() - 1);
            int key = keys.get(keys.size() - 1);
            keys.remove(keys.size() - 1);
            return key;
        } else {
            int key = ((ABTreeInnerNode) child).removeRightMostChild();
            balanceChildren(children.size() - 1);
            return key;
        }
    }

    @Override
    public int height() {
        return 1 + children.get(0).height();
    }

    @Override
    public Integer min() {
        Integer result = children.get(0).min();
        if (result == null) {
            result = keys.get(0);
        }
        return result;
    }

    @Override
    public Integer max() {
        Integer result = children.get(children.size() - 1).max();
        if (result == null) {
            result = keys.get(keys.size() - 1);
        }
        return result;
    }

    @Override
    public boolean validAB(boolean root) {
        if (children.size() < (root ? 2 : a)) {
            System.err.println("children.size < a");
            return false;
        }
        if (children.size() > b) {
            System.err.println("children.size > b");
            return false;
        }
        int h = height();
        for (int i = 0; i < children.size(); i++) {
            if (!children.get(i).validAB(false)) {
                System.err.println(String.format("child %d invalid", i));
                return false;
            }
            if (h != children.get(i).height() + 1) {
                System.err.println(String.format("child %d has invalid height", i));
                return false;
            }
        }
        if (h == 1) {
            return true;
        }
        for (int i = 0; i < keys.size(); i++) {
            Integer min = children.get(i).min();
            Integer max = children.get(i).max();
            if (max != null && max >= keys.get(i)) {
                System.err.println(String.format("max %d >= key(%d) %d", max, i, keys.get(i)));
                return false;
            }
            if (min != null && min >= keys.get(i)) {
                System.err.println(String.format("min %d >= key(%d) %d", min, i, keys.get(i)));
                return false;
            }
        }
        return true;
    }

    @Override
    public int dot(StringBuilder sb, int from) {
        int mine = from++;
        sb.append(String.format("\tstruct%s [label=\"", mine));
        for (int i = 0; i < 2 * keys.size() + 1; i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(String.format("<f%d> ", i));
            if (i % 2 == 1) {
                sb.append(keys.get(i / 2));
            }
        }
        sb.append("\"];\n");
        for (int i = 0; i < children.size(); i++) {
            int field = 2 * i;
            sb.append(String.format("\tstruct%d:<f%d> -> struct%d;\n", mine, field, from));
            from = children.get(i).dot(sb, from);
        }
        return from;
    }
}

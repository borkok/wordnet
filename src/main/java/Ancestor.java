

class Ancestor implements Comparable<Ancestor> {
    private final int ancestor;
    private final int distanceTo;

    Ancestor(int ancestor, int distanceTo) {
        this.ancestor = ancestor;
        this.distanceTo = distanceTo;
    }

    int getAncestor() {
        return ancestor;
    }

    int getDistanceTo() {
        return distanceTo;
    }

    @Override
    public int compareTo(Ancestor other) {
        return this.distanceTo - other.distanceTo;
    }
}

package src.model.interseccao;

public class IntersectResult {
    public double distance;
    public Intersectable object;

    public IntersectResult(Intersectable object, double distance) {
        this.distance = distance;
        this.object = object;
    }    

    public IntersectResult() {
        this.distance = Double.MAX_VALUE;
        this.object = null;
    }

    public double getDistance() {
        return this.distance;
    }

    public Intersectable getObject() {
        return this.object;
    }
}

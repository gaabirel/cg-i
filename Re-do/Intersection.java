public class Intersection {
    public Vector3 position;
    public double distance;

    public Intersection(Vector3 position, double distance){
        this.position = position;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Intersection at " + position + " with distance " + distance;
    }
    
}

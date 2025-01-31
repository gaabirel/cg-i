package src.model.objetos;

import java.util.ArrayList;

import src.model.interseccao.Intersection;
import src.model.interseccao.Ray;
import src.model.interseccao.Vector3;

public class Malha extends Objeto3D implements Intersectable{
    
    private int faceInterseccionadaID =  0;
    private ArrayList<Vector3> vertices;
    private ArrayList<Aresta> arestas;
    private ArrayList<Triangulo> faces;
    
    public Malha(ArrayList<Vector3> vertices, ArrayList<Aresta> arestas, ArrayList<Triangulo> faces){
        this.vertices = vertices;
        this.arestas = arestas;
        this.faces = faces;
    }
    
    public ArrayList<Vector3> getVertices(){
        return this.vertices;
    }
    
    public ArrayList<Aresta> getarestas(){
        return this.arestas;
    }
    
    
    public ArrayList<Triangulo> getFaces(){
        return this.faces;
    }
    
    public void setVertices(ArrayList<Vector3> vertices){
        this.vertices = vertices;
    }
    public void setArestas(ArrayList<Aresta> arestas){
        this.arestas = arestas;
    }

    public void setFaces(ArrayList<Triangulo> faces){
        this.faces = faces;
    }

    @Override
    public Intersection intersect(Ray ray, double[][] matrizTransformacao) {
        for(Triangulo face : faces){
            Intersection interseccao = face.intersect(ray, matrizTransformacao);
            if(interseccao != null){
                faceInterseccionadaID = faces.indexOf(face);
                return interseccao;
            }
        }
        return null;
    }

    @Override
    public Vector3 calcularNormal(Vector3 ponto, double[][] matrizTransformacao) {
        return faces.get(faceInterseccionadaID).calcularNormal(ponto, matrizTransformacao);
    }

    @Override
    public void transladar(double dx, double dy, double dz) {
        for(Vector3 vertice : vertices){
            double x = vertice.getX();
            double y = vertice.getY();
            double z = vertice.getZ();
            vertice.setX(x + dx);
            vertice.setY(y + dy);
            vertice.setZ(z + dz);
        }
    }

    @Override
    public void rotacionar(double angulo, Vector3 axis) {
        for(Vector3 vertice : vertices){
            Vector3 verticeRodado = vertice.rotate(angulo, axis);
            vertice.setX(verticeRodado.getX());
            vertice.setY(verticeRodado.getY());
            vertice.setZ(verticeRodado.getZ());
        }
    }

    @Override
    public Vector3 getKdifuso(){
        return faces.get(faceInterseccionadaID).getMaterial().getkDifuso();
    }
    
    @Override
    public Vector3 getKespecular(){
        return faces.get(faceInterseccionadaID).getMaterial().getkEspecular();
    }
    
    @Override
    public Vector3 getKambiente(){
        return faces.get(faceInterseccionadaID).getMaterial().getkAmbiente();
    }

    @Override
    public double getBrilho(){
        return faces.get(faceInterseccionadaID).getBrilho();
    }
}

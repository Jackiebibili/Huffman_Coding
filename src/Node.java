import java.io.*;

public class Node<E extends Comparable<E>> implements Comparable<Node<E>>, Serializable {
   private E payload;
   private Node<E> left;
   private Node<E> right;

   public Node() {
      this(null);
   }

   public Node(E load) {
      payload = load;
      left = right = null;
   }

   @Override
   public int compareTo(Node<E> obj) {
      return payload.compareTo(obj.getPayload());
   }

   public void setLeft(Node<E> l) {
      left = l;
   }

   public void setRight(Node<E> r) {
      right = r;
   }

   public void setPayload(E load) {
      payload = load;
   }

   public E getPayload() {
      return payload;
   }

   public Node<E> getLeft() {
      return left;
   }

   public Node<E> getRight() {
      return right;
   }

}

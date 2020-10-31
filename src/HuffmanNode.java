public class HuffmanNode<E extends Comparable<E>> extends Node<E> {
   private int weight;

   public HuffmanNode() {
      this(null, 0);
   }

   public HuffmanNode(E e, int w) {
      super(e);
      weight = w;
   }

   @Override
   public int compareTo(Node<E> obj) {
      return this.weight - ((HuffmanNode<E>) (obj)).weight;
   }

   public int getWeight() {
      return weight;
   }

   public void setWeight(int w) {
      weight = w;
   }

   public HuffmanNode<E> getLeft() {
      return (HuffmanNode<E>) (super.getLeft());
   }

   public HuffmanNode<E> getRight() {
      return (HuffmanNode<E>) (super.getRight());
   }
}

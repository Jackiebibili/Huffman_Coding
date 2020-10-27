import java.util.ArrayList;

public class BSTree<E extends Comparable<E>> {
   protected Node<E> root;

   public BSTree() {
      this(null);
   }

   public BSTree(Node<E> root) {
      this.root = root;
   }

   public ArrayList<Node<E>> getPath(E payload) {
      ArrayList<Node<E>> list = new ArrayList<>();
      Node<E> find = createNewNode(payload), cur = root;
      while (cur != null) {
         list.add(cur);
         if (cur.compareTo(find) < 0) {
            cur = cur.getLeft();
         } else {
            cur = cur.getRight();
         }
      }
      return list;
   }

   public void inorder() {
      inorder(root);
      System.out.println();
   }

   private void inorder(Node<E> node) {
      if (node == null)
         return;
      inorder(node.getLeft());
      System.out.print(node.getPayload() + "  ");
      inorder(node.getRight());
   }

   public Node<E> createNewNode(E payload) {
      return new Node<E>(payload);
   }

   public void insert(E payload) {
      Node<E> newNode = createNewNode(payload);
      if (root == null) {
         root = newNode;
      } else {
         insert(newNode, root, null);
      }
   }

   private void insert(Node<E> newNode, Node<E> node, Node<E> parent) {
      if (node == null) {
         if (newNode.compareTo(parent) < 0) {
            parent.setLeft(newNode);
         } else {
            parent.setRight(newNode);
         }
      } else if (newNode.compareTo(node) < 0) {
         insert(newNode, node.getLeft(), node);
      } else {
         insert(newNode, node.getRight(), node);
      }
   }

   public Node<E> search(E payload) {
      return search(createNewNode(payload), root);
   }

   private Node<E> search(Node<E> find, Node<E> node) {
      if (node == null) {
         return null;
      } else if (find.compareTo(node) == 0) {
         return node;
      } else if (find.compareTo(node) < 0) {
         return search(find, node.getLeft());
      } else {
         return search(find, node.getRight());
      }
   }

   public Node<E> searchParent(E payload) {
      return searchParent(createNewNode(payload), root, null);
   }

   private Node<E> searchParent(Node<E> find, Node<E> node, Node<E> parent) {
      if (node == null) {
         return null;
      } else if (find.compareTo(node) == 0) {
         return parent;
      } else if (find.compareTo(node) < 0) {
         return searchParent(find, node.getLeft(), node);
      } else {
         return searchParent(find, node.getRight(), node);
      }
   }

   public Node<E> delete(E payload) {
      // get the parent and the current node
      Node<E> parent = searchParent(payload);
      Node<E> current = search(payload);
      if (current != null)
         return delete(current, parent);
      else
         return null;
   }

   private Node<E> delete(Node<E> current, Node<E> parent) {
      if (root == null) {
         return null;
      }
      // 2 children
      if (current.getLeft() != null && current.getRight() != null) {
         // get the leftmost node of the right subtree
         Node<E> leftMost = current.getRight(), leftMostParent = current;
         while (leftMost.getLeft() != null) {
            leftMostParent = leftMost;
            leftMost = leftMost.getLeft();
         }
         // swap the content
         E curPayload = current.getPayload();
         current.setPayload(leftMost.getPayload());
         leftMost.setPayload(curPayload);

         return delete(leftMost, leftMostParent);
      } else if (current.getLeft() != null) { // left child
         if (current == root) {
            root = current.getLeft();
         } else if (parent.getLeft() == current) {
            parent.setLeft(current.getLeft());
         } else {
            parent.setRight(current.getLeft());
         }
         current.setLeft(null);
         return current;
      } else { // current.getRight() != null; right child
         if (root == current) {
            root = current.getRight();
         } else if (parent.getLeft() == current) {
            parent.setLeft(current.getRight());
         } else {
            parent.setRight(current.getRight());
         }
         current.setRight(null);
         return current;
      }

   }

}

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class HuffmanTree {
   private ArrayList<Integer> chars;
   private ArrayList<String> codes;
   private HuffmanNode<Integer> root;

   public HuffmanTree() {
      root = null;
      chars = new ArrayList<Integer>();
      codes = new ArrayList<String>();
   }

   public HuffmanTree(String filename) {
      // input file to be compressed
      chars = new ArrayList<Integer>();
      ArrayList<Integer[]> occurrence = new ArrayList<Integer[]>();
      InputStream in = null;
      /////////////////////////////////////
      try {
         in = new FileInputStream(new File(filename));
         int num = in.read();
         while (num != -1) {
            int index = chars.indexOf(num);
            if (index == -1) {
               // new value to the list
               chars.add(num);
               occurrence.add(new Integer[] { 0, 1 });
            } else {
               occurrence.get(index)[1]++;
            }
            num = in.read();
         }
         in.close();
      } catch (IOException ex) {
         return;
      }
      for (int i = 0; i < chars.size(); i++) {
         occurrence.get(i)[0] = chars.get(i);
      }
      ////////////////////////////////////
      occurrence.sort((o1, o2) -> o1[1] - o2[1]);

      for (int i = 0; i < occurrence.size(); i++) {
         chars.set(i, occurrence.get(i)[0]);
      }
      codes = new ArrayList<String>(occurrence.size());
      for (int i = 0; i < occurrence.size(); i++) {
         codes.add("-1");
      }
      createHuffmanTree(occurrence);
      setCodeTable();
   }

   public void encode(String infile, String outFile) {
      InputStream in = null;
      OutputStream out = null;
      LinkedList<Integer> q1 = new LinkedList<Integer>();
      try {
         in = new FileInputStream(new File(infile));
         out = new FileOutputStream(new File(outFile));
         String outNum = "";
         int inNum = in.read();
         while (inNum != -1) {
            outNum = codes.get(chars.indexOf(inNum));
            enqueueInEncode(q1, outNum);
            if (q1.size() >= 8) {
               // dequeue
               out.write((byte) (Integer.parseInt(dequeue(q1, 8), 2)));
            }
            inNum = in.read();
         }
         if (!q1.isEmpty()) {
            out.write((byte) (Integer.parseInt(dequeue(q1, q1.size()), 2)));
         }

         in.close();
         out.close();
      } catch (IOException ex) {
         return;
      }

   }

   private String dequeue(LinkedList<Integer> list, int span) {
      String num = "";
      for (int i = 0; i < span; i++) {
         num = num + list.removeFirst();
      }
      return num;
   }

   private void enqueueInEncode(LinkedList<Integer> list, String num) {
      for (int i = 0; i < num.length(); i++) {
         list.add(Integer.valueOf("" + num.charAt(i)));
      }
   }

   public void decode(String infile, String outFile) {
      InputStream in = null;
      OutputStream out = null;
      try {
         in = new FileInputStream(new File(infile));
         out = new FileOutputStream(new File(outFile));
         int inNum = (byte) (in.read());
         int next = (byte) (in.read());
         HuffmanNode<Integer> node = root;
         LinkedList<Integer> queue = new LinkedList<>();
         enqueueInDecode(queue, inNum);
         while (inNum != -1) {
            inNum = (byte) next;
            enqueueInDecode(queue, inNum);
            while (queue.size() > 0) {
               if (queue.removeFirst() == 0) {
                  // branch left
                  node = node.getLeft();
               } else {
                  // branch right
                  node = node.getRight();
               }
               if (node.getLeft() == null && node.getRight() == null) {
                  out.write(node.getPayload());
                  node = root;
               }
            }
            next = in.read();
            inNum = in.read();
         }
         if (next != -1) {
            // take care of the last byte
            next = (byte) next;
            String num = Integer.toString(Integer.valueOf(next), 2);
            for (int i = 0; i < num.length(); i++) {
               queue.add(Integer.valueOf("" + num.charAt(i)));
            }
            while (queue.size() > 0) {
               if (queue.removeFirst() == 0) {
                  // branch left
                  node = node.getLeft();
               } else {
                  // branch right
                  node = node.getRight();
               }
               if (node.getLeft() == null && node.getRight() == null) {
                  out.write(node.getPayload());
                  node = root;
               }
            }
         }
         in.close();
         out.close();
      } catch (IOException ex) {
         return;
      }
   }

   private void enqueueInDecode(LinkedList<Integer> list, int num) {
      String binary = new BigNumber("" + num, 10).toSignedBinary();
      for (int i = 0; i < 8 - binary.length(); i++) {
         binary = "0" + binary;
      }
      enqueueInEncode(list, binary);
   }

   public HuffmanNode<Integer> createNewNode(Integer e, int w) {
      return new HuffmanNode<Integer>(e, w);
   }

   private void setCodeTable() {
      setCode(root, "");
   }

   private void setCode(HuffmanNode<Integer> node, String code) {
      if (node == null) {
         return;
      }
      setCode(node.getLeft(), code + "0");
      if (node.getLeft() == null && node.getRight() == null) {
         // leaf node
         codes.set(chars.indexOf(node.getPayload()), code);
      }
      setCode(node.getRight(), code + "1");
   }

   private void createHuffmanTree(ArrayList<Integer[]> occurrence) {
      int i = 0;
      HuffmanNode<Integer> h1 = null, h2 = null;
      if (occurrence.size() < 4) {

      }
      while (i < occurrence.size()) {
         if (h1 == null) {
            Integer[] left = occurrence.get(i++);
            Integer[] right = occurrence.get(i++);
            h1 = createNewNode(0, left[1] + right[1]);
            h1.setLeft(createNewNode(left[0], left[1]));
            h1.setRight(createNewNode(right[0], right[1]));
            continue;
         }
         if (h2 == null) {
            Integer[] left = occurrence.get(i++);
            Integer[] right = occurrence.get(i++);
            h2 = createNewNode(0, left[1] + right[1]);
            h2.setLeft(createNewNode(left[0], left[1]));
            h2.setRight(createNewNode(right[0], right[1]));
            continue;
         }
         if (i < occurrence.size()) {
            HuffmanNode<Integer> temp = h1;
            Integer[] left = occurrence.get(i++);
            h1 = createNewNode(0, left[1] + h1.getPayload());
            h1.setLeft(createNewNode(left[0], left[1]));
            h1.setRight(temp);
         }
         if (i < occurrence.size()) {
            HuffmanNode<Integer> temp = h2;
            Integer[] left = occurrence.get(i++);
            h2 = createNewNode(0, left[1] + h2.getPayload());
            h2.setLeft(createNewNode(left[0], left[1]));
            h2.setRight(temp);
         }
      }

      // combine two subtrees
      root = createNewNode(0, h1.getPayload() + h2.getPayload());
      root.setLeft(h1);
      root.setRight(h2);
   }

}

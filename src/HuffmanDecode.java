import java.io.*;
import java.util.LinkedList;

public class HuffmanDecode extends HuffmanTree {
   FileInputStream in;

   public HuffmanDecode() {
      super();
      in = null;
   }

   public HuffmanDecode(String filename) {
      // decompress the file of the given filename
      // init the Huffman Tree by deserializing the tree from the compressed file
      try {
         in = new FileInputStream(new File(filename));
         ObjectInputStream obj_in = new ObjectInputStream(in);
         HuffmanEncode result = (HuffmanEncode) obj_in.readObject();
         root = result.root;
         marker = result.marker;
      } catch (IOException | ClassNotFoundException ex) {
         root = null;
      }
   }

   public void decode(String outFile) {
      try {
         OutputStream out = new FileOutputStream(new File(outFile));
         boolean isEOF = false;
         int markerCount = marker[1];
         HuffmanNode<Integer> node = root;
         LinkedList<Integer> queue = new LinkedList<>();
         byte[] items = in.readAllBytes();
         for (int i = 0; !isEOF && i < items.length; i++) {
            int inNum = items[i];
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
                  int payload = node.getPayload();
                  //////////////// EOF ////////////////////
                  if (payload == marker[0] && markerCount == 0) {
                     // marker -- pseudo EOF
                     isEOF = true;
                     break;
                     //////////////////////////////////////
                  } else if (payload == marker[0]) {
                     markerCount--;
                     out.write(payload - BYTE_SIZE);
                     node = root;
                  } else {
                     out.write(payload - BYTE_SIZE);
                     node = root;
                  }
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

   private static void enqueueInEncode(LinkedList<Integer> list, String num) {
      for (int i = 0; i < num.length(); i++) {
         list.add(Integer.valueOf("" + num.charAt(i)));
      }
   }
}

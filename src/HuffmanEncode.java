import java.io.*;
import java.util.LinkedList;

public class HuffmanEncode extends HuffmanTree {
   public HuffmanEncode() {
      super();
   }

   public HuffmanEncode(String filename) {
      // compress the file of the given filename
      // init the Huffman Tree by the filename
      super(filename);
   }

   public void encode(String infile, String outFile) {
      String[] table = new String[SIZE];
      setCodeTable(table);
      LinkedList<Integer> que2 = new LinkedList<Integer>();
      try {
         InputStream in = new FileInputStream(new File(infile));
         OutputStream out = new FileOutputStream(new File(outFile));

         // serialize the Huffman tree
         ObjectOutputStream out_obj = new ObjectOutputStream(out);
         out_obj.writeObject(this);
         // writeObject(out_obj);

         String outNum = "";
         byte[] items = in.readAllBytes();
         for (int i = 0; i < items.length; i++) {
            outNum = table[items[i] + BYTE_SIZE];
            enqueueInEncode(que2, outNum);
            while (que2.size() >= 8) {
               // dequeue
               out.write((byte) (Integer.parseInt(dequeue(que2, 8), 2)));
            }
         }
         //////////// EOF ///////////////////
         enqueueInEncode(que2, table[marker[0]]);
         ////////////////////////////////////
         while (!que2.isEmpty()) {
            if (que2.size() < 8) {
               out.write((byte) (Integer.parseInt(dequeue(que2, que2.size()), 2)));
            } else {
               out.write((byte) (Integer.parseInt(dequeue(que2, 8), 2)));
            }
         }
         out_obj.close();
         in.close();
      } catch (IOException ex) {
         return;
      }
   }

   private static String dequeue(LinkedList<Integer> list, int span) {
      String num = "";
      for (int i = 0; i < span; i++) {
         num = num + list.removeFirst();
      }
      if (span < 8) {
         for (int i = 0; i < 8 - span; i++) {
            num = num + "0";
         }
      }
      return num;
   }

   private static void enqueueInEncode(LinkedList<Integer> list, String num) {
      for (int i = 0; i < num.length(); i++) {
         list.add(Integer.valueOf("" + num.charAt(i)));
      }
   }

}

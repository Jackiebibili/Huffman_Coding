import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename of the file to be compressed: ");
        String inFile = scanner.nextLine();
        System.out.print("Enter the filename of the compressed file: ");
        String outFile = scanner.nextLine();
        scanner.close();
        HuffmanTree huffman = new HuffmanTree(inFile);
        huffman.encode(inFile, outFile);
        huffman.decode(outFile, "result2.dat");
    }
}

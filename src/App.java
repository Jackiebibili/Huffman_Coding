import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename of the file to be compressed: ");
        String inFile = scanner.nextLine();
        System.out.print("Enter the filename of the compressed file: ");
        String outFile = scanner.nextLine();
        System.out.print("Enter the filename of the extracted file: ");
        String exFile = scanner.nextLine();

        scanner.close();
        HuffmanEncode huffman = new HuffmanEncode(inFile);
        huffman.encode(inFile, outFile);

        HuffmanDecode decode = new HuffmanDecode(outFile);
        decode.decode(exFile);
    }
}

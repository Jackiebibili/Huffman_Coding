# Huffman Coding
Huffman Coding a powerful Java module to compress and decompress a file in which codewords have integer numbers of bit lengths. 
* Shrinks the file size on the back-end while it can perfectly reconstruct the data when it decompresses.
* Has a significant reduced size for medium size text file consisting of alphanumeric characters.
* Compression process and decompression process are independent stages. The compressed file can be decoded any time into the original file.
## How It Works
1. Huffman Coding has two concrete classes of `HuffmanEncode` and `HuffmanDecode` which inherit the `HuffmanTree` class which maintains the Huffman tree which can be used to trace the characters it stores.
2. Huffman Coding serializes the non-static contents of `HuffmanTree` into the prefix of the file and appends the Huffman code sequence.
3. In encoding process, a queue is utilized to group 8 bits and to write the corresponding byte to the file each at a time.
4. In decoding process, a queue is utilized to reconstruct the binary sequence of 8 bits from the read-in byte each at a time.
5. The compressed file contains a pseudo EOF which marks the end of effective contents in a file and ignores the remaining bytes.
## Installation
Add (clone) this repo to your Java's project as a submodule under your Java resource folder and give a meaningful name. For example, add submodule in the libs directory and name it Huffman, using the url:
```bash
git submodule add https://github.com/Jackiebibili/Huffman_Coding.git libs/Huffman 
```
## Usage
### Encode a File (Data Compression)
1. Create an instance of the class `HuffmanEncode` by using the overloaded constructor, passing a file path as the argument.
2. Call the `encode` method in the `HuffmanEncode` with the compressed file path.
3. The compressed file will be saved in the file path you specified.
### Decode a File (Data Decompression)
1. Create an instance of the class `HuffmanDecode` by using the overloaded constructor, passing a file path as the argument.
2. Call the `decode` method in the `HuffmanDecode` with the extracted file path.
3. The extracted (original) file will be saved in the file path you specified.
## Requirements
Java 9 and above

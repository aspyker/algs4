package org.spyker.netflixtest;

public class Chapter1Problem6 {
    private static int N = 3;
    
    class Pixel {
        public byte[] bytes;
        
        public Pixel(byte[] inBytes) {
            assert(inBytes.length == 4);
            bytes = new byte[inBytes.length];
            System.arraycopy(inBytes, 0, bytes, 0, inBytes.length);
        }
        
        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append('[');
            for (int ii = 0; ii < bytes.length; ii++) {
                buf.append((char)bytes[ii]);
                if (ii != bytes.length - 1) {
                    buf.append(", ");
                }
            }
            return buf.append(']').toString();
        }
    }
    
    public static void main(String args[]) {
        new Chapter1Problem6().doTest();
    }
    
    private void doTest() {
        Pixel[][] pixels = new Pixel[N][N];
        byte[] bytes1 = {'1', '1', '1', '1'};
        pixels[0][0] = new Pixel(bytes1);
        byte[] bytes2 = {'2', '2', '2', '2'};
        pixels[0][1] = new Pixel(bytes2);
        byte[] bytes3 = {'3', '3', '3', '3'};
        pixels[0][2] = new Pixel(bytes3);
        
        byte[] bytes4 = {'4', '4', '4', '4'};
        pixels[1][0] = new Pixel(bytes4);
        byte[] bytes5 = {'5', '5', '5', '5'};
        pixels[1][1] = new Pixel(bytes5);
        byte[] bytes6 = {'6', '6', '6', '6'};
        pixels[1][2] = new Pixel(bytes6);

        byte[] bytes7 = {'7', '7', '7', '7'};
        pixels[2][0] = new Pixel(bytes7);
        byte[] bytes8 = {'8', '8', '8', '8'};
        pixels[2][1] = new Pixel(bytes8);
        byte[] bytes9 = {'9', '9', '9', '9'};
        pixels[2][2] = new Pixel(bytes9);
        
        printArray(pixels);
        
        System.out.println();
        
        printArray(rotatePixels(pixels));
    }
    
    private void printArray(Pixel[][] pixels) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                System.out.print(pixels[row][col]);
            }
            System.out.println();
        }
    }
    
    private Pixel[][] rotatePixels(Pixel[][] old) {
        Pixel[][] newP = new Pixel[N][N];
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                int newRow = col;
                int newCol = N - 1 - row;
                newP[newRow][newCol] = old[row][col];
            }
        }
        return newP;
    }
}

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Main  {
    private static String word;
    private static byte[] wordBytes;
    private static int wordLength;

    private static void setWord(String w) {
        word = w;
        wordLength = word.length();
        wordBytes = new byte[wordLength];

        char[] arr = word.toLowerCase().toCharArray();
        for(int x = 0; x < arr.length; x++) {
            wordBytes[x] = (byte) arr[x];
        }
    }
    private static byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        return Files.readAllBytes(file.toPath());
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        setWord(args[0]);


        long start = System.nanoTime();
        byte [] fileBytes = readFile(args[1]);
        Set<String> set = split(fileBytes);
        long end = (System.nanoTime() - start) / 1000;
        System.out.println(end + " " + set);



    }
    private static Set<String> split(byte[] fileBytes) throws InterruptedException {
        int start = 0;
        int end = fileBytes.length/4;
        ArrayList<Anagrams> anagrms = new ArrayList<>();
        while (true){
            if(fileBytes.length < end) break;
            if (fileBytes[end] == 10) {
                Anagrams thread = new Anagrams(wordBytes,wordLength,fileBytes,end,start);
                thread.start();
                anagrms.add(thread);
                end+= fileBytes.length/4;
                start+= fileBytes.length/4;
            }
            end--;
        }
        Set<String> words = new HashSet<>();
        for (int x = 0; x < 4; x++){
            Anagrams thread = anagrms.get(x);
            thread.join();
            words.addAll(thread.getWords());
        }
        return words;
    }

}


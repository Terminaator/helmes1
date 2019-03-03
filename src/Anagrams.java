import java.util.*;

public class Anagrams extends Thread {

    private final byte[] word;
    private final int length;
    private final byte[] fileBytes;
    private final int end;
    private final int start;
    private Set<String> words = new HashSet<>();

    public Anagrams(byte[] word, int length,byte[] fileBytes, int end,int start){
        this.word = word;
        this.length = length;
        this.fileBytes = fileBytes;
        this.end = end;
        this.start = start;
    }
    public void run() {
        findAnagrams();
    }

    private void findAnagrams() {
        int x = 0+start;
        int y = 0;
        do {
            if (fileBytes[x] == 10){
                if (y-2 == length) {
                    byte[] wor = new byte[length];
                    for(int i = 0;i<y-2;i++){
                        wor[i] = fileBytes[x+i-5];
                    }
                    if(areEqual(wor,word)){
                        words.add(new String(wor));
                    }
                }
                y = 0;
            }
            x++;
            y++;
        }while (end > x);

    }

    private boolean areEqual(byte arr1[], byte arr2[])
    {

        int n = arr1.length;


        Map<Byte, Integer> map = new HashMap<>();
        int count;
        for (int i = 0; i < n; i++)
        {
            if(map.get(arr1[i]) == null)
                map.put(arr1[i], 1);
            else
            {
                count = map.get(arr1[i]);
                count ++;
                map.put(arr1[i], count);
            }
        }


        for (int i = 0; i < n; i++)
        {

            if (!map.containsKey(arr2[i]))
                return false;


            if (map.get(arr2[i]) == 0)
                return false;

            count = map.get(arr2[i]);
            --count;
            map.put(arr2[i], count);
        }


        for(int i = 0; i < n; i++)
        {
            if(map.get(arr2[i]) > 0)
                return false;
        }
        return true;
    }

    public Set<String> getWords() {
        return words;
    }
}

package _01_BasicsAndMath.Hashing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupAnagrams {
    // TC: O(n*k)
    // SC: O(n*k)
    // n -> no of words
    // k -> max size of a word
    public List<List<String>> groupAnagrams(String[] words){
        var map=new HashMap<String,List<String>>();
        int[] frequency;
        StringBuilder sb;
        for(String word: words){
            frequency=new int[26];
            sb=new StringBuilder();
            for(int i=0;i<word.length();++i)
                frequency[word.charAt(i)-'a']++;
            for(int i=0;i<26;++i)
                if(frequency[i]>0)
                    sb.append((char)('a'+i)).append(frequency[i]);
            String key = sb.toString();
            map.computeIfAbsent(key, k-> new ArrayList<>()).add(word);
        }
        return new ArrayList<>(map.values());
    }
}

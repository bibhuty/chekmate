# [Group Anagrams](https://leetcode.com/problems/group-anagrams/)
**Category:** Basic Hashing

## 1. Interview Checklist & Edge Cases
- **Empty input:** `strs = [""]` $\rightarrow$ `[[""]]`
- **Single character:** `strs = ["a"]` $\rightarrow$ `[["a"]]`
- **The "Delimiter" Trap:** If you create a key using just frequency numbers (e.g., `1010`), it can overlap. 10 'a's and 1 'b' vs 1 'a' and 1 'c'. Always include the character as a delimiter (e.g., `a10b1`).

## 2. Frequency Map Solution (Optimal)
Instead of sorting each string (which takes $O(K \log K)$), we count the characters in $O(K)$ time and build a unique string signature for the anagram group (e.g., "eat" $\rightarrow$ `"a1e1t1"`).

**Time:** $O(N \times K)$ where $N$ is the number of words, and $K$ is the max length of a word.
**Space:** $O(N \times K)$ to store the Map and the result array.

```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            int[] count = new int[26];
            for (int i = 0; i < str.length(); ++i) {
                count[str.charAt(i) - 'a']++;
            }
            
            // Build the unique key (e.g., "eat" -> "a1e1t1")
            // Use StringBuilder instead of StringBuffer for faster, non-synchronized appending
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < 26; ++i) {
                if (count[i] > 0) {
                    keyBuilder.append((char)('a' + i)).append(count[i]);
                }
            }
            
            String key = keyBuilder.toString();
            
            // computeIfAbsent is the cleanest and fastest way to initialize/add to lists in Maps
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
}
```

## 3. The Sorting Baseline (For Discussion)
If asked for a simpler approach, you can sort each string. "eat" and "tea" both sort to "aet".
**Time:** $O(N \times K \log K)$ — Slower, but uses slightly less auxiliary space since we don't build custom strings.

```java
// Inside the loop:
char[] chars = str.toCharArray();
Arrays.sort(chars);
String key = new String(chars);
map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
```
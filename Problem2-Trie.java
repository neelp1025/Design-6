// Time Complexity : O(N log 3) where N is the number of matching string of average k length
// Space Complexity : O(ML)^2 where M is the number of unique strings and L is the length of strings
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Add all search strings in a hashmap with its count as values. Create a trie for all sentences with each node storing all sentences starting with that character.
 * When the input method is called with a character, find the associated sentences from trie. Put the first 3 sentences based on count and lexicopgraphical rules.
 * When the input method is called with #, add the current search string into the map, trie and reset the search string..
 */
class AutocompleteSystem {
    class TrieNode {
        // save all strings starting with this character
        Set<String> startsWith;
        HashMap<Character, TrieNode> children;

        public TrieNode() {
            this.startsWith = new HashSet<>();
            children = new HashMap<>();
        }
    }

    private TrieNode root;

    public void addToTrie(String sentence) {
        TrieNode curr = root;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (!curr.children.containsKey(ch)) {
                curr.children.put(ch, new TrieNode());
            }
            curr = curr.children.get(ch);
            curr.startsWith.add(sentence);
        }
    }

    public Set<String> search(String sentence) {
        TrieNode curr = root;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (!curr.children.containsKey(ch)) return new HashSet<>();
            curr = curr.children.get(ch);
        }

        return curr.startsWith;
    }

    StringBuilder currStr = new StringBuilder();
    Map<String, Integer> countMap = new HashMap<>();

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = new TrieNode();
        for (int i = 0; i < sentences.length; i++) {
            String str = sentences[i];
            countMap.put(str, countMap.getOrDefault(str, 0) + times[i]);
            addToTrie(str);
        }
    }

    public List<String> input(char c) {
        // finalize the search string, add the search string to map, add it to trie, reset the search string
        if (c == '#') {
            String searchStr = currStr.toString();
            countMap.put(searchStr, countMap.getOrDefault(searchStr, 0) + 1);
            addToTrie(searchStr);
            currStr = new StringBuilder();
            return new ArrayList<>();
        }

        currStr.append(c);
        String searchStr = currStr.toString();
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
            int counta = countMap.get(a);
            int countb = countMap.get(b);
            if (counta == countb) {
                return b.compareTo(a);
            }

            return counta - countb;
        });

        // find all string starting with the search string and add the response to min heap with size 3 so that first 3 results can be returned
        Set<String> searchResult = search(searchStr);
        for (String sentence : searchResult) {
            pq.add(sentence);
            if (pq.size() > 3) {
                pq.poll();
            }
        }

        // add the result at 0th index via polling elements from queue so that they are in descending order
        List<String> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(0, pq.poll());
        }

        return res;
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
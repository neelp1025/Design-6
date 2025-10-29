// Time Complexity : O(L log 3) where L is the average length of the sentence
// Space Complexity : O(ML) where M is the number of unique strings and L is the length of strings
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Add all search strings in a hashmap with its count as values. Create a trie with each node storing top 3 sentences starting with that character based on count and lexicographical order.
 * When the input method is called with a character, find the associated sentences from trie and return the response.
 * When the input method is called with #, add the current search string into the map, update the trie and possibly top 3 for each node in the hierarchy
 */
class AutocompleteSystem {
    class TrieNode {
        List<String> top3;
        HashMap<Character, TrieNode> children;

        public TrieNode() {
            this.top3 = new ArrayList<>();
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
            List<String> li = curr.top3;
            if (!li.contains(sentence))
                li.add(sentence);

            // store the top 3 results in the trie when adding a new sentence
            Collections.sort(li, (a, b) -> {
                int cnta = countMap.get(a);
                int cntb = countMap.get(b);
                if (cnta == cntb) {
                    return a.compareTo(b);
                }

                return cntb - cnta;
            });

            if (li.size() > 3)
                li.remove(3);
        }
    }

    public List<String> search(String sentence) {
        TrieNode curr = root;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (!curr.children.containsKey(ch))
                return new LinkedList<>();
            curr = curr.children.get(ch);
        }

        // the result is already sorted when it was added, so directly return
        return curr.top3;
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
        if (c == '#') {
            String searchStr = currStr.toString();
            countMap.put(searchStr, countMap.getOrDefault(searchStr, 0) + 1);
            addToTrie(searchStr);
            currStr = new StringBuilder();
            return new ArrayList<>();
        }

        currStr.append(c);
        String searchStr = currStr.toString();

        return search(searchStr);
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */
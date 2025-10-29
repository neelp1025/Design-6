// Time Complexity : O(N log 3) where N is the number of matching string of k length
// Space Complexity : O(N) where N is the number of strings of k average length
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Add all search strings in a hashmap with its count as values.
 * When the input method is called with a character, iterate over the keyset of map and find all strings that start with the current search string. Add the result to priority queue to find top 3 results. If there is a tie, return the first lexicographic string.
 * When the input method is called with #, add the current search string into the map and reset the search string.
 */
class AutocompleteSystem {
    StringBuilder currStr = new StringBuilder();
    Map<String, Integer> countMap = new HashMap<>();

    public AutocompleteSystem(String[] sentences, int[] times) {

        for (int i = 0; i < sentences.length; i++) {
            String str = sentences[i];
            countMap.put(str, countMap.getOrDefault(str, 0) + times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            // end current search string, add it to the search map, reset the search string
            String searchStr = currStr.toString();
            countMap.put(searchStr, countMap.getOrDefault(searchStr, 0) + 1);
            currStr = new StringBuilder();
            return new ArrayList<>();
        }

        currStr.append(c);
        String searchStr = currStr.toString();
        // create min heap for returning the result with top 3 results
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> {
            int counta = countMap.get(a);
            int countb = countMap.get(b);
            if (counta == countb) {
                return b.compareTo(a);
            }

            return counta - countb;
        });

        // if the sentence starts with search string, add it to priority queue
        for (String sentence : countMap.keySet()) {
            if (sentence.startsWith(searchStr)) {
                pq.add(sentence);
                if (pq.size() > 3) {
                    pq.poll();
                }
            }
        }
        List<String> res = new ArrayList<>();
        // add the result in 0th position so it will be in descending order
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
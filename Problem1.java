// Time Complexity : O(1) for all operations
// Space Complexity : O(n) where n is the max number
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Initialize all numbers in constructors in a list. When a new number is needed, remove one number from list and add it to used set.
 * Check the used set to check if the number is used.
 * When number is released, remove it from used set and add it back to the list.
 */
class PhoneDirectory {
    LinkedList<Integer> nums = new LinkedList<>();
    Set<Integer> set = new HashSet<>();

    public PhoneDirectory(int maxNumbers) {
        for (int i = 0; i < maxNumbers; i++) {
            nums.add(i);
        }
    }

    public int get() {
        if (nums.isEmpty())
            return -1;

        int curr = nums.removeFirst();
        set.add(curr);
        return curr;
    }

    public boolean check(int number) {
        return !set.contains(number);
    }

    public void release(int number) {
        if (!set.contains(number))
            return;

        set.remove(number);
        nums.addLast(number);
    }
}

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
class Solution {
    public int missingInteger(int[] nums) {

        boolean[] present = new boolean[52];

        
        for (int num : nums) {
            present[num] = true;
        }

        
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        
        int x = sum;

        while (x <= 50 && present[x]) {
            x++;
        }

        return x;
    }
}
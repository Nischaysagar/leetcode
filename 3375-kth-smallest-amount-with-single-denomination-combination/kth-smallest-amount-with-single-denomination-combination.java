import java.util.*;

class Solution {

    long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    long count(long x, int[] coins) {
        int n = coins.length;
        long result = 0;

        // Every non-empty subset
        for (int mask = 1; mask < (1 << n); mask++) {

            long currentLCM = 1;
            int bits = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {
                    bits++;

                    long g = gcd(currentLCM, coins[i]);

                    // Prevent LCM overflow
                    long value = currentLCM / g;

                    if (value > x / coins[i]) {
                        overflow = true;
                        break;
                    }

                    currentLCM = value * coins[i];
                }
            }

            if (overflow || currentLCM > x)
                continue;

            long contribution = x / currentLCM;

            if ((bits & 1) == 1)
                result += contribution;
            else
                result -= contribution;
        }

        return result;
    }

    public long findKthSmallest(int[] coins, int k) {

        // Remove redundant denominations
        Arrays.sort(coins);

        ArrayList<Integer> useful = new ArrayList<>();

        for (int coin : coins) {

            boolean redundant = false;

            for (int x : useful) {
                if (coin % x == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant)
                useful.add(coin);
        }

        int[] arr = new int[useful.size()];

        for (int i = 0; i < useful.size(); i++) {
            arr[i] = useful.get(i);
        }

        /*
         * The answer cannot exceed k * smallest coin.
         */
        long low = 1;
        long high = (long) arr[0] * k;

        while (low < high) {

            long mid = low + (high - low) / 2;

            if (count(mid, arr) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
}
package com.rahulografy.dsalgokotlin.datastructures.priorityqueue

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.Collections
import java.util.PriorityQueue

class MinDHeapTest {
    @Before
    fun setup() {
    }

    @Test
    fun testEmpty() {
        val q = MinDHeap<Int>(4, 0)
        Truth.assertThat(q.size()).isEqualTo(0)
        Truth.assertThat(q.isEmpty).isTrue()
        Truth.assertThat(q.poll()).isNull()
        Truth.assertThat(q.peek()).isNull()
    }

    @Test
    fun testHeapProperty() {
        val q = MinDHeap<Int>(3, 30)
        val nums = arrayOf(3, 2, 5, 6, 7, 9, 4, 8, 1)

        // Try manually creating heap
        for (n in nums) q.add(n)
        for (i in 1..9) Truth.assertThat(q.poll()).isEqualTo(i)
    }

    @Test
    fun testPriorityQueueSizeParam() {
        for (i in 1 until LOOPS) {
            val lst = genRandArray(i)
            val pq = MinDHeap<Int>(i, lst.size)
            val pq2 = PriorityQueue<Int>(i)
            for (x in lst) {
                pq2.add(x)
                pq.add(x)
            }
            while (!pq2.isEmpty()) Truth.assertThat(pq.poll()).isEqualTo(pq2.poll())
        }
    }

    @Test
    fun testPriorityRandomOperations() {
        for (loop in 0 until LOOPS) {
            var p1 = Math.random()
            var p2 = Math.random()
            if (p2 < p1) {
                val tmp = p1
                p1 = p2
                p2 = tmp
            }
            val ar = genRandArray(LOOPS)
            val d = 2 + (Math.random() * 6).toInt()
            val pq = MinDHeap<Int>(d, LOOPS)
            val pq2 = PriorityQueue<Int>(LOOPS)
            for (i in 0 until LOOPS) {
                val e = ar[i]
                val r = Math.random()
                if (0 <= r && r <= p1) {
                    pq.add(e)
                    pq2.add(e)
                } else if (p1 < r && r <= p2) {
                    if (!pq2.isEmpty()) Truth.assertThat(pq.poll()).isEqualTo(pq2.poll())
                } else {
                    pq.clear()
                    pq2.clear()
                }
            }
            Truth.assertThat(pq.peek()).isEqualTo(pq2.peek())
        }
    }

    @Test
    fun testClear() {
        val strs = arrayOf("aa", "bb", "cc", "dd", "ee")
        val q = MinDHeap<String>(2, strs.size)
        for (s in strs) q.add(s)
        q.clear()
        Truth.assertThat(q.size()).isEqualTo(0)
        Truth.assertThat(q.isEmpty).isTrue()
    }

    /*
  @Test
  public void testContainmentRandomized() {

    for (int i = 0; i < LOOPS; i++) {

      List <Integer> randNums = genRandList(100);
      PriorityQueue <Integer> PQ = new PriorityQueue<>();
      PQueue <Integer> pq = new PQueue<>();
      for (int j = 0; j < randNums.size(); j++) {
        pq.add(randNums.get(j));
        PQ.add(randNums.get(j));
      }

      for (int j = 0; j < randNums.size(); j++) {

        int randVal = randNums.get(j);
        assertThat( pq.contains(randVal)).isEqualTo(PQ.contains(randVal) );
        pq.remove(randVal); PQ.remove(randVal);
        assertThat( pq.contains(randVal)).isEqualTo(PQ.contains(randVal) );

      }

    }

  }

  public void sequentialRemoving(Integer[] in, Integer[] removeOrder) {

    assertThat(in.length, removeOrder.length);

    PQueue <Integer> pq = new PQueue<>(in);
    PriorityQueue <Integer> PQ = new PriorityQueue<>();
    for (int value : in) PQ.offer(value);

    assertThat(pq.isMinHeap(0)).isTrue();

    for (int i = 0; i < removeOrder.length; i++) {

      int elem = removeOrder[i];

      assertThat(pq.peek()).isEqualTo(PQ.peek());
      assertThat( pq.remove(elem)).isEqualTo(PQ.remove(elem));
      assertThat(pq.size()).isEqualTo(PQ.size());
      assertThat(pq.isMinHeap(0)).isTrue();

    }

    assertThat(pq.isEmpty()).isTrue();

  }

  @Test
  public void testRemoving() {

    Integer [] in = {1,2,3,4,5,6,7};
    Integer [] removeOrder = { 1,3,6,4,5,7,2 };
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {1,2,3,4,5,6,7,8,9,10,11};
    removeOrder = new Integer[] {7,4,6,10,2,5,11,3,1,8,9};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {8, 1, 3, 3, 5, 3};
    removeOrder = new Integer[] {3,3,5,8,1,3};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {7, 7, 3, 1, 1, 2};
    removeOrder = new Integer[] {2, 7, 1, 3, 7, 1};
    sequentialRemoving(in, removeOrder);

    in = new Integer[] {32, 66, 93, 42, 41, 91, 54, 64, 9, 35};
    removeOrder = new Integer[] {64, 93, 54, 41, 35, 9, 66, 42, 32, 91};
    sequentialRemoving(in, removeOrder);

  }
  */
    @Test
    fun testRemovingDuplicates() {
        val `in` = arrayOf(2, 7, 2, 11, 7, 13, 2)
        val pq = MinDHeap<Int>(3, `in`.size + 1)
        for (x in `in`) pq.add(x)
        Truth.assertThat(pq.peek()).isEqualTo(2)
        pq.add(3)
        Truth.assertThat(pq.poll()).isEqualTo(2)
        Truth.assertThat(pq.poll()).isEqualTo(2)
        Truth.assertThat(pq.poll()).isEqualTo(2)
        Truth.assertThat(pq.poll()).isEqualTo(3)
        Truth.assertThat(pq.poll()).isEqualTo(7)
        Truth.assertThat(pq.poll()).isEqualTo(7)
        Truth.assertThat(pq.poll()).isEqualTo(11)
        Truth.assertThat(pq.poll()).isEqualTo(13)
    }

    companion object {
        const val LOOPS = 1000
        const val MAX_SZ = 100

        /*
  @Test
  public void testRandomizedPolling() {

    for (int i = 0; i < LOOPS; i++) {

      int size = i;
      List <Integer> randNums = genRandList(size);
      PriorityQueue <Integer> pq1 = new PriorityQueue<>();
      PQueue <Integer> pq2 = new PQueue<>();

      // Add all the elements to both priority queues
      for (Integer value : randNums) {
        pq1.offer(value);
        pq2.add(value);
      }

      while( !pq1.isEmpty() ) {

        assertThat(pq2.isMinHeap(0)).isTrue();
        assertThat(pq1.size()).isEqualTo(pq2.size());
        assertThat(pq1.peek()).isEqualTo(pq2.peek());
        assertThat(pq1.contains(pq1.peek())).isEqualTo(pq2.contains(pq2.peek()));

        Integer v1 = pq1.poll();
        Integer v2 = pq2.poll();

        assertThat(v1).isEqualTo(v2);
        assertThat(pq1.peek()).isEqualTo(pq2.peek());
        assertThat(pq1.size()).isEqualTo(pq2.size());
        assertThat(pq2.isMinHeap(0)).isTrue();

      }

    }

  }

  @Test
  public void testRandomizedRemoving() {

    for (int i = 0; i < LOOPS; i++) {

      int size = i;
      List <Integer> randNums = genRandList(size);
      PriorityQueue <Integer> pq1 = new PriorityQueue<>();
      PQueue <Integer> pq2 = new PQueue<>();

      // Add all the elements to both priority queues
      for (Integer value : randNums) {
        pq1.offer(value);
        pq2.add(value);
      }

      Collections.shuffle(randNums);
      int index = 0;

      while( !pq1.isEmpty() ) {

        int removeNum = randNums.get(index++);

        assertThat(pq2.isMinHeap(0)).isTrue();
        assertThat( pq1.size()).isEqualTo(pq2.size());
        assertThat( pq1.peek()).isEqualTo(pq2.peek());
        pq1.remove(removeNum); pq2.remove(removeNum);
        assertThat( pq1.peek()).isEqualTo(pq2.peek());
        assertThat( pq1.size()).isEqualTo(pq2.size());
        assertThat(pq2.isMinHeap(0)).isTrue();

      }

    }

  }

  @Test
  public void testPQReusability() {

    List <Integer> SZs = genUniqueRandList(LOOPS);

    PriorityQueue <Integer> PQ = new PriorityQueue<>();
    PQueue <Integer> pq = new PQueue<>();

    for (int size : SZs) {

      pq.clear();
      PQ.clear();

      List <Integer> nums = genRandList(size);
      for (int n : nums) {
        pq.add(n);
        PQ.add(n);
      }

      Collections.shuffle(nums);

      for (int i = 0; i < size/2; i++) {

        // Sometimes add a new number into the Pqueue
        if (0.25 < Math.random()) {
          int randNum = (int) (Math.random() * 10000);
          PQ.add(randNum);
          pq.add(randNum);
        }

        int removeNum = nums.get(i);

        assertThat(pq.isMinHeap(0)).isTrue();
        assertThat( PQ.size()).isEqualTo(pq.size());
        assertThat( PQ.peek().isEqualTo(pq.peek());

        PQ.remove(removeNum);
        pq.remove(removeNum);

        assertThat( PQ.peek().isEqualTo(pq.peek());
        assertThat( PQ.size().isEqualTo(pq.size());
        assertThat(pq.isMinHeap(0)).isTrue();

      }

    }

  }
  */
        fun genRandArray(size: Int): Array<Int?> {
            val lst = arrayOfNulls<Int>(size)
            for (i in 0 until size) lst[i] = (Math.random() * MAX_SZ).toInt()
            return lst
        }

        // Generate a list of random numbers
        fun genRandList(size: Int): List<Int> {
            val lst: MutableList<Int> = ArrayList(size)
            for (i in 0 until size) lst.add((Math.random() * MAX_SZ).toInt())
            return lst
        }

        // Generate a list of unique random numbers
        fun genUniqueRandList(size: Int): List<Int?> {
            val lst: MutableList<Int?> = ArrayList(size)
            for (i in 0 until size) lst.add(i)
            Collections.shuffle(lst)
            return lst
        }
    }
}

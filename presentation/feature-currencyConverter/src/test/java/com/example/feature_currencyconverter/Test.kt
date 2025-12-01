package com.example.feature_currencyconverter

import com.example.domain.model.ConversionResult
import io.mockk.coEvery
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import viewmodel.ConversionIntent
import kotlin.math.abs
import kotlin.math.max
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

/*
Length of the longest substring
e.g.

1)
Input: s = “aaabccbbb”
Output: 3

Explanation: The answer is “abc”, with the length of 3.

2)
Input: s = “pay2paypay”
Output: 4

Explanation: The answer is “pay2” or “ay2p” or “y2pa” or  “2pay” with the length of 4.

3)
Input: s = “ccccc”
Output: 1

Explanation: The answer is “c”, with the length of 1.

4)
Input: s = "pwwkew"
Output: 3

Explanation: The answer is "wke", with the length of 3.

Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.


Constraints:

* 0 <= s.length <= 5 * 104
* s consists of English letters, digits, symbols and spaces.
 */

class Test {

    @Test
    fun `handleIntent Entered Amount Manipulation`() = runTest {
        assertContentEquals(arrayOf(0,1,2,3,4),testBfs(arrayOf(
            intArrayOf(1,2),
            intArrayOf(0,2,3),
            intArrayOf(0,1,4),
            intArrayOf(1,4),
            intArrayOf(2,3)
        )))
        //assertEquals(4,test("pay2paypay"))
        //assertEquals(1,test("ccccc"))
        //assertEquals(3,test("pwwkew"))

    }

    fun test(arr: Array<IntArray>) : Array<Int> {
        val list = mutableListOf<Int>()
        val visited = BooleanArray(arr.size){ false }
        for(i in arr.indices){
            if(!visited[i]){
                dfs(i , arr, visited, list)
            }
        }

        return list.toTypedArray()
    }

    fun testBfs(arr: Array<IntArray>) : Array<Int>{
        val queue = ArrayDeque<Int>()
        val visited = BooleanArray(arr.size)
        val list = mutableListOf<Int>()
        queue.addLast(0)
        visited[0] = true
        while(!queue.isEmpty()){
            val edge = queue.removeFirst()
            list.add(edge)
            for(i in arr[edge]){
                if(!visited[i]){
                    visited[i] = true
                    queue.addLast(i)
                }
            }
        }

        return list.toTypedArray()
    }

    private fun bfs(i : Int){


    }

    private fun dfs(i : Int, arr: Array<IntArray>, visited: BooleanArray, list: MutableList<Int>){

        if(visited[i]) return

        visited[i] = true
        list.add(i)

        for(j in 0 until arr[i].size){
            dfs(arr[i][j], arr, visited , list)
        }
    }

    private fun numberOfIslands(arr : Array<CharArray>){
        var islandCount = 0
        for(i in 0 until arr.size){
            for(j in 0 .. arr[0].size){
                if(arr[i][j] == '1'){
                    islandCount ++
                    dfsIsland(i,j,arr)
                }
            }
        }
    }

    private fun dfsIsland(i : Int, j:Int,arr : Array<CharArray>){
        if(i < 0 || j <0 || i >= arr.size || j >= arr[0].size || arr[i][j] == '0'){
            return
        }

        arr[i][j] = '0'
        dfsIsland(i+1,j, arr)
        dfsIsland(i-1,j, arr)
        dfsIsland(i,j+1, arr)
        dfsIsland(i,j-1, arr)

    }
}




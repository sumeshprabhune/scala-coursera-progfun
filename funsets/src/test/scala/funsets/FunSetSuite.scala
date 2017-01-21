package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
    val s5 = singletonSet(5)
    val s6 = singletonSet(6)
    val s3_1 =singletonSet(3) 
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */

  
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
   
  test("intersection contains one element") {
    new TestSets {
      val s = union(s1, s3)
      val t = union(s4, s3_1)
      val u = intersect(s, t)
      assert(!contains(u, 1), "Intersection 1")
      assert(!contains(u, 2), "Intersection 2")
      assert(contains(u, 3), "Intersection 3")
      
      val v = intersect(s1, s1)
      assert(contains(v, 1), "Intersection 1")
      assert(!contains(v, 2), "Intersection 2")
      assert(!contains(v, 3), "Intersection 3")
    }
  }
  
  test("intersection contains no element") {
    new TestSets {
      val s = union(s1, s3)
      val t = union(s4, s5)
      val u = intersect(s, t)
      assert(!contains(u, 1), "Intersection 1")
      assert(!contains(u, 2), "Intersection 2")
      assert(!contains(u, 3), "Intersection 3")
      assert(!contains(u, 4), "Intersection 3")
      assert(!contains(u, 5), "Intersection 3")
    }
  }
  
  test("intersection contains more than one element") {
    new TestSets {
      val s = union(union(s1, s2),s3)
      val t = union(s1,s2)
      val u = intersect(s, t)
      assert(contains(u, 1), "Intersection 1")
      assert(contains(u, 2), "Intersection 2")
      assert(!contains(u, 3), "Intersection 3")
    }
  }
  
   
  test("Difference contains one element") {
    new TestSets {
      val s = union(union(s1, s2),s3)
      val t = union(s1,s2)
      val u = diff(s, t)
      assert(!contains(u, 1), "Intersection 1")
      assert(!contains(u, 2), "Intersection 2")
      assert(contains(u, 3), "Intersection 3")
    }
  }
  
  test("Difference contains more than one element") {
    new TestSets {
      val s = union(union(s1, s2),s3)
      val t = union(s1,s4)
      val u = diff(s, t)
      assert(!contains(u, 1), "Intersection 1")
      assert(contains(u, 2), "Intersection 2")
      assert(contains(u, 3), "Intersection 3")
      assert(!contains(u, 4), "Intersection 3")
    }
  }
  
  test("Difference contains no element") {
    new TestSets {
      val s = union(union(s1, s2),s3)
      val t = union(union(s1, s2),s3_1)
      val u = diff(s, t)
      assert(!contains(u, 1), "Intersection 1")
      assert(!contains(u, 2), "Intersection 2")
      assert(!contains(u, 3), "Intersection 3")
    }
  }
  
  
    
  test("Filter returns one element") {
    new TestSets {
      val s = union(union(s1, s2),s3)
      val p = (x : Int) => x >= 3
      val u = filter(s, p)
      assert(!contains(u, 1), "Intersection 1")
      assert(!contains(u, 2), "Intersection 2")
      assert(contains(u, 3), "Intersection 3")
    }
  }
  
  test("Filter returns more than one element") {
    new TestSets {
      
      val s = union(union(s1, s2),s3)
      val p = (x : Int) => x%2 == 1
      
      val t = filter(s,p)
      assert(contains(t, 1), "Intersection 1")
      assert(!contains(t, 2), "Intersection 2")
      assert(contains(t, 3), "Intersection 3")
      assert(!contains(t, 4), "Intersection 3")
    }
  }
  
  test("Filter returns no element") {
    new TestSets {
      val s = union(union(union(s1, s2),s3),s4)
      val p = (x : Int) => x > 4
      val u = filter(s, p)
      assert(!contains(u, 1), "Filter 1")
      assert(!contains(u, 2), "Filter 2")
      assert(!contains(u, 3), "Filter 3")
      assert(!contains(u, 4), "Filter 4")
    }
  }
  
  test("Check if all values are odd") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val p = (x : Int) => x%2 == 1
      val u = forall(s, p)
      assert(u)
    }
  }
    
  test("Check if all values have remainder of divide by 4 eq 1") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val p = (x : Int) => x%4 == 1
      val u = forall(s, p)
      assert(!u)
    }
  }
    
    test("Check if any value is odd") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val p = (x : Int) => x%2 == 1
      val u = exists(s, p)
      assert(u)
    }
  }
    
  test("Check if any value has remainder of divide by 4 eq 1") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val p = (x : Int) => x%4 == 1
      val u = exists(s, p)
      assert(u)
    }
  }
  
   test("Check if any value has value gt 5") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val p = (x : Int) => x > 5
      val u = exists(s, p)
      assert(!u)
    }
  }
   
    
   test("Map set to the negative of each of its value ") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val f = (x : Int) => -x
      val u = map(s, f)
      assert(!contains(u, 1), "Filter 1")
      assert(!contains(u, 2), "Filter 2")
      assert(!contains(u, 3), "Filter 3")
      assert(!contains(u, 4), "Filter 4")
      assert(!contains(u, 4), "Filter 5")
      assert(contains(u, -1), "Filter -1")
      assert(contains(u, -3), "Filter -3")
      assert(contains(u, -5), "Filter -5")
    }
  }
    
  test("Map set to twice of each of its value") {
    new TestSets {
      val s = union(union(s1, s3),s5)
      val f = (x : Int) => 2 * x
      val u = map(s, f)
      assert(!contains(u, 1), "Filter 1")
      assert(contains(u, 2), "Filter 2")
      assert(!contains(u, 3), "Filter 3")
      assert(!contains(u, 4), "Filter 4")
      assert(!contains(u, 5), "Filter 5")
     assert(contains(u, 6), "Filter 6")
      assert(contains(u, 10), "Filter 10")
    }
  }
  
  test("Map set to the square of each of its value") {
    new TestSets {
      val s = union(union(union(s1, s3),s4),s5)
      val f = (x : Int) => x * x
      val u = map(s, f)
      assert(contains(u, 1), "Filter 1")
      assert(!contains(u, 2), "Filter 2")
      assert(!contains(u, 3), "Filter 3")
      assert(!contains(u, 4), "Filter 4")
      assert(!contains(u, 5), "Filter 5")
      assert(contains(u, 9), "Filter 9")
      assert(contains(u, 16), "Filter 6")
      assert(contains(u, 25), "Filter 10")
    }
  }
  
  
}

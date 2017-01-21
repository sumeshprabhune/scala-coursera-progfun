package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(c ==0 || c == r)
        return 1
      else
       return pascal(c-1,r-1) + pascal(c,r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      
      def balanceHelper(cnt : Int , chars: List[Char]) : Boolean = {
        
        if(chars.isEmpty)
            return cnt == 0
        else
          if(chars.head == ')')
             if(cnt == 0)
               return false;
             else
               balanceHelper(cnt -1 , chars.tail)
          else if(chars.head == '(')
             balanceHelper(cnt + 1 , chars.tail)
          else
             balanceHelper(cnt , chars.tail)
        
      }
      
      balanceHelper(0, chars);
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if(money == 0)
        return 1
      else if(money < 0 || coins.isEmpty)
        return 0;
      else
        return countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
    
    
  }
